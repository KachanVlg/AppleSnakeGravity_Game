
public abstract class Acceptor extends ObjectOnField{

    public Acceptor(Cell cell) {
        super(cell);
    }

    public abstract void accept(SnakeVisitor visitor);
}
