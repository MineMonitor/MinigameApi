package mcapi.davidout.minigame.arena.event.area;

import mcapi.davidout.minigame.arena.IArena;
import mcapi.davidout.minigame.arena.area.IArea;
import mcapi.davidout.minigame.event.CustomEvent;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public abstract class AreaMoveEvent extends CustomEvent {

    private final IArena arena;
    private final IArea area;
    private final Player player;
    private final Location from;
    private final Location to;

    public AreaMoveEvent() {
        this.arena = null;
        this.area = null;
        this.player = null;
        this.from = null;
        this.to = null;
    }


    public AreaMoveEvent(IArena arena, IArea area, Player player, Location from, Location to) {
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


}
