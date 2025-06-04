public abstract class AbstractSegment extends ObjectOnField{

    private AbstractSegment next;
    private static final String NEXT_EXISTS_MSG = "Segment already has next segment";
    private static final String NEXT_NULL_MSG = "Next segment mustn't be null";


    public AbstractSegment(Cell cell, AbstractSegment next, World world) {
        super(cell, world);
        this.next = next;
    }

    public AbstractSegment(Cell cell, World world) {
        super(cell, world);
    }


    public AbstractSegment getNext() {
        return next;
    }

    public boolean hasNext() {
        return next != null;
    }

    public void setNext(AbstractSegment next) {
        if(next == null) throw new IllegalArgumentException(NEXT_NULL_MSG);
        if (hasNext()) throw new RuntimeException(NEXT_EXISTS_MSG);
        this.next = next;
    }

    public void moveTo(Cell cell) {
        Cell forgottenCell = resetCell(cell);
        if(hasNext()) next.moveTo(forgottenCell);
    }
}
