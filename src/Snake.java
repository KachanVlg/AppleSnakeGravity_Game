import java.util.List;
import java.util.stream.IntStream;

public class Snake implements SnakeVisitor, {



    private final List<AbstractSegment> segments;
    private final Head head;
    private Segment tail;
    private final World world;
    private static final String FELL_MSG = "Snake fell";

    public Snake(List<AbstractSegment> segments, Head head, Segment tail, World world) {
        this.segments = segments;
        this.head = head;
        this.tail = tail;
        this.world = world;
    }

    public void moveOn(Direction dir) {
        Direction headDir = head.getDir();
        Cell headCell = head.getCell();
        Cell targetCell = world.getNeighbour(headCell, dir);

        if((headDir.opposite() == dir) || containsSegmentWith(targetCell)) {
            fireMovedOn();
            return;
        }

        ObjectOnField objectInDirectionOfMove = targetCell.getObject();
        if((objectInDirectionOfMove instanceof Acceptor acceptor)) {
            acceptor.accept(this);
            return;
        }

        head.moveTo(targetCell);
        fall();
    }

    private List<Cell> getSegmentsCells() {
        return segments.stream().map(AbstractSegment::getCell).toList();
    }


    private boolean containsSegmentWith(Cell cell) {
        return segments.stream().anyMatch(segment -> segment.getCell().equals(cell));
    }

    private void fall() {
        List<Cell> newSegmentCells = world.applyGravity(getSegmentsCells());
        if(newSegmentCells == null) {
            fireFell();
            return;
        }

        if(newSegmentCells == getSegmentsCells()) {
            fireMovedOn();
            return;
        }

        IntStream.range(0, segments.size()).forEach(i -> segments.get(i).resetCell(newSegmentCells.get(i)));
        fireMovedOn();
    }



    private void grow(Cell cell) {
        Segment oldTail = tail;
        tail = new Segment(cell);
        oldTail.setNext(tail);
    }

    @Override
    public void visit(Apple apple) {
        Cell targetCell = apple.getCell();
        apple.eat();
        Cell growthCell = tail.getCell();
        head.moveTo(targetCell);
        grow(growthCell);
        fall();
    }

    @Override
    public void visit(Portal portal) {
        fireEnteredPortal();
    }

    @Override
    public void visit(Block block) {
        fireMovedOn();
    }

    private void fireMovedOn() {

    }

    private void fireEnteredPortal() {

    }

    private void fireFell() {

    }
}
