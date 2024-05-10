package mcapi.davidout.minigame.arena;

import mcapi.davidout.minigame.arena.area.IArea;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldCreator;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class Arena implements IArena {

    private final HashMap<UUID, IArenaWorld> activeArenas;
    private final List<IArea> areaList;
    private final String name;

    public Arena(String arenaName) {
        this.name = arenaName;
        this.activeArenas = new HashMap<>();
        this.areaList = new ArrayList<>();
    }

    public Arena(String arenaName, List<IArea> areaList) {
        this.name = arenaName;
        this.areaList = areaList;
        this.activeArenas = new HashMap<>();
    }

    @Override
    public String getArenaName() {
        return this.name;
    }

    @Override
    public List<IArenaWorld> getActiveArenaWorlds() {
        return new ArrayList<>(this.activeArenas.values());
    }

    @Override
    public IArenaWorld getArenaWorld(UUID uuid) {
        return activeArenas.get(uuid);
    }

    @Override
    public void createArenaWorld(File file, IArenaCallback callback) {
        World world = Bukkit.getWorld(file.getName());
        if(world == null) {
            world = Bukkit.createWorld(new WorldCreator(file.getName()));
        }

        ArenaWorld arenaWorld = new ArenaWorld(world);
        this.activeArenas.put(arenaWorld.getUUID(), arenaWorld);
        if(callback != null) {
            callback.onSucces(arenaWorld);
        }
    }

    @Override
    public List<IArea> getAreas() {
        return this.areaList;
    }

    @Override
    public boolean worldIsArena(World world) {
        return this.activeArenas.get(world.getUID()) != null;
    }

}
