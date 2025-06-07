import core.AbstractSegment;
import core.Cell;
import core.Segment;
import core.World;
import org.junit.Before;
import org.junit.Test;

import java.awt.*;

import static org.junit.Assert.*;

public class SegmentTest extends AbstractSegmentTest{


    private Point basePoint;
    private Cell baseCell;
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
        Segment testSegment = new Segment(baseCell, world);

        assertEquals(baseCell, testSegment.getCell());
        assertEquals(testSegment, baseCell.getObject());
    }

    @Override
    @Test
    public void unsetCellTest() {
        Segment testSegment = new Segment(baseCell, world);

        Cell actForgottenCell = testSegment.unsetCell();

        assertEquals(baseCell, actForgottenCell);
        assertNull(testSegment.getCell());
        assertNull(baseCell.getObject());
    }

    @Override
    public void setCellTest() {
        Segment testSegment = new Segment(baseCell, world);

        testSegment.unsetCell();
        testSegment.setCell(baseCell);

        assertEquals(baseCell, testSegment.getCell());
        assertEquals(testSegment, testSegment.getCell().getObject());
    }

    @Override
    @Test
    public void setNullCell() {
        Segment testSegment = new Segment(baseCell, world);

        testSegment.unsetCell();

        assertThrows(IllegalArgumentException.class, () -> testSegment.setCell(null));
    }

    @Override
    @Test
    public void setCellWhenAlreadyInstalled() {
        Segment testSegment = new Segment(baseCell, world);

        assertThrows(RuntimeException.class, () -> testSegment.setCell(baseCell));
    }

    @Override
    @Test
    public void resetCell() {
        Segment testSegment = new Segment(baseCell, world);
        Cell newCell = new Cell(new Point(1,1));

        Cell forgottenCell = testSegment.resetCell(newCell);

        assertNotEquals(newCell, forgottenCell);
        assertEquals(testSegment.getCell(), newCell);
    }

    @Override
    public void getNextSegment() {
        World world = new World("test/resources/test_level.json");
        AbstractSegment testSegment = ((AbstractSegment)world.getCellBy(new Point(2,3)).getObject());
        AbstractSegment expNextSegment = ((AbstractSegment)world.getCellBy(new Point(3,3)).getObject());
        AbstractSegment actNextSegment = testSegment.getNext();

        assertEquals(expNextSegment, actNextSegment);
    }

    @Override
    public void setNextSegment() {
        World world = new World("test/resources/test_level.json");
        AbstractSegment testSegment = ((AbstractSegment)world.getCellBy(new Point(3,3)).getObject());
        Cell newSegmentCell = world.getCellBy(new Point(4,3));
        AbstractSegment newSegment = new Segment(newSegmentCell, world);
        testSegment.setNext(newSegment);
        AbstractSegment actNextSegment = testSegment.getNext();

        assertEquals(newSegment, actNextSegment);
    }

    @Override
    public void setNextSegmentAlreadyExist() {
        World world = new World("test/resources/test_level.json");
        AbstractSegment testSegment = (AbstractSegment)world.getCellBy(new Point(2,3)).getObject();

        assertThrows(RuntimeException.class, () -> testSegment.setNext(new Segment(baseCell, world)));
    }

    @Override
    public void setNextNullSegment() {
        World world = new World("test/resources/test_level.json");
        AbstractSegment testSegment = (AbstractSegment)world.getCellBy(new Point(3,3)).getObject();

        assertThrows(IllegalArgumentException.class, () -> testSegment.setNext(null));

    }
}
