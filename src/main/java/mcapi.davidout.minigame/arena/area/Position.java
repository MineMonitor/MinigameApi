package mcapi.davidout.minigame.arena.area;


import org.bukkit.Location;

public class Position implements IPosition {

    private final int x;
    private final int y;
    private final int z;

    public Position(Location location) {
        this.x = location.getBlockX();
        this.y = location.getBlockY();
        this.z = location.getBlockZ();
    }

    public Position(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }


    @Override
    public int getX() {
        return x;
    }

    @Override
    public int getY() {
        return y;
    }

    @Override
    public int getZ() {
        return z;
    }
}
