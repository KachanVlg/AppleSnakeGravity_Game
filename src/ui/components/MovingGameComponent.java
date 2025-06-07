package ui.components;

import core.ObjectOnField;
import ui.GamePanel;

import javax.swing.*;
import java.awt.*;

public abstract class MovingGameComponent extends GameComponent {

    protected GamePanel gamePanel;
    protected boolean toDelete;
    protected Point targetPoint;
    protected boolean animating = false;
    protected double currentX;
    protected double currentY;
    protected final double speed = 9.0;

    public MovingGameComponent(ObjectOnField objectOnField) {
        super(objectOnField);
    }

    public boolean isToDelete() {
        return toDelete;
    }

    public abstract void refresh();
    public abstract boolean isAnimating();

    public void animate() {
        if (!animating || targetPoint == null) return;

        double dx = targetPoint.x - currentX;
        double dy = targetPoint.y - currentY;

        double dist = Math.hypot(dx, dy);

        if (dist < speed) {
            currentX = targetPoint.x;
            currentY = targetPoint.y;
            animating = false;
        } else {
            currentX += (dx / dist) * speed;
            currentY += (dy / dist) * speed;
            animating = true;
        }


        setLocation((int) currentX, (int) currentY);
        repaint();

        if(!animating && objectOnField.isFell()) {
            toDelete = true;
        }

    }
}
