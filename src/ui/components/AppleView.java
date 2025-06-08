package ui.components;

import core.Apple;
import ui.GamePanel;

import javax.swing.*;
import java.awt.*;

public class AppleView extends GameComponent {
    private static final String imageSrc = "src/ui/images/Apple.png";

    public AppleView(Apple apple) {
        super(apple, imageSrc);
    }

    @Override
    public void paintComponent(Graphics g) {
        if(objectOnField.getCell() == null) {
            setVisible(false);
        }
        super.paintComponent(g);
    }



}
