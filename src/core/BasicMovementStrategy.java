package core;

import utils.Direction;

public class BasicMovementStrategy extends MovementStrategy {

    public BasicMovementStrategy(World world) {
        super(world);
    }

    @Override
    public boolean moveOn(Direction direction, GameEntity initiator) {
        Cell targetCell = getWorld().getNeighbour(getOwner().getCell(), direction);
        if(targetCell.getObject() != null) {
            return false;
        }
        getOwner().unsetCell();
        getOwner().setCell(targetCell);
        return true;
    }
}
