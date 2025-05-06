
import java.awt.*;
import java.io.IOException;
import java.util.List;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) throws IOException {
        Level level = Level.loadFromJson("src/level.json");

        List<Point> snakePoints = level.getSnakePoints();

        int a = 5 + 4;
    }
}