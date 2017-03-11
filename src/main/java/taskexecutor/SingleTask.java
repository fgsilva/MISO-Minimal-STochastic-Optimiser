package taskexecutor;

import game.Direction;
import game.DirectionMapper;
import game.Game2048;
import stochasticOptimiser.MoveSequence;

/**
 * Created by fernando on 3/8/17.
 */
public class SingleTask {

    private final Game2048 g;
    private double score = 0;
    private MoveSequence sequence;
    private DirectionMapper mapper;

    private int trials = 100;

    public SingleTask(MoveSequence sequence, Game2048 game) {
        this.sequence = sequence;
        this.g = game;
        mapper = new DirectionMapper();
    }

    public void run() {
        boolean mayLose = false;

        for (int t = 0; t < trials; t++) {
             Game2048 game = new Game2048(g.getTiles().clone());

            byte[] b = sequence.getValues();
            for (int i = 0; i < b.length; i++) {
                byte move = b[i];

                Direction toPlay = mapper.map(move);
                game.play(toPlay);
            }

            score += game.getMyScore();
        }

        score /= trials;
    }


    public SimpleScoreResult getResult() {
        SimpleScoreResult fr = new SimpleScoreResult(sequence.getID(), score);
        return fr;
    }
}
