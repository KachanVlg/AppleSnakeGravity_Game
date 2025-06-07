package ui.components;

import core.Apple;
import ui.GamePanel;

import javax.swing.*;
import java.awt.*;

public class AppleView extends GameComponent {
    private final Apple apple;
    private Image appleImage;

    public AppleView(Apple apple) {
        super(apple);
        this.apple = apple;
        setSize(size, size);
        setPreferredSize(new Dimension(size, size));
        setOpaque(false);
        updatePosition();
        appleImage = new ImageIcon("src/ui/images/Apple.png").getImage();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (appleImage != null) {
            g.drawImage(appleImage, 0, 0, size, size, this);
        }
    }
}
