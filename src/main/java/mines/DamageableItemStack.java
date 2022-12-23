package mines;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;

public class DamageableItemStack implements Damageable {

    private final ItemStack item;

    public DamageableItemStack(ItemStack item) {
        this.item = item;
    }

    @Override
    public void setDamage(int damage) {
        ItemMeta meta = item.getItemMeta();
        ((Damageable) meta).setDamage(damage);  // Set the damage value of the ItemMeta
        item.setItemMeta(meta);
    }

    @Override
    public Damageable clone() {
        return null;
    }

    @Override
    public boolean hasDamage() {
        return false;
    }

    @Override
    public int getDamage() {
        ItemMeta meta = item.getItemMeta();
        return ((Damageable) meta).getDamage();
    }
}
