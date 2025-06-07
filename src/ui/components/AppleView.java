package ui.components;

import core.Apple;
import ui.GamePanel;

import javax.swing.*;
import java.awt.*;

public class AppleView extends JComponent {
    private final Apple apple; // модель
    private final int size = 40;
    private final Color color = Color.RED;
    private final int fieldSize = 800;
    private Image appleImage;

    public AppleView(Apple apple) {
        this.apple = apple;
        setSize(size, size);
        setPreferredSize(new Dimension(size, size));
        setOpaque(false);
        updatePosition();
        appleImage = new ImageIcon("src/ui/images/Apple.png").getImage();
    }

    public void updatePosition() {
        Point point = apple.getCell().getPoint();
        int invY = fieldSize -1 - point.y*size;
        setLocation(point.x * size, invY);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (appleImage != null) {
            g.drawImage(appleImage, 0, 0, size, size, this);
        } else {
            g.setColor(color);
            g.fillOval(0, 0, size, size);

            // Нарисуем небольшой листик сверху яблока
            g.setColor(new Color(34, 139, 34)); // темно-зеленый
            int leafWidth = size / 5;
            int leafHeight = size / 3;
            g.fillOval(size / 2 - leafWidth / 2, 0, leafWidth, leafHeight);
        }
    }
}
