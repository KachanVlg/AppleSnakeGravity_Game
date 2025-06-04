import org.junit.Before;
import org.junit.Test;
import utils.Direction;

import java.awt.*;

import static org.junit.Assert.*;

public class BlockTest extends ObjectOnFieldTest {

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
        Block testBlock = new Block(baseCell,world);

        assertEquals(baseCell, testBlock.getCell());
        assertEquals(testBlock, baseCell.getObject());
    }

    @Override
    @Test
    public void unsetCellTest() {
        Block testBlock = new Block(baseCell, world);

        Cell actForgottenCell = testBlock.unsetCell();

        assertEquals(baseCell, actForgottenCell);
        assertNull(testBlock.getCell());
        assertNull(baseCell.getObject());
    }

    @Override
    public void setCellTest() {
        Block testBlock = new Block(baseCell, world);

        testBlock.unsetCell();
        testBlock.setCell(baseCell);

        assertEquals(baseCell, testBlock.getCell());
        assertEquals(testBlock, testBlock.getCell().getObject());
    }

    @Override
    @Test
    public void setNullCell() {
        Block testBlock = new Block(baseCell, world);

        testBlock.unsetCell();

        assertThrows(IllegalArgumentException.class, () -> testBlock.setCell(null));
    }

    @Override
    @Test
    public void setCellWhenAlreadyInstalled() {
        Block testBlock = new Block(baseCell, world);

        assertThrows(RuntimeException.class, () -> testBlock.setCell(baseCell));
    }

    @Override
    @Test
    public void resetCell() {
        Block testBlock = new Block(baseCell ,world);
        Cell newCell = new Cell(new Point(1,1));

        Cell forgottenCell = testBlock.resetCell(newCell);

        assertNotEquals(newCell, forgottenCell);
        assertEquals(testBlock.getCell(), newCell);
    }

    @Test
    public void snakeTryToMoveToBlock() {
        World world = new World("test/resources/test_level_block.json");

        Cell blockCell = world.getCellBy(new Point(4,3));
        Cell headCell = world.getCellBy(new Point(3,3));

        Snake controller = (Snake)world.getSnakeController();
        controller.moveOn(Direction.RIGHT);

        assertTrue(blockCell.getObject() instanceof Block);
        assertEquals(headCell, controller.getHead().getCell());
    }
}
