package core;

import events.GameListener;
import events.SnakeListener;
import utils.Direction;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class GameModel implements SnakeListener {


    private boolean isFinished;
    private boolean isStarted;
    private boolean hasWon;
    private final World world;


    public GameModel() {
        isFinished = false;
        isStarted = false;
        hasWon = false;
        world = new World();
    }

    public boolean hasWon() {
        return this.hasWon;
    }

    public void start() {
        isStarted = true;
        world.getSnakeController().addListener(this);
    }

    public void moveSnakeOn(Direction dir) {
        if(!isFinished) {
            world.getSnakeController().moveOn(dir);
        }
    }

    private void finishGame() {
        isFinished = true;
    }

    public boolean isFinished() {
        return isFinished;
    }

    public World getWorld() {
        return world;
    }



    @Override
    public void portalIsEntered() {
        world.applyGravityAllSingleObjects();
        hasWon = true;
        finishGame();
        fireEnteredPortal();
    }

    @Override
    public void fell() {
        finishGame();
        fireFell();
    }

    @Override
    public void movedOn() {
        fireMovedOn();
    }

    @Override
    public void eatApple(Segment segment, Cell cell) {
        fireEatApple(segment, cell);
    }

    @Override
    public void gravityApplied() {
        world.applyGravityAllSingleObjects();
        fireGravityApplied();
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

    private Set<GameListener> listeners = new HashSet<>();

    public void addListener(GameListener listener) {
        listeners.add(listener);
    }

    private void fireEatApple(Segment segment, Cell cell) {
        listeners.stream().forEach(listeners -> listeners.eatApple(segment, cell));
    }

    private void fireGravityApplied() {
        listeners.stream().forEach(listener -> listener.gravityApplied());
    }


    public Collection<GameListener> getListeners() {
        return listeners;
    }
}
