public class Block extends Acceptor{
    public Block(Cell cell) {
        super(cell);
    }

    @Override
    public void accept(SnakeVisitor visitor) {
        visitor.visit(this);
    }
}
