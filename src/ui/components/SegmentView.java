package ui.components;

import core.Segment;

import javax.swing.*;
import java.awt.*;

public class SegmentView extends AbstractSegmentView {
    private final Segment segment; // модель
    private Image segmentImage;

    public SegmentView(Segment segment) {
        super(segment);
        this.segment = segment;
        setSize(size, size);
        setPreferredSize(new Dimension(size, size));
        setOpaque(false);

        Point point = segment.getCell().getPoint();
        currentX = point.x * size;
        currentY = fieldSize - point.y * size - 1;
        setLocation((int) currentX, (int) currentY);
        segmentImage = new ImageIcon("src/ui/images/snake/Segment-1.png").getImage();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (segmentImage != null) {
            g.drawImage(segmentImage, 0, 0, size, size, this);
        }
    }
}
