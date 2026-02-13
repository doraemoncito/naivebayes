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

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * A message tokenizer that extracts all words from the input string and calculates their frequencies.
 *
 * @author Jose Hernandez
 */
public class MessageTokenizer {

    private final Map<String, Integer> wordFrequencyTable = new ConcurrentHashMap<>();

    /**
     * Constructs a MessageTokenizer and processes the input string.
     *
     * @param useStemmer boolean flag indicating whether to use stemming
     * @param string     the input string to tokenize
     */
    public MessageTokenizer(boolean useStemmer, String string) {
        string = string.replaceAll("[-=|]", " ");
        String[] wordArray = string.toLowerCase().split("\\s");

        for (int i = 0; i < wordArray.length; i++) {
            wordArray[i] = wordArray[i].replaceAll("(^[^a-z]+|[^a-z]+$)", "");
            if (!wordArray[i].isEmpty()) {
                if (useStemmer) {
                    // stem the tokenized word.
                    // TODO: can we cache the stemming result so that we don't have to calculate it over and over again?
                    wordArray[i] = stemWord(wordArray[i]);
                }

                int frequency = wordFrequencyTable.getOrDefault(wordArray[i], 0);
                wordFrequencyTable.put(wordArray[i], frequency + 1);
            }
        }
    }

    /**
     * Stems a given word using the Porter Stemmer algorithm.
     *
     * @param word the word to stem
     * @return the stemmed word
     */
    private String stemWord(String word) {
        Stemmer stemmer = new Stemmer();
        stemmer.add(word.toCharArray(), word.length());
        stemmer.stem();
        return stemmer.toString();
    }

    /**
     * Returns the map of tokens and their frequencies.
     *
     * @return a map where the key is the token and the value is its frequency count
     */
    public Map<String, Integer> getTokenList() {
        return wordFrequencyTable;
    }

    /**
     * Returns a string representation of the tokenized words.
     *
     * @return a space-separated string of tokens
     */
    public String toString() {
        String[] stringArray = wordFrequencyTable.keySet().toArray(new String[0]);
        StringBuilder stringBuffer = new StringBuilder();

        for (String s : stringArray) {
            stringBuffer.append(s).append(" ");
        }

        return stringBuffer.toString();
    }

}
