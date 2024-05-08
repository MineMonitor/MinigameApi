package mcapi.davidout.minigame.arena;

import org.bukkit.World;

import java.util.UUID;

public class ArenaWorld implements IArenaWorld {

    private final World world;

    public ArenaWorld(World world) {
        this.world = world;
    }

    @Override
    public UUID getUUID() {
        return this.world.getUID();
    }

    @Override
    public String getWorldName() {
        return this.world.getName();
    }

    @Override
    public World getWorld() {
        return this.world;
    }
}
