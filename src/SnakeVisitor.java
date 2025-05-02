public interface SnakeVisitor {
    void visit(Apple apple);
    void visit(Portal portal);
    void visit(Block block);
}
