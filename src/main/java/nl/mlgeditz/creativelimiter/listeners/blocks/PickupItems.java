package nl.mlgeditz.creativelimiter.listeners.blocks;

import java.util.ArrayList;

import nl.mlgeditz.creativelimiter.utils.Logger;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.plugin.Plugin;

import nl.mlgeditz.creativelimiter.Main;
import nl.mlgeditz.creativelimiter.manager.ChangeGameMode;

public class PickupItems implements Listener {

	private ArrayList<Player> pick = new ArrayList<>();

	@EventHandler
	public void onPickup(PlayerPickupItemEvent e) {
		Player p = e.getPlayer();
		if (ChangeGameMode.getBuildingPlayers().contains(p)) {
			if (!p.hasPermission("limiter.bypass") || !p.hasPermission("limiter.pickup")) {
				if (!pick.contains(p)) {
					p.sendMessage(Logger.prefixFormat(Main.messageData.get("noPickups")));
					pick.add(p);
					Bukkit.getScheduler().scheduleAsyncDelayedTask(Main.pl, new Runnable() {
						public void run() {
							pick.remove(p);
						}
					}, 100L);
				}
				e.setCancelled(true);
			}
		}
	}
}
