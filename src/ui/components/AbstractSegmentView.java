package ui.components;

import core.AbstractSegment;

import java.awt.*;

public abstract class AbstractSegmentView extends MovingGameComponent{

    private final AbstractSegment abstractSegment;

    protected AbstractSegmentView(AbstractSegment abstractSegment) {
        super(abstractSegment);
        this.abstractSegment = abstractSegment;
    }

    @Override
    public void refresh() {
        int targetX;
        int targetY;

        if(abstractSegment.isFell()) {
            targetY = fieldSize - size - 1;
            targetX = (int) currentX;
        } else if(abstractSegment.isEnteredPortal()) {
            Point portalPoint = abstractSegment.getPortalCell().getPoint();
            targetX = portalPoint.x * size;
            targetY = fieldSize - portalPoint.y * size - 1;
        } else {
            Point newPoint = abstractSegment.getCell().getPoint();
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

        if(!animating && abstractSegment.isFell()) {
            toDelete = true;
        }

    }

    @Override
    public boolean isAnimating() {
        return animating; // по умолчанию ничего не анимируется
    }

}
