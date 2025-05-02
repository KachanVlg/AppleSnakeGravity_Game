import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class World {

    private static final int WIDTH = 10;
    private static final int HEIGHT = 10;
    private List<Cell> field;
    private Snake snake;
    private  static final Direction GRAVITY_DIR = Direction.DOWN;

    public List<Cell> applyGravity(List<Cell> item) {

        List<Cell> newItemPositions = getNeighbours(item, GRAVITY_DIR);
        if(newItemPositions.size() != item.size()) return null;
        newItemPositions.removeAll(item);
        boolean newPositionIsFind = false;
        boolean snakeFell = false;

        while(!newPositionIsFind && !snakeFell) {

            boolean isFind = false;
            while(!isFind) {}




        }
        return null;
    }

    public Cell getNeighbour(Cell cell, Direction dir) {
        return getCellBy(new Point((cell.getPoint().x + dir.getDx()), cell.getPoint().y + dir.getDy()));
    }

    public List<Cell> getNeighbours(List<Cell> cells, Direction dir) {
        return cells.stream().map(oldCell -> getNeighbour(oldCell, dir)).filter(Objects::nonNull).collect(Collectors.toCollection(ArrayList::new));
    }

    private Cell getCellBy(Point point) {
        return field.stream().filter(cell -> cell.getPoint().equals(point)).findFirst().orElse(null);
    }



    
}
