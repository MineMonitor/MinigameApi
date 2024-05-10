package mcapi.davidout.minigame.game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class GameManager implements IGameManager {

    public HashMap<UUID, IGame> gameList;


    public GameManager() {
        this.gameList = new HashMap<>();
    }

    @Override
    public List<IGame> getGameList() {
        return new ArrayList<>(this.gameList.values());
    }

    @Override
    public void createGame(IGame game) {
        game.setUpGame();
        this.gameList.put(game.getGameUUID(), game);
    }

    @Override
    public void removeGame(UUID uuid) {
        IGame game = this.gameList.get(uuid);
        if(game == null) {
            return;
        }

        game.endGame();
        this.gameList.remove(uuid);
    }

    @Override
    public IGame getGame(UUID uuid) {
        return this.gameList.get(uuid);
    }
}
