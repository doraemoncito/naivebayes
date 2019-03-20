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

import static org.junit.Assert.fail;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;

import org.doraemoncito.naivebayes.loaders.CsvDataSetLoader;
import org.doraemoncito.naivebayes.loaders.NewsGroupDataSetLoader;
import org.doraemoncito.naivebayes.loaders.StopWordLoader;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NaiveBayesTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(NaiveBayesTest.class);
    private static final String STOP_WORDS_FILE_NAME = "stop_words.txt";

    private List<String> stopWords;

    public void setup() throws IOException {
        ClassLoader classLoader = getClass().getClassLoader();
        File stopWordsFile = new File(Objects.requireNonNull(classLoader.getResource(STOP_WORDS_FILE_NAME)).getFile());
        stopWords = new StopWordLoader().read(stopWordsFile);
    }

    @Test
    public void testClassifyNewsGroups() {
        URL newsgroupsURL = getClass().getResource("/mini_newsgroups");
        try {
            Path newsgroupsPath = Paths.get(newsgroupsURL.toURI());
            final File directory = new File(newsgroupsPath.toAbsolutePath().toString());
            final List<LabelledInstance> labelledInstances = new NewsGroupDataSetLoader().read(directory);
            final DataSet samples = new DataSet(labelledInstances);
            new NaiveBayes(samples, stopWords,3, true);
        } catch (URISyntaxException | IOException e) {
            LOGGER.error("Newsgroup classification failed", e);
            fail(String.format("Newsgroup classification failed: %s", e.getMessage()));
        }
    }

    @Ignore
    @Test
    public void testClassifyCsv() {
        URL csvURL = getClass().getResource("/csv/train.csv");
        try {
            Path csvPath = Paths.get(csvURL.toURI());
            final File file = new File(csvPath.toAbsolutePath().toString());
            final List<LabelledInstance> labelledInstances = new CsvDataSetLoader().read(file);
            final DataSet samples = new DataSet(labelledInstances);
            new NaiveBayes(samples, stopWords,3, true);
        } catch (URISyntaxException | IOException e) {
            LOGGER.error("CSV classification failed", e);
            fail(String.format("CSV classification failed: %s", e.getMessage()));
        }
    }

}
