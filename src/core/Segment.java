package core;

public class Segment extends AbstractSegment {


    public Segment(Cell cell, World world) {
        super(cell, world);
    }

    public Segment(Cell cell, AbstractSegment next, World world) {
        super(cell ,next, world);
    }

}
