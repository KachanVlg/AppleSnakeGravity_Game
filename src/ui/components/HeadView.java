package ui.components;

import core.Head;

import javax.swing.*;
import java.awt.*;

public class HeadView extends AbstractSegmentView {
    private final Head head; // модель
    private Image headUp;
    private Image headDown;
    private Image headLeft;
    private Image headRight;

    public HeadView(Head head) {
        super(head);
        this.head = head;
        setSize(size, size);
        setPreferredSize(new Dimension(size, size));
        setOpaque(false);

        // Инициализируем текущие координаты из модели (начальная позиция)
        Point point = head.getCell().getPoint();
        currentX = point.x * size;
        currentY = fieldSize - point.y * size - 1;
        setLocation((int) currentX, (int) currentY);


        // Загрузка изображений
        headUp = new ImageIcon("src/ui/images/snake/HeadUp.png").getImage();
        headDown = new ImageIcon("src/ui/images/snake/HeadDown.png").getImage();
        headLeft = new ImageIcon("src/ui/images/snake/HeadLeft.png").getImage();
        headRight = new ImageIcon("src/ui/images/snake/HeadRight.png").getImage();
//        updatePosition();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Image imageToDraw = null;

        switch (head.getDir()) {
            case UP:
                imageToDraw = headUp;
                break;
            case DOWN:
                imageToDraw = headDown;
                break;
            case LEFT:
                imageToDraw = headLeft;
                break;
            case RIGHT:
                imageToDraw = headRight;
                break;
        }

        if (imageToDraw != null) {
            g.drawImage(imageToDraw, 0, 0, size, size, this);
        }
    }

}