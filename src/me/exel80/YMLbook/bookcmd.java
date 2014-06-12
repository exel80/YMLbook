package me.exel80.YMLbook;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Sign;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;

public class bookcmd implements CommandExecutor, Listener {
	private bookmain plugin;
	public bookcmd (bookmain plugin) {
		this.plugin = plugin;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String lable, String[] args) {
		File Pconf = new File(plugin.getDataFolder(), "config.yml");
		File Pbook = new File(plugin.getDataFolder(), "books.yml");
		
		// PLAYER
		if(sender instanceof Player) {
			Player p = (Player) sender;
			if (lable.equalsIgnoreCase("bookreload") && p.hasPermission("ymlbook.reload")) { //COMMAND: /bookreload
				try {
					plugin.getConfig().load(Pconf);
					plugin.getConfig().load(Pbook);
					sendMessages(getConfig("messages", "reloaded"), p);
				} catch (Exception e) { if(p.isOp()){sendMessages("Can't reload config. Check output, http://yaml-online-parser.appspot.com/", p);} }
				return true;
			} else if (lable.equalsIgnoreCase("booklist") && p.hasPermission("ymlbook.booklist")) { //COMMAND: /booklist
				try {
					if (!Pconf.exists() && !Pbook.exists()) { sendMessages(getConfig("messages", "notfound"), p); }
					else {
						sendMessages(getConfig("messages", "booklist").replace("{BOOKS}", getBooks().toString()), p);
					}
				} catch (Exception e) { sendMessages(getConfig("messages", "bookgiveusage"), p); }
				return true;
			}/* else if (lable.equalsIgnoreCase("bookcopy") && p.hasPermission("ymlbook.bookcopy")) { //COMMAND: /bookcopy <name>
					try {
						if (!Pconf.exists() && !Pbook.exists()) { sendMessages(getConfig("messages", "notfound"), p); }
						else
						{
							if (p.hasPermission("ymlbook.book.*") || p.hasPermission("ymlbook.book." + args[0]))
							{
								sendMessages(getConfig("messages", "bookcopy"), p);
							}
							else { sendMessages(getConfig("messages", "bookcopyusage"), p); }
						}
					} catch (Exception e) { sendMessages(getConfig("messages", "bookcopy"), p); }
					return true;
			}*/ else if (lable.equalsIgnoreCase("bookgive") && p.hasPermission("ymlbook.bookgive")) { //COMMAND: /bookgive <player> <name>
				try {
					if (!Pconf.exists() && !Pbook.exists()) { sendMessages(getConfig("messages", "notfound"), p); }
						else {
							plugin.strtitle = getBook(args[1] + ".title");
							plugin.strauthor = getBook(args[1] + ".author");
							plugin.strtext = getBook(args[1] + ".text");
							plugin.strlore = getBook(args[1] + ".lore");
							
							if (p.hasPermission("ymlbook.book.*") || p.hasPermission("ymlbook.book." + args[0])) {
								if (p.hasPermission("ymlbook.bookgive")) {
									try {
										Player give = (Bukkit.getServer().getPlayer(args[0]));
										if (give.isOnline()) {
											setPages(plugin.book, plugin.strtext, plugin.strtitle, plugin.strauthor, plugin.strlore);
											give.getInventory().addItem(plugin.book);
											sendMessages(getConfig("messages", "getbook").replace("{BOOKNAME}", plugin.strtitle).replace("{PLAYER}", give.getName()), (Player) give);
											sendMessages(getConfig("messages", "bookgive"), p);
										}
									} catch (Exception e) { e.getStackTrace(); }
								}
							} else { sendMessages(getConfig("messages", "nopermission"), p); }
						}
					} catch (Exception e) {sendMessages(getConfig("messages", "bookgiveusage"), p); }
				return true;
			} else if (lable.equalsIgnoreCase("book") && p.hasPermission("ymlbook.use")) { //COMMAND: /book <name>
				try {
				if (!Pconf.exists() && !Pbook.exists()) { sendMessages(getConfig("messages", "notfound"), p); }
					else {
						plugin.strtitle = getBook(args[0] + ".title");
						plugin.strauthor = getBook(args[0] + ".author");
						plugin.strtext = getBook(args[0] + ".text");
						plugin.strlore = getBook(args[0] + ".lore");
							
						if (p.hasPermission("ymlbook.book.*") || p.hasPermission("ymlbook.book." + args[0])) {
							try	{
								setPages(plugin.book, plugin.strtext, plugin.strtitle, plugin.strauthor, plugin.strlore);
								p.getInventory().addItem(plugin.book);
							} catch (Exception e) { e.getStackTrace(); }
						} else { sendMessages(getConfig("messages", "nopermission"), p); }
					}
				} catch (Exception e) { sendMessages(getConfig("messages", "usage"), p); }
			  return true;
			} else { sendMessages(getConfig("messages", "nopermission"), p); }
		return true;
		}

		// CONSOLE		
		else if (sender instanceof ConsoleCommandSender) {
			if (lable.equalsIgnoreCase("bookreload")) { //COMMAND: /bookreload
				try {
					plugin.getConfig().load(Pconf);
					plugin.getConfig().load(Pbook);
					plugin.log.info(getConfig("messages", "reloaded"));
				} catch (Exception e) { plugin.log.info("Can't reload config. Check output, http://yaml-online-parser.appspot.com/");}
				return true;
			} else if (lable.equalsIgnoreCase("bookgive")) { //COMMAND: /bookgive <player> <name>
				try {
					if (!Pconf.exists() && !Pbook.exists()) { plugin.log.info(getConfig("messages", "notfound")); }
					else {
						plugin.strtitle = getBook(args[1] + ".title");
						plugin.strauthor = getBook(args[1] + ".author");
						plugin.strtext = getBook(args[1] + ".text");
						plugin.strlore = getBook(args[1] + ".lore");
						try {
							Player give = (Bukkit.getServer().getPlayer(args[0]));
							if (give.isOnline()) {
								setPages(plugin.book, plugin.strtext, plugin.strtitle, plugin.strauthor, plugin.strlore);
								give.getInventory().addItem(plugin.book);
								sendMessages(getConfig("messages", "getbook").replace("{BOOKNAME}", plugin.strtitle), (Player) give);
								plugin.log.info(getConfig("messages", "bookgive"));
							}
						} catch (Exception e) { e.getStackTrace(); }
					}
				} catch (Exception e) { plugin.log.info(getConfig("messages", "bookgiveusage")); }
				return true;
			}
		}
		return true;
	}
	
