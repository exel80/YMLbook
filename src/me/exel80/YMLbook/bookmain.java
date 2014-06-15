package me.exel80.YMLbook;

import java.io.IOException;
import java.util.logging.Logger;

import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class bookmain extends JavaPlugin {
	public static bookmain plugin;
	public Logger log = Logger.getLogger("Minecraft");
	String pluginname = ChatColor.RED + "[YMLbook] " + ChatColor.WHITE;	
	public ItemStack book = new ItemStack(387, 1);
	String strargs, strtitle, strauthor, strtext, strlore;
	
	public final me.exel80.YMLbook.bookcmd cmd = new me.exel80.YMLbook.bookcmd(this);

	@Override
	public void onDisable() {
		
	}
	@Override
	public void onEnable() {
		PluginManager pm = getServer().getPluginManager();
		pm.registerEvents(cmd, this);
		
		getCommand("book").setExecutor(new bookcmd(this));
		getCommand("bookreload").setExecutor(new bookcmd(this));
		getCommand("bookgive").setExecutor(new bookcmd(this));
		getCommand("booklist").setExecutor(new bookcmd(this));
		
		try {
		    Metrics metrics = new Metrics(this);
		    metrics.start();
		} catch (IOException e) {
		    // Failed to submit the stats :-(
		}
	}
}