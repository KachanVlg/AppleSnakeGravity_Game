import org.junit.Before;
import org.junit.Test;
import utils.Direction;

import java.awt.*;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

public class BoxTest extends MovableObstacleTest{


    private Point basePoint;
    private Cell baseCell;
    private World world;
    private MovementStrategy movementStrategy;


    @Before
    public void initTestParams() {
        basePoint = new Point(0,0);
        baseCell = new Cell(basePoint);
        world = new World();
        movementStrategy = new BasicMovementStrategy();
    }


    @Override
    @Test
    public void constructorTest() {
        Box testBox = new Box(movementStrategy, baseCell, world);

        assertEquals(baseCell, testBox.getCell());
        assertEquals(testBox, baseCell.getObject());
    }

    @Override
    @Test
    public void unsetCellTest() {
        Box testBox = new Box(movementStrategy, baseCell, world);

        Cell actForgottenCell = testBox.unsetCell();

        assertEquals(baseCell, actForgottenCell);
        assertNull(testBox.getCell());
        assertNull(baseCell.getObject());
    }

    @Override
    @Test
    public void setCellTest() {
        Box testBox = new Box(movementStrategy, baseCell, world);

        testBox.unsetCell();
        testBox.setCell(baseCell);

        assertEquals(baseCell, testBox.getCell());
        assertEquals(testBox, testBox.getCell().getObject());
    }

    @Override
    @Test
    public void setNullCell() {
        Box testBox = new Box(movementStrategy, baseCell, world);

        testBox.unsetCell();

        assertThrows(IllegalArgumentException.class, () -> testBox.setCell(null));
    }

    @Override
    @Test
    public void setCellWhenAlreadyInstalled() {
        Box testBox = new Box(movementStrategy, baseCell, world);

        assertThrows(RuntimeException.class, () -> testBox.setCell(baseCell));
    }

    @Override
    @Test
    public void resetCell() {
        Box testBox = new Box(movementStrategy, baseCell, world);
        Cell newCell = new Cell(new Point(1,1));

        Cell forgottenCell = testBox.resetCell(newCell);

        assertNotEquals(newCell, forgottenCell);
        assertEquals(testBox.getCell(), newCell);
    }

    @Test
    public void testSnakeCanPushBox() {

        World world;
        Box box;
        Snake snake;
        Cell boxCellBefore;
        Cell boxCellAfter;

        world = new World("test/resources/test_level_box_push_basic.json");

        // Устанавливаем коробку вручную на (4,3)
        boxCellBefore = world.getField().stream()
                .filter(c -> c.getPoint().equals(new Point(4, 3)))
                .findFirst()
                .orElseThrow();

        box = new Box(new BasicMovementStrategy(), boxCellBefore, world);

        // Находим целевую ячейку (5,3) — туда должна переместиться коробка
        boxCellAfter = world.getNeighbour(boxCellBefore, Direction.RIGHT);

        // Получаем змею из мира
        snake = (Snake) world.getSnakeController();




        // Проверка исходного положения
        assertEquals("Коробка стоит на (4,3)", new Point(4, 3), box.getCell().getPoint());
        assertNull("Целевая ячейка пуста", boxCellAfter.getObject());

        // Змея движется вправо и должна толкнуть коробку
        snake.moveOn(Direction.RIGHT);

        // Проверка положения коробки и головы змеи
        assertEquals("Коробка переместилась на (5,3)", new Point(5, 3), box.getCell().getPoint());
        assertEquals("Голова змеи теперь на (4,3)", new Point(4, 3), snake.getHead().getCell().getPoint());
        assertSame("Коробка стоит на новой клетке", box, boxCellAfter.getObject());
    }

