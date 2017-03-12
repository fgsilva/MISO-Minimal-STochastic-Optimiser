package game;

/**
 * Direction mapper. Maps a byte (0, 1, 2, 3) to a Direction.
 */
public class DirectionMapper {

    public Direction map(byte move) {
        switch (move) {
            case 0:
                return Direction.UP;
            case 1:
                return Direction.RIGHT;
            case 2:
                return Direction.DOWN;
            case 3:
                return Direction.LEFT;
            default:
                return null;
        }
    }
}
