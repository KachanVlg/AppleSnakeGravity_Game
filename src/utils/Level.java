package utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.awt.Point;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public record Level(
        List<MyPoint> snake,
        Direction direction,
        List<MyPoint> blocks,
        MyPoint apple,
        MyPoint portal
) {


    // Метод для загрузки уровня из JSON файла
    public static Level loadFromJson(String filePath) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(new File(filePath), Level.class);
    }

    // Геттеры для совместимости с AWT Point
    public List<Point> getSnakePoints() {
        return snake.stream()
                .map(MyPoint::toAwtPoint)
                .collect(Collectors.toList());
    }

    public List<Point> getBlocksPoints() {
        return blocks.stream()
                .map(MyPoint::toAwtPoint)
                .collect(Collectors.toList());
    }

    public Point getApplePoint() {
        return apple != null ? apple.toAwtPoint() : null;
    }

    public Direction getSnakeDirection() {
        return direction != null ? direction : null;
    }

    public Point getPortalPoint() {
        return portal != null ? portal.toAwtPoint() : null;
    }

    // Валидация уровня
    public boolean isValid(int fieldWidth, int fieldHeight) {
        // Проверка что змея имеет минимум 3 сегмента
        if (snake.size() < 3) return false;

        // Проверка всех точек на поле
        return allPointsInBounds(snake, fieldWidth, fieldHeight) &&
                allPointsInBounds(blocks, fieldWidth, fieldHeight) &&
                (apple == null || isInBounds(apple, fieldWidth, fieldHeight)) &&
                (portal == null || isInBounds(portal, fieldWidth, fieldHeight));
    }

    private boolean allPointsInBounds(List<MyPoint> points, int width, int height) {
        return points.stream().allMatch(p -> isInBounds(p, width, height));
    }

    private boolean isInBounds(MyPoint p, int width, int height) {
        return p.x() >= 0 && p.x() < width && p.y() >= 0 && p.y() < height;
    }
}