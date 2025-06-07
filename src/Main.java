import core.GameModel;
import ui.GameWindow;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new GameWindow(new GameModel());
        });
    }
}
