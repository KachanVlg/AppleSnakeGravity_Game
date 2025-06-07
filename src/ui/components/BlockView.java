package ui.components;

import core.Block;

import javax.swing.*;
import java.awt.*;

public class BlockView extends JComponent {
    private final Block block; // модель
    private final int size = 40;
    private final Color color = Color.DARK_GRAY;
    private final int fieldSize = 800;
    private Image blockImage;

    public BlockView(Block block) {
        this.block = block;
        setSize(size, size);
        setPreferredSize(new Dimension(size, size));
        setOpaque(false);
        updatePosition();
        blockImage = new ImageIcon("src/ui/images/Block.png").getImage();
    }

    public void updatePosition() {
        Point point = block.getCell().getPoint();
        int invY = fieldSize -1 - point.y*size;
        setLocation(point.x * size, invY);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (blockImage != null) {
            g.drawImage(blockImage, 0, 0, size, size, this);
        } else {
            g.setColor(color);
            g.fillRect(0, 0, size, size);

            g.setColor(Color.BLACK);
            g.drawRect(0, 0, size - 1, size - 1);
        }

    }


}
