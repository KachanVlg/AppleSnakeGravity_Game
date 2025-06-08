package core;

import utils.*;

import java.awt.*;
import java.io.IOException;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

public class World {

    private static final int WIDTH = 20;
    private static final int HEIGHT = 20;
    private List<Cell> field;
    private SnakeController snake;
    private  static final Direction GRAVITY_DIR = Direction.DOWN;
    private static Level level;
    private List<ObjectOnField> singleGravityObjects = new ArrayList<>();

    public int getWidth() {
        return WIDTH;
    }

    public int getHeight() {
        return HEIGHT;
    }

    public List<Cell> getField() {
        return field;
    }

    static {
        try {
            level = Level.loadFromJson("src/utils/level2.json");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void cellsInit() {
        field = new ArrayList<>(WIDTH * HEIGHT);
        for (int y = 0; y < HEIGHT; y++) {
            for (int x = 0; x < WIDTH; x++) {
                field.add(new Cell(new Point(x, y)));
            }
        }
    }

    public SnakeController getSnakeController() {
        return snake;
    }

    public void addGravityObject(ObjectOnField obj) {
        singleGravityObjects.add(obj);
    }

    public World(String pathToLevel) {
        try {
            level = Level.loadFromJson(pathToLevel);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        init();
    }

    public <T extends ObjectOnField> List<T> getObjectsOfType(Class<T> clazz) {
        return field.stream()
                .map(Cell::getObject)
                .filter(Objects::nonNull)
                .filter(clazz::isInstance)
                .map(clazz::cast)
                .collect(Collectors.toList());
    }

    private void initSnake() {
        List<Point> snakePoints = level.getSnakePoints();
        List<Cell> snakeCells = snakePoints.stream().map(this::getCellBy).collect(Collectors.toCollection(ArrayList::new));
        Direction snakeDirection = level.getSnakeDirection();
        snake = new Snake(snakeCells, this, snakeDirection);
    }

    private void initBlocks() {
        List<Point> blocksPoints = level.getBlocksPoints();
        List<Cell> blocksCells = blocksPoints.stream().map(this::getCellBy).collect(Collectors.toCollection(ArrayList::new));
        blocksCells.forEach(cell -> new Block(cell, this));
    }

    private void initApple() {
        Point applePoint = level.getApplePoint();
        Cell appleCell = getCellBy(applePoint);
        new Apple(appleCell, this);
    }

    private void initPortal() {
        Point portalPoint = level.getPortalPoint();
        Cell portalCell = getCellBy(portalPoint);
        new Portal(portalCell, this);
    }

    private void initBoxes() {

        if (level.getBoxes() == null) {
            return;
        }

        for (BoxDto boxDto : level.getBoxes()) {
            MyPoint myPoint = boxDto.getPoint();
            Point awtPoint = myPoint.toAwtPoint();
            Cell boxCell = getCellBy(awtPoint);

            MovementStrategyEnum strategyEnum = boxDto.getMovementStrategy();
            MovementStrategy strategy;

            // Выбор стратегии по enum'у
            switch (strategyEnum) {
                case BASIC -> strategy = new BasicMovementStrategy(this);
                case TIME -> strategy = new TimeLimitedMovementStrategy(this);
                case SUPPORT_BY -> strategy = new SupportedOnlyByBoxStrategy(this);
                default -> throw new IllegalArgumentException("Неизвестная стратегия: " + strategyEnum);
            }
            new Box(strategy, boxCell, this);
        }
    }

    private void init() {
        cellsInit();
        initSnake();
        initBlocks();
        initApple();
        initPortal();
        initBoxes();
    }

    public World() {
        init();
    }

    public boolean applyGravity(List< ? extends ObjectOnField> item) {

        List<Cell> itemCells = item.stream()
                .map(ObjectOnField::getCell)
                .toList();

        List<Cell> neighbours = getNeighbours(itemCells, GRAVITY_DIR);

        if(itemCells.size() != neighbours.size()) {
            return false;
        }

        neighbours.removeAll(itemCells);
        final int checkSize = neighbours.size();
        boolean hasFallen = false;
        boolean hasLanded = false;

        while(!hasFallen && !hasLanded) {

            hasLanded = neighbours.stream()
                    .anyMatch(cell -> cell.getObject() !=null);

            if(!hasLanded) {
                singleGravityShiftItem(item);
                neighbours = getNeighbours(neighbours, GRAVITY_DIR);
                hasFallen = neighbours.size() != checkSize;
            }
        }
        return !hasFallen;
    }

    public void applyGravityAllSingleObjects() {
        if(singleGravityObjects.isEmpty()) {return;}

        ySort(singleGravityObjects);
        singleGravityObjects.forEach(object -> {
            object.setFell(!applyGravity(new ArrayList<>(List.of(object))));
        });
        singleGravityObjects.removeIf(object -> object.isFell());
    }

    private List<? extends ObjectOnField> ySort(List<? extends ObjectOnField> objects) {
        objects.sort(Comparator.comparingInt(obj -> obj.getCell().getPoint().y));
        return objects;
    }

    private void singleGravityShiftItem(List<? extends ObjectOnField> item) {
        ySort(item);
        item.forEach(obj -> {
            Point current = obj.getCell().getPoint();
            Point newPoint = new Point(
                    current.x + GRAVITY_DIR.getDx(),
                    current.y + GRAVITY_DIR.getDy()
            );
            obj.resetCell(getCellBy(newPoint));
        });
    }
    public Cell getNeighbour(Cell cell, Direction dir) {
        return getCellBy(new Point((cell.getPoint().x + dir.getDx()), cell.getPoint().y + dir.getDy()));
    }

    public List<Cell> getNeighbours(List<Cell> cells, Direction dir) {
        return cells.stream().map(oldCell -> getNeighbour(oldCell, dir)).filter(Objects::nonNull).collect(Collectors.toCollection(ArrayList::new));
    }

    public Cell getCellBy(Point point) {
        return field.stream().filter(cell -> cell.getPoint().equals(point)).findFirst().orElse(null);
    }

}
