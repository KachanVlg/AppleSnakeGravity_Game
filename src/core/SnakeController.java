import events.SnakeListener;
import utils.Direction;

public interface SnakeController {

    void moveOn(Direction dir);
    void addListener(SnakeListener listener);
}
