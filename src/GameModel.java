public class GameModel implements SnakeListener{


    private boolean isFinished;
    private boolean isStarted;
    private boolean hasWon;
    private final World world;


    public GameModel() {
        isFinished = false;
        isStarted = false;
        hasWon = false;
        world = new World();
    }

    public void start() {
        isStarted = true;
        world.getSnakeController().addListener(this);
    }

    public void moveSnakeOn(Direction dir) {

    }

    private void finishGame() {
        isFinished = true;
    }



    @Override
    public void portalIsEntered() {
        hasWon = true;
        finishGame();
    }

    @Override
    public void fell() {
        finishGame();
    }

    @Override
    public void movedOn() {}
}
