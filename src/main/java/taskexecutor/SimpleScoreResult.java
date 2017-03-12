package taskexecutor;

/**
 * Task result - simple score result
 */
public class SimpleScoreResult {
    private int sequenceId;
    private double score = 0;


    public SimpleScoreResult(int genomeId, double score) {
        super();
        this.sequenceId = genomeId;
        this.score = score;
    }

    public double getScore() {
        return score;
    }

    public int getSequenceId() {
        return sequenceId;
    }


}