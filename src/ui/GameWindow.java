package ui;

import core.GameModel;

import javax.swing.*;

public class GameWindow extends JFrame {

    private final GameModel gameModel;

    public GameWindow(GameModel gameModel) {
        super("AppleSnake");
    }
}
