import java.awt.*;

public class Portal extends StaticObstacle{
    public Portal(Cell cell, World world) {
        super(cell, world);
    }

    @Override
    public void interact(Snake initiator) {
        initiator.enterPortal();
    }

    @Override
    public void draw(Graphics g, int x, int y, int cellSize) {
        g.setColor(Color.MAGENTA);
        g.fillOval(x, y, cellSize, cellSize);
    }
}
