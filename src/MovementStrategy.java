import utils.Direction;

import java.awt.*;

public interface MovementStrategy {
    boolean moveOn(Direction direction, World world, GameEntity initiator, ObjectOnField ownObject);
    Color getColor();
}
