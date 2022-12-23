package mines;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.*;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class MineHandler implements Listener {


    @EventHandler
    public void inMine(BlockBreakEvent event) {
        Player player = event.getPlayer();
        if (!isInMine(player.getLocation())) {
            event.setCancelled(true);
            player.sendMessage("§4You are not inside of a Mine!");
        }
    }



    private boolean isInMine(Location location) {
        // Define the coordinates of the mine
        int minX = 10;
        int maxX = 20;
        int minY = 0;
        int maxY = 256;
        int minZ = -5;
        int maxZ = 5;

        // Check if the player's location is within the bounds of the mine
        if (location.getX() >= minX && location.getX() <= maxX &&
                location.getY() >= minY && location.getY() <= maxY &&
                location.getZ() >= minZ && location.getZ() <= maxZ) {
            return true;
        }

        return false;
    }

}
