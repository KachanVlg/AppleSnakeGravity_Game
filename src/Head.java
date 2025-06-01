import javax.swing.*;
import java.awt.*;

public class Head extends AbstractSegment{

    private static Image upHeadImage;
    private static Image downHeadImage;
    private static Image leftHeadImage;
    private static Image rightHeadImage;

    static {
        try {
            upHeadImage = new ImageIcon(Apple.class.getResource("/UpHead.png")).getImage();
        } catch (Exception e) {
            System.err.println("Ошибка загрузки apple.png: " + e.getMessage());
        }
    }
    static {
        try {
            downHeadImage = new ImageIcon(Apple.class.getResource("/DownHead.png")).getImage();
        } catch (Exception e) {
            System.err.println("Ошибка загрузки apple.png: " + e.getMessage());
        }
    }
    static {
        try {
            leftHeadImage = new ImageIcon(Apple.class.getResource("/LeftHead.png")).getImage();
        } catch (Exception e) {
            System.err.println("Ошибка загрузки apple.png: " + e.getMessage());
        }
    }
    static {
        try {
            rightHeadImage = new ImageIcon(Apple.class.getResource("/RightHead.png")).getImage();
        } catch (Exception e) {
            System.err.println("Ошибка загрузки apple.png: " + e.getMessage());
        }
    }


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

    @Override
    public void draw(Graphics g, int x, int y, int cellSize) {


        if (getDir() == Direction.UP) {
            g.drawImage(upHeadImage, x, y, cellSize, cellSize, null);
        } else if (getDir() == Direction.DOWN) {
            g.drawImage(downHeadImage, x, y, cellSize, cellSize, null);
        } else if (getDir() == Direction.LEFT) {
            g.drawImage(leftHeadImage, x, y, cellSize, cellSize, null);
        } else if (getDir() == Direction.RIGHT) {
            g.drawImage(rightHeadImage, x, y, cellSize, cellSize, null);
        }
    }
}
