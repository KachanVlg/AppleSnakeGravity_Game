public class Head extends AbstractSegment{

    private Direction dir;

    public Head(Cell cell, Direction dir) {
        super(cell);
        this.dir = dir;
    }

    public Head(Cell cell, Direction dir, AbstractSegment next) {
        super(cell, next);
        this.dir = dir;
    }

    public Direction getDir() {
        return dir;
    }

    public void resetDir(Direction dir) {
        this.dir = dir;
    }
}
