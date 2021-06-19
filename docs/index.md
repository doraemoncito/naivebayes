# Naïve Bayes Classifier

The Naïve Bayes application described in this page is a Java implementation of a multi-class Naïve Bayesian classifier for the classification of newsgroup messages but the implementation can be used to detect spam by restricting it to two classes.  A description of the algorithm can be found in the [Machine Learning book](http://www.cs.cmu.edu/afs/cs.cmu.edu/user/mitchell/ftp/mlbook.html) by [Tom Mitchell](http://www.cs.cmu.edu/~tom/) (Published by McGraw-Hill. ISBN: 0071154671).

The program works by feeding some sample newsgroup messages (training data set) into the Naïve Bayes learning algorithm to build a probability table describing the chance of a particular word appearing in a document of a given class. The basic premise is that certain words are more likely than others to appear in a given newsgroup.  I.e. the word “windows” is more likely to appear in a Microsoft Windows programming newsgroup than in a politics newsgroup.  Once the target concept has been learned, the classifier is able to calculate the probability that a given but previously unseen message belongs to a particular newsgroup.

The accuracy of the classification process can be improved by removing stop words, i.e. generic words that are equally likely to appear in any newsgroup. These are typically adverbs, articles, prepositions, etc. that help string sentences together but carry no context specific value.

Removal of infrequently occurring words can also increase the accuracy of the classification.  The reason behind this is that infrequent words do not add any significant value to the probability of identifying a particular class unless groups of them happen to always appear in the same newsgroup.

Another way of improving classification results is to use lemmatization or stemming to group together words with common characteristics.

To enable all these features when running the program, simply invoke it with the options shown below.  This example assumes you have unpacked the contents of the zip file to a new directory and are running the program from the root of that directory:

```shell
java -jar naivebayes.jar --newsgroups mini_newsgroups --use-stemmer --stop-words stop_words.txt --prune-level 3
```

The source code and sample data are availble dow download from [this GitHub repository](https://github.com/doraemoncito/naivebayes).
