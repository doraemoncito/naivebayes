package org.doraemoncito.naivebayes;

import com.opencsv.bean.CsvBindByPosition;

public class LabelledInstance {

    @CsvBindByPosition(position = 0)
    private String classLabel;
    @CsvBindByPosition(position = 1)
    private String text;

    public LabelledInstance() {
        // This no-argument constructor is required to create LabelledInstance object instances using reflection
    }

    public LabelledInstance(final String classLabel, final String text) {
        this.classLabel = classLabel;
        this.text = text;
    }

    public String getClassLabel() {
        return classLabel;
    }

    public String getText() {
        return text;
    }

}
