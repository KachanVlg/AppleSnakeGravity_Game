package core;

import utils.Direction;

public class Box extends MovableObstacle {

    public Box(MovementStrategy movementStrategy, Cell cell, World world) {
        super(cell, world);
        setMovementStrategy(movementStrategy);
        movementStrategy.setOwner(this);
        world.addGravityObject(this);
    }

    @Override
    public boolean tryToMove(GameEntity initiator, Direction direction) {
        return getMovementStrategy().moveOn(direction, initiator);
    }

}
