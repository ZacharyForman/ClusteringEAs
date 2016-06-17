package dddm;

import org.uma.jmetal.solution.Solution;

import org.uma.jmetal.problem.Problem;
import org.uma.jmetal.problem.impl.AbstractIntegerDoubleProblem;

import dddm.cure.Cure;
import dddm.cure.Cluster;
import dddm.cure.Point;

import java.util.List;
import java.util.ArrayList;


public class ClusteringProblem
    extends AbstractIntegerDoubleProblem<ClusteringRepresentation> {

  protected final int points;

  public ClusteringProblem(int points) {
    super();
    this.points = points;
    setNumberOfObjectives(1);
    setNumberOfDoubleVariables(points * 2);
    setNumberOfIntegerVariables(0);
    setNumberOfVariables(points * 2);
  }

  @Override
  public ClusteringRepresentation createSolution() {
    return new ClusteringRepresentation(this);
  }

  @Override
  public Number getUpperBound(int index) {
    return new Double(100);
  }

  @Override
  public Number getLowerBound(int index) {
    return new Double(0);
  }

  @Override
  public void evaluate(ClusteringRepresentation solution) {

    List<double[]> data = new ArrayList<double[]>();
    for (int i = 0; i < points; i++) {
      data.add(new double[]{
        (Double)solution.getVariableValue(2*i), (Double)solution.getVariableValue(2*i+1)
      });
    }

    List<Cluster> clusters1 = CureDriver1.cluster(data, -1);
    List<Cluster> clusters2 = CureDriver2.cluster(data, -1);
    double cluster1Quality = 0.0, cluster2Quality = 0.0;

    for (Cluster c1 : clusters1) {
      double best = 0.0;
      for (Cluster c2 : clusters1) {
        if (c1 == c2) continue;
        double tmp = (c1.intraClusterDistance() + c2.intraClusterDistance())
                    / (c1.computeDistanceFromCluster(c2));
        if (tmp > best) best = tmp;
      }
      cluster1Quality += best;
    }
    for (Cluster c1 : clusters2) {
      double best = 0.0;
      for (Cluster c2 : clusters2) {
        if (c1 == c2) continue;
        double tmp = (c1.intraClusterDistance() + c2.intraClusterDistance())
                    / (c1.computeDistanceFromCluster(c2));
        if (tmp > best) best = tmp;
      }
      cluster2Quality += best;
    }
    solution.setObjective(0, cluster1Quality / clusters1.size() - cluster2Quality /  clusters2.size());
  }

}
