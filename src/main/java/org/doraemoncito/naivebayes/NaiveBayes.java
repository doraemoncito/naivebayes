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

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Implementation of the Naive Bayes classification algorithm for multi-class newsgroup classification. This can be
 * turned into a spam detection algorithm by restricting it to just two classes.
 * 
 * The client must first train the algorithm by invoking the learn method and passing in a training data set. Once the
 * algorithm has been trained we can invoke the classify method to predict the class of previously unseen instances.
 * 
 * @author Jose Hernandez
 */
public class NaiveBayes {

	private static final Logger LOGGER = LoggerFactory.getLogger(NaiveBayes.class);

	// Statistical confidence intervals
	private static final float t_Nv9[][] = {
		{ 90, (float) 1.83 },
		{ 95, (float) 2.26 },
		{ 98, (float) 2.82 },
		{ 99, (float) 3.25 }
	};

	private Vocabulary vocabulary = null;
	private List<String> wordList = null;
	private List<String> classLabels = null;
	private float[][] m_P_wk_vj = null;
	private float[] m_P_vj = null;

	private final static int NUM_FOLDS = 10;

	private int numClassLabels;
	private int numWords;

	public NaiveBayes(final DataSet samples, final List<String> stopWords, final int pruneLevel, final boolean useStemmer) throws IOException {

		float[] errorRate = new float[NUM_FOLDS];

		LOGGER.info("Shuffling samples...");
		final DataSet shuffledSamples = samples.shuffle();
		classLabels = shuffledSamples.classLabelList();

		DecimalFormat foldFormat = new DecimalFormat("00");

		for (int fold = 0; fold < NUM_FOLDS; fold++) {

			LOGGER.info("Running fold {}", foldFormat.format(fold + 1));
			LOGGER.info("===============");

			DataSet trainingSamples = shuffledSamples.getTrainingSet(fold);
			learn(trainingSamples, classLabels, stopWords, pruneLevel, useStemmer);

			// confusion matrix
			int[][] confMatrix = new int[numClassLabels][numClassLabels];

			DataSet testSamples = shuffledSamples.getTestSet(fold);
			int numTestSamples = testSamples.size();
			LOGGER.info("");
			LOGGER.info("Classifying {} test samples with Naive Bayes...", numTestSamples);

			// Classify the test samples and build a confusion matrix.
			for (int j = 0; j < numTestSamples; j++) {
				LabelledInstance exampleInstance = testSamples.getLabelledInstance(j);

				int predictedClassLabel = classify(exampleInstance, useStemmer);
				int actualClassLabel = classLabels.indexOf(exampleInstance.getClassLabel());

				confMatrix[actualClassLabel][predictedClassLabel]++;
			}

			// And now display the confusion matrix.
			errorRate[fold] = calculateConfussionMatrix(confMatrix);
			// saveToFile("Predtable" + decimalFormat.format(fold) + ".csv", false);
		}

		calcErrorBounds(errorRate);
	}

	private void calcErrorBounds(final float[] errorRate) {

		DecimalFormat foldFormat = new DecimalFormat("00");
		DecimalFormat errorFormat = new DecimalFormat("0.0000");

		LOGGER.info("Calculating average error and error bounds...");
		LOGGER.info("fold    error rate");
		LOGGER.info("----    ----------");

		double errorMean = 0;

		for (int fold = 0; fold < NUM_FOLDS; fold++) {
			errorMean += errorRate[fold];
			LOGGER.info("  " + foldFormat.format(fold + 1) + "        " + errorFormat.format(errorRate[fold]));
		}
		errorMean /= NUM_FOLDS;
		LOGGER.info("----    ----------");
		LOGGER.info("mean error: " + errorFormat.format(errorMean));

		// calculate standard deviation from the mean
		double stdDev = 0;
		for (int fold = 0; fold < NUM_FOLDS; fold++)
			stdDev += Math.pow(errorRate[fold] - errorMean, 2);

		stdDev = Math.sqrt(stdDev / (NUM_FOLDS * (NUM_FOLDS - 1)));
		LOGGER.info("std. dev.:  " + errorFormat.format(stdDev));

		LOGGER.info("");
		LOGGER.info("Calculating confidence intervals...");

		for (int idxInterval = 0; idxInterval < t_Nv9.length; idxInterval++) {
			LOGGER.info("Confidence interval at " + foldFormat.format(t_Nv9[idxInterval][0]) + "% --> ["
					+ errorFormat.format(errorMean - (t_Nv9[idxInterval][1] * stdDev)) + " , "
					+ errorFormat.format(errorMean + (t_Nv9[idxInterval][1] * stdDev)) + "]");
		}
	}

