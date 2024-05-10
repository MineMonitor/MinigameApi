package mcapi.davidout.minigame.arena.event.area;

import mcapi.davidout.minigame.arena.ArenaManager;
import mcapi.davidout.minigame.arena.IArena;
import mcapi.davidout.minigame.arena.area.IArea;
import mcapi.davidout.minigame.arena.event.ArenaEvent;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class AreaEnterEvent extends ArenaEvent implements Listener {

    private final IArena arena;
    private final IArea area;
    private final Player player;
    private final Location from;
    private final Location to;


    public AreaEnterEvent(IArena arena, IArea area, Player player, Location from, Location to) {
        this.arena = arena;
        this.area = area;
        this.player = player;
        this.from = from;
        this.to = to;
    }

    public Player getPlayer() {
        return player;
    }


    public IArena getArena() {
        return arena;
    }

    public IArea getArea() {
        return area;
    }

    public Location getFrom() {
        return this.from;
    }

    public Location getTo() {
        return this.to;
    }

    @EventHandler
    public void onEnter(PlayerMoveEvent e) {
        World w = e.getPlayer().getWorld();

        if(ArenaManager.getInstance() == null) {
            return;
        }

        IArena cArena =  ArenaManager.getInstance().getArenaByWorld(w);
        if(cArena == null) {
            return;
        }

        IArea area = cArena.getAreas().stream().filter(a ->
                a.locationInArea(e.getTo()) &&
                !a.locationInArea(e.getFrom())
        ).findFirst()
        .orElse(null);

        if(area == null) {
            return;
        }

        AreaEnterEvent enterEvent = new AreaEnterEvent(cArena, area, e.getPlayer(), e.getFrom(), e.getTo());
        Bukkit.getPluginManager().callEvent(enterEvent);

        if(!enterEvent.isCancelled()) {
            return;
        }

        e.setCancelled(true);
    }
}
