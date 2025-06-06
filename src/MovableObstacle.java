import utils.Direction;

public abstract class MovableObstacle extends ObjectOnField{

    private int queueNumber = 0;

    public int getQueueNumber() {
        return queueNumber;
    }
    public void setQueueNumber(int queueNumber) {
        this.queueNumber = queueNumber;
    }

    public MovableObstacle(Cell cell, World world) {
        super(cell, world);
    }

    public abstract boolean tryToMove(GameEntity initiator, Direction direction);
}
