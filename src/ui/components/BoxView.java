package ui.components;

import core.Box;
import ui.util.StrategyUIMapper;

import java.awt.*;

public class BoxView extends MovingGameComponent {
    private final Box box;
    private Color color;

    public Box getBox() {
        return box;
    }

    public BoxView(Box box) {
        super(box);
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
    protected void paintComponent(Graphics g) {
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
