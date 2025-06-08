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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GamePanel extends JPanel implements GameListener {

    private final GameModel gameModel;

    private final List<MovingGameComponent> movingComponents = new ArrayList<>();
    private boolean isAnimating = false;
    private final static int CELL_SIZE = 40;
    private final int fieldSize = 800;
    private final Map<ObjectOnField, GameComponent> componentMap = new HashMap<>();

    private static final Map<Integer, Direction> KEY_TO_DIRECTION = Map.of(
            KeyEvent.VK_UP, Direction.UP,
            KeyEvent.VK_DOWN, Direction.DOWN,
            KeyEvent.VK_LEFT, Direction.LEFT,
            KeyEvent.VK_RIGHT, Direction.RIGHT
    );


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

        setLayout(null);
        setPreferredSize(new Dimension(800, 800));


        gameTimer = new Timer(35, e -> gameLoop());
        gameModel.start();
        initComponents();
        gameTimer.start();
    }

    private void addMovingComponent(MovingGameComponent comp) {
        movingComponents.add(comp);
        add(comp);
    }

    private void handleKeyPress(KeyEvent e) {
        if (isAnimating) return;
        Direction dir = KEY_TO_DIRECTION.get(e.getKeyCode());
        if (dir != null) gameModel.moveSnakeOn(dir);
    }

    private void addStaticComponent(JComponent comp) {
        add(comp);
    }



    private void initComponents() {

        List<Block> blocks = gameModel.getWorld().getObjectsOfType(Block.class);
        for (Block block : blocks) {
            BlockView blockView = new BlockView(block);
            addStaticComponent(blockView);
            blockView.repaint();
        }

        Portal portal = gameModel.getWorld().getObjectsOfType(Portal.class).getFirst();
        PortalView portalView = new PortalView(portal);
        addStaticComponent(portalView);
        portalView.repaint();


        Head head = gameModel.getWorld().getObjectsOfType(Head.class).getFirst();
        HeadView headView = new HeadView(head);
        componentMap.put(head, headView);
        addMovingComponent(headView);
        headView.refresh();

        List<Segment> segments = gameModel.getWorld().getObjectsOfType(Segment.class);
        for (Segment segment : segments) {
            SegmentView segmentView = new SegmentView(segment);
            componentMap.put(segment, segmentView);
            addMovingComponent(segmentView);
            segmentView.refresh();
        }

        Apple apple = gameModel.getWorld().getObjectsOfType(Apple.class).getFirst();
        AppleView appleView = new AppleView(apple);
        addStaticComponent(appleView);
        componentMap.put(apple, appleView);



        List<Box> boxes = gameModel.getWorld().getObjectsOfType(Box.class);
        for (Box box : boxes) {
            BoxView boxView = new BoxView(box);
            componentMap.put(box, boxView);
            addMovingComponent(boxView);
            boxView.refresh();
        }
    }

    private void gameLoop() {
        boolean anyAnimating = false;
        for (MovingGameComponent comp : movingComponents) {
            comp.animate();
            comp.refresh();
            anyAnimating |= comp.isAnimating();
        }

        isAnimating = anyAnimating;

        if (!isAnimating) {
            removeDeletedComponents();
        }
        revalidate();
        repaint();
    }

    private void removeDeletedComponents() {
        movingComponents.removeIf(comp -> {
            if (comp.isToDelete()) {
                remove(comp);
                return true;
            }
            return false;
        });
    }

    public void stopGameLoop() {
        gameTimer.stop();
    }

    public void startGameLoop() {
        gameTimer.start();
    }

    @Override
    public void portalIsEntered() {

        JOptionPane optionPane = new JOptionPane(
                "Вы победили!",
                JOptionPane.INFORMATION_MESSAGE
        );
        JDialog dialog = optionPane.createDialog(this, "Игра завершена");
        Point location = getLocationOnScreen();
        dialog.setLocation(location.x + getWidth() / 2 - dialog.getWidth() / 2, location.y + 100); // 100 px от верхнего края окна
        dialog.setVisible(true);
        stopGameLoop();
    }

    @Override
    public void fell() {
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
    }

    @Override
    public void eatApple(Segment segment) {
        SegmentView segmentView = new SegmentView(segment);
        addMovingComponent(segmentView);
    }

    @Override
    public void gravityApplied() {
        isAnimating = true;
    }
}

