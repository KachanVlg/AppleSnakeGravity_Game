package core;

import events.SnakeListener;
import utils.Direction;

import java.awt.*;
import java.util.*;
import java.util.List;
import javax.swing.Timer;

public class Snake extends GameEntity implements SnakeController {
    private final List<AbstractSegment> segments = new ArrayList<>();
    private final Head head;
    private AbstractSegment tail;
    private static final String FELL_MSG = "core.Snake fell";
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

    public Head getHead() {
        return head;
    }

    public List<AbstractSegment> getSegments() {
        return segments;
    }
    public void moveOn(Direction dir) {
        planDir = dir;
        Direction headDir = head.getDir();
        Cell headCell = head.getCell();
        Cell targetCell = getWorld().getNeighbour(headCell, dir);

        if((headDir.opposite() == dir) || containsSegmentWith(targetCell) || targetCell == null) {
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
            fireMovedOn();

            Timer timer = new Timer(200, e -> fall());
            timer.setRepeats(false);
            timer.start();
        }
    }

    private boolean containsSegmentWith(Cell cell) {
        return segments.stream().anyMatch(segment -> segment.getCell().equals(cell));
    }

    private void fall() {
        boolean isOk = getWorld().applyGravity(segments);
        fireGravityApplied();
        if(!isOk) {
            segments.forEach(segment -> segment.setFell(true));
            fireFell();
        }
    }

    public void enterPortal(Cell portalCell) {
        segments.forEach(segment -> segment.setEnteredPortal(portalCell));
        fireEnteredPortal();
    }



    public void grow(Cell cell, Apple apple) {
        AbstractSegment oldTail = tail;
        Cell growthCell = oldTail.getCell();
        head.moveTo(cell);
        head.resetDir(planDir);
        tail = new Segment(growthCell, getWorld());
        oldTail.setNext(tail);
        segments.addLast(tail);
        fireEatApple((Segment)tail, apple);
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

    private void fireEatApple(Segment segment, Apple apple) {
        listeners.stream().forEach(listeners -> listeners.eatApple(segment, apple));
    }

    private Set<SnakeListener> listeners = new HashSet<>();

    public void addListener(SnakeListener listener) {
        listeners.add(listener);
    }


    public Collection<SnakeListener> getListeners() {
        return listeners;
    }

    public void fireGravityApplied() {
        listeners.stream().forEach(listener -> listener.gravityApplied());
    }
}
