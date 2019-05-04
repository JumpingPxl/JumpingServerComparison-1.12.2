package de.jumpingpxl.servercomparison.util;

import com.google.gson.JsonObject;
import de.jumpingpxl.servercomparison.JumpingAddon;
import lombok.Getter;
import net.labymod.settings.elements.*;
import net.labymod.utils.Material;

import java.util.List;

/**
 * @author Nico (JumpingPxl) Middendorf
 * @date 21.04.2019
 */

@Getter
public class Settings {

	private JumpingAddon jumpingAddon;
	private String serverAddress1;
	private String serverAddress2;
	private boolean enabled;
	private int cooldown;

	public Settings(JumpingAddon jumpingAddon) {
		this.jumpingAddon = jumpingAddon;
	}

	private JsonObject getConfig() {
		return jumpingAddon.getConfig();
	}

	private void saveConfig() {
		jumpingAddon.saveConfig();
	}

	public void loadConfig() {
		this.enabled = !getConfig().has("enabled") || getConfig().get("enabled").getAsBoolean();
		this.cooldown = getConfig().has("cooldown") ? getConfig().get("cooldown").getAsInt() : 5;
		this.serverAddress1 = getConfig().has("server1") ? getConfig().get("server1").getAsString() : "GommeHD.net:25565";
		this.serverAddress2 = getConfig().has("server2") ? getConfig().get("server2").getAsString() : "GrieferGames.net:25565";
	}

	public void fillSettings(List<SettingsElement> list) {
		list.add(new HeaderElement("§6ServerComparison v" + jumpingAddon.getVersion()));
		list.add(new BooleanElement("§eEnabled", new ControlElement.IconData(Material.LEVER), enabled -> {
			this.enabled = enabled;
			getConfig().addProperty("enabled", enabled);
			saveConfig();
		}, enabled));
		list.add(new NumberElement("§eCooldown in sec", new ControlElement.IconData(Material.WATCH), cooldown).setRange(2, 30).addCallback(integer -> {
			this.cooldown = integer;
			getConfig().addProperty("cooldown", integer);
			saveConfig();
		}));
		list.add(new HeaderElement("§cServerlayout: Domain:Port"));
		list.add(new StringElement("§cServer1", new ControlElement.IconData(Material.PAPER), serverAddress1, string -> {
			this.serverAddress1 = string;
			getConfig().addProperty("server1", string);
			saveConfig();
		}));
		list.add(new StringElement("§cServer2", new ControlElement.IconData(Material.PAPER), serverAddress2, string -> {
			this.serverAddress2 = string;
			getConfig().addProperty("server2", string);
			saveConfig();
		}));
	}
}