package me.exel80.YMLbook.Events;

import java.io.File;
import java.util.List;
import java.util.Set;

import me.exel80.YMLbook.BookMain;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;

public class BookSign implements Listener {
	private BookMain plugin;
	private BookCmd cmd;
	public BookSign (BookMain plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler(priority = EventPriority.HIGH)
    public void SignRead(SignChangeEvent event) {
        if (event.getLine(0).equalsIgnoreCase("book") || event.getLine(0).equalsIgnoreCase("[Book]")) {
        	if (!event.getLine(1).isEmpty() && event.getPlayer().hasPermission("ymlbook.signbook")) {
        		event.setLine(0, ChatColor.GREEN + "[Book]");
        		event.setLine(2, "---------------");
        		event.setLine(3, "Right-click me!");
        	}
        	if (!event.getLine(1).isEmpty() && event.getPlayer().hasPermission("ymlbook.signbookkit")) {
        		event.setLine(0, ChatColor.GREEN + "[Book]");
        		event.setLine(2, "---------------");
        		event.setLine(3, "Right-click me!");
        	} else { event.setLine(0, ""); event.setLine(1, ""); event.setLine(2, ""); event.setLine(3, ""); }
        }
    }
	@EventHandler
    public void SignHook(PlayerInteractEvent event) {
       if(event.getAction() == Action.RIGHT_CLICK_BLOCK) {
	      if(event.getClickedBlock().getType() == Material.SIGN || event.getClickedBlock().getType() == Material.SIGN_POST
	    		  || event.getClickedBlock().getType() == Material.WALL_SIGN) {
	        try {
		    	Sign sign = (Sign) event.getClickedBlock().getState();
			    if(sign.getLine(0).contains("[Book]") && !sign.getLine(1).isEmpty()) {
			    	plugin.strtitle = getBook(sign.getLine(1) + ".title");
			    	plugin.strauthor = getBook(sign.getLine(1) + ".author");
			    	plugin.strtext = getBook(sign.getLine(1) + ".text");
			    	plugin.strlore = getBook(sign.getLine(1) + ".lore");
			    	
					Player p = event.getPlayer();
					if(p.hasPermission("ymlbook.use")) {
						if (p.hasPermission("ymlbook.book.*") || p.hasPermission("ymlbook.book." + sign.getLine(1))) {
							try {
								cmd.setPages(plugin.book, plugin.strtext, plugin.strtitle, plugin.strauthor, plugin.strlore);		
								p.getInventory().addItem(plugin.book);
							} catch (Exception ex) { ex.getStackTrace(); }
						} else { cmd.sendMessages( cmd.getConfig("messages", "nopermission"), p); }
					} else { cmd.sendMessages( cmd.getConfig("messages", "nopermission"), p); }
			    }
	        } catch (Exception ex) { ex.getStackTrace(); }
	      }
       }
    }
    
    @EventHandler
    public void onPlayerFirstJoin(PlayerJoinEvent event) {
    	if(getFirstBook(event.getPlayer().getWorld().getName()) != null) {
	    	if(!event.getPlayer().hasPlayedBefore()) {
	    		List<String> spawnbooks = plugin.getConfig().getStringList("World." + event.getPlayer().getWorld().getName());
	    		for (int i = 0; i < spawnbooks.size(); i++) {
		    		try {
				    	plugin.strtitle = getBook(spawnbooks.get(i) + ".title");
					    plugin.strauthor = getBook(spawnbooks.get(i) + ".author");
					    plugin.strtext = getBook(spawnbooks.get(i) + ".text");
					    plugin.strlore = getBook(spawnbooks.get(i) + ".lore");
	
						Player p = event.getPlayer();
						try {					
							cmd.setPages(plugin.book, plugin.strtext, plugin.strtitle, plugin.strauthor, plugin.strlore);
							p.getInventory().addItem(plugin.book);
						} catch (Exception ex) { ex.getStackTrace(); }
			        } catch (Exception ex) { ex.getStackTrace(); }
	    		}
	    	}
    	}
    }
    @EventHandler
    public void onPlayerChangeWorld(PlayerChangedWorldEvent event) {
    	if(getFirstBook(event.getPlayer().getWorld().getName()) != null) {
	    	if(!event.getPlayer().hasPlayedBefore()) {
	    		List<String> spawnbooks = plugin.getConfig().getStringList("World." + event.getPlayer().getWorld().getName());
	    		for (int i = 0; i < spawnbooks.size(); i++) {
		    		try {
				    	plugin.strtitle = getBook(spawnbooks.get(i) + ".title");
					    plugin.strauthor = getBook(spawnbooks.get(i) + ".author");
					    plugin.strtext = getBook(spawnbooks.get(i) + ".text");
					    plugin.strlore = getBook(spawnbooks.get(i) + ".lore");
	
						Player p = event.getPlayer();
						try {					
							cmd.setPages(plugin.book, plugin.strtext, plugin.strtitle, plugin.strauthor, plugin.strlore);
							p.getInventory().addItem(plugin.book);
						} catch (Exception ex) { ex.getStackTrace(); }
			        } catch (Exception ex) { ex.getStackTrace(); }
	    		}
	    	}
    	}
    }

    public String getBook(String name) {
		File path = new File(plugin.getDataFolder(), "books.yml");
		try { plugin.getConfig().load(path); }
		catch (Exception e) { e.getStackTrace(); }
			
		String getMessage = plugin.getConfig().getString("book." + name);
			
		if (getMessage != null)
			return getMessage;
		else 
			return "";
	}
    public String getFirstBook (String world) {
    	File path = new File(plugin.getDataFolder(), "books.yml");
		try { plugin.getConfig().load(path); }
		catch (Exception e) { e.getStackTrace(); }
		
		if(plugin.getConfig().contains("World." + world)) {
			return plugin.getConfig().getString("World." + world);
		} return "";
    }
}
