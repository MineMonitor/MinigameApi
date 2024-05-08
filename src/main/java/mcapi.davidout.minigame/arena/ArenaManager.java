package mcapi.davidout.minigame.arena;

import mcapi.davidout.minigame.utils.Folder;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class ArenaManager implements IArenaManager {



    private final List<IArena> arenas;
    private final File fromFolder;
    private final File toFolder;

    private final Plugin plugin;

    public ArenaManager(Plugin plugin, File folderToCopyFrom) {
        this.plugin = plugin;
        this.arenas = new ArrayList<>();
        this.fromFolder = folderToCopyFrom;
        this.toFolder = Bukkit.getWorldContainer();

        this.createFolders();
    }

    public ArenaManager(Plugin plugin) {
        this.plugin = plugin;
        this.arenas = new ArrayList<>();
        this.fromFolder = Bukkit.getWorldContainer();
        this.toFolder = Bukkit.getWorldContainer();

        this.createFolders();
    }

    private void createFolders() {
        if(!this.fromFolder.exists()) {
            this.fromFolder.mkdirs();
        }

        if(!this.toFolder.exists()) {
            this.fromFolder.mkdirs();
        }
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
        Folder.copyDirectory(new File(this.fromFolder, arena.getArenaName()), arenaWorldFolder, Arrays.asList("uid.dat", "session.lock"));
        arena.createArenaWorld(arenaWorldFolder, null);
        return true;
    }

    @Override
    public void createNewArenaAsync(IArena arena, IArenaCallback callback) {
        int arenaId = this.getArenaID(arena);
        File arenaWorldFolder = new File(this.toFolder, arena.getArenaName() + "-" + arenaId);
        File fromFolder = new File(this.fromFolder, arena.getArenaName());
        List<String> ignoreFiles = Arrays.asList("uid.dat", "session.lock");

       Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
           try {
               Folder.copyDirectory(fromFolder, arenaWorldFolder, ignoreFiles);

               Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> {
                   arena.createArenaWorld(arenaWorldFolder, callback);
               }, 2);
           } catch (IOException e) {
               throw new RuntimeException(e);
           }
       });
    }

    private int getArenaID(IArena arena) {
        return Arrays.stream(Objects.requireNonNull(this.toFolder.listFiles())).filter(file -> file.getName().startsWith(arena.getArenaName())).toArray().length + 1;
    }
}
