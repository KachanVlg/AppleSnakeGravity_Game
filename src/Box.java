
public class Box extends MovableObstacle{
    private MovementStrategy movementStrategy;

    public Box(MovementStrategy movementStrategy, Cell cell, World world) {
        super(cell, world);
        this.movementStrategy = movementStrategy;
    }

    @Override
    public boolean tryToMove(GameEntity initiator, Direction direction) {
        return movementStrategy.moveOn(direction, getWorld(), initiator, this);
    }

    public void setMovementStrategy(MovementStrategy movementStrategy) {
        this.movementStrategy = movementStrategy;
    }
}
