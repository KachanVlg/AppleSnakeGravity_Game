import utils.Direction;

import java.awt.*;

public class BasicMovementStrategy implements MovementStrategy{
    @Override
    public boolean moveOn(Direction direction, World world, GameEntity initiator, ObjectOnField ownObject) {
        Cell targetCell = world.getNeighbour(ownObject.getCell(), direction);
        if(targetCell.getObject() != null) {
            return false;
        }
        ownObject.unsetCell();
        ownObject.setCell(targetCell);
        return true;
    }

    @Override
    public Color getColor() {
        return Color.orange;
    }
}
