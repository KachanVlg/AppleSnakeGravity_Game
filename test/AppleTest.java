import org.junit.Before;
import org.junit.Test;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class AppleTest extends StaticObstacleTest {

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
        Apple testApple = new Apple(baseCell, world);

        assertEquals(baseCell, testApple.getCell());
        assertEquals(testApple, baseCell.getObject());
    }

    @Override
    @Test
    public void unsetCellTest() {
        Apple testApple = new Apple(baseCell, world);

        Cell actForgottenCell = testApple.unsetCell();

        assertEquals(baseCell, actForgottenCell);
        assertNull(testApple.getCell());
        assertNull(baseCell.getObject());
    }

    @Override
    @Test
    public void setCellTest() {
        Apple testApple = new Apple(baseCell, world);

        testApple.unsetCell();
        testApple.setCell(baseCell);

        assertEquals(baseCell, testApple.getCell());
        assertEquals(testApple, testApple.getCell().getObject());
    }

    @Override
    @Test
    public void setNullCell() {
        Apple testApple = new Apple(baseCell, world);

        testApple.unsetCell();

        assertThrows(IllegalArgumentException.class, () -> testApple.setCell(null));
    }

    @Override
    @Test
    public void setCellWhenAlreadyInstalled() {
        Apple testApple = new Apple(baseCell, world);

        assertThrows(RuntimeException.class, () -> testApple.setCell(baseCell));
    }

    @Override
    @Test
    public void resetCell() {
        Apple testApple = new Apple(baseCell, world);
        Cell newCell = new Cell(new Point(1,1));

        Cell forgottenCell = testApple.resetCell(newCell);

        assertNotEquals(newCell, forgottenCell);
        assertEquals(testApple.getCell(), newCell);
    }

    @Test
    public void snakeEatApple() {

        Apple apple = new Apple(baseCell, world);


        Cell headCell = new Cell(new Point(0, 1));
        Cell bodyCell = new Cell(new Point(0, 2));
        Cell tailCell = new Cell(new Point(0, 3));
        List<Cell> snakeCells = new ArrayList<>();
        snakeCells.add(headCell);
        snakeCells.add(bodyCell);
        snakeCells.add(tailCell);

        Snake snake = new Snake(snakeCells, world, Direction.UP);
        int initialWeight = snake.getWeight();


        apple.interact(snake);


        assertNull("Яблоко должно быть снято с клетки", apple.getCell());
        assertEquals("Змея должна увеличиться на 1 сегмент", initialWeight + 1, snake.getWeight());


        assertTrue("Клетку должен занимать объект типа Head", baseCell.getObject() instanceof Head);
    }




}
