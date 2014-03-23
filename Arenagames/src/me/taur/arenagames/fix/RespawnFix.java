package me.taur.arenagames.fix;

import me.taur.arenagames.util.ReflectionUtil;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class RespawnFix implements Listener {
	@EventHandler(priority = EventPriority.HIGHEST)
	public void autoRespawnPlayer(PlayerDeathEvent evt) {
		Player p = evt.getEntity();
		
		try {
			Object packet = Class.forName("net.minecraft.server." + ReflectionUtil.getVersionString() + ".PacketPlayInClientCommand").getConstructors()[0].newInstance();
			Object nmsPlayer = ReflectionUtil.getMethod(p.getClass(), "getHandle").invoke(p);
			
			Class<?> enumClass = Class.forName(nmsPlayer.getClass().getPackage().getName() + ".EnumClientCommand");
			 
            for (Object ob : enumClass.getEnumConstants()){
                if (ob.toString().equals("PERFORM_RESPAWN")){
                    packet = packet.getClass().getConstructor(enumClass).newInstance(ob);
                }
            }
			
			Object con = nmsPlayer.getClass().getField("playerConnection").get(nmsPlayer);
			con.getClass().getMethod("a", packet.getClass()).invoke(con, packet);

		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
