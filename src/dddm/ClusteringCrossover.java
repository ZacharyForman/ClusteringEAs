package dddm;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.uma.jmetal.operator.CrossoverOperator;

import org.uma.jmetal.util.pseudorandom.JMetalRandom;

/**
 * N-point crossover. Good because we can potentially combine good
 * city locations.
 */
public class ClusteringCrossover implements CrossoverOperator<ClusteringRepresentation> {

  class Point {
    public double x;
    public double y;

    Point (double x, double y) {
      this.x = x;
      this.y = y;
    }

    @Override
    public boolean equals(Object o) {
      if (! (o instanceof Point)) return false;
      Point r = (Point) o;
      return this.x == r.x && this.y == r.y;
    }

    @Override
    public int hashCode() {
      long bits = Double.doubleToLongBits(x);
      bits ^= Double.doubleToLongBits(y) * 31;
      return (((int) bits) ^ ((int) (bits >> 32)));
    }
  }


  private JMetalRandom randomGenerator;

  public ClusteringCrossover() {
    this.randomGenerator = JMetalRandom.getInstance();
  }

  @Override
  public List<ClusteringRepresentation> execute(List<ClusteringRepresentation> parents) {
    List<ClusteringRepresentation> children = new ArrayList<ClusteringRepresentation>();
    ClusteringRepresentation parent1 = parents.get(0);
    ClusteringRepresentation parent2 = parents.get(1);
    ClusteringRepresentation child1 = parent1.makeCopy();
    ClusteringRepresentation child2 = parent2.makeCopy();

    HashSet<Point> p1set = new HashSet<Point>();
    HashSet<Point> p2set = new HashSet<Point>();

    int n = parent1.getNumberOfIntegerVariables();

    for (int i = 0; i < parent1.getNumberOfDoubleVariables()/2; i++) {
      Point p1 = new Point(child1.getVariableValue(n+2*i).doubleValue(), child1.getVariableValue(n+2*i+1).doubleValue());
      Point p2 = new Point(child2.getVariableValue(n+2*i).doubleValue(), child2.getVariableValue(n+2*i+1).doubleValue());
      p1set.add(p1);
      p2set.add(p2);
    }

    for (int i = 0; i < parent1.getNumberOfDoubleVariables()/2; i++) {
      Point p1 = new Point(child1.getVariableValue(n+2*i).doubleValue(), child1.getVariableValue(n+2*i+1).doubleValue());
      Point p2 = new Point(child2.getVariableValue(n+2*i).doubleValue(), child2.getVariableValue(n+2*i+1).doubleValue());

      if (randomGenerator.nextDouble() <= 0.5 &&
          !p1set.contains(p2) && !p2set.contains(p1)) {
        double tx = child1.getVariableValue(n+2*i).doubleValue();
        double ty = child1.getVariableValue(n+2*i+1).doubleValue();
        child1.setVariableValue(n+2*i, child2.getVariableValue(n+2*i).doubleValue());
        child1.setVariableValue(n+2*i+1, child2.getVariableValue(n+2*i+1).doubleValue());
        child2.setVariableValue(n+2*i, tx);
        child2.setVariableValue(n+2*i+1, ty);
      }
    }
    children.add(child1);
    children.add(child2);
    return children;
  }
}
