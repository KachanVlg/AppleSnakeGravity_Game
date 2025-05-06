import java.awt.*;

public record MyPoint(int x, int y) {
    public Point toAwtPoint() {
        return new Point(x, y);
    }
}
