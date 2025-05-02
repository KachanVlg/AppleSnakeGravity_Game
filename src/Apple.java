public class Apple extends Acceptor{
    public Apple(Cell cell) {
        super(cell);
    }

    @Override
    public void accept(SnakeVisitor visitor) {
        visitor.visit(this);
    }

    public void eat() {

    }
}
