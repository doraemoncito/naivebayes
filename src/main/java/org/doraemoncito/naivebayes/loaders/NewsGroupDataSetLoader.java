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
package org.doraemoncito.naivebayes.loaders;

import org.doraemoncito.naivebayes.LabelledInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.*;

public class NewsGroupDataSetLoader implements Cloneable {

    private static final Logger LOGGER = LoggerFactory.getLogger(NewsGroupDataSetLoader.class);

    /**
     * Read label instances
     *
     * @param file File object representing the directory at the top of the hierarchy
     * @return List of labelled instances
     */
    public List<LabelledInstance> read(final File file) throws IOException {
        if (file.isDirectory()) {
            return parseDirectory(file, new ArrayList<>());
        } else {
            throw new RuntimeException("Invalid parameter " + file.getCanonicalPath());
        }
    }

    private List<LabelledInstance> parseDirectory(final File file, final List<LabelledInstance> instances) {
        String[] dir = file.list();

        if (null != dir) {
            for (String s : dir) {

                try {
                    File localfile = Paths.get(file.getCanonicalPath() + "/" + s).toFile();

                    LOGGER.trace("Processing file '{}'", localfile.getAbsolutePath());

                    if (localfile.isDirectory()) {
                        parseDirectory(localfile, instances);
                    } else if (localfile.isFile()) {
                        instances.add(parseFile(localfile));
                    } else {
                        throw new RuntimeException("Unable to read data file: " + localfile.getName());
                    }
                } catch (IOException e) {
                    throw new RuntimeException("Unable to read data file: " + file.getName());
                }
            }
        }

        return instances;
    }

    private LabelledInstance parseFile(final File file) throws IOException {
        // Get the class label from the directory name
        final String classLabel = file.getParentFile().getName();

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            skipPastClassLabel(reader);

            // Concatenate all strings into a single message buffer ready to be tokenized.
            StringBuilder data = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                data.append(line).append(" ");
            }

            return new LabelledInstance(classLabel, data.toString());
        }
    }

    /**
     * Advances the file pointer to the first character passed the document header.
     * <p>
     * Some documents may contain headers which should be stripped before the document is processed. Subclasses should
     * re-implement this method if the document instance contains a discardable header. The default implementation
     * doesn't do anything.
     *
     * @param InputFile document containing a header.
     * @throws IOException
     */
    private void skipPastClassLabel(BufferedReader InputFile) throws IOException {
        String MessageLine;

        /*
         * In newsgroup message the body is separated from the header by a single blank line so we need to move the file
         * pointer to just after the blank line.
         */
        while (null != (MessageLine = InputFile.readLine())) {
            if (MessageLine.equals("")) {
                break;
            }
        }
    }

}