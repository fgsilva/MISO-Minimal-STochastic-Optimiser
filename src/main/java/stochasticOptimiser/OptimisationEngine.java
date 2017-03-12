package stochasticOptimiser;

import game.Direction;
import game.DirectionMapper;
import game.Game2048;
import game.Tile;
import taskexecutor.ParallelTaskExecutor;
import taskexecutor.SimpleScoreResult;
import taskexecutor.SingleTask;
import taskexecutor.TaskExecutor;

import java.util.Random;

/**
 * Optimisation engine
 */
public class OptimisationEngine {

    private final Random random;

    private MoveSequencePopulation population;
    private TaskExecutor taskExecutor = new ParallelTaskExecutor();
    private Tile[] gameConfiguration;


    public OptimisationEngine(int populationSize, int genomeLength, Random random, MoveSequenceFactory factory) {
        this.random = random;
        setupPopulation(populationSize, factory);
    }

    private void setupPopulation(int populationSize, MoveSequenceFactory factory) {
        this.population = new MoveSequencePopulation(populationSize, factory);
    }

    public Direction[] getCurrentBestSequence() {
        MoveSequence best = this.population.getBestSequence();
        byte[] values = best.getValues();
        Direction[] moves = new Direction[values.length];
        DirectionMapper mapper = new DirectionMapper();
        for (int i = 0; i < moves.length; i++) {
            moves[i] = mapper.map(values[i]);
        }

        return moves;
    }

    public void executeLearning() {
        this.population.createRandomPopulation();

        MoveSequence g;
        int totalSequences = 0;

        while ((g = population.getNextSequenceToEvaluate()) != null) {
            SingleTask task = new SingleTask(g, getGameCopy());
            taskExecutor.addTask(task);

            totalSequences++;
        }

        while (totalSequences-- > 0) {
            SimpleScoreResult result = taskExecutor.getResult();
            population.setEvaluationResultForId(result.getSequenceId(), result.getScore());
        }


    }

    public Game2048 getGameCopy() {
        Tile[] tiles = new Tile[this.gameConfiguration.length];
        for (int i = 0; i < tiles.length; i++)
            tiles[i] = new Tile(this.gameConfiguration[i].getValue());

        return new Game2048(tiles);
    }


    public void setConfiguration(Tile[] configuration) {
        this.gameConfiguration = new Tile[configuration.length];
        for (int i = 0; i < this.gameConfiguration.length; i++) {
            gameConfiguration[i] = new Tile(configuration[i].getValue());
        }
    }

    public MoveSequencePopulation getPopulation() {
        return this.population;
    }
}
