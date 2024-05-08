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
    public void createArenaWorld(File file) {
        if (!file.isDirectory()) return;

        World world = Bukkit.getWorld(file.getName());
        if (world == null) Bukkit.createWorld(new WorldCreator(file.getName()));

        this.activeArenas.add(new ArenaWorld(world));
    }
}
