package ui.components;

import core.Segment;

import javax.swing.*;
import java.awt.*;

public class SegmentView extends AbstractSegmentView {
    private static final String imageSrc = "src/ui/images/snake/Segment-1.png";

    public SegmentView(Segment segment) {
        super(segment, imageSrc);
    }
}
