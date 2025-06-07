package core;

import events.SnakeListener;
import utils.Direction;

import java.util.List;

public interface SnakeController {

    void moveOn(Direction dir);
    void addListener(SnakeListener listener);
    List<AbstractSegment> getSegments();
    Head getHead();
}
