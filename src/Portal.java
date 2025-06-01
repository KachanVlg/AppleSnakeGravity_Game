public class Portal extends StaticObstacle{
    public Portal(Cell cell, World world) {
        super(cell, world);
    }

    @Override
    public void interact(Snake initiator) {
        initiator.enterPortal();
    }
}
