package events;

import core.Segment;

public interface SnakeListener {

    void portalIsEntered();
    void fell();
    void movedOn();
    void eatApple(Segment segment);
    void gravityApplied();
}
