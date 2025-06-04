import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class SnakeTest {

    @Test
    public void moveOnTest() {
        World world = new World("test/resources/test_level.json");
        Snake snake = (Snake)world.getSnakeController();
        List<Cell> oldSnakeCells = snake.getSegments().stream().map(ObjectOnField::getCell).toList();

        snake.moveOn(Direction.LEFT);
        List<Cell> expNewSnakeCells = new ArrayList<>();

        expNewSnakeCells.add(world.getNeighbour(oldSnakeCells.get(0), Direction.LEFT));
        expNewSnakeCells.add(world.getNeighbour(oldSnakeCells.get(1), Direction.LEFT));
        expNewSnakeCells.add(world.getNeighbour(oldSnakeCells.get(2), Direction.LEFT));

        List<Cell> actNewSnakeCells = snake.getSegments().stream().map(ObjectOnField::getCell).toList();
        Assert.assertTrue(actNewSnakeCells.containsAll(expNewSnakeCells));
    }
}
