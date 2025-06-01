public class Apple extends StaticObstacle{

    public Apple(Cell cell, World world) {
        super(cell, world);
    }

    @Override
    public void interact(Snake initiator) {
        Cell oldCell = unsetCell();
        initiator.grow(oldCell);
    }
}
