package utils;

public class BoxDto {
    private MyPoint point;
    private MovementStrategyEnum movementStrategy;

    public BoxDto() {}

    public BoxDto(MyPoint point, MovementStrategyEnum movementStrategy) {
        this.point = point;
        this.movementStrategy = movementStrategy;
    }

    public MyPoint getPoint() {
        return point;
    }

    public void setPoint(MyPoint point) {
        this.point = point;
    }

    public MovementStrategyEnum getMovementStrategy() {
        return movementStrategy;
    }

    public void setMovementStrategy(MovementStrategyEnum movementStrategy) {
        this.movementStrategy = movementStrategy;
    }
}
