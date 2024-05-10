package mcapi.davidout.minigame.game;

import java.util.List;
import java.util.UUID;

public interface IGameManager {

    List<IGame> getGameList();
    void createGame(IGame game);
    void removeGame(UUID uuid);
    IGame getGame(UUID uuid);



}
