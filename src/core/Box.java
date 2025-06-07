import utils.Direction;

import java.awt.*;

public class Box extends MovableObstacle{


    private MovementStrategy movementStrategy;

    public Box(MovementStrategy movementStrategy, Cell cell, World world) {
        super(cell, world);
        this.movementStrategy = movementStrategy;
        movementStrategy.setOwner(this);
        world.addGravityObject(this);
    }

    @Override
    public boolean tryToMove(GameEntity initiator, Direction direction) {
        return movementStrategy.moveOn(direction, initiator);
    }

    public void setMovementStrategy(MovementStrategy movementStrategy) {
        this.movementStrategy = movementStrategy;
    }

    public MovementStrategy getMovementStrategy() {
        return movementStrategy;
    }
}
