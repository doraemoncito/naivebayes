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

import java.util.*;
import java.util.stream.Collectors;

/**
 * Represents a dataset of labelled instances.
 * Stores instances and statistics about the dataset, such as label frequencies.
 * Provides methods for dataset manipulation like splitting into folds for cross-validation and shuffling.
 */
@Slf4j
public class DataSet {


    private List<LabelledInstance> labelledInstances;
    private Map<String, Long> labelFrequencyTable;


    public DataSet(final List<LabelledInstance> labelledInstances) {

        this.labelledInstances = labelledInstances;
        labelFrequencyTable = buildClassLabelTable(labelledInstances);
    }

    public DataSet(DataSet other) {
        this.labelledInstances = new ArrayList<>(other.labelledInstances);
        this.labelFrequencyTable = new HashMap<>(other.labelFrequencyTable);
    }

    public DataSet copy() {
        return new DataSet(this);
    }

    public DataSet shuffle() {
        DataSet shuffledSamples = copy();
        Collections.shuffle(shuffledSamples.labelledInstances);
        return shuffledSamples;
    }

    /**
     * Build a map of sample counts indexed by class label.
     *
     * @return a map of labels to sample counts for the given class label.
     */
    private Map<String, Long> buildClassLabelTable(final List<LabelledInstance> labelledInstances) {
        return labelledInstances.stream()
                .map(LabelledInstance::classLabel)
                .collect(Collectors.groupingBy(e -> e, Collectors.counting()));
    }

    public DataSet getTrainingSet(final int fold) {
        DataSet trainingSet = copy();
        trainingSet.labelledInstances.removeAll(getFold(fold));
        trainingSet.labelFrequencyTable = trainingSet.buildClassLabelTable(trainingSet.labelledInstances);

        return trainingSet;
    }

    public DataSet getTestSet(final int fold) {

        DataSet testSet = this.copy();
        testSet.labelledInstances = getFold(fold);
        testSet.labelFrequencyTable = testSet.buildClassLabelTable(testSet.labelledInstances);

        return testSet;
    }

    private ArrayList<LabelledInstance> getFold(final int fold) {
        DataSet testSet = this.copy();
        ArrayList<LabelledInstance> subset = new ArrayList<>();
        int numSlots = size() / 10;

        for (int i = 0; i < numSlots; i++) {
            subset.add(testSet.labelledInstances.get((i * 10) + fold));
        }

        return subset;
    }

    public DataSet getClassLabelSet(final String classLabel) {
        DataSet classifierSet = this.copy();
        ArrayList<LabelledInstance> keepList = new ArrayList<>();

        for (int i = 0; i < classifierSet.labelledInstances.size(); i++) {
            if (classifierSet.getLabel(i).equals(classLabel)) {
                keepList.add(classifierSet.labelledInstances.get(i));
            }
        }

        classifierSet.labelledInstances = keepList;
        classifierSet.buildClassLabelTable(classifierSet.labelledInstances);
        return classifierSet;
    }


    public List<String> classLabelList() {
        return new ArrayList<>(labelFrequencyTable.keySet());
    }

    public String getLabel(int index) {
        return labelledInstances.get(index).classLabel();
    }

    /**
     * Counts the number of documents in this example set that belong to the given classification.
     *
     * @param classLabel the classification to count documents for
     * @return number of messages belonging to the particular classLabel / class
     */
    public long getLabelCount(final String classLabel) {
        return labelFrequencyTable.getOrDefault(classLabel, 0L);
    }

    public int size() {
        return labelledInstances.size();
    }

    public LabelledInstance getLabelledInstance(final int index) {
        return labelledInstances.get(index);
    }

}