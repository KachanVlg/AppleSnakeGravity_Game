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



}
