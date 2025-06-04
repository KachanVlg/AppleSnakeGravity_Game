import org.junit.Test;

public abstract class StaticObstacleTest extends ObjectOnFieldTest{
    @Override
    public abstract void constructorTest();

    @Override
    public abstract void unsetCellTest();

    @Override
    public abstract void setCellTest();

    @Override
    public abstract void setNullCell();

    @Override
    public abstract void setCellWhenAlreadyInstalled();

    @Override
    public abstract void resetCell();

}
