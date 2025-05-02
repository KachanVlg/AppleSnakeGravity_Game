import java.awt.*;
import java.util.Objects;

public class Cell {

    private static final String CELL_OCCUPIED_MSG = "Cell is occupied";
    private static final String OBJ_NULL_MSG = "Object mustn't be null";
    private ObjectOnField object;
    private final Point point;

    public Cell(Point point) {
        this.point = point;
    }

    public Point getPoint() {
        return point;
    }

    public ObjectOnField getObject() {
        return object;
    }

    public void unsetObject() {
        object = null;
    }

    public void setObject(ObjectOnField object) {
        if(this.object != null) throw new RuntimeException(CELL_OCCUPIED_MSG);
        if(object == null) throw new IllegalArgumentException(OBJ_NULL_MSG);
        this.object = object;
    }

    public void resetObject(ObjectOnField object) {
        unsetObject();
        setObject(object);
    }

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof Cell)) return false;
        return (obj == this) || ((point.x == ((Cell) obj).getPoint().x) && (point.y == ((Cell) obj).getPoint().y));
    }

    @Override
    public int hashCode() {
        return Objects.hash(point);
    }
}
