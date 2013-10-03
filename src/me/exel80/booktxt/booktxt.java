package me.exel80.booktxt;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;



import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Sign;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.plugin.java.JavaPlugin;

public class booktxt extends JavaPlugin implements Listener {
	public static booktxt plugin;
	public Logger log = Logger.getLogger("Minecraft");
	String pluginname = ChatColor.RED + "[YMLbook] " + ChatColor.WHITE;	
	public ItemStack book = new ItemStack(387, 1);
	String strargs, strtitle, strauthor, strtext, strlore;

	@Override
	public void onDisable() {
		
	}
	@Override
	public void onEnable() {	
		try {
			getServer().getPluginManager().registerEvents(this, this);
		    Metrics metrics = new Metrics(this);
		    metrics.start();
		} catch (IOException e) {
		    // Failed to submit the stats :-(
		}
	}
		
	public boolean onCommand(CommandSender sender, Command cmd, String lable, String[] args) {
		Player p = (Player) sender;	
		File Pconf = new File(this.getDataFolder(), "config.yml");
		File Pbook = new File(this.getDataFolder(), "books.yml");
		
		if (lable.equalsIgnoreCase("bookreload") && p.hasPermission("ymlbook.reload")) { //COMMAND: /bookreload
			try {
				this.getConfig().load(Pconf);
				this.getConfig().load(Pbook);
				sendMessages(getConfig("messages", "reloaded"), p);
			}
			catch (Exception e) { if(p.isOp()){sendMessages("Can't reload config. Check output, http://yaml-online-parser.appspot.com/", p);} }
			return true;
		}/*  else if (lable.equalsIgnoreCase("booklist") && p.hasPermission("ymlbook.booklist")) { //COMMAND: /booklist
			try {
				if (!Pconf.exists() && !Pbook.exists()) { sendMessages(getConfig("messages", "notfound"), p); }
					else 
					{
						sendMessages( getConfig("messages", "booklist"), p);
						
						String getMessage = this.getConfig().getString("book");
						p.sendMessage(getMessage);
						String getMessage2 = this.getConfig().getName().toString();
						p.sendMessage(getMessage2);
					}
				}
				catch (Exception e) { sendMessages(getConfig("messages", "bookgiveusage"), p); }
				return true;
		} else if (lable.equalsIgnoreCase("bookcopy") && p.hasPermission("ymlbook.bookcopy")) { //COMMAND: /bookcopy <name>
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
					else 
					{
						strtitle = getBook(args[1] + ".title");
						strauthor = getBook(args[1] + ".author");
						strtext = getBook(args[1] + ".text");
						strlore = getBook(args[1] + ".lore");
						
						if (p.hasPermission("ymlbook.book.*") || p.hasPermission("ymlbook.book." + args[0]))
						{
							if (p.hasPermission("ymlbook.bookgive"))
							{
								try 
								{
										Player give = (Bukkit.getServer().getPlayer(args[0]));
										if (give.isOnline())
										{
											setPages(book, strtext, strtitle, strauthor, strlore);		
											
											give.getInventory().addItem(book);
											
											sendMessages(getConfig("messages", "getbook"), (Player) give);
											sendMessages(getConfig("messages", "bookgive"), p);
										}
								}
								catch (Exception e) { e.getStackTrace(); }
							}
						}
						else { sendMessages(getConfig("messages", "nopermission"), p); }
					}
				}
				catch (Exception e) { sendMessages(getConfig("messages", "bookgiveusage"), p); }
				return true;
		} else if (lable.equalsIgnoreCase("book") && p.hasPermission("ymlbook.use")) { //COMMAND: /book <name>
			try {
			if (!Pconf.exists() && !Pbook.exists()) { sendMessages(getConfig("messages", "notfound"), p); }
				else 
				{
					strtitle = getBook(args[0] + ".title");
					strauthor = getBook(args[0] + ".author");
					strtext = getBook(args[0] + ".text");
					strlore = getBook(args[0] + ".lore");
					
					if (p.hasPermission("ymlbook.book.*") || p.hasPermission("ymlbook.book." + args[0]))
					{
						try 
						{							
							setPages(book, strtext, strtitle, strauthor, strlore);		
								
							p.getInventory().addItem(book);
						}
						catch (Exception e) { e.getStackTrace(); }
					}
					else { sendMessages( getConfig("messages", "nopermission"), p); }
				}
			}
			catch (Exception e) { sendMessages(getConfig("messages", "usage"), p); }
			return true;
		} else { sendMessages(getConfig("messages", "nopermission"), p); }
	return true;
	}
	
    public ItemStack setPages(ItemStack book, String str, String title, String author, String strlore){
    	int fullPages = str.length() / 255;
    	int fullLore = strlore.length() / 40;
    	
    	List<String> lore = new ArrayList<String>();
    	
    	if(strlore != "false")
    	{
	    	try
	    	{
				for (int i = 0; i < fullLore; i++){
					lore.add(strlore.substring(40 * i, 40 * (i + 1)));
				}
			lore.add(strlore.substring(fullLore * 40, strlore.length() - 0));
	    	}
	    	catch (Exception e) { log.info(e.toString()); }
    	}
		
    	List<String> pages = new ArrayList<String>();
    		for (int i = 0; i < fullPages; i++){
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
	
    public void sendMessages(String message, Player p)
    {    	
    	if(message != "")
    	{    		
    		p.sendMessage( pluginname + message );
    	}
    }
    
    @EventHandler(priority = EventPriority.HIGH)
    public void SignRead(SignChangeEvent event) {
        if (event.getLine(0).equalsIgnoreCase("book") || event.getLine(0).equalsIgnoreCase("[Book]")) {
        	if (!event.getLine(1).isEmpty() && event.getPlayer().hasPermission("ymlbook.signbook"))
        	{
        		event.setLine(0, ChatColor.GREEN + "[Book]");
        		event.setLine(2, "---------------");
        		event.setLine(3, "Right-click me!");
        	}
        	if (!event.getLine(1).isEmpty() && event.getPlayer().hasPermission("ymlbook.signbookkit"))
        	{
        		event.setLine(0, ChatColor.GREEN + "[Book]");
        		event.setLine(2, "---------------");
        		event.setLine(3, "Right-click me!");
        	}
        	else { event.setLine(0, ""); event.setLine(1, ""); event.setLine(2, ""); event.setLine(3, ""); }
        }
    }
    @EventHandler
    public void SignHook(PlayerInteractEvent e) {
      if(e.getAction() == Action.RIGHT_CLICK_BLOCK) {
	      if(e.getClickedBlock().getType() == Material.SIGN || e.getClickedBlock().getType() == Material.SIGN_POST || e.getClickedBlock().getType() == Material.WALL_SIGN) {
	        try
	        {
		    	Sign sign = (Sign) e.getClickedBlock().getState();
			    if(sign.getLine(0).contains("[Book]") && !sign.getLine(1).isEmpty()) {
			        strtitle = getBook(sign.getLine(1) + ".title");
					strauthor = getBook(sign.getLine(1) + ".author");
					strtext = getBook(sign.getLine(1) + ".text");
					strlore = getBook(sign.getLine(1) + ".lore");
					
					Player p = e.getPlayer();
					if(p.hasPermission("ymlbook.use"))
					{
						if (p.hasPermission("ymlbook.book.*") || p.hasPermission("ymlbook.book." + sign.getLine(1)))
						{
							try 
							{							
								setPages(book, strtext, strtitle, strauthor, strlore);		
								p.getInventory().addItem(book);
							}
							catch (Exception ex) { ex.getStackTrace(); }
						}
						else { sendMessages( getConfig("messages", "nopermission"), p); }
					}
					else { sendMessages( getConfig("messages", "nopermission"), p); }
			    } else {}
	        } catch (Exception ex) { ex.getStackTrace(); }
	      } else {}
      } else{}
    }
 
    public String getBook(String name)
	{
		File path = new File(getDataFolder(), "books.yml");
		try {
			this.getConfig().load(path);
		} catch (Exception e) { e.getStackTrace(); }
		
		String getMessage = this.getConfig().getString("book." + name);
		
		if (getMessage != null)
		{
			String usages = getMessage;
			return usages;
		}
		else { return ""; }
	}   
	public String getConfig(String paths, String name)
	{
		File path = new File(getDataFolder(), "config.yml");
		try {
			this.getConfig().load(path);
		} catch (Exception e) { e.getStackTrace(); }
		
		String getMessage = this.getConfig().getString(paths + "." + name);
		String getDisplay = this.getConfig().getString(paths + "." + name + "-display");
		
		if (getDisplay == "true")
		{
			String usages = getMessage;
			return usages;
		}
		else { return ""; }
	}
}
