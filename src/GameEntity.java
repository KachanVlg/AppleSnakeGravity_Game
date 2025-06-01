public abstract class GameEntity {

    private final World world;

    public GameEntity(World world) {
        this.world = world;
    }

    public World getWorld() {
        return world;
    }
}
