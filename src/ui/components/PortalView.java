package ui.components;

import core.Portal;

import javax.swing.*;
import java.awt.*;

public class PortalView extends GameComponent { // модель
    private Image portalImage;

    public PortalView(Portal portal) {
        super(portal);
        setSize(size, size);
        setPreferredSize(new Dimension(size, size));
        setOpaque(false);
        updatePosition();
        portalImage = new ImageIcon("src/ui/images/Portal.png").getImage();
    }

    @Override
    protected void paintComponent(Graphics g) {
        if (portalImage != null) {
            g.drawImage(portalImage, 0, 0, size, size, this);
        }
    }
}
