package mcapi.davidout.minigame.arena.area;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.UUID;

public class Area implements IArea {

    private final String name;
    private final IPosition pos1;
    private final IPosition pos2;


    public Area(Location pos1, Location pos2) {
        this.pos1 = new Position(pos1);
        this.pos2 = new Position(pos2);
        this.name = UUID.randomUUID().toString();
    }

    public Area(Position pos1, Position pos2) {
        this.pos1 = pos1;
        this.pos2 = pos2;
        this.name = UUID.randomUUID().toString();
    }

    public Area(String name, Location pos1, Location pos2) {
        this.pos1 = new Position(pos1);
        this.pos2 = new Position(pos2);
        this.name = name;
    }

    public Area(String name, Position pos1, Position pos2) {
        this.pos1 = pos1;
        this.pos2 = pos2;
        this.name = name;
    }



    @Override
    public IPosition getPosition1() {
        return this.pos1;
    }

    @Override
    public IPosition getPosition2() {
        return this.pos2;
    }

    @Override
    public boolean locationInArea(Location loc) {
        double minX = Math.min(this.pos1.getX(), this.pos2.getX());
        double minY = Math.min(this.pos1.getY(), this.pos2.getY());
        double minZ = Math.min(this.pos1.getZ(), this.pos2.getZ());
        double maxX = Math.max(this.pos1.getX(), this.pos2.getX());
        double maxY = Math.max(this.pos1.getY(), this.pos2.getY());
        double maxZ = Math.max(this.pos1.getZ(), this.pos2.getZ());

        return loc.getX() >= minX && loc.getX() <= maxX &&
                loc.getY() >= minY && loc.getY() <= maxY &&
                loc.getZ() >= minZ && loc.getZ() <= maxZ;
    }

    @Override
    public boolean entityInArea(Entity et) {
        return locationInArea(et.getLocation());
    }

    @Override
    public boolean playerIsInArea(Player player) {
       return locationInArea(player.getLocation());
    }

    @Override
    public String getName() {
        return name;
    }


}
