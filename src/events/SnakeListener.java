package events;

import core.Apple;
import core.Cell;
import core.Segment;

public interface SnakeListener {

    void portalIsEntered();
    void fell();
    void movedOn();
    void eatApple(Segment segment);
    void gravityApplied();
}
