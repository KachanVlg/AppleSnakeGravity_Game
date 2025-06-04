import javax.swing.*;
import java.awt.*;

public class Segment extends AbstractSegment{

    private static Image segmentImage;

    static {
        try {
            segmentImage = new ImageIcon(Apple.class.getResource("/ui/images/snake/Segment.png")).getImage();
        } catch (Exception e) {
            System.err.println("Ошибка загрузки Segment.png: " + e.getMessage());
        }
    }


    public Segment(Cell cell, World world) {
        super(cell, world);
    }

    public Segment(Cell cell, AbstractSegment next, World world) {
        super(cell ,next, world);
    }

    @Override
    public void draw(Graphics g, int x, int y, int cellSize) {
        if (segmentImage != null) {
            g.drawImage(segmentImage, x, y, cellSize, cellSize, null);
        } else {
            g.setColor(Color.GREEN);
            g.fillRect(x, y, cellSize, cellSize);
        }
    }
}
