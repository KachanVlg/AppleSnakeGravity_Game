package events;

import core.Cell;
import core.Segment;

public interface SnakeListener {

    void portalIsEntered();
    void fell();
    void movedOn();
    void eatApple(Segment segment, Cell cell);
    void gravityApplied();
}
