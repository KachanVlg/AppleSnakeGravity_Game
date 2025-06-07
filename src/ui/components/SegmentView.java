package ui.components;

import core.Segment;

import javax.swing.*;
import java.awt.*;

public class SegmentView extends AbstractSegmentView {
    private Image segmentImage;

    public SegmentView(Segment segment) {
        super(segment);
        segmentImage = new ImageIcon("src/ui/images/snake/Segment-1.png").getImage();
    }

    @Override
    protected void paintComponent(Graphics g) {
        if (segmentImage != null) {
            g.drawImage(segmentImage, 0, 0, size, size, this);
        }
    }
}
