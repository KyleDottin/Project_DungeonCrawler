/**
 * Direction is an enum that allows us to associate each direction with numbers.
 */

public enum Direction {
    NORTH(2), SOUTH(0), EAST(3), WEST(1);
    private final int frameLineNumber;

    private Direction(int frameLineNumber) {
        this.frameLineNumber = frameLineNumber;
    }

    public int getFrameLineNumber() {
        return frameLineNumber;
    }
}

