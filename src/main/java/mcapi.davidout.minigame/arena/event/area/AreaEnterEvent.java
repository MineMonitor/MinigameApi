package mcapi.davidout.minigame.arena.event.area;

import mcapi.davidout.minigame.arena.ArenaManager;
import mcapi.davidout.minigame.arena.IArena;
import mcapi.davidout.minigame.arena.IArenaManager;
import mcapi.davidout.minigame.arena.area.IArea;
import mcapi.davidout.minigame.event.CustomEvent;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class AreaEnterEvent extends AreaMoveEvent implements Listener {

    private static IArenaManager arenaManager;

    public AreaEnterEvent(IArenaManager manager) {
        arenaManager = manager;
    }

    public AreaEnterEvent(IArena cArena, IArea area, Player player, Location from, Location to) {
        super(cArena, area, player, from, to);
    }


    @EventHandler
    public void onEnter(PlayerMoveEvent e) {
        World w = e.getPlayer().getWorld();

        if(arenaManager == null) {
            return;
        }

        IArena cArena =  arenaManager.getArenaByWorld(w);
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
