all: java

java:
	find src/ -name '*.java' | xargs javac \
				-cp dep/jmetal-problem-5.1-SNAPSHOT-jar-with-dependencies.jar:dep/jmetal-core-5.1-SNAPSHOT-jar-with-dependencies.jar:dep/jmetal-exec-5.1-SNAPSHOT-jar-with-dependencies.jar:dep/jmetal-algorithm-5.1-SNAPSHOT-jar-with-dependencies.jar:dep/kd.jar -d classes

clean:
	rm -rf classes/*
