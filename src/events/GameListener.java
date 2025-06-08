package events;

import core.Apple;
import core.Cell;
import core.Segment;

public interface GameListener {
    void portalIsEntered();
    void fell();
    void movedOn();
    void eatApple(Segment segment);
    void gravityApplied();
}
