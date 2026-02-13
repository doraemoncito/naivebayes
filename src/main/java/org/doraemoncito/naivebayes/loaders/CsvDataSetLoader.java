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
package org.doraemoncito.naivebayes.loaders;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.doraemoncito.naivebayes.LabelledInstance;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.List;

/**
 * Loader for CSV datasets.
 * Reads a CSV file and converts it into a list of {@link LabelledInstance} objects.
 */
public class CsvDataSetLoader {

    /**
     * Reads the given CSV file.
     *
     * @param file the CSV file to read
     * @return a list of labelled instances parsed from the file
     * @throws IOException if an I/O error occurs while reading the file
     */
    public List<LabelledInstance> read(final File file) throws IOException {
        try (Reader reader = new FileReader(file);
             CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT)) {

            return csvParser.getRecords().stream()
                    .map(this::toLabelledInstance)
                    .toList();
        }
    }

    private LabelledInstance toLabelledInstance(CSVRecord record) {
        String classLabel = record.get(0);
        String text = record.get(1);
        return new LabelledInstance(classLabel, text);
    }
}
