package mcapi.davidout.minigame.arena;

import java.util.function.Consumer;

public interface IArenaLoadPromise {

    boolean loadArena(String worldName);
    void loadArenaAsync(String worldName);

    void whenLoaded(Consumer<IArenaWorld> world);


}
