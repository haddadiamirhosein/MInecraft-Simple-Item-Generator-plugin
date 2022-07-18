package nit.ds.mc.simpleitemgenerator;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockBurnEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class MyListeners implements Listener {

    public static Map<Location, ItemGenerator> itemGenerators;
    public static List<Location> generatorLocations;

    public MyListeners() {
        itemGenerators = new HashMap<>();
        generatorLocations = new ArrayList<>();

        Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(
                SimpleItemGenerator.plugin, () -> generatorLocations.forEach(
                        location -> itemGenerators.get(location).update()
                ),
                0,
                2
        );
    }

    @EventHandler
    public void onPlaceDiamondBlock(BlockPlaceEvent e) {
        if (e.getBlock().getType() == Material.DIAMOND_BLOCK) {
            Location location = e.getBlock().getLocation().clone();
            generatorLocations.add(location);
            itemGenerators.put(location, new ItemGenerator(location));
        }
    }

    @EventHandler
    public void onBreakDiamondBlock(BlockBreakEvent e) {
        if (e.getBlock().getType() == Material.DIAMOND_BLOCK) {
            Location location = e.getBlock().getLocation().clone();
            generatorLocations.remove(location);
            itemGenerators.remove(location);
        }
    }

    @EventHandler
    synchronized public void onPlayerInteract(PlayerInteractEvent e) throws InterruptedException {
        Player player = e.getPlayer();
        Block block = e.getClickedBlock();
        Location location = block.getLocation();



        if (e.getAction() == Action.RIGHT_CLICK_BLOCK) {

            if (player.getWorld().getBlockAt(location).getType() == Material.DIAMOND_BLOCK) {
                ItemGenerator itemGenerator = itemGenerators.get(location);
                ItemStack itemInMainHand = player.getInventory().getItemInMainHand();
                e.getPlayer().sendMessage("o");

                if (itemInMainHand.getType() == Material.DIAMOND &&
                        itemInMainHand.getAmount() >= itemGenerator.getAmountOfItemsRequiredToUpgrade()) {
                    itemGenerator.upgrade(player);


                    e.getPlayer().sendMessage("oo");

                    itemInMainHand.setAmount(itemInMainHand.getAmount() - itemGenerator.getAmountOfItemsRequiredToUpgrade());
                }
            }
        }
    }
}

