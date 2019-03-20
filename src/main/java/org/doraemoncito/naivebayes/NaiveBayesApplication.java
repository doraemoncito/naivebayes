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
 * Copyright (c) 2003-2019 Jose Hernandez
 */
package org.doraemoncito.naivebayes;

import org.doraemoncito.naivebayes.loaders.CsvDataSetLoader;
import org.doraemoncito.naivebayes.loaders.NewsGroupDataSetLoader;
import org.doraemoncito.naivebayes.loaders.StopWordLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;


public class NaiveBayesApplication implements CommandLineRunner {

    private static final Logger LOGGER = LoggerFactory.getLogger(NaiveBayesApplication.class);

    private static DataSet dataset = null;

    /*
     * Prune words with frequency counts lower than prune-level. less than 2 means no pruning. Default is 0 (no
     * pruning).
     */
    private static int pruneLevel = 0;

    /* Use Porter stemming algorithm. Default is NOT to use it. */
    private static boolean useStemmer = false;

    /* Stop words to remove (if any). Default is NO stop words. */
    private List<String> stopWords = null;

    public static void usage() {
        LOGGER.info("Usage: NaiveBayes -n|--newsgroups newsgroups [-w|--stop-words stopwords] [-p|--prune-level prune-level] [-s|--use-stemmer]");
    }

    public static void main(String[] args) {

        SpringApplication.run(NaiveBayesApplication.class, args);
    }

    @Override
    public void run(String... args) {

        if (args.length > 0 && args[0].equals("exitcode")) {
            throw new RuntimeException();
        }

        LOGGER.info("A Naive Bayes newsgroup classification program.");
        LOGGER.info("Copyright (c) 2003-2019 Jose Hernandez.");
        LOGGER.info("");
        LOGGER.info("Stemming algorithm (c) 2000 Martin Porter (http://www.tartarus.org/~martin/PorterStemmer)");
        LOGGER.info("GNU command line option suite (c) 2003 Steve Purcell (http://jargs.sourceforge.net/)");
        LOGGER.info("");

        try {
            CmdLineParser cmdLineParser = new CmdLineParser();

            CmdLineParser.Option csvOption = cmdLineParser.addStringOption('d', "csv");
            CmdLineParser.Option newsgroupsOption = cmdLineParser.addStringOption('n', "newsgroups");
            CmdLineParser.Option stopWordsOption = cmdLineParser.addStringOption('w', "stop-words");
            CmdLineParser.Option pruneLevelOption = cmdLineParser.addIntegerOption('p', "prune-level");
            CmdLineParser.Option useStemmerOption = cmdLineParser.addBooleanOption('s', "use-stemmer");

            cmdLineParser.parse(args);

            // Get the list of samples from the location specified in the newsgroup option.
            final String csv = (String) cmdLineParser.getOptionValue(csvOption);

            // Get the list of samples from the location specified in the newsgroup option.
            final String newsgroups = (String) cmdLineParser.getOptionValue(newsgroupsOption);

            final String path = new File(".").getCanonicalPath();

            if (newsgroups != null) {
                LOGGER.info("Reading newsgroup test data from {}", newsgroups);
                final List<LabelledInstance> labelledInstances = new NewsGroupDataSetLoader().read(Paths.get(path, newsgroups).toFile());
                dataset = new DataSet(labelledInstances);
            } else if (csv != null) {
                LOGGER.info("Reading CSV test data from {}", csv);
                final List<LabelledInstance> labelledInstances = new CsvDataSetLoader().read(Paths.get(path, csv).toFile());
                dataset = new DataSet(labelledInstances);
            } else {
                LOGGER.info("Please specify a CSV data file or a newsgroup directory.");
                usage();
            }

            Boolean useStemmerValue = (Boolean) cmdLineParser.getOptionValue(useStemmerOption);
            if (useStemmerValue != null) {
                useStemmer = useStemmerValue;
            }

            String stopWordsFile = (String) cmdLineParser.getOptionValue(stopWordsOption);

            if (stopWordsFile != null) {
                stopWords = new StopWordLoader().read(Paths.get(path, stopWordsFile).toFile());
                LOGGER.info("Loaded {} stop words from file", stopWords.size());
            } else {
                LOGGER.info("Stop words disabled");
            }

            Integer pruneLevelValue = (Integer) cmdLineParser.getOptionValue(pruneLevelOption);

            if (pruneLevelValue != null) {
                pruneLevel = pruneLevelValue;
            }

            if (pruneLevel < 2)
                LOGGER.info("Word pruning disabled");
            else
                LOGGER.info("Pruning words in vocabulary with frequencies lower than " + pruneLevel);

            LOGGER.info("Porter stemming algorithm " + ((useStemmer) ? "enabled" : "disabled"));
            LOGGER.info("");

            // ...and now train and run the Naive Bayes algorithm.
            new NaiveBayes(dataset, stopWords, pruneLevel, useStemmer);

            LOGGER.info("Done.");

        } catch (CmdLineParser.OptionException e) {
            LOGGER.error("Unable to parse command line arguments: {}", e.getMessage(), e);
            usage();
        } catch (FileNotFoundException e) {
            LOGGER.info("Unable to find file: {}", e.getMessage(), e);
            usage();
        } catch (IOException e) {
            LOGGER.info("An exception was caught whilst attempting to classify documents using NaiveBayes", e);
        }
    }
}
