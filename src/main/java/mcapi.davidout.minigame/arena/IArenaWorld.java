package mcapi.davidout.minigame.arena;

import org.bukkit.World;

import java.util.UUID;

public interface IArenaWorld {

    UUID getUUID();
    String getWorldName();
    World getWorld();
}
