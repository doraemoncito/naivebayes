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
 * Copyright (c) 2003-2026 Jose Hernandez
 */
package org.doraemoncito.naivebayes;

import lombok.extern.slf4j.Slf4j;
import org.doraemoncito.naivebayes.loaders.CsvDataSetLoader;
import org.doraemoncito.naivebayes.loaders.NewsGroupDataSetLoader;
import org.doraemoncito.naivebayes.loaders.StopWordLoader;
import org.jspecify.annotations.NonNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;
import java.nio.file.Paths;
import java.util.List;


/**
 * Main application class for the Naive Bayes newsgroup classification program.
 * <p>
 * This class handles command-line arguments to load datasets (either from newsgroup directories or CSV files),
 * load stop words, configure pruning and stemming, and then executes the Naive Bayes training and classification.
 *
 * @author Jose Hernandez
 */
@Slf4j
@SpringBootApplication
public class Application implements ApplicationRunner {

    @Value("${csv:#{null}}")
    private String csv;

    @Value("${newsgroups:#{null}}")
    private String newsgroups;

    @Value("${stop-words:#{null}}")
    private String stopWordsFile;

    @Value("${prune-level:0}")
    private int pruneLevel;

    @Value("${use-stemmer:false}")
    private boolean useStemmer;

    public static void usage() {
        log.info("Usage:");
        log.info("       java -jar naivebayes.jar --newsgroups=<path> [--csv=<path>] [--stop-words=<path>] [--prune-level=<n>] [--use-stemmer=<true|false>]");
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(@NonNull ApplicationArguments args) throws Exception {
        log.info("A Naive Bayes newsgroup classification program.");
        log.info("Copyright (c) 2003-2026 Jose Hernandez.");
        log.info("");
        log.info("Stemming algorithm (c) 2000 Martin Porter (http://www.tartarus.org/~martin/PorterStemmer)");
        log.info("");

        final String path = new File(".").getCanonicalPath();
        DataSet dataset = null;
        List<String> stopWords = null;

        // Load dataset
        if (newsgroups != null) {
            log.info("Reading newsgroup test data from {}", newsgroups);
            final List<LabelledInstance> labelledInstances = new NewsGroupDataSetLoader().read(Paths.get(path, newsgroups).toFile());
            dataset = new DataSet(labelledInstances);
        } else if (csv != null) {
            log.info("Reading CSV test data from {}", csv);
            final List<LabelledInstance> labelledInstances = new CsvDataSetLoader().read(Paths.get(path, csv).toFile());
            dataset = new DataSet(labelledInstances);
        } else {
            log.info("Please specify a CSV data file or a newsgroup directory.");
            usage();
            return;
        }

        // Load stop words
        if (stopWordsFile != null) {
            stopWords = new StopWordLoader().read(Paths.get(path, stopWordsFile).toFile());
            log.info("Loaded {} stop words from file", stopWords.size());
        } else {
            log.info("Stop words disabled");
        }

        // Log configuration
        if (pruneLevel < 2) {
            log.info("Word pruning disabled");
        } else {
            log.info("Pruning words in vocabulary with frequencies lower than {}", pruneLevel);
        }

        log.info("Porter stemming algorithm {}", useStemmer ? "enabled" : "disabled");
        log.info("");

        // Train and run the Naive Bayes algorithm
        new NaiveBayes(dataset, stopWords, pruneLevel, useStemmer);

        log.info("Done.");
    }
}
