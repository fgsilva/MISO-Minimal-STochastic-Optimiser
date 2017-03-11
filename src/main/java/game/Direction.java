package game;

public enum Direction {
    /**
     * Move Up
     */
    UP(0),

    /**
     * Move Right
     */
    RIGHT(1),

    /**
     * Move Down
     */
    DOWN(2),

    /**
     * Move Left
     */
    LEFT(3);


    /**
     * The numeric code of the status
     */
    private final int code;

    Direction(final int code) {
        this.code = code;
    }

    /**
     * Getter for code.
     *
     * @return
     */
    public int getCode() {
        return code;
    }

}
