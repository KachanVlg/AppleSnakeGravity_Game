package events;

import core.Segment;

public interface GameListener {
    void portalIsEntered();
    void fell();
    void movedOn();
    void eatApple(Segment segment);
    void gravityApplied();
}
