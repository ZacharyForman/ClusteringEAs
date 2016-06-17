package dddm;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;

import dddm.cure.Cure;
import dddm.cure.Cluster;
import dddm.cure.Point;

class CureDriver2 {
  static final int MIN_REPRESENTIVE_COUNT = 4;
  static final double SHRINK_FACTOR = 0.5;
  static final double REQUIRED_REPRESENTATION_PROBABILITY = 0.1;
  static final int REDUCING_FACTOR_FOR_EACH_PARTITION = 2;
  static final int NUMBER_OF_PARTITIONS = 100;

  public static List<Cluster> cluster(List<double[]> data, int n) {
    Cure cureClusterer = new Cure(data, n);

    cureClusterer.setShrinkFactor(SHRINK_FACTOR);

    cureClusterer.setRequiredRepresentationProbablity(
        REQUIRED_REPRESENTATION_PROBABILITY);

    cureClusterer.setNumberOfPartitions(NUMBER_OF_PARTITIONS);

    cureClusterer.setReducingFactorForEachPartition(
        REDUCING_FACTOR_FOR_EACH_PARTITION);

    return cureClusterer.cluster();
  }

  public static void printClusters(List<Cluster> clusters) {
    for (int i = 0; i < clusters.size(); ++i) {
      Cluster cluster = clusters.get(i);
      for (int j = 0; j < cluster.pointsInCluster.size(); j++) {
        Point p = (Point)cluster.pointsInCluster.get(j);
        System.out.println(p.x + " " + p.y + " " + (i + 1));
      }
    }
    System.out.println();
  }

  public static void printRepresentation(ClusteringRepresentation solution) {
    List<double[]> data = new ArrayList<double[]>();
    for (int i = 0; i < solution.getNumberOfDoubleVariables()/2; i++) {
      data.add(new double[]{
        (Double)solution.getVariableValue(2*i), (Double)solution.getVariableValue(2*i+1)
      });
    }

    List<Cluster> clusters = cluster(data, -1);
    printClusters(clusters);
  }
}
