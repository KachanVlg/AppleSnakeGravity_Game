import utils.Direction;

public class SupportedOnlyByBoxStrategy extends MovementStrategy {

    public SupportedOnlyByBoxStrategy(World world) {
        super(world);
    }

    @Override
    public boolean moveOn(Direction direction, GameEntity initiator) {
        Cell currentCell = getOwner().getCell();
        Cell below = getWorld().getNeighbour(currentCell, Direction.DOWN);

        // Движение разрешено только если под объектом находится другая коробка
        if (below != null && below.getObject() instanceof Box) {
            Cell target = getWorld().getNeighbour(currentCell, direction);
            if (target != null && target.getObject() == null) {
                getOwner().resetCell(target);
                return true;
            }
        }
        return false;
    }
}