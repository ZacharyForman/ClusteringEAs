package dddm;

import org.uma.jmetal.solution.impl.DefaultIntegerDoubleSolution;

public class ClusteringRepresentation extends DefaultIntegerDoubleSolution {

  public ClusteringRepresentation(ClusteringProblem problem) {
    super(problem);
    initializeSolution();
  }

  public ClusteringRepresentation(ClusteringRepresentation solution) {
    super((ClusteringProblem)solution.problem);
    for (int i = 0; i < getNumberOfDoubleVariables(); i++) {
      setVariableValue(i, solution.getVariableValue(i));
    }
    setObjective(0, solution.getObjective(0));
  }

  public ClusteringRepresentation makeCopy() {
    return new ClusteringRepresentation(this);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append(String.format("%d\n", getNumberOfDoubleVariables()/2));
    for (int i = 0; i < getNumberOfDoubleVariables()/2; i++) {
      sb.append(String.format("%f %f\n",
                getVariableValue(2*i).doubleValue(),
                getVariableValue(2*i+1).doubleValue()));
    }
    return sb.toString();
  }

  private void initializeSolution() {
    java.util.Random gaussianRand = new java.util.Random();
    int n = getNumberOfDoubleVariables();

    for (int i = 0; i < 5; i++) {
      double d1 = randomGenerator.nextDouble(getLowerBound(i).doubleValue(),
                                             getUpperBound(i).doubleValue());
      double d2 = randomGenerator.nextDouble(getLowerBound(i).doubleValue(),
                                             getUpperBound(i).doubleValue());
      for (int j = 0; j < n/10; j++) {
        double off1 = gaussianRand.nextGaussian();
        double off2 = gaussianRand.nextGaussian();
        setVariableValue(i*(n/5)+2*j, new Double(d1 + off1));
        setVariableValue(i*(n/5)+2*j+1, new Double(d2 + off2));
      }
    }
  }

}
