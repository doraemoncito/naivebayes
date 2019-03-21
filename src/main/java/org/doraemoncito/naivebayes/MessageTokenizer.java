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

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * A message tokenizer that extracts all words from the input string and calculates their frequencies.
 *
 * @author Jose Hernandez
 */
public class MessageTokenizer {

    private Map<String, Integer> wordFrecuencyTable = new ConcurrentHashMap<>();

    /*
     * TODO: pass in (inject) a stemmer object.  When stemming is not required with pass in a passthrough stemmer.
     * Can we use a lambda here for the stemmer?
     */
    public MessageTokenizer(boolean useStemmer, String string) throws IOException {
        string = string.replaceAll("(-|=|\\|)", " ");
        String[] wordArray = string.toLowerCase().split("\\s");

        for (int i = 0; i < wordArray.length; i++) {
            wordArray[i] = wordArray[i].replaceAll("(^[^a-z]+|[^a-z]+$)", "");
            if (!wordArray[i].isEmpty()) {
                if (useStemmer) {
                    // stem the tokenized word.
                    // TODO: can we cache the stemming result so that we don't have to calculate it over and over again?
                    wordArray[i] = stemWord(wordArray[i]);
                }

                int frequency = wordFrecuencyTable.getOrDefault(wordArray[i], 0);
                wordFrecuencyTable.put(wordArray[i], frequency + 1);
            }
        }
    }

    private String stemWord(String word) {
        Stemmer stemmer = new Stemmer();
        stemmer.add(word.toCharArray(), word.length());
        stemmer.stem();
        return stemmer.toString();
    }

    public Map<String, Integer> getTokenList() {
        return wordFrecuencyTable;
    }

    public String toString() {
        String[] stringArray = wordFrecuencyTable.keySet().toArray(new String[wordFrecuencyTable.size()]);
        StringBuffer stringBuffer = new StringBuffer();

        for (int i = 0; i < stringArray.length; i++) {
            stringBuffer.append(stringArray[i]).append(" ");
        }

        return stringBuffer.toString();
    }

}
