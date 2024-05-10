package mcapi.davidout.minigame.arena.event;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public abstract class ArenaEvent extends Event {

    private static final HandlerList handlers = new HandlerList();
    private boolean cancelled;

    public ArenaEvent() {
        this.cancelled = false;
    }

    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    public boolean isCancelled() {
        return this.cancelled;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}

