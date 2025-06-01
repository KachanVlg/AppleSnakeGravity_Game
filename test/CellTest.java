import org.junit.Test;

import java.awt.*;

import static org.junit.Assert.*;

public class CellTest {

    private static final int testX = 0;
    private static final int testY = 0;


    @Test //Тест конструктора ячейки
    public void constructorTest() {
        Point expPoint = new Point(testX,testY);
        Point testPoint = new Point(expPoint);

        Cell testCell = new Cell(testPoint);
        Point actPoint = testCell.getPoint();
        testPoint.move(testX+1, testY+1);

        assertEquals(expPoint, actPoint);
        assertNotSame(testPoint, actPoint);
    }
}
