package ui.components;

import core.Block;

import javax.swing.*;
import java.awt.*;

public class BlockView extends GameComponent {
    private Image blockImage;

    public BlockView(Block block) {
        super(block);
        setSize(size, size);
        setPreferredSize(new Dimension(size, size));
        setOpaque(false);
        updatePosition();
        blockImage = new ImageIcon("src/ui/images/Block.png").getImage();
    }

    @Override
    protected void paintComponent(Graphics g) {
        if (blockImage != null) {
            g.drawImage(blockImage, 0, 0, size, size, this);
        }
    }
}
