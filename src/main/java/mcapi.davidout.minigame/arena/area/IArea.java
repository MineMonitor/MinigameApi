package mcapi.davidout.minigame.arena.area;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public interface IArea {

    IPosition getPosition1();
    IPosition getPosition2();
    boolean locationInArea(Location location);
    boolean entityInArea(Entity et);
    boolean playerIsInArea(Player player);

    String getName();

}
