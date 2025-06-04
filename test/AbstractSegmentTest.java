import org.junit.Test;

public abstract class AbstractSegmentTest extends ObjectOnFieldTest{

    @Test
    public abstract void getNextSegment();

    @Test
    public abstract void setNextSegment();

    @Test
    public abstract void setNextSegmentAlreadyExist();

    @Test
    public abstract void setNextNullSegment();
}
