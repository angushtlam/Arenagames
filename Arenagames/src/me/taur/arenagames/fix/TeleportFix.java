// Released under the BSD License.
// Kristian S. Stangeland 2013.
 
package me.taur.arenagames.fix;
 
import java.util.ArrayList;
import java.util.List;

import me.taur.arenagames.Arenagames;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;
 
public class TeleportFix implements Listener {
    private final int TELEPORT_FIX_DELAY = 15; // ticks
    
    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onPlayerTeleport(PlayerTeleportEvent event) {
 
        final Player player = event.getPlayer();
        final int visibleDistance = Bukkit.getViewDistance() * 16;
        
        // Fix the visibility issue one tick later
        Bukkit.getScheduler().scheduleSyncDelayedTask(Arenagames.plugin, new Runnable() {
            @Override
            public void run() {
                // Refresh nearby clients
                final List<Player> nearby = getPlayersWithin(player, visibleDistance);
                
                // System.out.println("Applying fix ... " + visibleDistance);
                
                // Hide every player
                updateEntities(nearby, false);
                
                // Then show them again
                Bukkit.getScheduler().scheduleSyncDelayedTask(Arenagames.plugin, new Runnable() {
                    @Override
                    public void run() {
                        updateEntities(nearby, true);
                    }
                }, 1);
            }
        }, TELEPORT_FIX_DELAY);
    }
    
    private void updateEntities(List<Player> players, boolean visible) {
        
        // Hide every player
        for (Player observer : players) {
            for (Player player : players) {
                if (observer.getEntityId() != player.getEntityId()) {
                    if (visible)
                        observer.showPlayer(player);
                    else
                        observer.hidePlayer(player);
                }
            }
        }
    }
    
    private List<Player> getPlayersWithin(Player player, int distance) {
        List<Player> res = new ArrayList<Player>();
        int d2 = distance * distance;
 
        for (Player p : Bukkit.getOnlinePlayers()) {
            if (p.getWorld() == player.getWorld()
                    && p.getLocation().distanceSquared(player.getLocation()) <= d2) {
                res.add(p);
            }
        }
 
        return res;
    }
}