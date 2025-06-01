
public abstract class ObjectOnField extends GameEntity{

    private Cell cell;
    private static final String OBJ_INSTALLED_IN_CELL_MSG = "Object already installed in cell";
    private static final String CELL_NULL_MSG = "Cell mustn't be null";
    private boolean isFell = false;

    public ObjectOnField(Cell cell, World world) {
        super(world);
        setCell(cell);
    }

    public Cell getCell() {
        return cell;
    }

    public void setFell(boolean isFell) {
        this.isFell = isFell;
    }

    public void setCell(Cell cell) {
        if(this.cell != null) throw new RuntimeException(OBJ_INSTALLED_IN_CELL_MSG);
        if(cell == null) throw new IllegalArgumentException(CELL_NULL_MSG);
        cell.setObject(this);
        this.cell = cell;
    }

    public Cell unsetCell() {
        cell.unsetObject();
        Cell forgottenCell = cell;
        cell = null;
        return forgottenCell;
    }

    public Cell resetCell(Cell cell) {
        Cell forgottenCell = unsetCell();
        setCell(cell);
        return forgottenCell;
    }
}
