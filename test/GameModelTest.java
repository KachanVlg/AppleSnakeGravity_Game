import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

public class GameModelTest {
    @Test
    public void testGameModelStart() {
        GameModel model = new GameModel();
        model.start();

        // Проверяем, что после старта змея подписалась на события
        SnakeController snake = model.getWorld().getSnakeController();
        assertFalse("Snake должен содержать хотя бы одного слушателя", ((Snake) snake).getListeners().isEmpty());
    }

    @Test
    public void testGameModelPortalWin() {
        GameModel model = new GameModel();
        model.start();

        model.portalIsEntered();

        assertTrue("Игра должна быть завершена", model.isFinished());
        assertTrue("Игрок должен победить", model.hasWon());
    }

    @Test
    public void testGameModelFallFinishesGame() {
        GameModel model = new GameModel();
        model.start();

        model.fell();

        assertTrue("Игра должна быть завершена после падения", model.isFinished());
        assertFalse("Падение — это не победа", model.hasWon());
    }

}
