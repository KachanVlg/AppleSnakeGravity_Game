package ui.components;

import core.Head;

import javax.swing.*;
import java.awt.*;

public class HeadView extends AbstractSegmentView {
    private final Head head;
    private Image headUp = new ImageIcon("src/ui/images/snake/HeadUp.png").getImage();
    private Image headDown = new ImageIcon("src/ui/images/snake/HeadDown.png").getImage();
    private Image headLeft = new ImageIcon("src/ui/images/snake/HeadLeft.png").getImage();
    private Image headRight = new ImageIcon("src/ui/images/snake/HeadRight.png").getImage();

    public HeadView(Head head) {
        super(head, null);
        this.head = head;
    }

    @Override
    protected void paintComponent(Graphics g) {
        image = switch (head.getDir()) {
            case UP -> headUp;
            case DOWN -> headDown;
            case LEFT -> headLeft;
            case RIGHT -> headRight;
        };
        super.paintComponent(g);
    }

}