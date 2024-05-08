package mcapi.davidout.minigame.arena;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldCreator;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Arena implements IArena {

    private String name;
    private List<IArenaWorld> activeArenas;

    public Arena(String arenaName) {
        this.name = arenaName;
        this.activeArenas = new ArrayList<>();
    }

    @Override
    public String getArenaName() {
        return this.name;
    }

    @Override
    public List<IArenaWorld> getActiveArenaWorlds() {
        return this.activeArenas;
    }

    @Override
    public IArenaWorld getArenaWorld(UUID uuid) {
        return this.activeArenas.stream().filter(world -> world.getUUID().equals(uuid)).findFirst().orElse(null);
    }

    @Override
    public void createArenaWorld(File file, IArenaCallback callback) {
        World world = Bukkit.getWorld(file.getName());
        if(world == null) {
            world = Bukkit.createWorld(new WorldCreator(file.getName()));
        }

        ArenaWorld arenaWorld = new ArenaWorld(world);
        this.activeArenas.add(arenaWorld);
        if(callback != null) {
            callback.onSucces(arenaWorld);
        }
    }
}
