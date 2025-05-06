import org.junit.Before;
import org.junit.Test;

import java.awt.*;

import static org.junit.Assert.*;

public class SegmentTest extends AbstractSegmentTest{


    private Point basePoint;
    private Cell baseCell;


    @Before
    public void initTestParams() {
        basePoint = new Point(0,0);
        baseCell = new Cell(basePoint);
    }

    @Override
    @Test
    public void constructorTest() {
        Segment testSegment = new Segment(baseCell);

        assertEquals(baseCell, testSegment.getCell());
        assertEquals(testSegment, baseCell.getObject());
    }

    @Override
    @Test
    public void unsetCellTest() {
        Segment testSegment = new Segment(baseCell);

        Cell actForgottenCell = testSegment.unsetCell();

        assertEquals(baseCell, actForgottenCell);
        assertNull(testSegment.getCell());
        assertNull(baseCell.getObject());
    }

    @Override
    public void setCellTest() {
        Segment testSegment = new Segment(baseCell);

        testSegment.unsetCell();
        testSegment.setCell(baseCell);

        assertEquals(baseCell, testSegment.getCell());
        assertEquals(testSegment, testSegment.getCell().getObject());
    }

    @Override
    @Test
    public void setNullCell() {
        Segment testSegment = new Segment(baseCell);

        testSegment.unsetCell();

        assertThrows(IllegalArgumentException.class, () -> testSegment.setCell(null));
    }

    @Override
    @Test
    public void setCellWhenAlreadyInstalled() {
        Segment testSegment = new Segment(baseCell);

        assertThrows(RuntimeException.class, () -> testSegment.setCell(baseCell));
    }

    @Override
    @Test
    public void resetCell() {
        Segment testSegment = new Segment(baseCell);
        Cell newCell = new Cell(new Point(1,1));

        Cell forgottenCell = testSegment.resetCell(newCell);

        assertNotEquals(newCell, forgottenCell);
        assertEquals(testSegment.getCell(), newCell);
    }
}
