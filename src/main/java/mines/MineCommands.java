package mines;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.enchantments.EnchantmentWrapper;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;

public class MineCommands implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = (Player) sender;

        switch (command.getLabel()) {
            case "mines": {

            } case "test": {
                String inventoryString = "27;0:DIAMOND_PICKAXE:1:0:My Pickaxe:Lore line 1|Lore line 2:DURABILITY-3|EFFICIENCY-5;1:GOLD_INGOT:64:0;2:DIAMOND_HELMET:1:0:My Helmet:Lore line 1|Lore line 2:PROTECTION-4|UNBREAKING-3;3:DIAMOND_CHESTPLATE:1:0:My Chestplate:Lore line 1|Lore line 2:PROTECTION-4|UNBREAKING-3;4:DIAMOND_LEGGINGS:1:0:My Leggings:Lore line 1|Lore line 2:PROTECTION-4|UNBREAKING-3;5:DIAMOND_BOOTS:1:0:My Boots:Lore line 1|Lore line 2:PROTECTION-4|UNBREAKING-3;6:DIAMOND_SWORD:1:0:My Sword:Lore line 1|Lore line 2:DURABILITY-3|FIRE_ASPECT-2;7:BOW:1:0:My Bow:Lore line 1|Lore line 2:POWER-5|PUNCH-2;8:ARROW:64:0;9:COOKED_BEEF:64:0;10:COOKED_CHICKEN:64:0;11:COOKED_MUTTON:64:0;12:COOKED_PORKCHOP:64:0;13:GOLDEN_APPLE:64:0;14:MUSHROOM_STEW:1:0;15:BAKED_POTATO:64:0;16:RABBIT_STEW:1:0;17:PUMPKIN_PIE:64:0;18:BREAD:64:0;19:APPLE:64:0;20:GOLD_INGOT:64:0;21:IRON_INGOT:64:0;22:COAL:64:0;23:EMERALD:64:0;24:DIAMOND:64:0;25:LAPIS_LAZULI:64:0;26:REDSTONE:64:0;";
                Inventory myInventory = stringToInventory(inventoryString);
                player.openInventory(myInventory);
            }

        }
        return true;
    }

    public static void openMineGUI(Player player) {

    }

    public static String inventoryToString(Inventory inventory) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(inventory.getSize() + ";");
        for (int i = 0; i < inventory.getSize(); i++) {
            ItemStack itemStack = inventory.getItem(i);
            if (itemStack != null) {
                String itemString = i + ":" + itemStack.getType().name() + ":" + itemStack.getAmount();
                if (itemStack.getItemMeta() instanceof Damageable) {
                    Damageable damageable = (Damageable) itemStack.getItemMeta();
                    itemString += ":" + damageable.getDamage();
                }
                if (itemStack.hasItemMeta()) {
                    if (itemStack.getItemMeta().hasDisplayName()) {
                        itemString += ":" + itemStack.getItemMeta().getDisplayName();
                    }
                    if (itemStack.getItemMeta().hasLore()) {
                        itemString += ":";
                        for (String loreLine : itemStack.getItemMeta().getLore()) {
                            itemString += loreLine + "|";
                        }
                    }
                    if (itemStack.getItemMeta().hasEnchants()) {
                        itemString += ":";
                        for (Map.Entry<Enchantment, Integer> enchant : itemStack.getItemMeta().getEnchants().entrySet()) {
                            itemString += enchant.getKey().getKey().getKey() + "-" + enchant.getValue() + "|";
                        }
                    }
                }
                stringBuilder.append(itemString + ";");
            }
        }
        return stringBuilder.toString();
    }


    public static Inventory stringToInventory(String inventoryString) {
        String[] data = inventoryString.split(";");
        int size = Integer.parseInt(data[0]);
        Inventory inventory = Bukkit.createInventory(null, size);
        for (int i = 1; i < data.length; i++) {

            String[] itemData = data[i].split(":");
            int position = Integer.parseInt(itemData[0]);
            Material material = Material.matchMaterial(itemData[1]);
            if (material == null) {
                continue;
            }
            int amount = Integer.parseInt(itemData[2]);
            short durability = 0;
            String name = null;
            List<String> lore = null;
            Map<String, Integer> enchantments = null;
            if (itemData.length > 3) {
                if (itemData[3].matches("-?\\d+(\\.\\d+)?")) {
                    durability = Short.parseShort(itemData[3]);
                } else {
                    name = itemData[3];
                }
            }
            if (itemData.length > 4) {
                if (itemData[4].contains("|")) {
                    lore = Arrays.asList(itemData[4].split("\\|"));
                } else {
                    enchantments = new HashMap<>();
                    String[] enchants = itemData[4].split("\\|");
                    for (String enchant : enchants) {
                        String[] enchantData = enchant.split("-");
                        if (enchantData.length < 2) {
                            continue;
                        }
                        String enchantmentName = enchantData[0];
                        int level = Integer.parseInt(enchantData[1]);
                        enchantments.put(enchantmentName, level);
                    }
                }
            }
            ItemStack item = new ItemStack(material, amount, durability);
            ItemMeta meta = item.getItemMeta();
            if (name != null) {
                meta.setDisplayName(name);
            }
            if (lore != null) {
                meta.setLore(lore);
            }
            item.setItemMeta(meta);
            if (enchantments != null) {
                for (Map.Entry<String, Integer> entry : enchantments.entrySet()) {
                    Bukkit.getLogger().info("Enchantment name: " + entry.getKey() + ", level: " + entry.getValue());
                }
                for (Map.Entry<String, Integer> entry : enchantments.entrySet()) {
                    item.addUnsafeEnchantment(Enchantment.getByName(entry.getKey()), entry.getValue());
                }
            }
            inventory.setItem(position, item);
        }
        return inventory;
    }

}
