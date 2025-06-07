package ui.components;

import core.Segment;
import utils.Direction;

import javax.swing.*;
import java.awt.*;

public class SegmentView extends GameComponent {
    private final Segment segment; // модель
    private final int size = 40;
    private final Color color = new Color(0, 100, 0);
    private final int fieldSize = 800;// тёмно-зелёный
    private Image segmentImage;

    private Point targetPoint;
    private boolean animating = false;

    private double currentX;
    private double currentY;

    // Скорость движения в пикселях за обновление (кадр)
    private final double speed = 9.0;

    public SegmentView(Segment segment) {
        this.segment = segment;
        setSize(size, size);
        setPreferredSize(new Dimension(size, size));
        setOpaque(false);

        // Инициализируем текущие координаты из модели (начальная позиция)
        Point point = segment.getCell().getPoint();
        currentX = point.x * size;
        currentY = fieldSize - point.y * size - 1;
        setLocation((int) currentX, (int) currentY);
        segmentImage = new ImageIcon("src/ui/images/snake/Segment-1.png").getImage();
    }

    public void updatePosition() {
        Point point = segment.getCell().getPoint();
        setLocation(point.x * size, fieldSize - point.y * size - 1);
    }

    @Override
    public void refresh() {
        int targetX;
        int targetY;

        if(segment.isFell()) {
            targetY = fieldSize - size - 1;
            targetX = (int) currentX;
        } else if(segment.isEnteredPortal()) {
            Point portalPoint = segment.getPortalCell().getPoint();
            targetX = portalPoint.x * size;
            targetY = fieldSize - portalPoint.y * size - 1;
        } else {
            Point newPoint = segment.getCell().getPoint();
            targetX = newPoint.x * size;
            targetY = fieldSize - newPoint.y * size - 1;
        }

        // Если уже на месте — не анимируем
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

        if(!animating && segment.isFell()) {
            toDelete = true;
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (segmentImage != null) {
            g.drawImage(segmentImage, 0, 0, size, size, this);
        } else {
            g.setColor(color);
            g.fillRect(0, 0, size, size);
            g.setColor(Color.BLACK); // Чёрная рамка
            g.drawRect(0, 0, size - 1, size - 1);
        }
    }

    public boolean isAnimating() {
        return animating; // по умолчанию ничего не анимируется
    }
}
