public enum Direction { //Enum for the movement
    NORTH(2), SOUTH(0), EAST(3), WEST(1);
    private final int frameLineNumber;

    private Direction(int frameLineNumber) {
        this.frameLineNumber = frameLineNumber;
    }

    public int getFrameLineNumber() {
        return frameLineNumber;
    }
}

