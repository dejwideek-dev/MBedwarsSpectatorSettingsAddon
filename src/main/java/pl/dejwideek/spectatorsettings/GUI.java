package pl.dejwideek.spectatorsettings;

import com.cryptomorin.xseries.XEnchantment;
import com.cryptomorin.xseries.XMaterial;
import de.marcely.bedwars.tools.gui.type.ChestGUI;
import dev.dejvokep.boostedyaml.YamlDocument;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffectType;
import pl.dejwideek.spectatorsettings.color.ColorAPI;

import java.util.ArrayList;

@SuppressWarnings("ALL")
public class GUI extends ChestGUI {

    private static SpectSettingsPlugin plugin;

    public GUI(SpectSettingsPlugin plugin) {
        this.plugin = plugin;
    }

    public void gui(Player player) {
        YamlDocument config = plugin.config;
        ColorAPI colorAPI = new ColorAPI();
        String[] itemNames = new String[]{
                "no-speed", "night-vision"
        };
        String[] speedItemNames = new String[] {
                "speed1", "speed2", "speed3", "speed4"
        };

        String title = config.getString("menu.title");
        int size = config.getInt("menu.size");
        boolean isItemEnchant = config
                .getBoolean("menu.active-effect-item-enchant");
        boolean isFillEmptySlots = config.getBoolean("menu.fill-empty-slots.enabled");

        Inventory gui = Bukkit.createInventory(
                null, size, colorAPI.process(title));

        for(String itemName : itemNames) {
            boolean isItemEnabled = config
                    .getBoolean("menu.items." + itemName + ".enabled");
            String itemMaterial = config
                    .getString("menu.items." + itemName + ".material")
                    .toUpperCase();
            byte itemData = config
                    .getByte("menu.items." + itemName + ".material-data");
            int itemAmount = config
                    .getInt("menu.items." + itemName + ".amount");
            String itemDisplayName = config
                    .getString("menu.items." + itemName + ".display-name");
            int itemSlot = config.getInt("menu.items." + itemName + ".slot");
            ItemStack item = new ItemStack(XMaterial.valueOf(itemMaterial)
                    .parseMaterial(), itemAmount, itemData);
            ItemMeta meta = item.getItemMeta();

            meta.setDisplayName(colorAPI
                    .process(itemDisplayName));
            ArrayList<String> loreList = new ArrayList<String>();

            if(itemName.equals("night-vision")) {
                if(isItemEnabled) {
                    if(player.hasPotionEffect(
                            PotionEffectType.NIGHT_VISION)) {
                        for(String s : config.getStringList(
                                "menu.items.night-vision.lore.disable")) {
                            loreList.add(colorAPI.process(s));
                        }
                        if(isItemEnchant) {
                            meta.addEnchant(XEnchantment.DAMAGE_ALL
                                    .getEnchant(), 1, true);
                        }
                    }
                    else {
                        for(String s : config.getStringList(
                                "menu.items.night-vision.lore.enable")) {
                            loreList.add(colorAPI.process(s));
                        }
                    }
                }
            }
            else {
                if(player.getWalkSpeed() == 0.2f
                        && player.getFlySpeed() == 0.1f) {
                    for(String s : config.getStringList(
                            "menu.items.no-speed.lore.selected")) {
                        loreList.add(colorAPI.process(s));
                    }
                    if(isItemEnchant) {
                        meta.addEnchant(XEnchantment.DAMAGE_ALL
                                    .getEnchant(), 1, true);
                    }
                }
                else {
                    for(String s : config.getStringList(
                            "menu.items.no-speed.lore.select")) {
                        loreList.add(colorAPI.process(s));
                    }
                }
            }
            meta.addItemFlags(
                    ItemFlag.HIDE_ATTRIBUTES,
                    ItemFlag.HIDE_ENCHANTS,
                    ItemFlag.HIDE_DESTROYS,
                    ItemFlag.HIDE_POTION_EFFECTS,
                    ItemFlag.HIDE_UNBREAKABLE,
                    ItemFlag.HIDE_PLACED_ON);
            meta.setLore(loreList);
            item.setItemMeta(meta);

            if(isItemEnabled) gui.setItem(itemSlot, item);
        }

        for(String itemName : speedItemNames) {
            boolean isItemEnabled = config
                    .getBoolean("menu.items.speed." + itemName + ".enabled");
            String itemMaterial = config
                    .getString("menu.items.speed." + itemName + ".material")
                    .toUpperCase();
            byte itemData = config
                    .getByte("menu.items.speed." + itemName + ".material-data");
            int itemAmount = config
                    .getInt("menu.items.speed." + itemName + ".amount");
            String itemDisplayName = config
                    .getString("menu.items.speed." + itemName + ".display-name");
            int itemSlot = config.getInt("menu.items.speed." + itemName + ".slot");
            float itemWalkSpeed = config
                    .getFloat("menu.items.speed." + itemName + ".walk-speed");
            float itemFlySpeed = config
                    .getFloat("menu.items.speed." + itemName + ".fly-speed");
            ItemStack item = new ItemStack(XMaterial.valueOf(itemMaterial)
                    .parseMaterial(), itemAmount, itemData);
            ItemMeta meta = item.getItemMeta();

            meta.setDisplayName(colorAPI
                    .process(itemDisplayName));
            ArrayList<String> loreList = new ArrayList<String>();

            if(player.getWalkSpeed() == itemWalkSpeed
                    && player.getFlySpeed() == itemFlySpeed) {
                for(String s : config.getStringList(
                        "menu.items.speed." + itemName + ".lore.selected")) {
                    loreList.add(colorAPI.process(s));
                }
                if(isItemEnchant) {
                    meta.addEnchant(XEnchantment.DAMAGE_ALL
                            .getEnchant(), 1, true);
                }
            }
            else {
                for(String s : config.getStringList(
                        "menu.items.speed." + itemName + ".lore.select")) {
                    loreList.add(colorAPI.process(s));
                }
            }
            meta.addItemFlags(
                    ItemFlag.HIDE_ATTRIBUTES,
                    ItemFlag.HIDE_ENCHANTS,
                    ItemFlag.HIDE_DESTROYS,
                    ItemFlag.HIDE_POTION_EFFECTS,
                    ItemFlag.HIDE_UNBREAKABLE,
                    ItemFlag.HIDE_PLACED_ON);
            meta.setLore(loreList);
            item.setItemMeta(meta);

            if(isItemEnabled) gui.setItem(itemSlot, item);
        }

        try {
            for(Object obj : config.getSection(
                    "menu.items.additional-speed").getKeys()) {
                String itemName = (String) obj;

                String itemMaterial = config
                        .getString("menu.items.additional-speed." + itemName + ".material")
                        .toUpperCase();
                byte itemData = config
                        .getByte("menu.items.additional-speed." + itemName + ".material-data");
                int itemAmount = config
                        .getInt("menu.items.additional-speed." + itemName + ".amount");
                String itemDisplayName = config
                        .getString("menu.items.additional-speed." + itemName + ".display-name");
                int itemSlot = config.getInt("menu.items.additional-speed." + itemName + ".slot");
                float itemWalkSpeed = config
                        .getFloat("menu.items.additional-speed." + itemName + ".walk-speed");
                float itemFlySpeed = config
                        .getFloat("menu.items.additional-speed." + itemName + ".fly-speed");
                ItemStack item = new ItemStack(XMaterial.valueOf(itemMaterial)
                        .parseMaterial(), itemAmount, itemData);
                ItemMeta meta = item.getItemMeta();

                meta.setDisplayName(colorAPI
                        .process(itemDisplayName));
                ArrayList<String> loreList = new ArrayList<String>();

                if(player.getWalkSpeed() == itemWalkSpeed
                        && player.getFlySpeed() == itemFlySpeed) {
                    for(String s : config.getStringList(
                            "menu.items.additional-speed." + itemName + ".lore.selected")) {
                        loreList.add(colorAPI.process(s));
                    }
                    if(isItemEnchant) {
                        meta.addEnchant(XEnchantment.DAMAGE_ALL
                                .getEnchant(), 1, true);
                    }
                }
                else {
                    for(String s : config.getStringList(
                            "menu.items.additional-speed." + itemName + ".lore.select")) {
                        loreList.add(colorAPI.process(s));
                    }
                }
                meta.addItemFlags(
                        ItemFlag.HIDE_ATTRIBUTES,
                        ItemFlag.HIDE_ENCHANTS,
                        ItemFlag.HIDE_DESTROYS,
                        ItemFlag.HIDE_POTION_EFFECTS,
                        ItemFlag.HIDE_UNBREAKABLE,
                        ItemFlag.HIDE_PLACED_ON);
                meta.setLore(loreList);
                item.setItemMeta(meta);

                gui.setItem(itemSlot, item);
            }
        } catch (Exception e) {}

        player.openInventory(gui);

        if(isFillEmptySlots) {
            String itemMaterial = config.getString(
                            "menu.fill-empty-slots.material")
                    .toUpperCase();
            byte itemData = config.getByte(
                    "menu.fill-empty-slots.material-data");

            ItemStack item = new ItemStack(XMaterial.valueOf(
                    itemMaterial).parseMaterial(), 1, itemData);
            ItemMeta meta = item.getItemMeta();
            meta.setDisplayName(" ");
            meta.addItemFlags(
                    ItemFlag.HIDE_ATTRIBUTES,
                    ItemFlag.HIDE_ENCHANTS,
                    ItemFlag.HIDE_DESTROYS,
                    ItemFlag.HIDE_POTION_EFFECTS,
                    ItemFlag.HIDE_UNBREAKABLE,
                    ItemFlag.HIDE_PLACED_ON);
            item.setItemMeta(meta);

            for(int i = 0; i < size; i++) {
                if(gui.getItem(i) == null
                        || gui.getItem(i)
                        .getType().equals(XMaterial.AIR))
                    gui.setItem(i, item);
            }
        }
    }
}
