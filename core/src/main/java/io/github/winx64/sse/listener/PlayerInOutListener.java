package io.github.winx64.sse.listener;

import io.github.winx64.sse.SmartSignEditor;
import io.github.winx64.sse.player.SmartPlayer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public final class PlayerInOutListener implements Listener {

    private final SmartSignEditor plugin;

    public PlayerInOutListener(SmartSignEditor plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        plugin.registerSmartPlayer(new SmartPlayer(event.getPlayer()));
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        plugin.unregisterSmartPlayer(event.getPlayer().getUniqueId());
    }
}
