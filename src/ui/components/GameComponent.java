package ui.components;

import core.ObjectOnField;

import javax.swing.*;
import java.awt.*;

public abstract class GameComponent extends JComponent {
    protected final int size = 40;
    protected final int fieldSize = 800;
    protected final ObjectOnField objectOnField;
    protected Image image;

    public GameComponent(ObjectOnField objectOnField) {
        this.objectOnField = objectOnField;
        setSize(size, size);
        setPreferredSize(new Dimension(size, size));
        setOpaque(false);
        updatePosition();
    }

    protected void updatePosition() {
        Point point = objectOnField.getCell().getPoint();
        setLocation(point.x * size, fieldSize - point.y * size - 1);
    }


}
