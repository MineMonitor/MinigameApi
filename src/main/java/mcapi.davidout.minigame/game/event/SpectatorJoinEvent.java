package mcapi.davidout.minigame.game.event;

import mcapi.davidout.minigame.event.CustomEvent;
import mcapi.davidout.minigame.game.IGame;
import org.bukkit.entity.Player;

public class SpectatorJoinEvent extends CustomEvent {

    private final IGame game;
    private final Player player;

    public SpectatorJoinEvent(Player player, IGame game) {
        this.player = player;
        this.game = game;
    }


    public IGame getGame() {
        return game;
    }

    public Player getPlayer() {
        return player;
    }
}
