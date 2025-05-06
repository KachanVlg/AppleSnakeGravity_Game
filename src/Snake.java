import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.IntStream;

public class Snake implements SnakeVisitor, SnakeController {



    private final List<AbstractSegment> segments = new ArrayList<>();
    private final Head head;
    private AbstractSegment tail;
    private final World world;
    private static final String FELL_MSG = "Snake fell";

    public Snake(List<Cell> segmentCells, World world, Direction headDir) {

        //Инициализируем хвост
        tail = new Segment(segmentCells.getLast());
        segments.addFirst(tail);
        segmentCells.removeLast();

        //Инициализируем тело
        int size = segmentCells.size()-2;
        for(int i = 0; i < size; i++) {
            AbstractSegment curNext = segments.getFirst();
            AbstractSegment curSegment = new Segment(segmentCells.getLast(), curNext);
            segments.addFirst(curSegment);
            segmentCells.removeLast();
        }

        //Инициализируем голову
        head = new Head(segmentCells.getFirst(), headDir, segments.getFirst());
        segments.addFirst(head);

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

    private boolean containsSegmentWith(Cell cell) {
        return segments.stream().anyMatch(segment -> segment.getCell().equals(cell));
    }

    private void fall() {
        boolean isOk = world.applyGravity(segments);
        if(isOk) {
            fireMovedOn();
        } else {
            fireFell();
        }
    }



    private void grow(Cell cell) {
        AbstractSegment oldTail = tail;
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
        listeners.stream().forEach(listener -> listener.movedOn());
    }

    private void fireEnteredPortal() {
        listeners.stream().forEach(listener -> listener.portalIsEntered());
    }

    private void fireFell() {
        listeners.stream().forEach(listener -> listener.fell());
    }

    private Set<SnakeListener> listeners = new HashSet<>();

    public void addListener(SnakeListener listener) {
        listeners.add(listener);
    }

}
