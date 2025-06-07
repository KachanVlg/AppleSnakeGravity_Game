import utils.Direction;

import javax.swing.*;
import java.awt.*;

public class Head extends AbstractSegment{

    private Direction dir;

    public Head(Cell cell, Direction dir, World world) {
        super(cell, world);
        this.dir = dir;
    }

    public Head(Cell cell, Direction dir, AbstractSegment next, World world) {
        super(cell, next, world);
        this.dir = dir;
    }

    public Direction getDir() {
        return dir;
    }

    public void resetDir(Direction dir) {
        this.dir = dir;
    }

}
