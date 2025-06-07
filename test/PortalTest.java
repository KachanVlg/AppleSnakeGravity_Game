import core.Cell;
import core.Portal;
import core.Snake;
import core.World;
import org.junit.Before;
import org.junit.Test;
import utils.Direction;

import java.awt.*;

import static org.junit.Assert.*;

public class PortalTest extends StaticObstacleTest {

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
        Portal testPortal = new Portal(baseCell,world);

        assertEquals(baseCell, testPortal.getCell());
        assertEquals(testPortal, baseCell.getObject());
    }

    @Override
    @Test
    public void unsetCellTest() {
        Portal testPortal = new Portal(baseCell, world);

        Cell actForgottenCell = testPortal.unsetCell();

        assertEquals(baseCell, actForgottenCell);
        assertNull(testPortal.getCell());
        assertNull(baseCell.getObject());
    }

    @Override
    public void setCellTest() {
        Portal testPortal = new Portal(baseCell, world);

        testPortal.unsetCell();
        testPortal.setCell(baseCell);

        assertEquals(baseCell, testPortal.getCell());
        assertEquals(testPortal, testPortal.getCell().getObject());
    }

    @Override
    @Test
    public void setNullCell() {
        Portal testPortal = new Portal(baseCell, world);

        testPortal.unsetCell();

        assertThrows(IllegalArgumentException.class, () -> testPortal.setCell(null));
    }

    @Override
    @Test
    public void setCellWhenAlreadyInstalled() {
        Portal testPortal = new Portal(baseCell, world);

        assertThrows(RuntimeException.class, () -> testPortal.setCell(baseCell));
    }

    @Override
    @Test
    public void resetCell() {
        Portal testPortal = new Portal(baseCell, world);
        Cell newCell = new Cell(new Point(1,1));

        Cell forgottenCell = testPortal.resetCell(newCell);

        assertNotEquals(newCell, forgottenCell);
        assertEquals(testPortal.getCell(), newCell);
    }

    @Test
    public void snakeEnteredPortal() {
        World world = new World("test/resources/test_level_portal.json");

        Cell headCell = world.getCellBy(new Point(3,3));

        Snake controller = (Snake)world.getSnakeController();
        controller.moveOn(Direction.RIGHT);

        assertEquals(headCell, controller.getHead().getCell());
    }
}
