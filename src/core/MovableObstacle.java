package core;

import utils.Direction;

public abstract class MovableObstacle extends ObjectOnField {

    private MovementStrategy movementStrategy;

    public MovableObstacle(Cell cell, World world) {
        super(cell, world);
    }

    public abstract boolean tryToMove(GameEntity initiator, Direction direction);

    public void setMovementStrategy(MovementStrategy movementStrategy) {
        this.movementStrategy = movementStrategy;
    }

    public MovementStrategy getMovementStrategy() {
        return movementStrategy;
    }


}
