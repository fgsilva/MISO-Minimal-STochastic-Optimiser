package stochasticOptimiser;

/**
 * Created by fernando on 08-03-2017.
 */
public class MoveSequencePopulation {

    private MoveSequence[] population;
    private MoveSequenceFactory factory;
    private MoveSequence bestSequence;

    private double bestScore;
    private int nextSequenceToEvaluate;


    public MoveSequencePopulation(int populationSize, MoveSequenceFactory factory) {
        this.population = new MoveSequence[populationSize];
        this.factory = factory;
    }


    protected void resetGeneration() {
        bestScore = -1e10;
        nextSequenceToEvaluate = 0;
    }

    public void createRandomPopulation() {
        for (int i = 0; i < population.length; i++) {
            MoveSequence g = factory.createRandomSequence();
            g.setID(i);
            population[i] = g;
        }

        resetGeneration();
    }

    public MoveSequence getNextSequenceToEvaluate() {

        if (nextSequenceToEvaluate < population.length) {
            return population[nextSequenceToEvaluate++];
        } else {
            return null;
        }
    }

    public void setEvaluationResultForId(int pos, double fitness) {
        if (pos >= population.length) {
            throw new java.lang.RuntimeException("No such position: " + pos
                    + " on the population");
        }

        population[pos].setScore(fitness);

        if (fitness > bestScore) {
            bestSequence = population[pos];
            bestScore = fitness;
        }

    }

    public MoveSequence getBestSequence() {
        return bestSequence;
    }

}
