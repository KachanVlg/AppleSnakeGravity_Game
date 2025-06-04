import utils.Direction;

import java.awt.*;

public class Box extends MovableObstacle{


    private MovementStrategy movementStrategy;

    public Box(MovementStrategy movementStrategy, Cell cell, World world) {
        super(cell, world);
        this.movementStrategy = movementStrategy;
        world.addGravityObject(this);
    }

    @Override
    public boolean tryToMove(GameEntity initiator, Direction direction) {
        return movementStrategy.moveOn(direction, getWorld(), initiator, this);
    }

    public void setMovementStrategy(MovementStrategy movementStrategy) {
        this.movementStrategy = movementStrategy;
    }

    @Override
    public void draw(Graphics g, int x, int y, int cellSize) {
        g.setColor(Color.ORANGE);
        g.fillRect(x, y, cellSize, cellSize);
        g.setColor(Color.BLACK);
        g.drawRect(x, y, cellSize, cellSize);
    }

    public MovementStrategy getMovementStrategy() {
        return movementStrategy;
    }
}
