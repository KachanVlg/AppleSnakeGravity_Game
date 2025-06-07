package ui.components;

import core.Portal;

import javax.swing.*;
import java.awt.*;

public class PortalView extends JComponent {
    private final Portal portal; // модель
    private final int size = 40;
    private final Color color = new Color(100, 149, 237); // например, цвет "королевский синий"
    private final int fieldSize = 800;
    private Image portalImage;

    public PortalView(Portal portal) {
        this.portal = portal;
        setSize(size, size);
        setPreferredSize(new Dimension(size, size));
        setOpaque(false);
        updatePosition();
        portalImage = new ImageIcon("src/ui/images/Portal.png").getImage();
    }

    public void updatePosition() {
        Point point = portal.getCell().getPoint();
        setLocation(point.x * size, fieldSize - point.y * size - 1);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (portalImage != null) {
            g.drawImage(portalImage, 0, 0, size, size, this);
        } else {
            g.setColor(color);
            g.fillOval(0, 0, size, size);
            g.setColor(Color.BLACK);
            g.drawOval(0, 0, size - 1, size - 1);
        }
    }
}
