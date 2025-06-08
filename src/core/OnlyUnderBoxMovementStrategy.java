package core;
import core.*;
import utils.Direction;

import java.awt.*;

public class OnlyUnderBoxMovementStrategy extends MovementStrategy {

    public OnlyUnderBoxMovementStrategy(World world) {
        super(world);
    }

    @Override
    public boolean moveOn(Direction direction, GameEntity initiator) {
        Cell aboveCell = getWorld().getNeighbour(getOwner().getCell(), Direction.UP);
        boolean isBoxAbove = aboveCell != null && aboveCell.getObject() instanceof Box;

        Cell targetCell = getWorld().getNeighbour(getOwner().getCell(), direction);
        if (isBoxAbove && targetCell != null && targetCell.getObject() == null) {
            getOwner().unsetCell();
            getOwner().setCell(targetCell);
            return true;
        }

        return false;
    }
}

