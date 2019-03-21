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
 * Copyright (c) 2003-2015 Jose Hernandez
 */
package org.doraemoncito.naivebayes;

import java.io.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * The vocabulary is the set of all distinct words and other tokens occurring in any text document from the given
 * samples.
 *
 * @author Jose Hernandez
 */
public class Vocabulary extends ConcurrentHashMap<String, Integer> {

    private static final long serialVersionUID = 1L;

    /**
     * Construct a vocabulary from the given samples ignoring all the stop words.
     *
     * @param samples
     * @param stopWords
     * @param pruneLevel
     * @param useStemmer
     * @throws FileNotFoundException
     * @throws IOException
     */
    public Vocabulary(final DataSet samples, List<String> stopWords, final int pruneLevel, final boolean useStemmer)
            throws FileNotFoundException, IOException {

        // Add the words from each example in turn to the vocabulary
        int numSamples = samples.size();
        for (int i = 0; i < numSamples; i++) {
            // Get a message instance and add its words to the vocabulary after stemming.

            LabelledInstance labelledInstance = samples.getLabelledInstance(i);

            Map<String, Integer> tokenMap = new MessageTokenizer(useStemmer, labelledInstance.getText()).getTokenList();

            for (java.util.Map.Entry<String, Integer> entry : tokenMap.entrySet()) {
                String word = entry.getKey();
                Integer frequency = entry.getValue();

                // Don't add stop words to the vocabulary
                if ((null == stopWords) || (!stopWords.contains(word))) {
                    /*
                     * If the word is already in the vocabulary, simply add up the word counts, otherwise just add the
                     * new word to the vocabulary.
                     */
                    put(word, getWordFrequency(word) + frequency);
                }
            }
        }

        // Remove from the vocabulary (prune) words that occur less than prune-level times.
        if (pruneLevel > 1) {
            for (java.util.Map.Entry<String, Integer> entry : entrySet()) {
                if (entry.getValue() < pruneLevel) {
                    remove(entry.getKey());
                }
            }
        }
    }

    public String toString() {
        StringBuilder stringBuffer = new StringBuilder();

        for (String word : getWordList()) {
            stringBuffer.append(word).append(" ");
        }

        return stringBuffer.toString();
    }

    public int getWordFrequency(String word) {
        return getOrDefault(word, 0);
    }

    /**
     * Retrieves an unmodifiable list of words in the vocabulary sorted in alphabetical order.
     *
     * @return sorted word list
     */
    public List<String> getWordList() {
        /*
         * Create a new word list with pointers to the original elements. For efficiency reasons we do not copy the
         * elements which shall remain in the original map.
         */
        ArrayList<String> wordList = new ArrayList<>(keySet());
        Collections.sort(wordList);
        return Collections.unmodifiableList(wordList);
    }

    public int totalCount() {
        return values().stream().mapToInt(Integer::intValue).sum();
    }

    public void saveToFile(String filename) throws IOException {
        BufferedWriter out = new BufferedWriter(new FileWriter(new File(filename)));
        DecimalFormat decimalFormat = new DecimalFormat("0000");

        for (String word : getWordList()) {
            Integer frequency = get(word);
            if (null != frequency) {
                out.write(decimalFormat.format(frequency.intValue()) + " " + word);
                out.newLine();
            }
        }

        out.close();
    }

}
