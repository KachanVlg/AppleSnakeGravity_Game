import utils.Direction;

import java.time.Instant;

public class TimeLimitedMovementStrategy extends MovementStrategy {
    private final long allowedMillis;
    private final Instant startTime;

    public TimeLimitedMovementStrategy(long allowedSeconds, World world) {
        super(world);
        this.allowedMillis = allowedSeconds * 1000;
        this.startTime = Instant.now();
    }

    @Override
    public boolean moveOn(Direction direction, GameEntity initiator) {
        if (isExpired()) return false;

        Cell current = getOwner().getCell();
        Cell target = getWorld().getNeighbour(current, direction);

        if (target != null && target.getObject() == null) {
            getOwner().resetCell(target);
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