    @Test
    public void testBoxCanMoveWhenSupportedByAnotherBox() {

        World world;
        Box topBox;
        Box baseBox;
        Snake snake;

        world = new World("test/resources/test_level_box_push_support.json");

        // Устанавливаем базовую коробку на (4,4)
        Cell baseCell = world.getField().stream()
                .filter(c -> c.getPoint().equals(new Point(4, 3)))
                .findFirst()
                .orElseThrow();
        baseBox = new Box(new BasicMovementStrategy(), baseCell, world);

        // Устанавливаем верхнюю коробку (которую будем двигать) на (4,3)
        Cell topCell = world.getNeighbour(baseCell, Direction.UP);
        topBox = new Box(new SupportedOnlyByBoxStrategy(), topCell, world);


        SnakeController controller = world.getSnakeController();
        snake = (Snake) controller;

        // Коробка должна стоять на другой коробке
        assertEquals(new Point(4, 4), topBox.getCell().getPoint());
        assertEquals(new Point(4, 3), baseBox.getCell().getPoint());

        // Клетка куда должна уйти верхняя коробка
        Cell targetCell = world.getCellBy(new Point(5, 4));
        assertNull("Целевая ячейка должна быть пуста", targetCell.getObject());

        // Двигаем змею вправо
        snake.moveOn(Direction.RIGHT);


        assertEquals("Верхняя коробка должна оказаться на (5,4)", new Point(5, 4), topBox.getCell().getPoint());
        assertSame("Коробка установлена в целевую ячейку", topBox, targetCell.getObject());
    }

    @Test
    public void setMovementStrategy() {
        MovementStrategy movementStrategy = new BasicMovementStrategy();
        Box testBox = new Box(movementStrategy, baseCell, world);
        MovementStrategy newMovementStrategy = new TimeLimitedMovementStrategy(100);
        testBox.setMovementStrategy(newMovementStrategy);

        assertTrue(testBox.getMovementStrategy() instanceof TimeLimitedMovementStrategy);
    }

    @Test
    public void testBoxCannotMoveIfBlockedByAnotherBox() {
        World world;
        Box pushingBox;
        Box blockingBox;
        Snake snake;

        world = new World("test/resources/test_level_box_push_basic.json");

        // Устанавливаем коробку, которую будет толкать змея, на (4,4)
        Cell pushingCell = world.getCellBy(new Point(4, 4));
        pushingBox = new Box(new BasicMovementStrategy(), pushingCell, world);

        // Устанавливаем блокирующую коробку на (5,4) — прямо за толкаемой
        Cell blockingCell = world.getCellBy(new Point(5, 4));
        blockingBox = new Box(new BasicMovementStrategy(), blockingCell, world);

        // Проверяем начальные позиции
        assertEquals(new Point(4, 4), pushingBox.getCell().getPoint());
        assertEquals(new Point(5, 4), blockingBox.getCell().getPoint());

        // Проверяем, что целевая клетка занята
        assertNotNull("Целевая ячейка занята блокирующей коробкой", blockingCell.getObject());

        // Двигаем змею вправо — коробка НЕ должна сдвинуться
        snake = (Snake) world.getSnakeController();
        snake.moveOn(Direction.RIGHT);

        // Позиции не должны измениться
        assertEquals("Толкаемая коробка должна остаться на месте", new Point(4, 4), pushingBox.getCell().getPoint());
        assertEquals("Блокирующая коробка должна остаться на месте", new Point(5, 4), blockingBox.getCell().getPoint());

        // Проверка объектов в ячейках
        assertSame("В ячейке (4,4) должна остаться толкаемая коробка", pushingBox, pushingCell.getObject());
        assertSame("В ячейке (5,4) должна остаться блокирующая коробка", blockingBox, blockingCell.getObject());
    }

    @Test
    public void testBoxWithTimeLimitedStrategy_DoesNotMoveAfterTimeout() throws InterruptedException {
        World world = new World("test/resources/test_level_time_limited.json");

        Cell startCell = world.getCellBy(new Point(4, 4));
        Box box = new Box(new TimeLimitedMovementStrategy(1), startCell, world); // 1 сек

        Thread.sleep(1200); // Ждём чуть больше секунды

        boolean moved = box.tryToMove(null, Direction.RIGHT);

        assertFalse("Коробка не должна сдвигаться после истечения времени", moved);
        assertEquals("Коробка осталась на месте", startCell, box.getCell());
    }

    @Test
    public void testBoxWithTimeLimitedStrategy_MovesBeforeTimeout() {
        World world = new World("test/resources/test_level_time_limited.json");

        Cell startCell = world.getCellBy(new Point(4, 4));
        Box box = new Box(new TimeLimitedMovementStrategy(5), startCell, world); // 5 сек

        Cell targetCell = world.getNeighbour(startCell, Direction.RIGHT);
        assertNull(targetCell.getObject());

        boolean moved = box.tryToMove(null, Direction.RIGHT);

        assertTrue("Коробка должна сдвинуться до истечения времени", moved);
        assertEquals("Коробка должна быть на новой позиции", targetCell, box.getCell());
    }



}
