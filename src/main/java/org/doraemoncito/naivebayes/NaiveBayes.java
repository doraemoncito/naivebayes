/*
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 * Copyright (c) 2010-2015 Jose Hernandez
 */
package org.doraemoncito.naivebayes;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;

/**
 * Implementation of the Naive Bayes classification algorithm for multi-class newsgroup classification. This can be
 * turned into a spam detection algorithm by restricting it to just two classes.
 * <p>
 * The client must first train the algorithm by invoking the learn method and passing in a training data set. Once the
 * algorithm has been trained we can invoke the classify method to predict the class of previously unseen instances.
 *
 * @author Jose Hernandez
 */
@Slf4j
public class NaiveBayes {

    // Statistical confidence intervals
    private static final float[][] CONFIDENCE_INTERVALS = {
            {90, (float) 1.83},
            {95, (float) 2.26},
            {98, (float) 2.82},
            {99, (float) 3.25}
    };

    private List<String> wordList;
    private List<String> classLabels;
    private float[][] probWordGivenClass;
    private float[] probClass;

    private static final int NUM_FOLDS = 10;

    private int numClassLabels;
    private int numWords;

    public NaiveBayes(final DataSet samples, final List<String> stopWords, final int pruneLevel, final boolean useStemmer) {

        float[] errorRate = new float[NUM_FOLDS];

        log.info("Shuffling samples...");
        final DataSet shuffledSamples = samples.shuffle();
        classLabels = shuffledSamples.classLabelList();

        DecimalFormat foldFormat = new DecimalFormat("00");

        for (int fold = 0; fold < NUM_FOLDS; fold++) {

            log.info("Running fold {}", foldFormat.format((long) fold + 1));
            log.info("===============");

            DataSet trainingSamples = shuffledSamples.getTrainingSet(fold);
            learn(trainingSamples, classLabels, stopWords, pruneLevel, useStemmer);

            // confusion matrix
            int[][] confMatrix = new int[numClassLabels][numClassLabels];

            DataSet testSamples = shuffledSamples.getTestSet(fold);
            int numTestSamples = testSamples.size();
            log.info("");
            log.info("Classifying {} test samples with Naive Bayes...", numTestSamples);

            // Classify the test samples and build a confusion matrix.
            for (int j = 0; j < numTestSamples; j++) {
                LabelledInstance exampleInstance = testSamples.getLabelledInstance(j);

                int predictedClassLabel = classify(exampleInstance, useStemmer);
                int actualClassLabel = classLabels.indexOf(exampleInstance.classLabel());

                confMatrix[actualClassLabel][predictedClassLabel]++;
            }

            // And now display the confusion matrix.
            errorRate[fold] = calculateConfussionMatrix(confMatrix);
        }

        calcErrorBounds(errorRate);
    }

    private void calcErrorBounds(final float[] errorRate) {

        DecimalFormat foldFormat = new DecimalFormat("00");
        DecimalFormat errorFormat = new DecimalFormat("0.0000");

        log.info("Calculating average error and error bounds...");
        log.info("fold    error rate");
        log.info("----    ----------");

        double errorMean = 0;

        for (int fold = 0; fold < NUM_FOLDS; fold++) {
            errorMean += errorRate[fold];
            log.info("  " + foldFormat.format((long) fold + 1) + "        " + errorFormat.format(errorRate[fold]));
        }
        errorMean /= NUM_FOLDS;
        log.info("----    ----------");
        log.info("mean error: " + errorFormat.format(errorMean));

        // calculate standard deviation from the mean
        double stdDev = 0;
        for (int fold = 0; fold < NUM_FOLDS; fold++)
            stdDev += Math.pow(errorRate[fold] - errorMean, 2);

        stdDev = Math.sqrt(stdDev / (NUM_FOLDS * (NUM_FOLDS - 1)));
        log.info("std. dev.:  " + errorFormat.format(stdDev));

        log.info("");
        log.info("Calculating confidence intervals...");

        for (float[] interval : CONFIDENCE_INTERVALS) {
            log.info("Confidence interval at " + foldFormat.format(interval[0]) + "% --> ["
                    + errorFormat.format(errorMean - (interval[1] * stdDev)) + " , "
                    + errorFormat.format(errorMean + (interval[1] * stdDev)) + "]");
        }
    }

