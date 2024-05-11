package mcapi.davidout.minigame.arena;

import mcapi.davidout.minigame.arena.event.area.AreaEnterEvent;
import mcapi.davidout.minigame.arena.event.area.AreaLeaveEvent;
import mcapi.davidout.minigame.utils.Folder;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class ArenaManager implements IArenaManager {

    private List<IArena> arenas;
    private File fromFolder;
    private File toFolder;

    private Plugin plugin;

    public ArenaManager(Plugin plugin, File folderToCopyFrom) {
        this.initialize(plugin, folderToCopyFrom);
    }

    public ArenaManager(Plugin plugin) {
        this.initialize(plugin, Bukkit.getWorldContainer());
    }

    private void initialize(Plugin plugin, File folderToCopyFrom) {
        this.plugin = plugin;
        this.arenas = new ArrayList<>();
        this.fromFolder = folderToCopyFrom;
        this.toFolder = Bukkit.getWorldContainer();
        this.createFolders();
        this.registerEvents(plugin.getServer().getPluginManager());
    }

    private void createFolders() {
        if(!this.fromFolder.exists()) {
            this.fromFolder.mkdirs();
        }

        if(!this.toFolder.exists()) {
            this.fromFolder.mkdirs();
        }
    }

    private void registerEvents(PluginManager pm) {
        pm.registerEvents(new AreaEnterEvent(this), this.plugin);
        pm.registerEvents(new AreaLeaveEvent(this), this.plugin);
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
    public IArena getArenaByWorld(World world) {
        return this.arenas.stream().filter(arena -> arena.worldIsArena(world)).findFirst().orElse(null);
    }

    @Override
    public boolean worldIsArenaWorld(World world) {
        return this.getArenaByWorld(world) != null;
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
        this.arenas.add(arena);
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
                   this.arenas.add(arena);
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
