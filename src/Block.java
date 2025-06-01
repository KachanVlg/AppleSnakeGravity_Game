import java.awt.*;

public class Block extends ObjectOnField{

    public Block(Cell cell, World world) {
        super(cell, world);
    }

    @Override
    public void draw(Graphics g, int x, int y, int cellSize) {
        g.setColor(Color.BLACK);
        g.fillRect(x, y, cellSize, cellSize);
    }
}
