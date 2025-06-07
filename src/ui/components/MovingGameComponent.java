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
        updatePosition();
    }

    public boolean isToDelete() {
        return toDelete;
    }

    public void refresh() {
        int targetX;
        int targetY;

        if (objectOnField.isFell()) {
            targetY = fieldSize - size - 1;
            targetX = (int) currentX;
        } else {
            Point newPoint = objectOnField.getCell().getPoint();
            targetX = newPoint.x * size;
            targetY = fieldSize - newPoint.y * size - 1;
        }

        if ((int) currentX == targetX && (int) currentY == targetY) {
            return;
        }

        targetPoint = new Point(targetX, targetY);
        animating = true;
    }

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

    public boolean isAnimating() {
        return animating;
    }

    @Override
    protected void updatePosition() {
        Point point = objectOnField.getCell().getPoint();
        currentX = point.x * size;
        currentY = fieldSize - point.y * size - 1;
        setLocation((int) currentX, (int) currentY);
    }
}
