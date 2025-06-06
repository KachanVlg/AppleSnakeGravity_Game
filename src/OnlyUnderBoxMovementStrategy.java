import utils.Direction;

import java.awt.*;

public class OnlyUnderBoxMovementStrategy implements MovementStrategy {
    @Override
    public boolean moveOn(Direction direction, World world, GameEntity initiator, ObjectOnField ownObject) {

        Cell aboveCell = world.getNeighbour(ownObject.getCell(), Direction.UP);
        boolean isBoxAbove = aboveCell != null && aboveCell.getObject() instanceof Box;

        Cell targetCell = world.getNeighbour(ownObject.getCell(), direction);
        if (isBoxAbove && targetCell != null && targetCell.getObject() == null) {
            ownObject.unsetCell();
            ownObject.setCell(targetCell);
            return true;
        }

        return false;
    }

    @Override
    public Color getColor() {
        return Color.RED;
    }
}
