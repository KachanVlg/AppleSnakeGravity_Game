package ui.components;

import core.ObjectOnField;

import javax.swing.*;
import java.awt.*;

public class GameComponent extends JComponent {
    protected final int size = 40;
    protected final int fieldSize = 800;
    protected final ObjectOnField objectOnField;

    public GameComponent(ObjectOnField objectOnField) {
        this.objectOnField = objectOnField;
    }

    protected void updatePosition() {
        Point point = objectOnField.getCell().getPoint();
        setLocation(point.x * size, fieldSize - point.y * size - 1);
    }


}
