import core.*;
import events.SnakeListener;
import org.junit.Assert;
import org.junit.Test;
import utils.Direction;


import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class WorldTest {

    @Test
    public void constructorTest() {

        // 2. Создаем мир с этим уровнем
        World testWorld = new World("test/resources/test_level_world.json");

        // 3. Проверки
        assertNotNull(testWorld.getSnakeController());
        assertEquals(20, testWorld.getHeight());
        assertEquals(20, testWorld.getWidth());
        assertEquals(400, testWorld.getField().size());

        // Убедимся, что голова змеи установлена правильно
        SnakeController snakeController = testWorld.getSnakeController();
        Head head = ((Snake) snakeController).getHead();
        assertEquals(new Point(1, 1), head.getCell().getPoint());

        // Проверка наличия яблока и портала
        assertNotNull(testWorld.getCellBy(new Point(5, 5)).getObject()); // core.Apple
        assertNotNull(testWorld.getCellBy(new Point(6, 6)).getObject()); // core.Portal
    }

    @Test
    public void testSnakeMovesWithoutFalling() {
        // Уровень: змея на блоках, под ней есть опора
        World world = new World("test/resources/test_level.json");
        Snake snake = (Snake) world.getSnakeController();

        // Подключим флаги, чтобы отслеживать поведение
        AtomicBoolean moved = new AtomicBoolean(false);
        AtomicBoolean fell = new AtomicBoolean(false);

        snake.addListener(new SnakeListener() {
            @Override
            public void movedOn() {
                moved.set(true);
            }

            @Override
            public void portalIsEntered() {}

            @Override
            public void fell() {
                fell.set(true);
            }
        });

        // Получаем начальные Y координаты всех сегментов
        List<Integer> initialY = snake.getSegments().stream()
                .map(segment -> segment.getCell().getPoint().y)
                .toList();

        // Двигаем змею вправо
        snake.moveOn(Direction.RIGHT);

        // Получаем Y координаты после движения
        List<Integer> afterMoveY = snake.getSegments().stream()
                .map(segment -> segment.getCell().getPoint().y)
                .toList();

        // Проверки
        Assert.assertTrue("Змея должна успешно передвинуться", moved.get());
        Assert.assertFalse("Змея не должна упасть", fell.get());
        Assert.assertEquals("Y-координаты не должны измениться (не упала)", initialY, afterMoveY);
    }

    @Test
    public void testSnakeFallsAfterMovingWithoutSupport() {
        // Уровень: змея "висит" в воздухе, под ней пусто
        World world = new World("test/resources/test_level_snake_fell.json");
        Snake snake = (Snake) world.getSnakeController();

        AtomicBoolean moved = new AtomicBoolean(false);
        AtomicBoolean fell = new AtomicBoolean(false);

        snake.addListener(new SnakeListener() {
            @Override
            public void movedOn() {
                moved.set(true);
            }

            @Override
            public void portalIsEntered() {}

            @Override
            public void fell() {
                fell.set(true);
            }
        });

        // Начальные координаты сегментов
        List<Point> initialPositions = snake.getSegments().stream()
                .map(segment -> segment.getCell().getPoint())
                .toList();

        // Двигаем змею вправо
        snake.moveOn(Direction.LEFT);

        // Координаты после движения
        List<Point> afterPositions = snake.getSegments().stream()
                .map(segment -> segment.getCell().getPoint())
                .toList();

        // Проверки
        Assert.assertFalse("Змея не должна просто переместиться (упадёт)", moved.get());
        Assert.assertTrue("Змея должна упасть", fell.get());

        // Проверим, что y-координаты увеличились (т.е. упала вниз)
        for (int i = 0; i < initialPositions.size(); i++) {
            int initialY = initialPositions.get(i).y;
            int newY = afterPositions.get(i).y;
            Assert.assertTrue("Сегмент должен опуститься ниже", newY < initialY);
        }
    }

    @Test
    public void testBoxGravity_fullBehavior() {
        // Подготавливаем мир с пустым пространством под коробкой
        World world = new World("test/resources/test_level_box_fell.json");

        // Создаем коробку вручную и ставим ее на (5, 2)
        Point boxStart = new Point(5, 2);
        Cell boxStartCell = world.getCellBy(boxStart);
        Box box = new Box(new BasicMovementStrategy(), boxStartCell, world);

        // Убедимся, что коробка зарегистрирована как гравитационный объект
        List<ObjectOnField> gravityObjects = world.getField().stream()
                .map(Cell::getObject)
                .filter(obj -> obj instanceof Box)
                .toList();

        Assert.assertTrue("Коробка должна быть зарегистрирована в поле", gravityObjects.contains(box));

        // Вызываем applyGravity напрямую — ТЕСТИРУЕМ ОСНОВНОЙ МЕТОД
        boolean didNotFall = world.applyGravity(new ArrayList<>(List.of(box)));

        // Коробка должна упасть => метод должен вернуть false
        Assert.assertFalse("Коробка должна упасть, т.к. под ней пусто", didNotFall);

        // Проверим что коробка переместилась ниже
        Point finalPosition = box.getCell().getPoint();
        Assert.assertTrue("Коробка должна опуститься ниже по Y", finalPosition.y < boxStart.y);
    }

    @Test
    public void testApplyGravityAllSingleObjects_behavior() {
        World world = new World("test/resources/test_level_two_box.json");

        // Создаем 2 коробки — одна на опоре, одна в воздухе
        Cell box1Cell = world.getCellBy(new Point(4, 4)); // Под ней пусто — упадет
        Box box1 = new Box(new BasicMovementStrategy(), box1Cell, world);


        Cell box2Cell = world.getCellBy(new Point(1, 2)); // Под ней блок — не упадет
        Box box2 = new Box(new BasicMovementStrategy(), box2Cell, world);

        // Вызываем метод applyGravityAllSingleObjects
        world.applyGravityAllSingleObjects();

        // box1 должен был упасть — его уже нет в singleGravityObjects
        Assert.assertNull("Коробка 1 должна упасть", box1.getCell());

        // box2 должна остаться на месте
        Assert.assertEquals("Коробка 2 не должна упасть", new Point(1, 2), box2.getCell().getPoint());
    }
}

