package dddm;

import java.util.List;

import org.uma.jmetal.algorithm.multiobjective.spea2.SPEA2;
import org.uma.jmetal.algorithm.multiobjective.spea2.SPEA2Builder;
import org.uma.jmetal.util.evaluator.impl.MultithreadedSolutionListEvaluator;

class SPEA2Driver {

  public static void main(String[] args) {

    if (args.length < 3) {
      System.err.println("Usage: java SPEA2Driver population points iterations");
      System.exit(1);
    }
    int population = Integer.parseInt(args[0]);
    int points = Integer.parseInt(args[1]);
    int iterations = Integer.parseInt(args[2]);

    ClusteringProblem problem = new ClusteringProblem(points);
    ClusteringCrossover crossover = new ClusteringCrossover();
    ClusteringMutator mutation = new ClusteringMutator(0.015);

    SPEA2Builder<ClusteringRepresentation> builder = new SPEA2Builder<ClusteringRepresentation>(problem, crossover, mutation);
    builder.setPopulationSize(population);
    builder.setMaxIterations(iterations);

    int threads = 6;
    MultithreadedSolutionListEvaluator<ClusteringRepresentation> evaluator =
      new MultithreadedSolutionListEvaluator<ClusteringRepresentation>(threads, problem);
    builder.setSolutionListEvaluator(evaluator);

    SPEA2<ClusteringRepresentation> spea = builder.build();

    spea.run();

    List<ClusteringRepresentation> results = spea.getResult();

    ClusteringRepresentation best = results.get(0);
    for (ClusteringRepresentation instance : results) {
      if (instance.getObjective(0) > best.getObjective(0)) best = instance;
    }
    CureDriver1.printRepresentation(best);
    CureDriver2.printRepresentation(best);



    System.exit(0);
  }

}
