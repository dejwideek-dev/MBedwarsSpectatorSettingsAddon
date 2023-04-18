package pl.dejwideek.spectatorsettings.events;

import com.cryptomorin.xseries.XMaterial;
import de.marcely.bedwars.api.BedwarsAPI;
import de.marcely.bedwars.api.game.spectator.Spectator;
import dev.dejvokep.boostedyaml.YamlDocument;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import pl.dejwideek.spectatorsettings.SpectSettingsPlugin;
import pl.dejwideek.spectatorsettings.color.ColorAPI;

@SuppressWarnings("ALL")
public class InvClickEvent implements Listener {

    private static SpectSettingsPlugin plugin;

    public InvClickEvent(SpectSettingsPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onInvClick(InventoryClickEvent e) {
        YamlDocument config = plugin.config;
        ColorAPI colorAPI = new ColorAPI();
        Player p = (Player) e.getWhoClicked();
        Inventory inv = e.getClickedInventory();

        String title = config.getString("menu.title");
        int size = config.getInt("menu.size");
        String noPermsMsg = config.getString(
                "messages.no-permission");
        String noSpeedMaterial = config
                .getString("menu.items.no-speed.material")
                .toUpperCase();
        String noSpeedDisplayName = config
                .getString("menu.items.no-speed.display-name");
        String noSpeedSelectedMsg = config
                .getString("messages.selected.no-speed");
        String speedSelectedMsg = config
                .getString("messages.selected.speed");
        String nightVisionMaterial = config
                .getString("menu.items.night-vision.material")
                .toUpperCase();
        String nightVisionDisplayName = config
                .getString("menu.items.night-vision.display-name");
        String nightVisionEnabledMsg = config
                .getString("messages.night-vision.enabled");
        String nightVisionDisabledMsg = config
                .getString("messages.night-vision.disabled");
        String nightVisionPermission = config
                .getString("menu.items.night-vision.permission");
        String speed1Material = config
                .getString("menu.items.speed.speed1.material")
                .toUpperCase();
        String speed1DisplayName = config
                .getString("menu.items.speed.speed1.display-name");
        float speed1WalkSpeed = config.getFloat(
                "menu.items.speed.speed1.walk-speed");
        float speed1FlySpeed = config.getFloat(
                "menu.items.speed.speed1.fly-speed");
        String speed1Permission = config
                .getString("menu.items.speed.speed1.permission");
        String speed2Material = config
                .getString("menu.items.speed.speed2.material")
                .toUpperCase();
        String speed2DisplayName = config
                .getString("menu.items.speed.speed2.display-name");
        float speed2WalkSpeed = config.getFloat(
                "menu.items.speed.speed2.walk-speed");
        float speed2FlySpeed = config.getFloat(
                "menu.items.speed.speed2.fly-speed");
        String speed2Permission = config
                .getString("menu.items.speed.speed2.permission");
        String speed3Material = config
                .getString("menu.items.speed.speed3.material")
                .toUpperCase();
        String speed3DisplayName = config
                .getString("menu.items.speed.speed3.display-name");
        float speed3WalkSpeed = config.getFloat(
                "menu.items.speed.speed3.walk-speed");
        float speed3FlySpeed = config.getFloat(
                "menu.items.speed.speed3.fly-speed");
        String speed3Permission = config
                .getString("menu.items.speed.speed3.permission");
        String speed4Material = config
                .getString("menu.items.speed.speed4.material")
                .toUpperCase();
        String speed4DisplayName = config
                .getString("menu.items.speed.speed4.display-name");
        float speed4WalkSpeed = config.getFloat(
                "menu.items.speed.speed4.walk-speed");
        float speed4FlySpeed = config.getFloat(
                "menu.items.speed.speed4.fly-speed");
        String speed4Permission = config
                .getString("menu.items.speed.speed4.permission");

        try {
            if(inv.getSize() == size
                    && p.getOpenInventory().getTitle()
                    .equals(colorAPI.process(title))) {
                ItemStack item = e.getCurrentItem();

                if(item.getType().equals(XMaterial
                        .valueOf(noSpeedMaterial).parseMaterial())
                        && item.getItemMeta()
                        .getDisplayName().equals(colorAPI
                                .process(noSpeedDisplayName))) {
                    if (p.getWalkSpeed() != 0.2f && p.getFlySpeed() != 0.1f) {
                        p.setWalkSpeed(0.2f);
                        p.setFlySpeed(0.1f);
                        p.sendMessage(colorAPI
                                .process(noSpeedSelectedMsg));
                        Spectator spectator = BedwarsAPI.getGameAPI()
                                .getSpectatorByPlayer(p);
                        BedwarsAPI.getGameAPI()
                                .getSpectatorItemHandler(
                                        "spectator-settings").handleUse(
                                        spectator, null);
                    }
                    else return;
                }
                else if(item.getType().equals(XMaterial
                        .valueOf(nightVisionMaterial).parseMaterial())
                        && item.getItemMeta()
                        .getDisplayName().equals(colorAPI
                                .process(nightVisionDisplayName))) {
                    if(p.hasPermission(nightVisionPermission)) {
                        if (!p.hasPotionEffect(PotionEffectType.NIGHT_VISION)) {
                            p.addPotionEffect(new PotionEffect(
                                    PotionEffectType.NIGHT_VISION,
                                    1000000, 0));
                            p.sendMessage(colorAPI
                                    .process(nightVisionEnabledMsg));
                            Spectator spectator = BedwarsAPI.getGameAPI()
                                    .getSpectatorByPlayer(p);
                            BedwarsAPI.getGameAPI()
                                    .getSpectatorItemHandler(
                                            "spectator-settings").handleUse(
                                            spectator, null);
                        } else {
                            p.removePotionEffect(PotionEffectType.NIGHT_VISION);
                            p.sendMessage(colorAPI
                                    .process(nightVisionDisabledMsg));
                            Spectator spectator = BedwarsAPI.getGameAPI()
                                    .getSpectatorByPlayer(p);
                            BedwarsAPI.getGameAPI()
                                    .getSpectatorItemHandler(
                                            "spectator-settings").handleUse(
                                            spectator, null);
                        }
                    }
                    else {
                        p.sendMessage(colorAPI
                                .process(noPermsMsg
                                        .replaceAll("%permission%",
                                                nightVisionPermission)));
                    }
                }
                else if(item.getType().equals(XMaterial
                        .valueOf(speed1Material).parseMaterial())
                        && item.getItemMeta()
                        .getDisplayName().equals(colorAPI
                                .process(speed1DisplayName))) {
                    if(p.hasPermission(speed1Permission)) {
                        String msgPlaceholder = config.getString(
                                "menu.items.speed.speed1.message-placeholder");
                        if(p.getWalkSpeed() != speed1WalkSpeed
                                && p.getFlySpeed() != speed1FlySpeed) {
                            p.setWalkSpeed(speed1WalkSpeed);
                            p.setFlySpeed(speed1FlySpeed);
                            p.sendMessage(colorAPI
                                    .process(speedSelectedMsg.replaceAll(
                                            "%speed%", msgPlaceholder)));
                            Spectator spectator = BedwarsAPI.getGameAPI()
                                    .getSpectatorByPlayer(p);
                            BedwarsAPI.getGameAPI()
                                    .getSpectatorItemHandler(
                                            "spectator-settings").handleUse(
                                            spectator, null);
                        }
                        else return;
                    }
                    else {
                        p.sendMessage(colorAPI
                                .process(noPermsMsg
                                        .replaceAll("%permission%",
                                                speed1Permission)));
                    }
                }
                else if(item.getType().equals(XMaterial
                        .valueOf(speed2Material).parseMaterial())
                        && item.getItemMeta()
                        .getDisplayName().equals(colorAPI
                                .process(speed2DisplayName))) {
                    if(p.hasPermission(speed2Permission)) {
                        String msgPlaceholder = config.getString(
                                "menu.items.speed.speed2.message-placeholder");
                        if(p.getWalkSpeed() != speed2WalkSpeed
                                && p.getFlySpeed() != speed2FlySpeed) {
                            p.setWalkSpeed(speed2WalkSpeed);
                            p.setFlySpeed(speed2FlySpeed);
                            p.sendMessage(colorAPI
                                    .process(speedSelectedMsg.replaceAll(
                                            "%speed%", msgPlaceholder)));
                            Spectator spectator = BedwarsAPI.getGameAPI()
                                    .getSpectatorByPlayer(p);
                            BedwarsAPI.getGameAPI()
                                    .getSpectatorItemHandler(
                                            "spectator-settings").handleUse(
                                            spectator, null);
                        }
                        else return;
                    }
                    else {
                        p.sendMessage(colorAPI
                                .process(noPermsMsg
                                        .replaceAll("%permission%",
                                                speed2Permission)));
                    }
                }
                else if(item.getType().equals(XMaterial
                        .valueOf(speed3Material).parseMaterial())
                        && item.getItemMeta()
                        .getDisplayName().equals(colorAPI
                                .process(speed3DisplayName))) {
                    if(p.hasPermission(speed3Permission)) {
                        String msgPlaceholder = config.getString(
                                "menu.items.speed.speed3.message-placeholder");
                        if(p.getWalkSpeed() != speed3WalkSpeed
                                && p.getFlySpeed() != speed3FlySpeed) {
                            p.setWalkSpeed(speed3WalkSpeed);
                            p.setFlySpeed(speed3FlySpeed);
                            p.sendMessage(colorAPI
                                    .process(speedSelectedMsg.replaceAll(
                                            "%speed%", msgPlaceholder)));
                            Spectator spectator = BedwarsAPI.getGameAPI()
                                    .getSpectatorByPlayer(p);
                            BedwarsAPI.getGameAPI()
                                    .getSpectatorItemHandler(
                                            "spectator-settings").handleUse(
                                            spectator, null);
                        }
                        else return;
                    }
                    else {
                        p.sendMessage(colorAPI
                                .process(noPermsMsg
                                        .replaceAll("%permission%",
                                                speed3Permission)));
                    }
                }
                else if(item.getType().equals(XMaterial
                        .valueOf(speed4Material).parseMaterial())
                        && item.getItemMeta()
                        .getDisplayName().equals(colorAPI
                                .process(speed4DisplayName))) {
                    if(p.hasPermission(speed4Permission)) {
                        String msgPlaceholder = config.getString(
                                "menu.items.speed.speed4.message-placeholder");
                        if(p.getWalkSpeed() != speed4WalkSpeed
                                && p.getFlySpeed() != speed4FlySpeed) {
                            p.setWalkSpeed(speed4WalkSpeed);
                            p.setFlySpeed(speed4FlySpeed);
                            p.sendMessage(colorAPI
                                    .process(speedSelectedMsg.replaceAll(
                                            "%speed%", msgPlaceholder)));
                            Spectator spectator = BedwarsAPI.getGameAPI()
                                    .getSpectatorByPlayer(p);
                            BedwarsAPI.getGameAPI()
                                    .getSpectatorItemHandler(
                                            "spectator-settings").handleUse(
                                            spectator, null);
                        }
                        else return;
                    }
                    else {
                        p.sendMessage(colorAPI
                                .process(noPermsMsg
                                        .replaceAll("%permission%",
                                                speed4Permission)));
                    }
                }
                else {
                    for(Object obj : config.getSection(
                            "menu.items.additional-speed").getKeys()) {
                        String key = (String) obj;

                        String itemMaterial = config.getString(
                                "menu.items.additional-speed."
                                        + key + ".material").toUpperCase();
                        String itemDisplayName = config.getString(
                                "menu.items.additional-speed."
                                        + key + ".display-name");
                        String itemPermission = config.getString(
                                "menu.items.additional-speed."
                                        + key + ".permission");
                        float itemWalkSpeed = config.getFloat(
                                "menu.items.additional-speed."
                                        + key + ".walk-speed");
                        float itemFlySpeed = config.getFloat(
                                "menu.items.additional-speed."
                                        + key + ".fly-speed");
                        String msgPlaceholder = config.getString(
                                "menu.items.additional-speed."
                                        + key + ".message-placeholder");

                        if(item.getType().equals(XMaterial.valueOf(itemMaterial)
                                .parseMaterial())
                                && item.getItemMeta().getDisplayName()
                                .equals(colorAPI.process(itemDisplayName))) {
                            if(p.hasPermission(itemPermission)) {
                                if(p.getWalkSpeed() != itemWalkSpeed
                                        && p.getFlySpeed() != itemFlySpeed) {
                                    p.setWalkSpeed(itemWalkSpeed);
                                    p.setFlySpeed(itemFlySpeed);
                                    p.sendMessage(colorAPI.process(
                                            speedSelectedMsg
                                                    .replaceAll(
                                                            "%speed%", msgPlaceholder)));
                                    Spectator spectator = BedwarsAPI.getGameAPI()
                                            .getSpectatorByPlayer(p);
                                    BedwarsAPI.getGameAPI()
                                            .getSpectatorItemHandler(
                                                    "spectator-settings").handleUse(
                                                            spectator, null);
                                }
                                else return;
                            }
                            else {
                                p.sendMessage(colorAPI
                                        .process(noPermsMsg
                                                .replaceAll("%permission%",
                                                        itemPermission)));
                            }
                        }
                    }
                }
            }
        } catch (Exception ex) {return;}
    }
}
