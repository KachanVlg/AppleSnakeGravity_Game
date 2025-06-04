import javax.swing.*;
import java.awt.*;

public class Block extends ObjectOnField{

    private static Image blockImage;

    static {
        try {
            blockImage = new ImageIcon(Block.class.getResource("/Block.png")).getImage();
            // Или так, если файл в корне:
            // blockImage = new ImageIcon("block.png").getImage();
        } catch (Exception e) {
            System.err.println("Не удалось загрузить block.png: " + e.getMessage());
        }
    }

    public Block(Cell cell, World world) {
        super(cell, world);
    }

//    @Override
//    public void draw(Graphics g, int x, int y, int cellSize) {
//        g.setColor(Color.BLACK);
//        g.fillRect(x, y, cellSize, cellSize);
//    }

    @Override
    public void draw(Graphics g, int x, int y, int cellSize) {
        if (blockImage != null) {
            g.drawImage(blockImage, x, y, cellSize, cellSize, null);
        } else {
            g.setColor(Color.BLACK);
            g.fillRect(x, y, cellSize, cellSize); // fallback
        }
    }
}
