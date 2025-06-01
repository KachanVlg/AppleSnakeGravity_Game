import java.awt.*;

public class Apple extends StaticObstacle{

    public Apple(Cell cell, World world) {
        super(cell, world);
    }

    @Override
    public void interact(Snake initiator) {
        Cell oldCell = unsetCell();
        initiator.grow(oldCell);
    }

    @Override
    public void draw(Graphics g, int x, int y, int cellSize) {
        g.setColor(Color.RED);
        g.fillOval(x, y, cellSize, cellSize);
    }
}
