package ui;

import core.GameModel;

import javax.swing.*;
import java.awt.*;

public class GameWindow extends JFrame {

    private GameModel gameModel;
    private GamePanel gamePanel;
    private JButton restartButton;
    public GameWindow(GameModel gameModel) {
        super("AppleSnake");

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setLayout(new BorderLayout());

        restartButton = new JButton("Start");
        restartButton.addActionListener(e -> restartGame());

        add(restartButton, BorderLayout.NORTH);

        startNewGame();

        pack(); // подогнать размер окна под размер панели
        setLocationRelativeTo(null); // окно по центру экрана
        setVisible(true);

    }

    private void restartGame() {
        if (gamePanel != null) {
            gamePanel.stopGameLoop();
            remove(gamePanel);
        }

        restartButton.setText("Restart");

        startNewGame();
        pack();
    }

    private void startNewGame() {
        gameModel = new GameModel();
        gamePanel = new GamePanel(gameModel);
        gamePanel.setBackground(new Color(135, 206, 235));
        add(gamePanel, BorderLayout.CENTER);
        revalidate();
        repaint();
        gamePanel.requestFocusInWindow();
    }
}
