import javax.swing.*;
import java.awt.*;

public class Portal extends StaticObstacle{

    private static Image portalImage;

    static {
        try {
            portalImage = new ImageIcon(Apple.class.getResource("/ui/images/Portal.png")).getImage();
        } catch (Exception e) {
            System.err.println("Ошибка загрузки Portal.png: " + e.getMessage());
        }
    }


    public Portal(Cell cell, World world) {
        super(cell, world);
    }

    @Override
    public void interact(Snake initiator) {
        initiator.enterPortal();
    }

    @Override
    public void draw(Graphics g, int x, int y, int cellSize) {
        if (portalImage != null) {
            g.drawImage(portalImage, x, y, cellSize, cellSize, null);
        } else {
            g.setColor(Color.MAGENTA);
            g.fillOval(x, y, cellSize, cellSize);
        }
    }
}
