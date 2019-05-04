package de.jumpingpxl.servercomparison.util;

import de.jumpingpxl.servercomparison.JumpingAddon;
import net.labymod.ingamegui.ModuleCategory;
import net.labymod.ingamegui.ModuleCategoryRegistry;
import net.labymod.ingamegui.moduletypes.TextModule;
import net.labymod.settings.elements.ControlElement;
import net.labymod.utils.Material;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Nico (JumpingPxl) Middendorf
 * @date 21.04.2019
 */

public class CompareModule extends TextModule {

	private JumpingAddon jumpingAddon;

	public CompareModule(JumpingAddon jumpingAddon) {
		this.jumpingAddon = jumpingAddon;
	}

	@Override
	public String[] getKeys() {
		return new String[]{jumpingAddon.getSettings().getServerAddress1().split(":")[0], jumpingAddon.getSettings().getServerAddress2().split(":")[0], "Difference"};
	}

	@Override
	public List<List<Text>> getTextValues() {
		int count1 = jumpingAddon.getPlayerCount(jumpingAddon.getSettings().getServerAddress1());
		int count2 = jumpingAddon.getPlayerCount(jumpingAddon.getSettings().getServerAddress2());
		int difference = count2 - count1;
		List<List<Text>> list = new ArrayList<>();
		List<Text> list1 = new ArrayList<>();
		List<Text> list2 = new ArrayList<>();
		List<Text> list3 = new ArrayList<>();
		list1.add(new Text(String.valueOf(count1), valueColor));
		list2.add(new Text(String.valueOf(count2), valueColor));
		list3.add(new Text((difference < 0 ? "" : "+") + difference, valueColor));
		list.add(list1);
		list.add(list2);
		list.add(list3);
		return list;
	}

	@Override
	public String[] getDefaultKeys() {
		return new String[]{"?", "?", "?"};
	}

	@Override
	public List<List<Text>> getDefaultTextValues() {
		List<List<Text>> list = new ArrayList<>();
		List<Text> list1 = new ArrayList<>();
		list1.add(new Text("?", valueColor));
		list.add(list1);
		list.add(list1);
		list.add(list1);
		return list;
	}

	@Override
	public ControlElement.IconData getIconData() {
		return new ControlElement.IconData(Material.REDSTONE);
	}

	@Override
	public boolean isShown() {
		return jumpingAddon.getSettings().isEnabled();
	}

	@Override
	public void loadSettings() {

	}

	@Override
	public String getSettingName() {
		return "jumping_comparemodule";
	}

	@Override
	public String getControlName() {
		return "ServerCompare";
	}

	@Override
	public String getDescription() {
		return null;
	}

	@Override
	public ModuleCategory getCategory() {
		return ModuleCategoryRegistry.CATEGORY_OTHER;
	}

	@Override
	public int getSortingId() {
		return 0;
	}
}