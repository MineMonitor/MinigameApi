package mcapi.davidout.minigame.arena;

import mcapi.davidout.minigame.utils.Folder;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class ArenaManager implements IArenaManager {



    private final List<IArena> arenas;
    private final File fromFolder;
    private final File toFolder;

    private final Plugin plugin;

    public ArenaManager(Plugin plugin, File folderToCopyFrom, File folderToCopyTo) {
        this.plugin = plugin;
        this.arenas = new ArrayList<>();
        this.fromFolder = folderToCopyFrom;
        this.toFolder = folderToCopyTo;
    }

    public ArenaManager(Plugin plugin) {
        this.plugin = plugin;
        this.arenas = new ArrayList<>();
        this.fromFolder = Bukkit.getWorldContainer();
        this.toFolder = Bukkit.getWorldContainer();
    }


    @Override
    public List<IArena> getArenas() {
        return this.arenas;
    }

    @Override
    public Map<IArena, List<IArenaWorld>> getActiveArenas() {
        Map<IArena, List<IArenaWorld>> map = new HashMap<>();
        this.arenas.forEach(arena -> map.put(arena, arena.getActiveArenaWorlds()));
        return map;
    }


    @Override
    public void addArena(IArena arena) {
        this.arenas.add(arena);
    }

    @Override
    public void removeArena(IArena arena) {
        this.arenas.remove(arena);
    }

    @Override
    public IArena getArenaByName(String name) {
        return this.arenas.stream().filter(arena -> arena.getArenaName().equalsIgnoreCase(name)).findFirst().orElse(null);
    }

    @Override
    public boolean createNewArena(IArena arena) throws IOException {
        File arenaWorldFolder = new File(this.toFolder, arena.getArenaName() + "-" + this.getArenaID(arena));
        Folder.copyDirectory(new File(this.fromFolder, arena.getArenaName()), arenaWorldFolder);
        arena.createArenaWorld(arenaWorldFolder);
        return true;
    }

    @Override
    public void createNewArenaAsync(IArena arena, IArenaCallback callback) {
        File arenaWorldFolder = new File(this.toFolder, arena.getArenaName() + "-" + this.getArenaID(arena));

        Folder.copyDirectoryAsync(plugin, new File(this.fromFolder, arena.getArenaName()), arenaWorldFolder, new Folder.DirectoryCopyCallback() {
            @Override
            public void onComplete(boolean succes, File folder) {
                Bukkit.getScheduler().runTask(plugin, () -> {
                   arena.createArenaWorld(folder);
                });
            }

            @Override
            public void onError(Exception exception) {
                Bukkit.getScheduler().runTask(plugin, () -> {
                   callback.onException(exception);
                });
            }
        });
    }

    private int getArenaID(IArena arena) {
        return Arrays.stream(Objects.requireNonNull(this.toFolder.listFiles())).filter(file -> file.getName().startsWith(arena.getArenaName())).toArray().length + 1;
    }
}
