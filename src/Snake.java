import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Snake extends GameEntity implements SnakeController {
    private final List<AbstractSegment> segments = new ArrayList<>();
    private final Head head;
    private AbstractSegment tail;
    private static final String FELL_MSG = "Snake fell";
    private Direction planDir;

    public Snake(List<Cell> segmentCells, World world, Direction headDir) {

        super(world);

        //Инициализируем хвост
        tail = new Segment(segmentCells.getLast(), world);
        segments.addFirst(tail);
        segmentCells.removeLast();

        //Инициализируем тело
        int size = segmentCells.size()-1;
        for(int i = 0; i < size; i++) {
            AbstractSegment curNext = segments.getFirst();
            AbstractSegment curSegment = new Segment(segmentCells.getLast(), curNext, world);
            segments.addFirst(curSegment);
            segmentCells.removeLast();
        }

        //Инициализируем голову
        head = new Head(segmentCells.getFirst(), headDir, segments.getFirst(), world);
        segments.addFirst(head);
    }

    public void moveOn(Direction dir) {
        planDir = dir;
        Direction headDir = head.getDir();
        Cell headCell = head.getCell();
        Cell targetCell = getWorld().getNeighbour(headCell, dir);

        if((headDir.opposite() == dir) || containsSegmentWith(targetCell)) {
            fireMovedOn();
            return;
        }

        ObjectOnField objectInDirectionOfMove = targetCell.getObject();
        if((objectInDirectionOfMove instanceof StaticObstacle obstacle)) {
            obstacle.interact(this);
            return;
        }

        if((objectInDirectionOfMove instanceof MovableObstacle obstacle) && obstacle.tryToMove(this, dir) || objectInDirectionOfMove == null) {
            head.moveTo(targetCell);
            head.resetDir(planDir);
            fall();
        }
    }

    private boolean containsSegmentWith(Cell cell) {
        return segments.stream().anyMatch(segment -> segment.getCell().equals(cell));
    }

    private void fall() {
        boolean isOk = getWorld().applyGravity(segments);
        if(isOk) {
            fireMovedOn();
        } else {
            fireFell();
        }
    }

    public void enterPortal() {
        fireEnteredPortal();
    }



    public void grow(Cell cell) {
        AbstractSegment oldTail = tail;
        Cell growthCell = oldTail.getCell();
        head.moveTo(cell);
        head.resetDir(planDir);
        tail = new Segment(growthCell, getWorld());
        oldTail.setNext(tail);
        segments.addLast(tail);
    }

    public int getWeight() {
        return segments.size();
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
