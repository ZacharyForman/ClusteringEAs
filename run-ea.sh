#!/bin/bash
java -cp dep/kd.jar:dep/jmetal-problem-5.1-SNAPSHOT-jar-with-dependencies.jar:dep/jmetal-core-5.1-SNAPSHOT-jar-with-dependencies.jar:dep/jmetal-exec-5.1-SNAPSHOT-jar-with-dependencies.jar:dep/jmetal-algorithm-5.1-SNAPSHOT-jar-with-dependencies.jar:classes dddm.SPEA2Driver $@
