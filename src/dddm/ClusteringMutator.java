package dddm;

import org.uma.jmetal.operator.MutationOperator;

import org.uma.jmetal.util.pseudorandom.JMetalRandom;

/**
 * Mutates by adjusting a point's location by a gaussian amount.
 * This is good because it means we can explore well locally.
 */
public class ClusteringMutator implements MutationOperator<ClusteringRepresentation> {
  private java.util.Random gaussianRand;
  private JMetalRandom randomGenerator;
  private double probability;

  public ClusteringMutator(double probability) {
    this.probability = probability;
    this.randomGenerator = JMetalRandom.getInstance();
    this.gaussianRand = new java.util.Random();

  }

  @Override
  public ClusteringRepresentation execute(ClusteringRepresentation ind) {
    ClusteringRepresentation mutatedIndividual = ind.makeCopy();
    int n = mutatedIndividual.getNumberOfIntegerVariables();
    for (int i = 0; i < mutatedIndividual.getNumberOfDoubleVariables()/2; i++) {
      if (randomGenerator.nextDouble() <= probability) {
        double dx = gaussianRand.nextGaussian();
        double dy = gaussianRand.nextGaussian();

        double x = ind.getVariableValue(n+2*i).doubleValue();
        double y = ind.getVariableValue(n+2*i+1).doubleValue();

        if ((x + dx) > ind.getUpperBound(n+2*i).doubleValue()) {
          mutatedIndividual.setVariableValue(n+2*i, 2*ind.getUpperBound(n+2*i).doubleValue()-x-dx);
        } else if ((x + dx) < ind.getLowerBound(n+2*i).doubleValue()) {
          mutatedIndividual.setVariableValue(n+2*i, -x-dx);
        } else {
          mutatedIndividual.setVariableValue(n+2*i, x+dx);
        }

        if ((y + dy) > ind.getUpperBound(n+2*i+1).doubleValue()) {
          mutatedIndividual.setVariableValue(n+2*i+1, 2*ind.getUpperBound(n+2*i+1).doubleValue()-y-dy);
        } else if ((y + dy) < ind.getLowerBound(n+2*i+1).doubleValue()) {
          mutatedIndividual.setVariableValue(n+2*i+1, -y-dy);
        } else {
          mutatedIndividual.setVariableValue(n+2*i+1, y+dy);
        }
      }
    }
    return mutatedIndividual;
  }

}
