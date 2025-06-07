package ui.components;

import core.Box;
import core.MovableObstacle;
import ui.util.StrategyUIMapper;

import java.awt.*;

public class BoxView extends MovingGameComponent {
    private final MovableObstacle movableObstacle;
    private Color color;

    public BoxView(MovableObstacle movableObstacle) {
        super(movableObstacle, null);
        this.movableObstacle = movableObstacle;
        updateColor();
    }

    private void updateColor() {
        this.color = StrategyUIMapper.getColorForStrategy(movableObstacle.getMovementStrategy());
    }

    @Override
    public void refresh() {
        updateColor();
        super.refresh();
    }

    @Override
    protected void paintComponent(Graphics g) {
        g.setColor(color);
        g.fillRect(0, 0, size, size);
        g.setColor(Color.BLACK); // Чёрная рамка
        g.drawRect(0, 0, size - 1, size - 1);
    }
}
