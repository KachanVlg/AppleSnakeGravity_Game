import org.junit.Before;
import org.junit.Test;

import java.awt.*;

import static org.junit.Assert.*;

public class HeadTest extends AbstractSegmentTest{


    private Point basePoint;
    private Cell baseCell;
    private final Direction baseDir = Direction.LEFT;


    @Before
    public void initTestParams() {
        basePoint = new Point(0,0);
        baseCell = new Cell(basePoint);
    }

    @Override
    @Test
    public void constructorTest() {
        Head testHead = new Head(baseCell, baseDir);

        assertEquals(baseCell, testHead.getCell());
        assertEquals(testHead, baseCell.getObject());
    }

    @Override
    @Test
    public void unsetCellTest() {
        Head testHead = new Head(baseCell, baseDir);

        Cell actForgottenCell = testHead.unsetCell();

        assertEquals(baseCell, actForgottenCell);
        assertNull(testHead.getCell());
        assertNull(baseCell.getObject());
    }

    @Override
    public void setCellTest() {
        Head testHead = new Head(baseCell, baseDir);

        testHead.unsetCell();
        testHead.setCell(baseCell);

        assertEquals(baseCell, testHead.getCell());
        assertEquals(testHead, testHead.getCell().getObject());
    }

    @Override
    @Test
    public void setNullCell() {
        Head testHead = new Head(baseCell, baseDir);

        testHead.unsetCell();

        assertThrows(IllegalArgumentException.class, () -> testHead.setCell(null));
    }

    @Override
    @Test
    public void setCellWhenAlreadyInstalled() {
        Head testHead = new Head(baseCell, baseDir);

        assertThrows(RuntimeException.class, () -> testHead.setCell(baseCell));
    }

    @Override
    @Test
    public void resetCell() {
        Head testHead = new Head(baseCell, baseDir);
        Cell newCell = new Cell(new Point(1,1));

        Cell forgottenCell = testHead.resetCell(newCell);

        assertNotEquals(newCell, forgottenCell);
        assertEquals(testHead.getCell(), newCell);
    }
}
