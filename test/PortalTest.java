import org.junit.Before;
import org.junit.Test;

import java.awt.*;

import static org.junit.Assert.*;

public class PortalTest extends AcceptorTest {

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
        Portal testPortal = new Portal(baseCell);

        assertEquals(baseCell, testPortal.getCell());
        assertEquals(testPortal, baseCell.getObject());
    }

    @Override
    @Test
    public void unsetCellTest() {
        Portal testPortal = new Portal(baseCell);

        Cell actForgottenCell = testPortal.unsetCell();

        assertEquals(baseCell, actForgottenCell);
        assertNull(testPortal.getCell());
        assertNull(baseCell.getObject());
    }

    @Override
    public void setCellTest() {
        Portal testPortal = new Portal(baseCell);

        testPortal.unsetCell();
        testPortal.setCell(baseCell);

        assertEquals(baseCell, testPortal.getCell());
        assertEquals(testPortal, testPortal.getCell().getObject());
    }

    @Override
    @Test
    public void setNullCell() {
        Portal testPortal = new Portal(baseCell);

        testPortal.unsetCell();

        assertThrows(IllegalArgumentException.class, () -> testPortal.setCell(null));
    }

    @Override
    @Test
    public void setCellWhenAlreadyInstalled() {
        Portal testPortal = new Portal(baseCell);

        assertThrows(RuntimeException.class, () -> testPortal.setCell(baseCell));
    }

    @Override
    @Test
    public void resetCell() {
        Portal testPortal = new Portal(baseCell);
        Cell newCell = new Cell(new Point(1,1));

        Cell forgottenCell = testPortal.resetCell(newCell);

        assertNotEquals(newCell, forgottenCell);
        assertEquals(testPortal.getCell(), newCell);
    }
}