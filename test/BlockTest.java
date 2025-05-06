import org.junit.Before;
import org.junit.Test;

import java.awt.*;

import static org.junit.Assert.*;

public class BlockTest extends AcceptorTest{

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
        Block testBlock = new Block(baseCell);

        assertEquals(baseCell, testBlock.getCell());
        assertEquals(testBlock, baseCell.getObject());
    }

    @Override
    @Test
    public void unsetCellTest() {
        Block testBlock = new Block(baseCell);

        Cell actForgottenCell = testBlock.unsetCell();

        assertEquals(baseCell, actForgottenCell);
        assertNull(testBlock.getCell());
        assertNull(baseCell.getObject());
    }

    @Override
    public void setCellTest() {
        Block testBlock = new Block(baseCell);

        testBlock.unsetCell();
        testBlock.setCell(baseCell);

        assertEquals(baseCell, testBlock.getCell());
        assertEquals(testBlock, testBlock.getCell().getObject());
    }

    @Override
    @Test
    public void setNullCell() {
        Block testBlock = new Block(baseCell);

        testBlock.unsetCell();

        assertThrows(IllegalArgumentException.class, () -> testBlock.setCell(null));
    }

    @Override
    @Test
    public void setCellWhenAlreadyInstalled() {
        Block testBlock = new Block(baseCell);

        assertThrows(RuntimeException.class, () -> testBlock.setCell(baseCell));
    }

    @Override
    @Test
    public void resetCell() {
        Block testBlock = new Block(baseCell);
        Cell newCell = new Cell(new Point(1,1));

        Cell forgottenCell = testBlock.resetCell(newCell);

        assertNotEquals(newCell, forgottenCell);
        assertEquals(testBlock.getCell(), newCell);
    }
}
