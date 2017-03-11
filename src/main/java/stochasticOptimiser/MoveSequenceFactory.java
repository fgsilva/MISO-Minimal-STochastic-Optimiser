package stochasticOptimiser;

import java.util.Random;

/**
 * Created by fernando on 3/8/17.
 */
public class MoveSequenceFactory {

    private final int sequenceLength;
    private final byte maxValue;
    private final Random random;

    public MoveSequenceFactory(int sequenceLength, byte maxValue, Random random) {
        this.sequenceLength = sequenceLength;
        this.maxValue = maxValue;
        this.random = new Random();
    }

    public MoveSequence createRandomSequence() {
        MoveSequence g = new MoveSequence(this.sequenceLength, -1);
        byte[] values = g.getValues();
        int bound = maxValue + 1;
        for (int i = 0; i < values.length; i++) {
            values[i] = (byte) random.nextInt(bound);
        }

        return g;
    }

}