	public ItemStack setPages(ItemStack book, String str, String title, String author, String strlore) {
    	int fullPages = str.length() / 255;
    	int fullLore = strlore.length() / 40;
    	
    	List<String> lore = new ArrayList<String>();
    	
    	if(strlore != "false") {
	    	try {
				for (int i = 0; i < fullLore; i++) {
					lore.add(strlore.substring(40 * i, 40 * (i + 1)));
				}
			lore.add(strlore.substring(fullLore * 40, strlore.length() - 0));
	    	} catch (Exception e) { plugin.log.info(e.toString()); }
    	}
		
    	List<String> pages = new ArrayList<String>();
    		for (int i = 0; i < fullPages; i++) {
    			pages.add(str.substring(255 * i, 255 * (i + 1)));
    		}
    	pages.add(str.substring(fullPages * 255, str.length() - 1));

    	BookMeta meta = (BookMeta)book.getItemMeta();
    	meta.setPages(pages);
    	meta.setTitle(title);
    	meta.setAuthor(author);
    	if(!lore.isEmpty()) { meta.setLore(lore); }
    	ItemStack newIs = book;
    	newIs.setItemMeta(meta);
    	return newIs;
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
	      if(event.getClickedBlock().getType() == Material.SIGN || event.getClickedBlock().getType() == Material.SIGN_POST || event.getClickedBlock().getType() == Material.WALL_SIGN) {
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
								setPages(plugin.book, plugin.strtext, plugin.strtitle, plugin.strauthor, plugin.strlore);		
								p.getInventory().addItem(plugin.book);
							} catch (Exception ex) { ex.getStackTrace(); }
						} else { sendMessages( getConfig("messages", "nopermission"), p); }
					} else { sendMessages( getConfig("messages", "nopermission"), p); }
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
							setPages(plugin.book, plugin.strtext, plugin.strtitle, plugin.strauthor, plugin.strlore);
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
							setPages(plugin.book, plugin.strtext, plugin.strtitle, plugin.strauthor, plugin.strlore);
							p.getInventory().addItem(plugin.book);
						} catch (Exception ex) { ex.getStackTrace(); }
			        } catch (Exception ex) { ex.getStackTrace(); }
	    		}
	    	}
    	}
    }
	
    public String getFirstBook (String world) {
    	File path = new File(plugin.getDataFolder(), "books.yml");
		try { plugin.getConfig().load(path); }
		catch (Exception e) { e.getStackTrace(); }
		
		if(plugin.getConfig().contains("World." + world)) {
			return plugin.getConfig().getString("World." + world);
		} return "";
    }
    
	public String getBooks() {
		File path = new File(plugin.getDataFolder(), "books.yml");
		try { plugin.getConfig().load(path); }
		catch (Exception e) { e.getStackTrace(); }

		Set<String> getBooks = plugin.getConfig().getConfigurationSection("book").getKeys(false);
			
		if (getBooks != null)
			return getBooks.toString().replace("[", "").replace("]", "") + "";
		else
			return "";
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
	public String getConfig(String paths, String name)
	{
		File path = new File(plugin.getDataFolder(), "config.yml");
		try { plugin.getConfig().load(path); }
		catch (Exception e) { e.getStackTrace(); }
		
		String getMessage = plugin.getConfig().getString(paths + "." + name);
		String getDisplay = plugin.getConfig().getString(paths + "." + name + "-display");
		
		if (getDisplay == "true")
			return getMessage;
		else 
			return "";
	}
	public Boolean getDisplay(String paths, String name)
	{
		File path = new File(plugin.getDataFolder(), "config.yml");
		try { plugin.getConfig().load(path); }
		catch (Exception e) { e.getStackTrace(); }
		
		String getDisplay = plugin.getConfig().getString(paths + "." + name + "-display");
		
		if (getDisplay == "true")
			return true;
		else 
			return false;
	}
	public void sendMessages(String message, Player p)
	{
		if (message != "" && getDisplay("messages","YMLbookPrefix") == true)
			p.sendMessage( plugin.pluginname + message );
		else
			p.sendMessage( message );
	}
}
