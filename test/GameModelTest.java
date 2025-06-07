import core.GameModel;
import core.Snake;
import core.SnakeController;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class GameModelTest {
    @Test
    public void testGameModelStart() {
        GameModel model = new GameModel();
        model.start();

        // Проверяем, что после старта змея подписалась на события
        SnakeController snake = model.getWorld().getSnakeController();
        assertFalse("core.Snake должен содержать хотя бы одного слушателя", ((Snake) snake).getListeners().isEmpty());
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
