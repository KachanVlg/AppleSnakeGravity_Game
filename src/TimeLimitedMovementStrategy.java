import utils.Direction;

import java.time.Instant;

public class TimeLimitedMovementStrategy implements MovementStrategy {
    private final long allowedMillis;
    private final Instant startTime;

    public TimeLimitedMovementStrategy(long allowedSeconds) {
        this.allowedMillis = allowedSeconds * 1000;
        this.startTime = Instant.now();
    }

    @Override
    public boolean moveOn(Direction direction, World world, GameEntity initiator, ObjectOnField ownObject) {
        if (isExpired()) return false;

        Cell current = ownObject.getCell();
        Cell target = world.getNeighbour(current, direction);

        if (target != null && target.getObject() == null) {
            ownObject.resetCell(target);
            return true;
        }

        return false;
    }

    public boolean isExpired() {
        long elapsed = Instant.now().toEpochMilli() - startTime.toEpochMilli();
        return elapsed > allowedMillis;
    }

    public long getTimeLeftMillis() {
        long elapsed = Instant.now().toEpochMilli() - startTime.toEpochMilli();
        return Math.max(0, allowedMillis - elapsed);
    }
}
