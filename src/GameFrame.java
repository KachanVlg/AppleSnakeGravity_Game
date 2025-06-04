import utils.Direction;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Timer;
import java.util.TimerTask;

public class GameFrame extends JFrame {
    private GameModel gameModel;
    private GamePanel gamePanel;
    private static final int CELL_SIZE = 40;
    private static final int REFRESH_RATE_MS = 150;
    private boolean gameOver = false;

    public GameFrame() {
        setTitle("Snake Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Инициализация модели и панели
        gameModel = new GameModel();
        gameModel.start();

        gamePanel = new GamePanel(gameModel, CELL_SIZE);
        add(gamePanel);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);

        // Обработка ввода
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (gameOver) return;
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_UP -> gameModel.moveSnakeOn(Direction.UP);
                    case KeyEvent.VK_DOWN -> gameModel.moveSnakeOn(Direction.DOWN);
                    case KeyEvent.VK_LEFT -> gameModel.moveSnakeOn(Direction.LEFT);
                    case KeyEvent.VK_RIGHT -> gameModel.moveSnakeOn(Direction.RIGHT);
                }
                gamePanel.repaint();
            }
        });

        // Таймер для обновления экрана
        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (!gameModel.isFinished()) {
                    gamePanel.repaint();
                } else {
                    gameOver = true;
                    cancel();
                    String message = gameModel.hasWon()
                            ? "Вы победили!"
                            : "Игра окончена! Вы проиграли.";
                    SwingUtilities.invokeLater(() ->
                            JOptionPane.showMessageDialog(null, message, "Конец", JOptionPane.INFORMATION_MESSAGE)
                    );
                }
            }
        }, 0, REFRESH_RATE_MS);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(GameFrame::new);
    }
}

class GamePanel extends JPanel {
    private final GameModel model;
    private final int cellSize;

    public GamePanel(GameModel model, int cellSize) {
        this.model = model;
        this.cellSize = cellSize;
        int width = 30;
        int height = 30;
        setPreferredSize(new Dimension(width * cellSize, height * cellSize));
        setBackground(new Color(173, 216, 230));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int height = model.getWorld().getHeight();

        for (Cell cell : model.getWorld().getField()) {
            Point p = cell.getPoint();
            ObjectOnField obj = cell.getObject();

            int x = p.x * cellSize;
            int y = (height - 1 - p.y) * cellSize;


            g.setColor(new Color(173, 216, 230));
            g.fillRect(x, y, cellSize, cellSize);

            if(obj != null) {
                obj.draw(g, x, y, cellSize);
            }
//            String coords = p.x + "," + p.y;
//            g.setColor(Color.BLACK);
//            g.setFont(new Font("Arial", Font.PLAIN, 10));
//            g.drawString(coords, x + 2, y + 12);
        }
    }
}
