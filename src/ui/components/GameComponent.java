package ui.components;

import ui.GamePanel;

import javax.swing.*;

public abstract class GameComponent extends JComponent {

    private GamePanel gamePanel;
    protected boolean toDelete;

    public boolean isToDelete() {
        return toDelete;
    }

    public abstract void refresh();
    public abstract void animate();
    public abstract boolean isAnimating();
}
