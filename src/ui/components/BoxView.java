package ui.components;

import core.Box;
import ui.util.StrategyUIMapper;

import javax.swing.*;
import java.awt.*;

public class BoxView extends GameComponent {
    private final Box box;
    private Color color;

    private final int size = 40;
    private final int fieldSize = 800;

    private Point targetPoint;
    private boolean animating = false;

    private double currentX;
    private double currentY;

    private final double speed = 9.0;

    public Box getBox() {
        return box;
    }

    public BoxView(Box box) {
        this.box = box;
        updateColor();
        setSize(size, size);
        setPreferredSize(new Dimension(size, size));
        setOpaque(false);


        Point point = box.getCell().getPoint();
        currentX = point.x * size;
        currentY = fieldSize - point.y * size - 1;
        setLocation((int) currentX, (int) currentY);
    }

    private void updateColor() {
        this.color = StrategyUIMapper.getColorForStrategy(box.getMovementStrategy());
    }

    public void updatePosition() {
        Point point = box.getCell().getPoint();
        setLocation((int) (point.getX() * size), (int) (fieldSize - point.getY() * size - 1));
    }

    @Override
    public void refresh() {
        updateColor();

        int targetX;
        int targetY;

        if(box.isFell()) {
            targetY = fieldSize - size - 1;
            targetX = (int) currentX;
        } else {
            Point newPoint = box.getCell().getPoint();
            targetX = newPoint.x * size;
            targetY = fieldSize - newPoint.y * size - 1;
        }

        if ((int) currentX == targetX && (int) currentY == targetY) {
            return;
        }

        targetPoint = new Point(targetX, targetY);
        animating = true;
    }

    @Override
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

        if(!animating && box.isFell()) {
            toDelete = true;
        }

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(color);
        g.fillRect(0, 0, size, size);
        g.setColor(Color.BLACK); // Чёрная рамка
        g.drawRect(0, 0, size - 1, size - 1);
    }

    @Override
    public boolean isAnimating() {
        return animating;
    }
}
