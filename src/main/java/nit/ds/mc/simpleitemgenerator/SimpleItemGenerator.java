package nit.ds.mc.simpleitemgenerator;

import org.bukkit.plugin.java.JavaPlugin;

public final class SimpleItemGenerator extends JavaPlugin {

    public static SimpleItemGenerator plugin;

    @Override
    public void onEnable() {
        // Plugin startup logic
        plugin = this;
        getServer().getPluginManager().registerEvents(new MyListeners(), this);
        getServer().getLogger().info("Simple Item Generator Enabled !");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
