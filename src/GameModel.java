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

    public boolean hasWon() {
        return this.hasWon;
    }

    public void start() {
        isStarted = true;
        world.getSnakeController().addListener(this);
    }

    public void moveSnakeOn(Direction dir) {
        world.getSnakeController().moveOn(dir);
    }

    private void finishGame() {
        isFinished = true;
    }

    public boolean isFinished() {
        return isFinished;
    }

    public World getWorld() {
        return world;
    }



    @Override
    public void portalIsEntered() {
        world.applyGravityAllSingleObjects();
        hasWon = true;
        finishGame();
    }

    @Override
    public void fell() {
        world.applyGravityAllSingleObjects();
        finishGame();
    }

    @Override
    public void movedOn() {
        world.applyGravityAllSingleObjects();}
}
