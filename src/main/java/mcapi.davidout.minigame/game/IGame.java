package mcapi.davidout.minigame.game;

import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;

public abstract class IGame {

    private final List<Player> spectatorList;
    private final List<Player> playerList;
    private final UUID uuid;
    private GameState state;



    public IGame() {
        this.uuid = UUID.randomUUID();
        this.state = GameState.IDLE;
        this.playerList = new ArrayList<>();
        this.spectatorList = new ArrayList<>();
    }

    public GameState getState() {
        return state;
    }
    public UUID getGameUUID() {
        return this.uuid;
    }

    public void setState(GameState state) {
        this.state = state;

        onGameStateChange(state);
    }

    public List<Player> getPlayerList() {
        return this.playerList;
    }

    public List<Player> getSpectatorList() {
        return this.spectatorList;
    }

    public abstract void setUpGame();
    public abstract void startGame();
    public abstract void endGame();
    public abstract void resetGame();

    public abstract void onGameStateChange(GameState state);

    public void addPlayer(Player player) {
        this.playerList.add(player);

        this.spectatorList.remove(player);
        this.setToGamePlayer(player);
    }

    public void removePlayer(Player player) {
        this.playerList.remove(player);
    }

    public void addSpectator(Player player) {
        this.spectatorList.add(player);

        this.playerList.remove(player);
        this.setToSpectator(player);
    }

    public void removeSpectator(Player player) {
        this.spectatorList.remove(player);
    }

    public abstract void setToSpectator(Player player);
    public abstract void setToGamePlayer(Player player);

}
