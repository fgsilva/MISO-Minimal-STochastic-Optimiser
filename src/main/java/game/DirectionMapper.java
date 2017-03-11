package game;

/**
 * Created by fernando on 09-03-2017.
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