	/**
	 * Learn the model.
	 *  
	 * @param samples
	 *            Set of text documents along with their target values.
	 * @param values
	 *            Set of all possible target values.
	 */
	public void learn(final DataSet samples, final List<String> values, final List<String> stopWords, final int pruneLevel, final boolean useStemmer) {
		try {
			classLabels = values;
			DecimalFormat decimalFormat = new DecimalFormat("0.0000");
			int numSamples = samples.size();
			LOGGER.info("Learning Naive Bayes model... ({} training samples)", numSamples);
			LOGGER.info("Building vocabulary...");

			vocabulary = new Vocabulary(samples, stopWords, pruneLevel, useStemmer);
			wordList = vocabulary.getWordList();
			numWords = wordList.size();
			numClassLabels = classLabels.size();

			LOGGER.info("Vocabulary contains {} words", numWords);

			m_P_wk_vj = new float[numClassLabels][numWords];
			m_P_vj = new float[numClassLabels];

			LOGGER.info("Building probability matrix...");

			for (int idxClassLabel = 0; idxClassLabel < numClassLabels; idxClassLabel++) {

				// This is the subset of documents from samples where the target value is v_j.
				float docs_j = samples.getLabelCount(classLabels.get(idxClassLabel));

				m_P_vj[idxClassLabel] = (docs_j != 0) ? (docs_j / numSamples) : 0;

				DataSet classLabelSamples = samples.getClassLabelSet(classLabels.get(idxClassLabel));

				Vocabulary Text_j = new Vocabulary(classLabelSamples, stopWords, pruneLevel, useStemmer);

				/*
				 * n is the total number of distinct word positions in Text_j. In our case is given by the number of
				 * word times their frequency.
				 */
				float n = Text_j.totalCount();

				LOGGER.info("Class '{}': |docsj| = {}, P(vj) = {}, n = {} ({}  distinct words)",
						classLabels.get(idxClassLabel),
						(int) docs_j,
						decimalFormat.format(m_P_vj[idxClassLabel]),
						(int) n,
						Text_j.size());

				// Number of words in the vocabulary.
				String w_k;
				float n_k;

				for (int idxWord = 0; idxWord < numWords; idxWord++) {
					// w_k represents each one of the words that occur in the vocabulary.
					w_k = wordList.get(idxWord);

					// n_k is the number of times that word w_k occurs in Text_j.
					n_k = Text_j.getWordFrequency(w_k);
					m_P_wk_vj[idxClassLabel][idxWord] = (n_k + 1) / (n + numWords);
				}
			}
		} catch (IOException e) {
			LOGGER.error("An error was encountered whilst learning the model", e);
		}
	}

