package ui.components;

import core.Portal;

import javax.swing.*;
import java.awt.*;

public class PortalView extends GameComponent {
    private final static String imageSrc = "src/ui/images/Portal.png";

    public PortalView(Portal portal) {
        super(portal, imageSrc);
    }
}
