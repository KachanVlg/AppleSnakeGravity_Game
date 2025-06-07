import utils.Direction;

public abstract class MovableObstacle extends ObjectOnField{
    public MovableObstacle(Cell cell, World world) {
        super(cell, world);
    }

    public abstract boolean tryToMove(GameEntity initiator, Direction direction);
}
