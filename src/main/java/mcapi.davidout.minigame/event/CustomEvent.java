package mcapi.davidout.minigame.event;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public abstract class CustomEvent extends Event {

    private static final HandlerList handlers = new HandlerList();
    private boolean cancelled;

    public CustomEvent() {
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

