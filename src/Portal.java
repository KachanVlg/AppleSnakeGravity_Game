public class Portal extends Acceptor{
    public Portal(Cell cell) {
        super(cell);
    }

    @Override
    public void accept(SnakeVisitor visitor) {
        visitor.visit(this);
    }
}
