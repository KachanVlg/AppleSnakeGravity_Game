package events;

import core.Cell;
import core.Segment;

public interface GameListener {
    void portalIsEntered();
    void fell();
    void movedOn();
    void eatApple(Segment segment, Cell cell);
    void gravityApplied();
}
