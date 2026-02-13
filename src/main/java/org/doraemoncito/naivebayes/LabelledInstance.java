package org.doraemoncito.naivebayes;

/**
 * A labelled instance representing a text document and its corresponding classification label.
 *
 * @param classLabel the classification label of the instance
 * @param text       the text content of the instance
 */
public record LabelledInstance(
        String classLabel,
        String text
) {
}
