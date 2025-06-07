package ui.components;

import core.Block;

import javax.swing.*;
import java.awt.*;

public class BlockView extends GameComponent {
    private static final String imageSrc = "src/ui/images/Block.png";

    public BlockView(Block block) {
        super(block, imageSrc);
    }
}