    /**
     * Learn the model.
     *
     * @param samples Set of text documents along with their target values.
     * @param values  Set of all possible target values.
     */
    public void learn(final DataSet samples, final List<String> values, final List<String> stopWords, final int pruneLevel, final boolean useStemmer) {
        try {
            classLabels = values;
            DecimalFormat decimalFormat = new DecimalFormat("0.0000");
            int numSamples = samples.size();
            log.info("Learning Naive Bayes model... ({} training samples)", numSamples);
            log.info("Building vocabulary...");

            Vocabulary vocabulary = new Vocabulary(samples, stopWords, pruneLevel, useStemmer);
            wordList = vocabulary.getWordList();
            numWords = wordList.size();
            numClassLabels = classLabels.size();

            log.info("Vocabulary contains {} words", numWords);

            probWordGivenClass = new float[numClassLabels][numWords];
            probClass = new float[numClassLabels];

            log.info("Building probability matrix...");

            for (int idxClassLabel = 0; idxClassLabel < numClassLabels; idxClassLabel++) {

                // This is the subset of documents from samples where the target value is v_j.
                float docsInClass = samples.getLabelCount(classLabels.get(idxClassLabel));

                probClass[idxClassLabel] = (docsInClass != 0) ? (docsInClass / numSamples) : 0;

                DataSet classLabelSamples = samples.getClassLabelSet(classLabels.get(idxClassLabel));

                Vocabulary classVocabulary = new Vocabulary(classLabelSamples, stopWords, pruneLevel, useStemmer);

                /*
                 * n is the total number of distinct word positions in classVocabulary. In our case is given by the number of
                 * word times their frequency.
                 */
                float totalDistinctWordPositions = classVocabulary.totalCount();

                log.info("Class '{}': |docsj| = {}, P(vj) = {}, n = {} ({}  distinct words)",
                        classLabels.get(idxClassLabel),
                        (int) docsInClass,
                        decimalFormat.format(probClass[idxClassLabel]),
                        (int) totalDistinctWordPositions,
                        classVocabulary.size());

                // Number of words in the vocabulary.
                String word;
                float wordCount;

                for (int idxWord = 0; idxWord < numWords; idxWord++) {
                    // word represents each one of the words that occur in the vocabulary.
                    word = wordList.get(idxWord);

                    // wordCount is the number of times that word occurs in classVocabulary.
                    wordCount = classVocabulary.getWordFrequency(word);
                    probWordGivenClass[idxClassLabel][idxWord] = (wordCount + 1) / (totalDistinctWordPositions + numWords);
                }
            }
        } catch (IOException e) {
            log.error("An error was encountered whilst learning the model", e);
        }
    }

    /**
     * Return the estimated target value for the given document.
     *
     * @param labelledInstance document whose target value we want to estimate.
     * @return estimated target value.
     */
    public int classify(final LabelledInstance labelledInstance, final boolean useStemmer) {
        Map<String, Integer> docAttributes = new MessageTokenizer(useStemmer, labelledInstance.text()).getTokenList();
        double argMax = -Double.MAX_VALUE;

        int bestClassIndex = 0;
        String word;

        // For each class label (a.k.a. newsgroup)...
        for (int idxClass = 0; idxClass < numClassLabels; idxClass++) {
            double posteriorProb = Math.log(probClass[idxClass]);

            /*
             * For each word in the document... (or rather the vocabulary!) We iterate the vocabulary and see if the
             * word is in the document because in this implementation it is far more efficient than iterating through
             * the document and then scanning the vocabulary.
             */
            for (int idxWord = 0; idxWord < numWords; idxWord++) {
                word = wordList.get(idxWord);
                Integer frequency = docAttributes.get(word);

                if (frequency != null) {
                    posteriorProb += Math.log(probWordGivenClass[idxClass][idxWord]) * frequency;
                }
            }

            if (posteriorProb > argMax) {
                argMax = posteriorProb;
                bestClassIndex = idxClass;
            }
        }

        return (bestClassIndex);
    }

    /**
     * Displays the confusion matrix and calculates the error rate for the given confusion matrix.
     *
     * @param confMatrix the confusion matrix containing the counts of predicted vs actual classifications
     * @return error rate given by the number of samples incorrectly classified divided by the total number of samples.
     */
    public float calculateConfussionMatrix(final int[][] confMatrix) {
        int n = confMatrix.length;
        int correct = 0;
        int total = 0;
        DecimalFormat decimalFormat = new DecimalFormat("00");

        log.info("Columns show predicted classification, rows show actual classification");

        StringBuilder line = new StringBuilder();
        StringBuilder line2 = new StringBuilder();
        for (int j = 0; j < n; j++) {
            line.append(" ").append(decimalFormat.format(j + 1)).append(" ");
            line2.append("----");
        }

        log.info("{} | predicted / actual ", line);
        log.info("{}-+--------------------", line2);

        for (int j = 0; j < n; j++) {
            line = new StringBuilder();
            for (int k = 0; k < n; k++) {
                if (j == k) {
                    correct += confMatrix[j][k];
                    line.append("[").append(decimalFormat.format(confMatrix[j][k])).append("]");
                } else {
                    line.append(" ").append(decimalFormat.format(confMatrix[j][k])).append(" ");
                }

                total += confMatrix[j][k];
            }

            log.info("{} | {} {}", line, decimalFormat.format(j + 1), classLabels.get(j));
        }
        // ...existing code...
        return ((float) (total - correct) / total);
    }

}