	/**
	 * Return the estimated target value for the given document.
	 * 
	 * @param labelledInstance
	 *            document whose target value we want to estimate.
	 * @return estimated target value.
	 */
	public int classify(final LabelledInstance labelledInstance, final boolean useStemmer) throws IOException {
		Map<String, Integer> docAttributes = new MessageTokenizer(useStemmer, labelledInstance.getText()).getTokenList();
		double argMax = -Double.MAX_VALUE;

		int v_NB = 0;
		String word;

		// For each class label (a.k.a. newsgroup)...
		for (int idxClass = 0; idxClass < numClassLabels; idxClass++) {
			double P_ai_vj = Math.log(m_P_vj[idxClass]);

			/*
			 * For each word in the document... (or rather the vocabulary!) We iterate the vocabulary and see if the
			 * word is in the document because in this implementation it is far more efficient than iterating through
			 * the document and then scanning the vocabulary.
			 */
			for (int idxWord = 0; idxWord < numWords; idxWord++) {
				word = wordList.get(idxWord);
				Integer frequency = docAttributes.get(word);

				if (frequency != null) {
					P_ai_vj += Math.log(m_P_wk_vj[idxClass][idxWord]) * frequency.intValue();
				}
			}

			if (P_ai_vj > argMax) {
				argMax = P_ai_vj;
				v_NB = idxClass;
			}
		}

		return (v_NB);
	}

	/**
	 * Displays the confusion matrix and calculates the error rate for the given confusion matrix.
	 * 
	 * @param confussionMatrix
	 * @return error rate give by the number of error divided by the number of samples.
	 */
	public float calculateConfussionMatrix(final int confussionMatrix[][]) {
		int n = confussionMatrix.length;
		int correct = 0;
		int total = 0;
		DecimalFormat decimalFormat = new DecimalFormat("00");

		LOGGER.info("Columns show predicted classification, rows show actual classification");

		StringBuilder line = new StringBuilder();
		StringBuilder line2 = new StringBuilder();
		for (int j = 0; j < n; j++) {
			line.append(" ").append(decimalFormat.format(j + 1)).append(" ");
			line2.append("----");
		}

		LOGGER.info("{} | predicted / actual ", line);
		LOGGER.info("{}-+--------------------", line2);

		for (int j = 0; j < n; j++) {
			line = new StringBuilder();
			for (int k = 0; k < n; k++) {
				if (j == k) {
					correct += confussionMatrix[j][k];
					line.append("[").append(decimalFormat.format(confussionMatrix[j][k])).append("]");
				} else {
					line.append(" ").append(decimalFormat.format(confussionMatrix[j][k])).append(" ");
				}

				total += confussionMatrix[j][k];
			}

			LOGGER.info("{} | {} {}", line.toString(), decimalFormat.format(j + 1), classLabels.get(j));
		}

		float percent = (((float) correct * 100) / total);
		LOGGER.info("");
		LOGGER.info("Correctly classified instances: " + correct + " (" + percent + "%)");
		LOGGER.info("");

		// Calculate and return the error rate.
		return ((float) (total - correct) / total);
	}

	public void saveToFile(final String filename, final boolean saveAsLn) throws IOException {
		BufferedWriter out = new BufferedWriter(new FileWriter(new File(filename)));
		DecimalFormat decimalFormat = new DecimalFormat((saveAsLn) ? "0000.00000" : "0.00000000");

		// write getClassLabel names
		for (int j = 0; j < classLabels.size(); j++) {
			out.write(classLabels.get(j) + ",");
		}
		out.write("classification");
		out.newLine();

		// Write the prior probability P(vj) for all classifications
		for (int j = 0; j < classLabels.size(); j++) {
			out.write(decimalFormat.format(saveAsLn ? Math.log(m_P_vj[j]) : m_P_vj[j]) + ",");
		}
		out.write("P(vj)");
		out.newLine();

		// write P(wk|vj), the probability of each word for a given classification
		out.newLine();
		for (int i = 0; i < wordList.size(); i++) {
			for (int j = 0; j < classLabels.size(); j++) {
				out.write(decimalFormat.format(saveAsLn ? Math.log(m_P_wk_vj[j][i]) : m_P_wk_vj[j][i]) + ",");
			}
			out.write(wordList.get(i));
			out.newLine();
		}
		out.close();
	}

}