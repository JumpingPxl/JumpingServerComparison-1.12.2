package de.jumpingpxl.servercomparison;

import de.jumpingpxl.servercomparison.util.CompareModule;
import de.jumpingpxl.servercomparison.util.Settings;
import lombok.Getter;
import net.labymod.api.LabyModAddon;
import net.labymod.core.LabyModCore;
import net.labymod.settings.elements.SettingsElement;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Nico (JumpingPxl) Middendorf
 * @date 21.04.2019
 * @project LabyMod-Addon: ServerComparison-1.12.2
 */

@Getter
public class JumpingAddon extends LabyModAddon {

	private Settings settings;
	private double version = 1.0D;
	private Map<String, Long> lastUpdate = new HashMap<>();
	private Map<String, Integer> lastPlayerCount = new HashMap<>();

	@Override
	public void onEnable() {
		settings = new Settings(this);
		getApi().registerModule(new CompareModule(this));
	}

	@Override
	public void loadConfig() {
		settings.loadConfig();
	}

	@Override
	protected void fillSettings(List<SettingsElement> list) {
		settings.fillSettings(list);
	}

	public int getPlayerCount(String address) {
		if (!settings.isEnabled())
			return -1;
		if (!lastUpdate.containsKey(address) || (lastUpdate.get(address) + settings.getCooldown() * 1000) < System.currentTimeMillis()) {
			lastUpdate.put(address, System.currentTimeMillis());
			LabyModCore.getServerPinger().pingServer(null, lastUpdate.get(address), address, serverPingData -> lastPlayerCount.put(address, serverPingData.getCurrentPlayers()));
		}
		if (lastPlayerCount.containsKey(address))
			return lastPlayerCount.get(address);
		return -1;
	}
}