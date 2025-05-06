import org.junit.Test;

import java.awt.*;

public abstract class ObjectOnFieldTest {

    @Test
    public abstract void constructorTest();

    @Test
    public abstract void unsetCellTest();

    @Test
    public abstract void setCellTest();

    @Test
    public abstract void setNullCell();

    @Test
    public abstract void setCellWhenAlreadyInstalled();

    @Test
    public abstract void resetCell();
}
