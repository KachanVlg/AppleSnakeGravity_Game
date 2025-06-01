import java.awt.*;

public class Segment extends AbstractSegment{


    public Segment(Cell cell, World world) {
        super(cell, world);
    }

    public Segment(Cell cell, AbstractSegment next, World world) {
        super(cell ,next, world);
    }

    @Override
    public void draw(Graphics g, int x, int y, int cellSize) {
        g.setColor(Color.GREEN);
        g.fillRect(x, y, cellSize, cellSize);
    }
}
