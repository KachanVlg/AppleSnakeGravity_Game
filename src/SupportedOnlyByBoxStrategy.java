import utils.Direction;

import java.awt.*;

public class SupportedOnlyByBoxStrategy implements MovementStrategy {
    @Override
    public boolean moveOn(Direction direction, World world, GameEntity initiator, ObjectOnField ownObject) {
        Cell currentCell = ownObject.getCell();
        Cell below = world.getNeighbour(currentCell, Direction.DOWN);

        // Движение разрешено только если под объектом находится другая коробка
        if (below != null && below.getObject() instanceof Box) {
            Cell target = world.getNeighbour(currentCell, direction);
            if (target != null && target.getObject() == null) {
                ownObject.resetCell(target);
                return true;
            }
        }

        return false;
    }

    @Override
    public Color getColor() {
        return Color.PINK;
    }
}