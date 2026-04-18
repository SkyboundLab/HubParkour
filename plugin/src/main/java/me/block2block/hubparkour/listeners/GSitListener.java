package me.block2block.hubparkour.listeners;

import dev.geco.gsit.api.event.PrePlayerPlayerSitEvent;
import me.block2block.hubparkour.managers.CacheManager;
import me.block2block.hubparkour.utils.ConfigUtil;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;

import java.util.Collections;

public class GSitListener implements Listener {

    // getPlayer() returns the rider (the one sitting ON another player)
    // getTarget() returns the player being sat on
    @EventHandler
    public void onPlayerPlayerSit(PrePlayerPlayerSitEvent e) {
        Player rider = e.getPlayer();
        if (CacheManager.isParkour(rider)) {
            if (ConfigUtil.getBoolean("Settings.Exploit-Prevention.Block-Mounting", true)) {
                ConfigUtil.sendMessage(rider, "Messages.Parkour.Cannot-Mount-In-Parkour", "You cannot mount an entity while in a parkour. To leave your current parkour, do /parkour leave.", true, Collections.emptyMap());
                e.setCancelled(true);
            }
        }
    }

    public static void register(PluginManager pm, me.block2block.hubparkour.HubParkour plugin) {
        if (pm.getPlugin("GSit") == null) {
            return;
        }
        try {
            pm.registerEvents(new GSitListener(), plugin);
            plugin.getLogger().info("GSit detected, registering GSit listener.");
        } catch (NoClassDefFoundError e) {
            plugin.getLogger().warning("GSit was detected but the API could not be loaded. GSit integration will be disabled.");
        }
    }
}
