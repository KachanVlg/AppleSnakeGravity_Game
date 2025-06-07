package ui;

import core.*;
import core.Box;
import events.GameListener;
import ui.components.*;
import utils.Direction;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

public class GamePanel extends JPanel implements GameListener {

    private final GameModel gameModel;

    private final List<GameComponent> movingComponents = new ArrayList<>();
    private final List<JComponent> staticComponents = new ArrayList<>();
    private AppleView appleView;
    private boolean isAnimating = false;


    private final Timer gameTimer;

    public GamePanel(GameModel gameModel) {
        this.gameModel = gameModel;
        gameModel.addListener(this);

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                handleKeyPress(e);
            }
        });

        setFocusable(true);
        requestFocusInWindow();

        setLayout(null); // абсолютное позиционирование
        setPreferredSize(new Dimension(800, 800)); // подстраивай под размер игрового поля

        // Запускаем игровой цикл с частотой ~25 кадров в секунду
        gameTimer = new Timer(40, e -> gameLoop());
        gameModel.start();
        initComponents();
        gameTimer.start();
    }

    public void addMovingComponent(GameComponent comp) {
        movingComponents.add(comp);
        add(comp);
    }

    private void handleKeyPress(KeyEvent e) {
        if (isAnimating) return;
        Direction dir = null;

        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP -> dir = Direction.UP;
            case KeyEvent.VK_DOWN -> dir = Direction.DOWN;
            case KeyEvent.VK_LEFT -> dir = Direction.LEFT;
            case KeyEvent.VK_RIGHT -> dir = Direction.RIGHT;
        }

        if (dir != null) {
            gameModel.moveSnakeOn(dir);
        }
    }

    public void addStaticComponent(JComponent comp) {
        staticComponents.add(comp);
        add(comp);
    }

    public void initComponents() {
        //blocks
        for (Block block : gameModel.getWorld().getBlocks()) {
            BlockView blockView = new BlockView(block);
            addStaticComponent(blockView);
            blockView.repaint();
        }


        //portal
        PortalView portalView = new PortalView(gameModel.getWorld().getPortal());
        addStaticComponent(portalView);
        portalView.repaint();

        //snake
        HeadView headView = new HeadView(gameModel.getWorld().getHead());
        addMovingComponent(headView);
        headView.refresh();

        for (Segment segment : gameModel.getWorld().getSegments()) {
            SegmentView segmentView = new SegmentView(segment);
            addMovingComponent(segmentView);
            segmentView.refresh();
        }

        appleView = new AppleView(gameModel.getWorld().getApple());
        addStaticComponent(appleView);
        appleView.repaint();


        //boxes
        for (Box box : gameModel.getWorld().getBoxes()) {
            BoxView boxView = new BoxView(box);
            addMovingComponent(boxView);
            boxView.refresh(); // чтобы сразу позиция обновилась
        }



    }

    private void gameLoop() {


        boolean anyAnimating = false;

        for (GameComponent comp : movingComponents) {
            comp.animate();
            comp.refresh();

            if (comp.isAnimating()) {
                anyAnimating = true;
            }
        }

        if (!anyAnimating) {
            isAnimating = false; // ✅ разрешаем ввод снова
        }

        if(!isAnimating) {
            // Удаляем компоненты, которые помечены на удаление
            List<GameComponent> toRemove = new ArrayList<>();
            for (GameComponent comp : movingComponents) {
                if (comp.isToDelete()) {
                    toRemove.add(comp);
                    remove(comp); // Удаляем с панели
                }
            }
            movingComponents.removeAll(toRemove);
        }
        revalidate();


        repaint();
    }

    public void stopGameLoop() {
        gameTimer.stop();
    }

    public void startGameLoop() {
        gameTimer.start();
    }

    @Override
    public void portalIsEntered() {



//        JOptionPane.showMessageDialog(
//                this,
//                "Вы победили!",
//                "Игра завершена",
//                JOptionPane.INFORMATION_MESSAGE
//        );

        JOptionPane optionPane = new JOptionPane(
                "Вы победили!",
                JOptionPane.INFORMATION_MESSAGE
        );

        JDialog dialog = optionPane.createDialog(this, "Игра завершена");

// Задать координаты — например, по центру по ширине, но ближе к верху по высоте
        Point location = getLocationOnScreen(); // позиция текущего окна
        dialog.setLocation(location.x + getWidth() / 2 - dialog.getWidth() / 2, location.y + 100); // 100 px от верхнего края окна

        dialog.setVisible(true);
        stopGameLoop(); // остановить таймер игры
    }

    @Override
    public void fell() {
        gameLoop();
        JOptionPane.showMessageDialog(
                this,
                "Вы проиграли!",
                "Игра завершена",
                JOptionPane.ERROR_MESSAGE
        );
    }

    @Override
    public void movedOn() {
        isAnimating = true;
        gameLoop();
    }

    @Override
    public void eatApple(Segment segment) {
        // Удалить яблоко с UI
        remove(appleView);
        staticComponents.remove(appleView);
        appleView = null;

        // Добавить новый сегмент
        SegmentView segmentView = new SegmentView(segment);
        addMovingComponent(segmentView);
        segmentView.refresh(); // не забудь обновить позицию

        // Перерисовать
        revalidate();
        repaint();
    }

    @Override
    public void gravityApplied() {
        isAnimating = true;
        gameLoop();
    }
}

