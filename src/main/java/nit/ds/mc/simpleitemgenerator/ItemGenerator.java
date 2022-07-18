package nit.ds.mc.simpleitemgenerator;

import nit.ds.mc.simpleitemgenerator.ds.Map;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Date;

public class ItemGenerator {
    /**
     * The generator level
     */
    private int level = 1;

    /**
     * Maximum level of the Generator
     */
    private static int maxLevel = 3;

    /**
     * Ratio to production rate map
     */
    private Map ratioMap;

    /**
     * Location of the diamond block +(0,1,0) or one block higher
     */
    private Location location;

    /**
     * Value (amount) of item generated until now
     */
    private double value;

    /**
     * Last time the update method has been called
     */
    private Date lastTime;

    /**
     * Constructor
     * @param blockLocation: The location of the diamond block
     */
    public ItemGenerator(Location blockLocation) {
        ratioMap = new Map();  // Instantiation
        lastTime = new Date(); // Current time
        location = blockLocation;

        /**
         * Hard-coded configs
         */
        ratioMap.put(2, 0.8);
        ratioMap.put(1, 0.2);
        ratioMap.put(3, 1.5);
    }

    /**
     * Update the time of the generator and spawns item if it's necessary
     */
    public void update() {
        Date now = new Date();

        /**
         * Spawn section
         */
        if (value >= 1) {
            int count = (int) Math.floor(value);
            spawnItem(count);
            value -= count;
        }

        /**
         * Time updates section
         */
        double deltaTime = (double) (now.getTime() - lastTime.getTime()) / 1000;
        double ratio = ratioMap.get(level);

        value += ratio * deltaTime;
        lastTime = now;
    }

    /**
     * Spawns items
     * @param count
     */
    public void spawnItem(int count) {
        location.getWorld().dropItem(
                location.clone().add(0, 1, 0),
                new ItemStack(Material.DIAMOND, count)
        );
    }

    /**
     * Level up
     * @param player: The player right-clicking the block
     */
    public void upgrade(Player player) {
        if (level < 3) {
            level++;
            player.sendMessage("Diamond generator upgraded to level " + level);
        }
    }

    /**
     * The number of needed items
     * @return
     */
    public int getAmountOfItemsRequiredToUpgrade() {
        return 10 * level;
    }
}
