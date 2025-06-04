import utils.Direction;

public interface MovementStrategy {
    boolean moveOn(Direction direction, World world, GameEntity initiator, ObjectOnField ownObject);
}
