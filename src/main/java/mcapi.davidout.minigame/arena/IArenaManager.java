package mcapi.davidout.minigame.arena;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface IArenaManager {

    List<IArena> getArenas();
    Map<IArena, List<IArenaWorld>> getActiveArenas();

    void addArena(IArena arena);
    void removeArena(IArena arena);



    IArena getArenaByName(String name);
    boolean createNewArena(IArena arena) throws IOException;
    void createNewArenaAsync(IArena arena, IArenaCallback callback);

}
