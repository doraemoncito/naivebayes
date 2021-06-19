# [Naive Bayes classifier](https://doraemoncito.github.io/naivebayes/)

A simple Naive Bayes classifier written in Java

Build with:

    mvn clean install
    
and run with

    ./target/naivebayes-1.0-SNAPSHOT-spring-boot.jar --newsgroups ../src/test/resources/mini_newsgroups --use-stemmer --stop-words ../src/main/resources/stop_words.txt --prune-level 3

A sample run on the supplied test data will yield results along these lines:

```
Shuffling samples...
Running fold 01
===============
Learning Naive Bayes model... (1800 training samples)
Building vocabulary...
Vocabulary contains 9066 words
Building probability matrix...
Class 'soc.religion.christian': |docsj| = 91, P(vj) = 0.0506, n = 34175 (1536  distinct words)
Class 'rec.autos': |docsj| = 89, P(vj) = 0.0494, n = 12769 (904  distinct words)
Class 'talk.religion.misc': |docsj| = 91, P(vj) = 0.0506, n = 23916 (1325  distinct words)
Class 'comp.windows.x': |docsj| = 93, P(vj) = 0.0517, n = 23168 (1229  distinct words)
Class 'rec.sport.baseball': |docsj| = 90, P(vj) = 0.0500, n = 15102 (979  distinct words)
Class 'comp.graphics': |docsj| = 93, P(vj) = 0.0517, n = 33930 (1699  distinct words)
Class 'talk.politics.mideast': |docsj| = 90, P(vj) = 0.0500, n = 27424 (1553  distinct words)
Class 'comp.sys.ibm.pc.hardware': |docsj| = 89, P(vj) = 0.0494, n = 12494 (825  distinct words)
Class 'sci.med': |docsj| = 94, P(vj) = 0.0522, n = 30483 (1740  distinct words)
Class 'comp.os.ms-windows.misc': |docsj| = 89, P(vj) = 0.0494, n = 11228 (780  distinct words)
Class 'sci.crypt': |docsj| = 92, P(vj) = 0.0511, n = 22816 (1262  distinct words)
Class 'comp.sys.mac.hardware': |docsj| = 90, P(vj) = 0.0500, n = 13269 (884  distinct words)
Class 'misc.forsale': |docsj| = 86, P(vj) = 0.0478, n = 5505 (576  distinct words)
Class 'rec.motorcycles': |docsj| = 90, P(vj) = 0.0500, n = 12555 (949  distinct words)
Class 'talk.politics.misc': |docsj| = 89, P(vj) = 0.0494, n = 30792 (1528  distinct words)
Class 'sci.electronics': |docsj| = 86, P(vj) = 0.0478, n = 24285 (1310  distinct words)
Class 'rec.sport.hockey': |docsj| = 90, P(vj) = 0.0500, n = 15775 (1080  distinct words)
Class 'sci.space': |docsj| = 94, P(vj) = 0.0522, n = 25004 (1595  distinct words)
Class 'alt.atheism': |docsj| = 92, P(vj) = 0.0511, n = 20807 (1163  distinct words)
Class 'talk.politics.guns': |docsj| = 82, P(vj) = 0.0456, n = 21029 (1315  distinct words)

Classifying 200 test samples with Naive Bayes...
Columns show predicted classification, rows show actual classification
 01  02  03  04  05  06  07  08  09  10  11  12  13  14  15  16  17  18  19  20  | predicted / actual 
---------------------------------------------------------------------------------+--------------------
[08] 00  00  00  00  00  00  00  00  00  00  00  00  01  00  00  00  00  00  00  | 01 soc.religion.christian
 00 [08] 00  00  00  00  00  00  00  00  00  00  00  00  01  01  00  00  00  01  | 02 rec.autos
 01  00 [04] 00  00  00  01  00  00  00  00  00  00  00  00  00  00  00  01  02  | 03 talk.religion.misc
 00  00  00 [02] 00  03  00  00  00  01  00  00  00  00  00  01  00  00  00  00  | 04 comp.windows.x
 00  00  00  00 [08] 00  00  00  00  00  00  00  00  01  01  00  00  00  00  00  | 05 rec.sport.baseball
 00  01  00  00  00 [06] 00  00  00  00  00  00  00  00  00  00  00  00  00  00  | 06 comp.graphics
 01  00  00  00  00  00 [09] 00  00  00  00  00  00  00  00  00  00  00  00  00  | 07 talk.politics.mideast
 00  00  00  00  00  02  00 [08] 00  00  00  00  00  00  00  00  00  00  01  00  | 08 comp.sys.ibm.pc.hardware
 00  00  00  00  00  00  00  00 [05] 00  00  00  00  00  01  00  00  00  00  00  | 09 sci.med
 00  00  00  03  00  03  00  01  00 [03] 00  00  01  00  00  00  00  00  00  00  | 10 comp.os.ms-windows.misc
 00  00  00  00  00  00  00  00  00  00 [08] 00  00  00  00  00  00  00  00  00  | 11 sci.crypt
 00  00  00  00  00  01  00  00  00  00  00 [07] 01  00  00  01  00  00  00  00  | 12 comp.sys.mac.hardware
 00  00  00  00  00  00  00  02  00  00  00  02 [06] 00  02  02  00  00  00  00  | 13 misc.forsale
 00  00  00  00  00  00  00  00  00  00  00  00  00 [07] 02  00  00  00  01  00  | 14 rec.motorcycles
 01  00  02  00  00  00  02  00  00  00  00  00  00  00 [06] 00  00  00  00  00  | 15 talk.politics.misc
 00  00  00  00  00  02  00  01  01  00  00  00  00  00  00 [10] 00  00  00  00  | 16 sci.electronics
 00  00  00  00  00  00  00  00  01  00  00  00  00  00  00  00 [09] 00  00  00  | 17 rec.sport.hockey
 00  00  00  00  00  01  00  00  00  00  00  00  00  00  00  00  00 [05] 00  00  | 18 sci.space
 00  00  04  00  00  00  00  00  00  00  00  00  00  00  00  00  00  00 [04] 00  | 19 alt.atheism
 00  00  01  00  00  00  00  00  00  00  01  00  00  01  07  00  00  00  00 [08] | 20 talk.politics.guns

Correctly classified instances: 131 (65.5%)

Running fold 02
===============
Learning Naive Bayes model... (1800 training samples)
Building vocabulary...
Vocabulary contains 9078 words
Building probability matrix...
Class 'soc.religion.christian': |docsj| = 91, P(vj) = 0.0506, n = 32318 (1465  distinct words)
Class 'rec.autos': |docsj| = 85, P(vj) = 0.0472, n = 12651 (906  distinct words)
Class 'talk.religion.misc': |docsj| = 88, P(vj) = 0.0489, n = 22746 (1289  distinct words)
Class 'comp.windows.x': |docsj| = 89, P(vj) = 0.0494, n = 22663 (1232  distinct words)
Class 'rec.sport.baseball': |docsj| = 83, P(vj) = 0.0461, n = 12848 (884  distinct words)
Class 'comp.graphics': |docsj| = 91, P(vj) = 0.0506, n = 33192 (1662  distinct words)
Class 'talk.politics.mideast': |docsj| = 87, P(vj) = 0.0483, n = 23921 (1449  distinct words)
Class 'comp.sys.ibm.pc.hardware': |docsj| = 91, P(vj) = 0.0506, n = 12806 (821  distinct words)
Class 'sci.med': |docsj| = 92, P(vj) = 0.0511, n = 30003 (1696  distinct words)
Class 'comp.os.ms-windows.misc': |docsj| = 92, P(vj) = 0.0511, n = 12863 (849  distinct words)
Class 'sci.crypt': |docsj| = 92, P(vj) = 0.0511, n = 23335 (1264  distinct words)
Class 'comp.sys.mac.hardware': |docsj| = 91, P(vj) = 0.0506, n = 14425 (925  distinct words)
Class 'misc.forsale': |docsj| = 92, P(vj) = 0.0511, n = 7013 (682  distinct words)
Class 'rec.motorcycles': |docsj| = 95, P(vj) = 0.0528, n = 13269 (986  distinct words)
Class 'talk.politics.misc': |docsj| = 89, P(vj) = 0.0494, n = 31665 (1583  distinct words)
Class 'sci.electronics': |docsj| = 91, P(vj) = 0.0506, n = 26110 (1372  distinct words)
Class 'rec.sport.hockey': |docsj| = 90, P(vj) = 0.0500, n = 16620 (1095  distinct words)
Class 'sci.space': |docsj| = 91, P(vj) = 0.0506, n = 25088 (1603  distinct words)
Class 'alt.atheism': |docsj| = 89, P(vj) = 0.0494, n = 19173 (1095  distinct words)
Class 'talk.politics.guns': |docsj| = 91, P(vj) = 0.0506, n = 22567 (1343  distinct words)

Classifying 200 test samples with Naive Bayes...
Columns show predicted classification, rows show actual classification
 01  02  03  04  05  06  07  08  09  10  11  12  13  14  15  16  17  18  19  20  | predicted / actual 
---------------------------------------------------------------------------------+--------------------
[09] 00  00  00  00  00  00  00  00  00  00  00  00  00  00  00  00  00  00  00  | 01 soc.religion.christian
 00 [10] 00  00  00  00  00  00  00  00  01  00  00  01  01  01  00  00  00  01  | 02 rec.autos
 00  00 [05] 00  00  00  00  00  00  00  00  00  00  00  02  00  00  00  02  03  | 03 talk.religion.misc
 00  00  00 [04] 00  05  00  00  00  00  00  00  00  00  01  01  00  00  00  00  | 04 comp.windows.x
 01  00  00  00 [13] 00  00  00  00  00  00  00  00  00  02  00  00  00  00  01  | 05 rec.sport.baseball
 00  00  00  00  00 [07] 00  00  01  00  01  00  00  00  00  00  00  00  00  00  | 06 comp.graphics
 00  00  00  00  00  00 [11] 00  00  00  00  00  00  00  02  00  00  00  00  00  | 07 talk.politics.mideast
 00  00  00  00  00  00  00 [06] 00  00  00  03  00  00  00  00  00  00  00  00  | 08 comp.sys.ibm.pc.hardware
 00  00  00  00  00  00  00  00 [08] 00  00  00  00  00  00  00  00  00  00  00  | 09 sci.med
 00  00  00  01  00  01  00  03  00 [01] 00  00  00  00  00  02  00  00  00  00  | 10 comp.os.ms-windows.misc
 00  00  00  00  02  00  00  00  00  00 [05] 00  00  00  00  00  00  00  00  01  | 11 sci.crypt
 00  00  00  00  00  01  00  01  00  00  00 [06] 00  00  01  00  00  00  00  00  | 12 comp.sys.mac.hardware
 00  00  00  01  00  01  00  01  00  00  00  01 [04] 00  00  00  00  00  00  00  | 13 misc.forsale
 00  00  00  00  00  00  00  00  00  01  00  00  00 [04] 00  00  00  00  00  00  | 14 rec.motorcycles
 00  00  01  00  00  00  01  00  01  00  00  00  00  00 [06] 00  00  00  00  02  | 15 talk.politics.misc
 00  00  00  00  00  01  00  01  00  00  00  01  00  01  00 [05] 00  00  00  00  | 16 sci.electronics
 00  00  00  00  00  00  00  00  00  00  00  00  00  00  01  00 [09] 00  00  00  | 17 rec.sport.hockey
 00  00  00  00  00  00  00  00  00  00  00  00  00  00  00  00  00 [09] 00  00  | 18 sci.space
 03  00  04  00  00  00  00  00  00  00  00  00  00  00  00  00  00  00 [04] 00  | 19 alt.atheism
 00  00  00  00  00  00  00  00  00  00  01  00  00  00  01  00  00  00  00 [07] | 20 talk.politics.guns

Correctly classified instances: 133 (66.5%)

Running fold 03
===============
Learning Naive Bayes model... (1800 training samples)
Building vocabulary...
Vocabulary contains 8875 words
Building probability matrix...
Class 'soc.religion.christian': |docsj| = 91, P(vj) = 0.0506, n = 33948 (1523  distinct words)
Class 'rec.autos': |docsj| = 89, P(vj) = 0.0494, n = 12257 (868  distinct words)
Class 'talk.religion.misc': |docsj| = 91, P(vj) = 0.0506, n = 22847 (1300  distinct words)
Class 'comp.windows.x': |docsj| = 91, P(vj) = 0.0506, n = 22753 (1215  distinct words)
Class 'rec.sport.baseball': |docsj| = 91, P(vj) = 0.0506, n = 15096 (993  distinct words)
Class 'comp.graphics': |docsj| = 89, P(vj) = 0.0494, n = 33244 (1683  distinct words)
Class 'talk.politics.mideast': |docsj| = 89, P(vj) = 0.0494, n = 25464 (1465  distinct words)
Class 'comp.sys.ibm.pc.hardware': |docsj| = 92, P(vj) = 0.0511, n = 13052 (832  distinct words)
Class 'sci.med': |docsj| = 84, P(vj) = 0.0467, n = 27051 (1596  distinct words)
Class 'comp.os.ms-windows.misc': |docsj| = 91, P(vj) = 0.0506, n = 12016 (820  distinct words)
Class 'sci.crypt': |docsj| = 90, P(vj) = 0.0500, n = 21914 (1224  distinct words)
Class 'comp.sys.mac.hardware': |docsj| = 91, P(vj) = 0.0506, n = 14388 (899  distinct words)
Class 'misc.forsale': |docsj| = 96, P(vj) = 0.0533, n = 7250 (706  distinct words)
Class 'rec.motorcycles': |docsj| = 89, P(vj) = 0.0494, n = 12043 (915  distinct words)
Class 'talk.politics.misc': |docsj| = 93, P(vj) = 0.0517, n = 31674 (1575  distinct words)
Class 'sci.electronics': |docsj| = 87, P(vj) = 0.0483, n = 24098 (1313  distinct words)
Class 'rec.sport.hockey': |docsj| = 93, P(vj) = 0.0517, n = 16377 (1097  distinct words)
Class 'sci.space': |docsj| = 88, P(vj) = 0.0489, n = 23223 (1487  distinct words)
Class 'alt.atheism': |docsj| = 90, P(vj) = 0.0500, n = 18143 (1079  distinct words)
Class 'talk.politics.guns': |docsj| = 85, P(vj) = 0.0472, n = 18482 (1174  distinct words)

Classifying 200 test samples with Naive Bayes...
Columns show predicted classification, rows show actual classification
 01  02  03  04  05  06  07  08  09  10  11  12  13  14  15  16  17  18  19  20  | predicted / actual 
---------------------------------------------------------------------------------+--------------------
[08] 00  00  00  00  00  00  00  00  00  00  00  00  00  01  00  00  00  00  00  | 01 soc.religion.christian
 01 [05] 00  00  00  00  00  00  00  00  01  00  00  01  02  00  00  00  00  01  | 02 rec.autos
 02  00 [05] 00  00  00  00  00  00  00  00  00  00  00  01  00  00  00  01  00  | 03 talk.religion.misc
 00  00  00 [03] 00  03  00  00  01  00  00  00  00  00  00  02  00  00  00  00  | 04 comp.windows.x
 00  00  00  00 [07] 00  01  01  00  00  00  00  00  00  00  00  00  00  00  00  | 05 rec.sport.baseball
 00  00  00  00  00 [08] 00  00  01  01  00  00  00  00  00  00  00  00  00  01  | 06 comp.graphics
 00  00  00  00  00  00 [10] 00  00  00  00  00  00  00  00  00  00  00  00  01  | 07 talk.politics.mideast
 00  00  00  01  00  01  00 [02] 00  01  00  02  00  00  00  01  00  00  00  00  | 08 comp.sys.ibm.pc.hardware
 00  00  00  00  00  00  00  00 [15] 00  00  00  00  00  00  01  00  00  00  00  | 09 sci.med
 00  00  00  02  00  01  00  01  00 [03] 00  00  00  00  01  01  00  00  00  00  | 10 comp.os.ms-windows.misc
 00  00  00  00  00  00  00  00  00  00 [08] 00  00  00  01  00  00  00  00  01  | 11 sci.crypt
 00  00  00  00  00  02  00  00  00  00  00 [05] 00  00  00  01  00  01  00  00  | 12 comp.sys.mac.hardware
 00  00  00  00  00  01  00  00  00  00  00  00 [03] 00  00  00  00  00  00  00  | 13 misc.forsale
 00  01  00  00  00  02  00  00  00  00  00  00  00 [08] 00  00  00  00  00  00  | 14 rec.motorcycles
 00  00  01  00  00  00  00  00  00  00  00  00  00  00 [06] 00  00  00  00  00  | 15 talk.politics.misc
 00  00  00  00  00  00  00  00  00  00  01  01  00  00  00 [11] 00  00  00  00  | 16 sci.electronics
 00  00  00  00  00  00  00  00  00  00  00  00  00  00  00  00 [07] 00  00  00  | 17 rec.sport.hockey
 00  00  01  00  00  00  00  00  01  00  00  00  00  00  01  00  00 [09] 00  00  | 18 sci.space
 00  00  02  00  00  00  00  00  00  00  00  00  00  00  00  00  00  00 [07] 01  | 19 alt.atheism
 00  00  02  00  00  00  00  00  00  00  01  00  00  00  02  00  00  00  00 [10] | 20 talk.politics.guns

Correctly classified instances: 140 (70.0%)

Running fold 04
===============
Learning Naive Bayes model... (1800 training samples)
Building vocabulary...
Vocabulary contains 8960 words
Building probability matrix...
Class 'soc.religion.christian': |docsj| = 91, P(vj) = 0.0506, n = 33410 (1480  distinct words)
Class 'rec.autos': |docsj| = 94, P(vj) = 0.0522, n = 14177 (968  distinct words)
Class 'talk.religion.misc': |docsj| = 91, P(vj) = 0.0506, n = 23203 (1309  distinct words)
Class 'comp.windows.x': |docsj| = 92, P(vj) = 0.0511, n = 23098 (1236  distinct words)
Class 'rec.sport.baseball': |docsj| = 92, P(vj) = 0.0511, n = 16383 (1021  distinct words)
Class 'comp.graphics': |docsj| = 89, P(vj) = 0.0494, n = 32849 (1656  distinct words)
Class 'talk.politics.mideast': |docsj| = 91, P(vj) = 0.0506, n = 26213 (1510  distinct words)
Class 'comp.sys.ibm.pc.hardware': |docsj| = 94, P(vj) = 0.0522, n = 13144 (838  distinct words)
Class 'sci.med': |docsj| = 88, P(vj) = 0.0489, n = 28875 (1684  distinct words)
Class 'comp.os.ms-windows.misc': |docsj| = 90, P(vj) = 0.0500, n = 12142 (818  distinct words)
Class 'sci.crypt': |docsj| = 86, P(vj) = 0.0478, n = 18121 (1112  distinct words)
Class 'comp.sys.mac.hardware': |docsj| = 92, P(vj) = 0.0511, n = 14433 (917  distinct words)
Class 'misc.forsale': |docsj| = 91, P(vj) = 0.0506, n = 6736 (662  distinct words)
Class 'rec.motorcycles': |docsj| = 85, P(vj) = 0.0472, n = 11696 (887  distinct words)
Class 'talk.politics.misc': |docsj| = 90, P(vj) = 0.0500, n = 21664 (1332  distinct words)
Class 'sci.electronics': |docsj| = 94, P(vj) = 0.0522, n = 26488 (1387  distinct words)
Class 'rec.sport.hockey': |docsj| = 88, P(vj) = 0.0489, n = 16023 (1068  distinct words)
Class 'sci.space': |docsj| = 82, P(vj) = 0.0456, n = 22045 (1488  distinct words)
Class 'alt.atheism': |docsj| = 90, P(vj) = 0.0500, n = 20682 (1154  distinct words)
Class 'talk.politics.guns': |docsj| = 90, P(vj) = 0.0500, n = 21274 (1298  distinct words)

Classifying 200 test samples with Naive Bayes...
Columns show predicted classification, rows show actual classification
 01  02  03  04  05  06  07  08  09  10  11  12  13  14  15  16  17  18  19  20  | predicted / actual 
---------------------------------------------------------------------------------+--------------------
[08] 00  00  00  00  00  00  00  00  00  00  00  00  00  00  00  00  00  00  01  | 01 soc.religion.christian
 00 [04] 00  00  00  00  00  00  00  00  00  01  00  00  00  01  00  00  00  00  | 02 rec.autos
 04  00 [01] 00  00  00  00  00  00  00  00  00  00  00  01  00  00  00  02  01  | 03 talk.religion.misc
 00  00  00 [04] 00  02  00  00  00  00  01  00  00  00  00  00  00  01  00  00  | 04 comp.windows.x
 00  00  00  00 [08] 00  00  00  00  00  00  00  00  00  00  00  00  00  00  00  | 05 rec.sport.baseball
 00  00  00  00  00 [09] 00  00  01  00  00  00  00  00  00  01  00  00  00  00  | 06 comp.graphics
 00  00  01  00  01  00 [07] 00  00  00  00  00  00  00  00  00  00  00  00  00  | 07 talk.politics.mideast
 00  00  00  00  00  00  00 [02] 00  00  00  03  00  00  00  01  00  00  00  00  | 08 comp.sys.ibm.pc.hardware
 00  00  00  00  00  00  00  00 [11] 00  00  00  00  00  00  00  00  00  00  01  | 09 sci.med
 01  00  00  02  00  00  00  02  00 [02] 00  01  00  00  00  01  00  00  00  01  | 10 comp.os.ms-windows.misc
 00  00  02  00  00  00  00  00  00  00 [11] 00  01  00  00  00  00  00  00  00  | 11 sci.crypt
 00  00  00  00  00  01  00  00  00  00  00 [06] 00  00  00  01  00  00  00  00  | 12 comp.sys.mac.hardware
 00  01  00  00  00  00  00  01  00  00  00  00 [04] 02  00  01  00  00  00  00  | 13 misc.forsale
 02  01  01  00  00  00  00  00  00  00  00  00  00 [10] 00  01  00  00  00  00  | 14 rec.motorcycles
 01  00  02  00  00  00  00  00  00  00  00  00  00  00 [07] 00  00  00  00  00  | 15 talk.politics.misc
 00  00  00  00  00  00  00  01  00  00  00  01  00  00  00 [04] 00  00  00  00  | 16 sci.electronics
 00  00  00  00  01  00  00  00  00  00  00  00  00  00  00  00 [11] 00  00  00  | 17 rec.sport.hockey
 01  00  01  00  00  00  00  01  00  00  00  00  00  00  02  01  00 [12] 00  00  | 18 sci.space
 03  01  02  00  00  00  00  00  00  00  00  00  00  00  00  00  00  00 [04] 00  | 19 alt.atheism
 00  00  00  00  00  00  00  00  00  00  00  00  00  00  02  00  00  00  00 [08] | 20 talk.politics.guns

Correctly classified instances: 133 (66.5%)

Running fold 05
===============
Learning Naive Bayes model... (1800 training samples)
Building vocabulary...
Vocabulary contains 8905 words
Building probability matrix...
Class 'soc.religion.christian': |docsj| = 89, P(vj) = 0.0494, n = 32484 (1473  distinct words)
Class 'rec.autos': |docsj| = 92, P(vj) = 0.0511, n = 13990 (957  distinct words)
Class 'talk.religion.misc': |docsj| = 89, P(vj) = 0.0494, n = 21843 (1264  distinct words)
Class 'comp.windows.x': |docsj| = 89, P(vj) = 0.0494, n = 21633 (1183  distinct words)
Class 'rec.sport.baseball': |docsj| = 89, P(vj) = 0.0494, n = 15932 (998  distinct words)
Class 'comp.graphics': |docsj| = 93, P(vj) = 0.0517, n = 34285 (1693  distinct words)
Class 'talk.politics.mideast': |docsj| = 91, P(vj) = 0.0506, n = 27991 (1572  distinct words)
Class 'comp.sys.ibm.pc.hardware': |docsj| = 92, P(vj) = 0.0511, n = 12927 (844  distinct words)
Class 'sci.med': |docsj| = 88, P(vj) = 0.0489, n = 24416 (1526  distinct words)
Class 'comp.os.ms-windows.misc': |docsj| = 89, P(vj) = 0.0494, n = 11869 (807  distinct words)
Class 'sci.crypt': |docsj| = 87, P(vj) = 0.0483, n = 19920 (1152  distinct words)
Class 'comp.sys.mac.hardware': |docsj| = 92, P(vj) = 0.0511, n = 13389 (868  distinct words)
Class 'misc.forsale': |docsj| = 83, P(vj) = 0.0461, n = 5799 (576  distinct words)
Class 'rec.motorcycles': |docsj| = 92, P(vj) = 0.0511, n = 12545 (938  distinct words)
Class 'talk.politics.misc': |docsj| = 89, P(vj) = 0.0494, n = 31320 (1566  distinct words)
Class 'sci.electronics': |docsj| = 91, P(vj) = 0.0506, n = 25566 (1355  distinct words)
Class 'rec.sport.hockey': |docsj| = 89, P(vj) = 0.0494, n = 14150 (947  distinct words)
Class 'sci.space': |docsj| = 94, P(vj) = 0.0522, n = 24070 (1559  distinct words)
Class 'alt.atheism': |docsj| = 94, P(vj) = 0.0522, n = 20497 (1155  distinct words)
Class 'talk.politics.guns': |docsj| = 88, P(vj) = 0.0489, n = 21465 (1316  distinct words)

Classifying 200 test samples with Naive Bayes...
Columns show predicted classification, rows show actual classification
 01  02  03  04  05  06  07  08  09  10  11  12  13  14  15  16  17  18  19  20  | predicted / actual 
---------------------------------------------------------------------------------+--------------------
[08] 00  00  00  00  00  00  00  00  00  00  00  00  00  01  00  00  00  01  01  | 01 soc.religion.christian
 01 [07] 00  00  00  00  00  00  00  00  00  00  00  00  00  00  00  00  00  00  | 02 rec.autos
 04  00 [02] 00  00  00  00  00  00  00  00  00  00  00  01  00  00  00  02  02  | 03 talk.religion.misc
 00  00  01 [04] 00  05  00  00  01  00  00  00  00  00  00  00  00  00  00  00  | 04 comp.windows.x
 00  00  00  00 [09] 00  00  00  00  00  00  00  00  01  01  00  00  00  00  00  | 05 rec.sport.baseball
 01  00  00  02  00 [02] 00  00  00  00  00  01  00  00  00  01  00  00  00  00  | 06 comp.graphics
 01  00  00  00  01  01 [04] 00  00  00  00  00  00  00  02  00  00  00  00  00  | 07 talk.politics.mideast
 00  00  00  00  00  02  00 [05] 00  00  00  01  00  00  00  00  00  00  00  00  | 08 comp.sys.ibm.pc.hardware
 01  00  00  00  00  00  00  00 [09] 00  00  00  00  00  00  00  00  00  00  02  | 09 sci.med
 00  00  00  01  00  04  00  02  00 [04] 00  00  00  00  00  00  00  00  00  00  | 10 comp.os.ms-windows.misc
 00  00  00  00  00  01  00  00  00  00 [08] 00  00  00  02  01  00  00  00  01  | 11 sci.crypt
 00  00  00  00  00  01  00  01  00  01  00 [03] 00  00  01  01  00  00  00  00  | 12 comp.sys.mac.hardware
 00  02  00  00  00  02  00  00  00  01  00  01 [06] 01  00  02  00  02  00  00  | 13 misc.forsale
 00  01  00  00  00  00  00  00  00  00  00  00  00 [04] 02  00  00  00  01  00  | 14 rec.motorcycles
 02  00  01  00  00  00  00  00  00  00  00  00  00  00 [06] 00  00  00  00  02  | 15 talk.politics.misc
 00  01  00  00  00  01  00  00  00  00  00  01  00  00  01 [05] 00  00  00  00  | 16 sci.electronics
 00  00  00  00  01  00  00  00  00  00  00  00  00  00  01  00 [08] 00  01  00  | 17 rec.sport.hockey
 00  00  00  00  00  01  00  00  00  00  00  00  00  00  02  00  00 [03] 00  00  | 18 sci.space
 00  00  01  00  00  00  00  00  00  00  00  00  00  00  00  00  00  00 [05] 00  | 19 alt.atheism
 02  00  02  00  00  00  00  00  00  00  00  00  00  01  00  00  00  00  00 [07] | 20 talk.politics.guns

Correctly classified instances: 109 (54.5%)

Running fold 06
===============
Learning Naive Bayes model... (1800 training samples)
Building vocabulary...
Vocabulary contains 8999 words
Building probability matrix...
Class 'soc.religion.christian': |docsj| = 86, P(vj) = 0.0478, n = 33336 (1516  distinct words)
Class 'rec.autos': |docsj| = 93, P(vj) = 0.0517, n = 13555 (952  distinct words)
Class 'talk.religion.misc': |docsj| = 95, P(vj) = 0.0528, n = 23303 (1304  distinct words)
Class 'comp.windows.x': |docsj| = 89, P(vj) = 0.0494, n = 22505 (1216  distinct words)
Class 'rec.sport.baseball': |docsj| = 94, P(vj) = 0.0522, n = 16217 (1009  distinct words)
Class 'comp.graphics': |docsj| = 89, P(vj) = 0.0494, n = 33892 (1698  distinct words)
Class 'talk.politics.mideast': |docsj| = 91, P(vj) = 0.0506, n = 26641 (1505  distinct words)
Class 'comp.sys.ibm.pc.hardware': |docsj| = 85, P(vj) = 0.0472, n = 10680 (728  distinct words)
Class 'sci.med': |docsj| = 90, P(vj) = 0.0500, n = 29686 (1697  distinct words)
Class 'comp.os.ms-windows.misc': |docsj| = 92, P(vj) = 0.0511, n = 12376 (840  distinct words)
Class 'sci.crypt': |docsj| = 94, P(vj) = 0.0522, n = 23553 (1281  distinct words)
Class 'comp.sys.mac.hardware': |docsj| = 84, P(vj) = 0.0467, n = 12430 (823  distinct words)
Class 'misc.forsale': |docsj| = 89, P(vj) = 0.0494, n = 6726 (666  distinct words)
Class 'rec.motorcycles': |docsj| = 92, P(vj) = 0.0511, n = 12519 (947  distinct words)
Class 'talk.politics.misc': |docsj| = 90, P(vj) = 0.0500, n = 31203 (1522  distinct words)
Class 'sci.electronics': |docsj| = 87, P(vj) = 0.0483, n = 24565 (1318  distinct words)
Class 'rec.sport.hockey': |docsj| = 90, P(vj) = 0.0500, n = 16460 (1077  distinct words)
Class 'sci.space': |docsj| = 88, P(vj) = 0.0489, n = 18553 (1330  distinct words)
Class 'alt.atheism': |docsj| = 92, P(vj) = 0.0511, n = 20152 (1129  distinct words)
Class 'talk.politics.guns': |docsj| = 90, P(vj) = 0.0500, n = 22003 (1334  distinct words)

Classifying 200 test samples with Naive Bayes...
Columns show predicted classification, rows show actual classification
 01  02  03  04  05  06  07  08  09  10  11  12  13  14  15  16  17  18  19  20  | predicted / actual 
---------------------------------------------------------------------------------+--------------------
[10] 00  01  00  00  00  00  00  00  00  00  00  00  00  02  00  00  00  01  00  | 01 soc.religion.christian
 00 [05] 00  00  00  00  00  00  00  00  00  00  00  01  00  00  00  00  00  01  | 02 rec.autos
 00  00 [03] 00  00  00  00  00  00  00  00  00  00  00  00  00  00  00  01  01  | 03 talk.religion.misc
 00  00  00 [07] 00  04  00  00  00  00  00  00  00  00  00  00  00  00  00  00  | 04 comp.windows.x
 00  00  01  00 [05] 00  00  00  00  00  00  00  00  00  00  00  00  00  00  00  | 05 rec.sport.baseball
 00  00  00  01  00 [08] 00  01  00  00  00  00  00  00  00  01  00  00  00  00  | 06 comp.graphics
 01  00  00  00  00  00 [07] 00  00  00  00  00  00  00  01  00  00  00  00  00  | 07 talk.politics.mideast
 01  00  00  01  00  00  00 [09] 00  00  00  01  01  00  00  02  00  00  00  00  | 08 comp.sys.ibm.pc.hardware
 02  00  00  00  00  00  00  00 [08] 00  00  00  00  00  00  00  00  00  00  00  | 09 sci.med
 00  00  00  00  00  03  00  01  00 [03] 00  00  00  00  01  00  00  00  00  00  | 10 comp.os.ms-windows.misc
 00  00  00  00  00  00  00  00  00  00 [06] 00  00  00  00  00  00  00  00  00  | 11 sci.crypt
 00  00  00  01  00  02  00  01  00  00  00 [09] 00  00  00  03  00  00  00  00  | 12 comp.sys.mac.hardware
 00  00  00  00  01  01  00  01  00  00  00  00 [06] 00  00  00  00  01  01  00  | 13 misc.forsale
 00  00  00  00  00  01  00  00  00  00  00  00  00 [07] 00  00  00  00  00  00  | 14 rec.motorcycles
 01  00  00  00  01  00  00  00  00  00  00  00  00  00 [04] 00  00  00  00  04  | 15 talk.politics.misc
 00  01  00  00  00  02  00  03  00  00  00  00  00  00  00 [07] 00  00  00  00  | 16 sci.electronics
 00  00  00  00  00  00  00  00  00  00  00  00  00  00  00  00 [10] 00  00  00  | 17 rec.sport.hockey
 00  00  00  00  00  00  00  00  00  00  01  00  00  00  00  01  00 [08] 00  02  | 18 sci.space
 00  00  02  00  00  00  01  00  00  00  00  00  00  00  01  00  00  00 [04] 00  | 19 alt.atheism
 00  00  00  00  00  00  00  00  00  00  00  00  00  00  04  00  00  00  00 [06] | 20 talk.politics.guns

Correctly classified instances: 132 (66.0%)

Running fold 07
===============
Learning Naive Bayes model... (1800 training samples)
Building vocabulary...
Vocabulary contains 8885 words
Building probability matrix...
Class 'soc.religion.christian': |docsj| = 90, P(vj) = 0.0500, n = 34307 (1533  distinct words)
Class 'rec.autos': |docsj| = 91, P(vj) = 0.0506, n = 13763 (961  distinct words)
Class 'talk.religion.misc': |docsj| = 87, P(vj) = 0.0483, n = 22072 (1256  distinct words)
Class 'comp.windows.x': |docsj| = 89, P(vj) = 0.0494, n = 22480 (1222  distinct words)
Class 'rec.sport.baseball': |docsj| = 89, P(vj) = 0.0494, n = 14576 (971  distinct words)
Class 'comp.graphics': |docsj| = 88, P(vj) = 0.0489, n = 26250 (1424  distinct words)
Class 'talk.politics.mideast': |docsj| = 91, P(vj) = 0.0506, n = 25439 (1478  distinct words)
Class 'comp.sys.ibm.pc.hardware': |docsj| = 89, P(vj) = 0.0494, n = 12190 (804  distinct words)
Class 'sci.med': |docsj| = 88, P(vj) = 0.0489, n = 25012 (1477  distinct words)
Class 'comp.os.ms-windows.misc': |docsj| = 94, P(vj) = 0.0522, n = 13017 (857  distinct words)
Class 'sci.crypt': |docsj| = 81, P(vj) = 0.0450, n = 20399 (1156  distinct words)
Class 'comp.sys.mac.hardware': |docsj| = 91, P(vj) = 0.0506, n = 14320 (910  distinct words)
Class 'misc.forsale': |docsj| = 90, P(vj) = 0.0500, n = 6589 (648  distinct words)
Class 'rec.motorcycles': |docsj| = 89, P(vj) = 0.0494, n = 12193 (951  distinct words)
Class 'talk.politics.misc': |docsj| = 91, P(vj) = 0.0506, n = 29130 (1483  distinct words)
Class 'sci.electronics': |docsj| = 94, P(vj) = 0.0522, n = 26409 (1381  distinct words)
Class 'rec.sport.hockey': |docsj| = 92, P(vj) = 0.0511, n = 17175 (1122  distinct words)
Class 'sci.space': |docsj| = 88, P(vj) = 0.0489, n = 25111 (1597  distinct words)
Class 'alt.atheism': |docsj| = 94, P(vj) = 0.0522, n = 20387 (1160  distinct words)
Class 'talk.politics.guns': |docsj| = 94, P(vj) = 0.0522, n = 23437 (1379  distinct words)

Classifying 200 test samples with Naive Bayes...
Columns show predicted classification, rows show actual classification
 01  02  03  04  05  06  07  08  09  10  11  12  13  14  15  16  17  18  19  20  | predicted / actual 
---------------------------------------------------------------------------------+--------------------
[10] 00  00  00  00  00  00  00  00  00  00  00  00  00  00  00  00  00  00  00  | 01 soc.religion.christian
 00 [05] 00  00  00  00  00  00  01  00  00  01  00  01  00  00  00  00  00  01  | 02 rec.autos
 02  00 [00] 00  00  00  00  00  00  00  00  00  00  01  01  01  00  00  07  01  | 03 talk.religion.misc
 00  00  00 [10] 00  01  00  00  00  00  00  00  00  00  00  00  00  00  00  00  | 04 comp.windows.x
 00  00  00  00 [09] 00  00  00  00  00  00  00  00  00  00  00  02  00  00  00  | 05 rec.sport.baseball
 00  00  00  00  00 [11] 00  00  00  00  00  00  00  00  00  00  00  00  01  00  | 06 comp.graphics
 01  00  00  00  00  00 [06] 00  00  00  00  00  00  00  02  00  00  00  00  00  | 07 talk.politics.mideast
 00  00  00  00  00  01  00 [07] 00  01  00  01  00  00  00  01  00  00  00  00  | 08 comp.sys.ibm.pc.hardware
 00  00  00  00  00  00  00  00 [11] 00  00  00  00  00  00  00  00  00  01  00  | 09 sci.med
 00  00  00  00  00  02  00  01  00 [01] 00  01  00  00  00  01  00  00  00  00  | 10 comp.os.ms-windows.misc
 00  00  00  00  00  01  00  00  01  00 [11] 01  00  00  02  01  00  00  00  02  | 11 sci.crypt
 00  00  00  00  00  01  00  00  00  00  01 [06] 00  00  00  01  00  00  00  00  | 12 comp.sys.mac.hardware
 00  00  00  00  00  01  01  01  00  00  00  00 [04] 00  00  02  00  01  00  00  | 13 misc.forsale
 00  00  00  00  01  00  01  00  00  00  00  00  00 [08] 01  00  00  00  00  00  | 14 rec.motorcycles
 00  01  00  00  00  00  00  00  00  00  00  00  00  00 [05] 00  00  00  00  03  | 15 talk.politics.misc
 00  00  00  00  00  00  00  00  00  00  00  00  00  00  01 [04] 00  00  00  01  | 16 sci.electronics
 00  00  00  00  01  00  00  00  00  00  00  00  00  00  00  00 [07] 00  00  00  | 17 rec.sport.hockey
 00  00  00  00  00  00  00  00  00  00  00  00  00  00  00  01  00 [11] 00  00  | 18 sci.space
 02  00  00  00  00  00  00  00  00  00  00  00  00  00  01  00  00  00 [03] 00  | 19 alt.atheism
 00  00  00  00  00  00  00  00  00  00  00  00  00  01  00  00  00  00  01 [04] | 20 talk.politics.guns

Correctly classified instances: 133 (66.5%)

Running fold 08
===============
Learning Naive Bayes model... (1800 training samples)
Building vocabulary...
Vocabulary contains 8882 words
Building probability matrix...
Class 'soc.religion.christian': |docsj| = 91, P(vj) = 0.0506, n = 34548 (1541  distinct words)
Class 'rec.autos': |docsj| = 85, P(vj) = 0.0472, n = 12745 (903  distinct words)
Class 'talk.religion.misc': |docsj| = 93, P(vj) = 0.0517, n = 22628 (1290  distinct words)
Class 'comp.windows.x': |docsj| = 87, P(vj) = 0.0483, n = 22503 (1227  distinct words)
Class 'rec.sport.baseball': |docsj| = 92, P(vj) = 0.0511, n = 15865 (1000  distinct words)
Class 'comp.graphics': |docsj| = 89, P(vj) = 0.0494, n = 27774 (1449  distinct words)
Class 'talk.politics.mideast': |docsj| = 88, P(vj) = 0.0489, n = 26240 (1525  distinct words)
Class 'comp.sys.ibm.pc.hardware': |docsj| = 91, P(vj) = 0.0506, n = 12749 (825  distinct words)
Class 'sci.med': |docsj| = 95, P(vj) = 0.0528, n = 30225 (1712  distinct words)
Class 'comp.os.ms-windows.misc': |docsj| = 84, P(vj) = 0.0467, n = 11803 (806  distinct words)
Class 'sci.crypt': |docsj| = 89, P(vj) = 0.0494, n = 22412 (1249  distinct words)
Class 'comp.sys.mac.hardware': |docsj| = 94, P(vj) = 0.0522, n = 14512 (919  distinct words)
Class 'misc.forsale': |docsj| = 92, P(vj) = 0.0511, n = 7015 (674  distinct words)
Class 'rec.motorcycles': |docsj| = 91, P(vj) = 0.0506, n = 12552 (951  distinct words)
Class 'talk.politics.misc': |docsj| = 88, P(vj) = 0.0489, n = 28129 (1442  distinct words)
Class 'sci.electronics': |docsj| = 84, P(vj) = 0.0467, n = 12976 (885  distinct words)
Class 'rec.sport.hockey': |docsj| = 93, P(vj) = 0.0517, n = 16511 (1095  distinct words)
Class 'sci.space': |docsj| = 94, P(vj) = 0.0522, n = 24567 (1592  distinct words)
Class 'alt.atheism': |docsj| = 87, P(vj) = 0.0483, n = 20198 (1115  distinct words)
Class 'talk.politics.guns': |docsj| = 93, P(vj) = 0.0517, n = 22721 (1371  distinct words)

Classifying 200 test samples with Naive Bayes...
Columns show predicted classification, rows show actual classification
 01  02  03  04  05  06  07  08  09  10  11  12  13  14  15  16  17  18  19  20  | predicted / actual 
---------------------------------------------------------------------------------+--------------------
[08] 00  00  00  00  00  00  00  01  00  00  00  00  00  00  00  00  00  00  00  | 01 soc.religion.christian
 00 [07] 00  00  01  00  00  00  00  00  00  00  00  00  03  02  00  00  00  02  | 02 rec.autos
 01  00 [02] 00  00  00  00  00  00  00  00  00  00  00  01  00  00  00  02  01  | 03 talk.religion.misc
 00  00  00 [06] 00  06  00  00  00  00  00  00  00  00  00  00  00  00  01  00  | 04 comp.windows.x
 00  00  00  00 [06] 00  00  00  00  00  00  00  00  00  00  00  00  00  02  00  | 05 rec.sport.baseball
 00  00  00  00  00 [06] 00  00  00  03  00  00  00  00  00  01  00  01  00  00  | 06 comp.graphics
 00  00  00  00  00  00 [12] 00  00  00  00  00  00  00  00  00  00  00  00  00  | 07 talk.politics.mideast
 00  00  00  00  00  02  00 [04] 00  01  00  02  00  00  00  00  00  00  00  00  | 08 comp.sys.ibm.pc.hardware
 00  00  01  00  00  00  00  00 [04] 00  00  00  00  00  00  00  00  00  00  00  | 09 sci.med
 00  00  01  00  00  05  00  03  00 [04] 00  02  00  00  00  01  00  00  00  00  | 10 comp.os.ms-windows.misc
 01  00  00  00  00  00  01  00  00  00 [09] 00  00  00  00  00  00  00  00  00  | 11 sci.crypt
 00  00  00  00  00  00  00  00  00  00  00 [06] 00  00  00  00  00  00  00  00  | 12 comp.sys.mac.hardware
 00  00  00  00  00  00  00  02  01  00  01  00 [03] 00  00  01  00  00  00  00  | 13 misc.forsale
 00  01  00  00  00  00  00  00  00  00  00  00  01 [07] 00  00  00  00  00  00  | 14 rec.motorcycles
 00  00  00  00  00  00  01  00  00  00  00  00  00  00 [09] 00  00  00  01  01  | 15 talk.politics.misc
 01  00  00  00  00  00  00  00  00  00  00  00  01  00  00 [11] 00  00  00  03  | 16 sci.electronics
 00  00  00  00  01  00  00  00  00  01  00  00  00  00  00  00 [05] 00  00  00  | 17 rec.sport.hockey
 00  00  00  00  00  00  00  00  00  00  00  00  00  00  01  00  00 [05] 00  00  | 18 sci.space
 02  00  06  00  00  00  00  00  00  00  00  00  00  00  00  00  00  00 [05] 00  | 19 alt.atheism
 00  00  00  00  00  00  00  00  00  00  00  00  00  00  02  00  00  00  00 [05] | 20 talk.politics.guns

Correctly classified instances: 124 (62.0%)

Running fold 09
===============
Learning Naive Bayes model... (1800 training samples)
Building vocabulary...
Vocabulary contains 8993 words
Building probability matrix...
Class 'soc.religion.christian': |docsj| = 91, P(vj) = 0.0506, n = 33583 (1524  distinct words)
Class 'rec.autos': |docsj| = 91, P(vj) = 0.0506, n = 13900 (972  distinct words)
Class 'talk.religion.misc': |docsj| = 86, P(vj) = 0.0478, n = 21395 (1239  distinct words)
Class 'comp.windows.x': |docsj| = 91, P(vj) = 0.0506, n = 22921 (1216  distinct words)
Class 'rec.sport.baseball': |docsj| = 91, P(vj) = 0.0506, n = 15877 (986  distinct words)
Class 'comp.graphics': |docsj| = 92, P(vj) = 0.0511, n = 24467 (1461  distinct words)
Class 'talk.politics.mideast': |docsj| = 91, P(vj) = 0.0506, n = 25814 (1493  distinct words)
Class 'comp.sys.ibm.pc.hardware': |docsj| = 87, P(vj) = 0.0483, n = 11548 (773  distinct words)
Class 'sci.med': |docsj| = 92, P(vj) = 0.0511, n = 27555 (1648  distinct words)
Class 'comp.os.ms-windows.misc': |docsj| = 89, P(vj) = 0.0494, n = 12758 (863  distinct words)
Class 'sci.crypt': |docsj| = 96, P(vj) = 0.0533, n = 23049 (1256  distinct words)
Class 'comp.sys.mac.hardware': |docsj| = 89, P(vj) = 0.0494, n = 14471 (916  distinct words)
Class 'misc.forsale': |docsj| = 89, P(vj) = 0.0494, n = 6718 (657  distinct words)
Class 'rec.motorcycles': |docsj| = 90, P(vj) = 0.0500, n = 12367 (928  distinct words)
Class 'talk.politics.misc': |docsj| = 90, P(vj) = 0.0500, n = 31178 (1560  distinct words)
Class 'sci.electronics': |docsj| = 90, P(vj) = 0.0500, n = 25112 (1342  distinct words)
Class 'rec.sport.hockey': |docsj| = 86, P(vj) = 0.0478, n = 16241 (1087  distinct words)
Class 'sci.space': |docsj| = 88, P(vj) = 0.0489, n = 24151 (1571  distinct words)
Class 'alt.atheism': |docsj| = 88, P(vj) = 0.0489, n = 19983 (1143  distinct words)
Class 'talk.politics.guns': |docsj| = 93, P(vj) = 0.0517, n = 21434 (1345  distinct words)

Classifying 200 test samples with Naive Bayes...
Columns show predicted classification, rows show actual classification
 01  02  03  04  05  06  07  08  09  10  11  12  13  14  15  16  17  18  19  20  | predicted / actual 
---------------------------------------------------------------------------------+--------------------
[09] 00  00  00  00  00  00  00  00  00  00  00  00  00  00  00  00  00  00  00  | 01 soc.religion.christian
 00 [06] 00  00  00  00  00  00  00  00  00  00  00  01  01  01  00  00  00  00  | 02 rec.autos
 03  00 [05] 00  00  00  00  00  00  00  00  00  00  00  02  00  00  00  02  02  | 03 talk.religion.misc
 01  00  00 [03] 00  03  00  00  00  00  00  00  00  00  01  01  00  00  00  00  | 04 comp.windows.x
 00  00  00  00 [06] 00  00  00  00  00  00  00  00  00  00  00  03  00  00  00  | 05 rec.sport.baseball
 00  00  00  01  00 [05] 00  01  00  01  00  00  00  00  00  00  00  00  00  00  | 06 comp.graphics
 02  00  00  00  00  00 [06] 00  00  00  00  00  00  00  01  00  00  00  00  00  | 07 talk.politics.mideast
 00  00  00  01  00  01  00 [07] 00  01  00  02  00  00  00  01  00  00  00  00  | 08 comp.sys.ibm.pc.hardware
 00  00  00  00  00  00  00  00 [07] 00  00  00  00  00  01  00  00  00  00  00  | 09 sci.med
 01  00  01  00  00  01  00  01  00 [06] 01  00  00  00  00  00  00  00  00  00  | 10 comp.os.ms-windows.misc
 00  00  00  00  00  00  00  00  00  00 [03] 00  00  00  01  00  00  00  00  00  | 11 sci.crypt
 00  00  00  00  00  02  00  00  00  00  00 [08] 00  00  00  01  00  00  00  00  | 12 comp.sys.mac.hardware
 00  01  00  02  00  03  00  00  00  00  00  01 [03] 00  00  01  00  00  00  00  | 13 misc.forsale
 00  00  00  00  00  01  00  00  00  00  00  00  00 [06] 02  01  00  00  00  00  | 14 rec.motorcycles
 02  00  01  00  00  00  00  00  00  00  00  01  00  00 [05] 00  00  00  00  01  | 15 talk.politics.misc
 00  01  00  00  00  01  00  00  01  00  00  01  00  00  00 [06] 00  00  00  00  | 16 sci.electronics
 00  00  01  00  02  00  00  00  00  00  00  00  00  00  00  00 [10] 00  01  00  | 17 rec.sport.hockey
 00  00  00  00  00  03  00  00  00  01  00  00  00  01  02  00  00 [05] 00  00  | 18 sci.space
 02  00  03  00  00  00  00  00  01  00  00  00  00  00  00  00  00  00 [06] 00  | 19 alt.atheism
 00  00  00  00  00  00  00  00  00  00  00  00  00  00  01  00  00  00  00 [06] | 20 talk.politics.guns

Correctly classified instances: 118 (59.0%)

Running fold 10
===============
Learning Naive Bayes model... (1800 training samples)
Building vocabulary...
Vocabulary contains 8822 words
Building probability matrix...
Class 'soc.religion.christian': |docsj| = 89, P(vj) = 0.0494, n = 32271 (1493  distinct words)
Class 'rec.autos': |docsj| = 91, P(vj) = 0.0506, n = 13229 (919  distinct words)
Class 'talk.religion.misc': |docsj| = 89, P(vj) = 0.0494, n = 19302 (1147  distinct words)
Class 'comp.windows.x': |docsj| = 90, P(vj) = 0.0500, n = 11627 (802  distinct words)
Class 'rec.sport.baseball': |docsj| = 89, P(vj) = 0.0494, n = 15300 (979  distinct words)
Class 'comp.graphics': |docsj| = 87, P(vj) = 0.0483, n = 33474 (1694  distinct words)
Class 'talk.politics.mideast': |docsj| = 91, P(vj) = 0.0506, n = 26385 (1541  distinct words)
Class 'comp.sys.ibm.pc.hardware': |docsj| = 90, P(vj) = 0.0500, n = 12908 (849  distinct words)
Class 'sci.med': |docsj| = 89, P(vj) = 0.0494, n = 26608 (1516  distinct words)
Class 'comp.os.ms-windows.misc': |docsj| = 90, P(vj) = 0.0500, n = 12161 (833  distinct words)
Class 'sci.crypt': |docsj| = 93, P(vj) = 0.0517, n = 22361 (1231  distinct words)
Class 'comp.sys.mac.hardware': |docsj| = 86, P(vj) = 0.0478, n = 13697 (876  distinct words)
Class 'misc.forsale': |docsj| = 92, P(vj) = 0.0511, n = 7008 (690  distinct words)
Class 'rec.motorcycles': |docsj| = 87, P(vj) = 0.0483, n = 11174 (880  distinct words)
Class 'talk.politics.misc': |docsj| = 91, P(vj) = 0.0506, n = 31308 (1562  distinct words)
Class 'sci.electronics': |docsj| = 96, P(vj) = 0.0533, n = 26346 (1383  distinct words)
Class 'rec.sport.hockey': |docsj| = 89, P(vj) = 0.0494, n = 15805 (1046  distinct words)
Class 'sci.space': |docsj| = 93, P(vj) = 0.0517, n = 24080 (1582  distinct words)
Class 'alt.atheism': |docsj| = 84, P(vj) = 0.0467, n = 20216 (1121  distinct words)
Class 'talk.politics.guns': |docsj| = 94, P(vj) = 0.0522, n = 22600 (1353  distinct words)

Classifying 200 test samples with Naive Bayes...
Columns show predicted classification, rows show actual classification
 01  02  03  04  05  06  07  08  09  10  11  12  13  14  15  16  17  18  19  20  | predicted / actual 
---------------------------------------------------------------------------------+--------------------
[11] 00  00  00  00  00  00  00  00  00  00  00  00  00  00  00  00  00  00  00  | 01 soc.religion.christian
 00 [05] 00  00  00  00  00  00  00  00  00  00  00  01  00  02  00  00  00  01  | 02 rec.autos
 06  00 [03] 00  00  00  00  00  00  00  00  00  00  00  01  00  00  00  00  01  | 03 talk.religion.misc
 00  00  00 [06] 00  03  00  00  00  00  01  00  00  00  00  00  00  00  00  00  | 04 comp.windows.x
 00  00  00  00 [11] 00  00  00  00  00  00  00  00  00  00  00  00  00  00  00  | 05 rec.sport.baseball
 00  00  00  00  00 [11] 00  01  00  01  00  00  00  00  00  00  00  00  00  00  | 06 comp.graphics
 00  00  00  00  00  00 [08] 00  00  00  00  00  00  00  00  00  00  00  00  01  | 07 talk.politics.mideast
 01  00  00  00  00  01  00 [07] 00  00  01  00  00  00  00  00  00  00  00  00  | 08 comp.sys.ibm.pc.hardware
 00  00  00  00  00  03  00  00 [07] 00  00  00  00  00  00  00  00  00  01  00  | 09 sci.med
 00  00  00  00  00  04  00  00  00 [03] 00  01  00  00  01  01  00  00  00  00  | 10 comp.os.ms-windows.misc
 00  00  00  00  00  00  00  00  00  00 [06] 00  00  00  01  00  00  00  00  00  | 11 sci.crypt
 00  00  00  00  00  02  00  01  00  00  01 [08] 00  00  00  02  00  00  00  00  | 12 comp.sys.mac.hardware
 00  01  00  00  00  00  00  00  00  00  00  00 [05] 00  00  02  00  00  00  00  | 13 misc.forsale
 00  01  00  00  00  00  00  00  00  00  00  00  00 [09] 00  01  00  01  00  01  | 14 rec.motorcycles
 00  00  00  00  00  00  00  00  00  00  00  00  00  00 [07] 00  00  01  00  01  | 15 talk.politics.misc
 00  00  00  00  00  00  00  00  00  00  00  00  00  00  00 [04] 00  00  00  00  | 16 sci.electronics
 00  00  00  00  00  00  00  00  00  00  00  00  00  00  01  00 [09] 01  00  00  | 17 rec.sport.hockey
 00  00  00  00  00  00  00  00  00  00  00  00  00  01  00  01  00 [05] 00  00  | 18 sci.space
 04  01  01  00  00  00  01  00  00  00  00  00  00  00  00  00  00  00 [07] 02  | 19 alt.atheism
 00  00  00  00  00  00  00  00  00  00  00  00  00  00  03  00  00  00  00 [03] | 20 talk.politics.guns

Correctly classified instances: 135 (67.5%)

Calculating average error and error bounds...
fold    error rate
----    ----------
  01        0.3450
  02        0.3350
  03        0.3000
  04        0.3350
  05        0.4550
  06        0.3400
  07        0.3350
  08        0.3800
  09        0.4100
  10        0.3250
----    ----------
mean error: 0.3560
std. dev.:  0.0146

Calculating confidence intervals...
Confidence interval at 90% --> [0.3293 , 0.3827]
Confidence interval at 95% --> [0.3231 , 0.3889]
Confidence interval at 98% --> [0.3149 , 0.3971]
Confidence interval at 99% --> [0.3087 , 0.4033]
```
