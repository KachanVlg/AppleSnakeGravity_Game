package ui.components;

import core.Head;

import javax.swing.*;
import java.awt.*;

public class HeadView extends GameComponent {
    private final Head head; // модель
    private final int size = 40;
    private final Color color = Color.GREEN;
    private final int fieldSize = 800;
    private Image headUp;
    private Image headDown;
    private Image headLeft;
    private Image headRight;

    private Point targetPoint;
    private boolean animating = false;

    // Текущие координаты в пикселях (для плавного движения)
    private double currentX;
    private double currentY;

    // Скорость движения в пикселях за обновление (кадр)
    private final double speed = 9.0;

    public HeadView(Head head) {
        this.head = head;
        setSize(size, size);
        setPreferredSize(new Dimension(size, size));
        setOpaque(false);

        // Инициализируем текущие координаты из модели (начальная позиция)
        Point point = head.getCell().getPoint();
        currentX = point.x * size;
        currentY = fieldSize - point.y * size - 1;
        setLocation((int) currentX, (int) currentY);


        // Загрузка изображений
        headUp = new ImageIcon("src/ui/images/snake/HeadUp.png").getImage();
        headDown = new ImageIcon("src/ui/images/snake/HeadDown.png").getImage();
        headLeft = new ImageIcon("src/ui/images/snake/HeadLeft.png").getImage();
        headRight = new ImageIcon("src/ui/images/snake/HeadRight.png").getImage();
//        updatePosition();
    }

    public void updatePosition() {
        Point point = head.getCell().getPoint();
        setLocation(point.x * size, fieldSize - point.y * size - 1);
    }

    @Override
    public void refresh() {

        int targetX;
        int targetY;

        if(head.isFell()) {
            targetY = fieldSize - size - 1;
            targetX = (int) currentX; }
            else if(head.isEnteredPortal()) {
                Point portalPoint = head.getPortalCell().getPoint();
                targetX = portalPoint.x * size;
                targetY = fieldSize - portalPoint.y * size - 1;
        } else {
            Point newPoint = head.getCell().getPoint();
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

        if(!animating && head.isFell()) {
            toDelete = true;
        }

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Image imageToDraw = null;

        switch (head.getDir()) {
            case UP:
                imageToDraw = headUp;
                break;
            case DOWN:
                imageToDraw = headDown;
                break;
            case LEFT:
                imageToDraw = headLeft;
                break;
            case RIGHT:
                imageToDraw = headRight;
                break;
        }

        if (imageToDraw != null) {
            g.drawImage(imageToDraw, 0, 0, size, size, this);
        } else {
            // fallback: просто рисуем прямоугольник
            g.setColor(color);
            g.fillRect(0, 0, size, size);
        }
    }

    public boolean isAnimating() {
        return animating; // по умолчанию ничего не анимируется
    }
}