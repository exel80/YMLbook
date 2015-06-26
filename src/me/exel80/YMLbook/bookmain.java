package me.exel80.YMLbook;

import java.io.IOException;
import java.util.logging.Logger;

import me.exel80.YMLbook.Events.BookCmd;

import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class BookMain extends JavaPlugin {
	public static BookMain plugin;
	
	public Logger log = Logger.getLogger("Minecraft");
	public String pluginname = ChatColor.RED + "[YMLbook] " + ChatColor.WHITE;	
	public String strargs, strtitle, strauthor, strtext, strlore;
	public ItemStack book = new ItemStack(387, 1);
	
	public final me.exel80.YMLbook.Events.BookCmd cmd = new me.exel80.YMLbook.Events.BookCmd(this);
	public final me.exel80.YMLbook.Events.BookSign other = new me.exel80.YMLbook.Events.BookSign(this);

	@Override
	public void onDisable() {
		
	}
	@Override
	public void onEnable() {
		PluginManager pm = getServer().getPluginManager();
		pm.registerEvents(cmd, this);
		pm.registerEvents(other, this);
		
		getCommand("book").setExecutor(new BookCmd(this));
		getCommand("bookreload").setExecutor(new BookCmd(this));
		getCommand("bookgive").setExecutor(new BookCmd(this));
		getCommand("booklist").setExecutor(new BookCmd(this));
	}
}