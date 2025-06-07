import core.*;
import org.junit.Before;
import org.junit.Test;
import utils.Direction;

import java.awt.*;

import static org.junit.Assert.*;

public class HeadTest extends AbstractSegmentTest{


    private Point basePoint;
    private Cell baseCell;
    private final Direction baseDir = Direction.LEFT;
    private World world;


    @Before
    public void initTestParams() {
        basePoint = new Point(0,0);
        baseCell = new Cell(basePoint);
        world = new World();
    }

    @Override
    @Test
    public void constructorTest() {
        Head testHead = new Head(baseCell, baseDir, world);

        assertEquals(baseCell, testHead.getCell());
        assertEquals(testHead, baseCell.getObject());
    }

    @Override
    @Test
    public void unsetCellTest() {
        Head testHead = new Head(baseCell, baseDir, world);

        Cell actForgottenCell = testHead.unsetCell();

        assertEquals(baseCell, actForgottenCell);
        assertNull(testHead.getCell());
        assertNull(baseCell.getObject());
    }

    @Override
    public void setCellTest() {
        Head testHead = new Head(baseCell, baseDir, world);

        testHead.unsetCell();
        testHead.setCell(baseCell);

        assertEquals(baseCell, testHead.getCell());
        assertEquals(testHead, testHead.getCell().getObject());
    }

    @Override
    @Test
    public void setNullCell() {
        Head testHead = new Head(baseCell, baseDir,world);

        testHead.unsetCell();

        assertThrows(IllegalArgumentException.class, () -> testHead.setCell(null));
    }

    @Override
    @Test
    public void setCellWhenAlreadyInstalled() {
        Head testHead = new Head(baseCell, baseDir, world);

        assertThrows(RuntimeException.class, () -> testHead.setCell(baseCell));
    }

    @Override
    @Test
    public void resetCell() {
        Head testHead = new Head(baseCell, baseDir, world);
        Cell newCell = new Cell(new Point(1,1));

        Cell forgottenCell = testHead.resetCell(newCell);

        assertNotEquals(newCell, forgottenCell);
        assertEquals(testHead.getCell(), newCell);
    }

    @Test
    public void setDir() {
        Head testHead = new Head(baseCell, baseDir, world);
        testHead.resetDir(Direction.RIGHT);
        Direction resDir = testHead.getDir();

        assertNotEquals(baseDir, resDir);
    }

    @Override
    public void getNextSegment() {
        World world = new World("test/resources/test_level.json");
        Head testHead = ((Snake)world.getSnakeController()).getHead();
        AbstractSegment expNextSegment = ((AbstractSegment)world.getCellBy(new Point(2,3)).getObject());
        AbstractSegment actNextSegment = testHead.getNext();

        assertEquals(expNextSegment, actNextSegment);
    }

    @Override
    public void setNextSegment() {

    }

    @Override
    public void setNextSegmentAlreadyExist() {
        World world = new World("test/resources/test_level.json");
        Head testHead = ((Snake)world.getSnakeController()).getHead();

        assertThrows(RuntimeException.class, () -> testHead.setNext(new Segment(baseCell, world)));
    }

    @Override
    public void setNextNullSegment() {
        World world = new World("test/resources/test_level.json");
        Head testHead = ((Snake)world.getSnakeController()).getHead();

        assertThrows(IllegalArgumentException.class, () -> testHead.setNext(null));

    }
}
