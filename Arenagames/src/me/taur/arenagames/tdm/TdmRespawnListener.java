package me.taur.arenagames.tdm;

import me.taur.arenagames.Config;
import me.taur.arenagames.room.Room;
import me.taur.arenagames.util.RoomType;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

public class TdmRespawnListener implements Listener {
	@EventHandler(priority = EventPriority.NORMAL)
	public void playerDiedInArena(PlayerDeathEvent evt) {
		Player p = evt.getEntity();

		if (!Room.PLAYERS.containsKey(p)) { // Only apply when the player who died is killed by playing in an arena.
			return;
		}

		Room room = Room.ROOMS.get(Room.PLAYERS.get(p));

		if (room != null) {
			if (room.getRoomType() == RoomType.TDM) {
				evt.setDroppedExp(0);
				evt.getDrops().clear();
				evt.setDeathMessage(null);

				TdmRoom r = (TdmRoom) room;

				if (room.isGameInProgress()) { // Check if the game is in progress.
					DamageCause c = p.getLastDamageCause().getCause();

					if (c.equals(DamageCause.ENTITY_ATTACK) || c.equals(DamageCause.PROJECTILE) || c.equals(DamageCause.MAGIC)) {
						LivingEntity damager = p.getKiller();

						if (damager instanceof Player) {
							Player d = (Player) damager;
							if (Room.PLAYERS.containsKey(d)) { // If the player who killed the player is playing
								r.playerKilled(p, d); // Tell the room that the player has been slain by another player.
							} else {
								r.playerKilled(p);
							}

						} else if (damager instanceof Monster) {
							Monster d = (Monster) damager;
							r.playerKilled(p, d.getType()); // Tell the room that the player has been slain by an monster.

						} else {
							r.playerKilled(p); // Tell the room that the player has been slain mysteriously.
						}

					} else if (c.equals(DamageCause.ENTITY_EXPLOSION)) {
						r.playerKilled(p, EntityType.CREEPER); // Tell the room that the player has been slain by a Creeper.
					} else {
						r.playerKilled(p, c); // Tell the room that the player has been slain by something else.
					}

				}
			}
		} else { // If the player is not in a game room and died.
			p.teleport(Config.getGlobalLobby());

		}
	}

	@EventHandler(priority = EventPriority.NORMAL)
	public void playerRespawnInArena(PlayerRespawnEvent evt) {
		final Player p = evt.getPlayer();

		if (!Room.PLAYERS.containsKey(p)) { // Only apply when the player who died is killed by playing in an arena.
			evt.setRespawnLocation(Config.getGlobalLobby());
			return;
		}

		Room room = Room.ROOMS.get(Room.PLAYERS.get(p));

		if (room != null) {
			if (room.getRoomType() == RoomType.TDM) {
				TdmRoom r = (TdmRoom) room;

				if (room.isGameInProgress()) { // Check if the game is in progress.
					int team = r.getTeamtrackboard().get(p.getName());

					if (team == TdmTeams.RED.getId()) {
						evt.setRespawnLocation(TdmConfig.getPossibleRedSpawnLocation(r));
					} else if (team == TdmTeams.BLUE.getId()) {
						evt.setRespawnLocation(TdmConfig.getPossibleBlueSpawnLocation(r));
					}

					TdmSpawnManager.respawnPrecheck(p);

				}
			}
		}

	}
}