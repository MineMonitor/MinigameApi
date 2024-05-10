package mcapi.davidout.minigame.arena;

import mcapi.davidout.minigame.arena.area.IArea;
import org.bukkit.World;

import java.io.File;
import java.util.List;
import java.util.UUID;

public interface IArena {

    String getArenaName();
    List<IArenaWorld> getActiveArenaWorlds();

    IArenaWorld getArenaWorld(UUID uuid);
    void createArenaWorld(File file, IArenaCallback callback);

    List<IArea> getAreas();

    boolean worldIsArena(World world);


}
