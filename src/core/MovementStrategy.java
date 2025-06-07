import utils.Direction;

public abstract class  MovementStrategy {

    private final World world;
    private MovableObstacle owner;


    public MovementStrategy(World world) {
        this.world = world;
    }

    public abstract boolean moveOn(Direction direction, GameEntity initiator);

    protected World getWorld() {
        return world;
    }

    protected MovableObstacle getOwner() {
        return owner;
    }

    protected void setOwner(MovableObstacle owner) {
        this.owner = owner;
    }


}
