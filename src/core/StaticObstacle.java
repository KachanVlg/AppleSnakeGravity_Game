public abstract class StaticObstacle extends ObjectOnField{
    public StaticObstacle(Cell cell, World world) {
        super(cell, world);
    }

    public abstract void interact(Snake initiator);
}
