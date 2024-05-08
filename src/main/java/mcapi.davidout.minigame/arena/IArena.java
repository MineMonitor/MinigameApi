package mcapi.davidout.minigame.arena;

import java.io.File;
import java.util.List;
import java.util.UUID;

public interface IArena {

    String getArenaName();
    List<IArenaWorld> getActiveArenaWorlds();

    IArenaWorld getArenaWorld(UUID uuid);
    void createArenaWorld(File file);


}
