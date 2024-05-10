package mcapi.davidout.minigame.arena;

import mcapi.davidout.minigame.arena.area.IArea;
import org.bukkit.World;

import java.util.List;
import java.util.UUID;

public interface IArenaWorld {

    UUID getUUID();
    String getWorldName();
    World getWorld();
}
