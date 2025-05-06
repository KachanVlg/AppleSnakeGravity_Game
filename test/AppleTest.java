import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;

import java.awt.*;

import static org.junit.Assert.*;

public class AppleTest extends AcceptorTest{

    private Point basePoint;
    private Cell baseCell;


    @Before
    public void initTestParams() {
        basePoint = new Point(0,0);
        baseCell = new Cell(basePoint);
    }


    @Override
    @Test
    public void constructorTest() {
        Apple testApple = new Apple(baseCell);

        assertEquals(baseCell, testApple.getCell());
        assertEquals(testApple, baseCell.getObject());
    }

    @Override
    @Test
    public void unsetCellTest() {
        Apple testApple = new Apple(baseCell);

        Cell actForgottenCell = testApple.unsetCell();

        assertEquals(baseCell, actForgottenCell);
        assertNull(testApple.getCell());
        assertNull(baseCell.getObject());
    }

    @Override
    @Test
    public void setCellTest() {
        Apple testApple = new Apple(baseCell);

        testApple.unsetCell();
        testApple.setCell(baseCell);

        assertEquals(baseCell, testApple.getCell());
        assertEquals(testApple, testApple.getCell().getObject());
    }

    @Override
    @Test
    public void setNullCell() {
        Apple testApple = new Apple(baseCell);

        testApple.unsetCell();

        assertThrows(IllegalArgumentException.class, () -> testApple.setCell(null));
    }

    @Override
    @Test
    public void setCellWhenAlreadyInstalled() {
        Apple testApple = new Apple(baseCell);

        assertThrows(RuntimeException.class, () -> testApple.setCell(baseCell));
    }

    @Override
    @Test
    public void resetCell() {
        Apple testApple = new Apple(baseCell);
        Cell newCell = new Cell(new Point(1,1));

        Cell forgottenCell = testApple.resetCell(newCell);

        assertNotEquals(newCell, forgottenCell);
        assertEquals(testApple.getCell(), newCell);
    }


}
