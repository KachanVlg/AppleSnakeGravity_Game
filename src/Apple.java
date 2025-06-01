import javax.swing.*;
import java.awt.*;

public class Apple extends StaticObstacle{
    private static Image appleImage;

    static {
        try {
            appleImage = new ImageIcon(Apple.class.getResource("/Apple.png")).getImage();
        } catch (Exception e) {
            System.err.println("Ошибка загрузки apple.png: " + e.getMessage());
        }
    }

    public Apple(Cell cell, World world) {
        super(cell, world);
    }

    @Override
    public void interact(Snake initiator) {
        Cell oldCell = unsetCell();
        initiator.grow(oldCell);
    }

//    @Override
//    public void draw(Graphics g, int x, int y, int cellSize) {
//        g.setColor(Color.RED);
//        g.fillOval(x, y, cellSize, cellSize);
//    }

    @Override
    public void draw(Graphics g, int x, int y, int cellSize) {
        if (appleImage != null) {
            g.drawImage(appleImage, x, y, cellSize, cellSize, null);
        } else {
            g.setColor(Color.RED);
            g.fillOval(x, y, cellSize, cellSize); // если картинка не загрузилась
        }
    }
}
