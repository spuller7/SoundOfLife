package starbreakerstudios.spuller.soundoflife;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.Material;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.ChatColor;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.Recipe;


import org.bukkit.event.Listener;


public class sol_Master extends JavaPlugin implements Listener{

	//Version
	private String version = "1.2";
	
	//Recipe variables for instruments you can make
	 private ShapedRecipe Lute;
     private ShapedRecipe BlankMusicSheet;
     private ShapedRecipe Drum;
     private ShapedRecipe Cello;
     private ShapedRecipe Maracas;
     private ShapedRecipe Claves;
     private ShapedRecipe Dulcimer;

     
     HashMap<Player, List<Recipe>> playerRecipes = new HashMap<Player, List<Recipe>>();
     HashMap<Player, Integer> schedual = new HashMap<Player, Integer>();
	
	private Menu menu;
	private craftingRecipe crafting;
	private musicPlayBack mpb;
	

	public void onEnable(){
		
		menu = new Menu(this);
		getServer().getPluginManager().registerEvents(this, this);
	
		crafting = new craftingRecipe(this);
		getServer().getPluginManager().registerEvents(this, this);
        
		mpb = new musicPlayBack(this);
		getServer().getPluginManager().registerEvents(this, this);
		mpb.whenEnabled();
		
		crafting.whenEnabled();
		
		//Section to craft a blank music sheet
        ItemStack blankSheet = new ItemStack(Material.MAP, 1);
        ItemMeta bs = blankSheet.getItemMeta();
        bs.setDisplayName (ChatColor.GOLD + "Untitled Music Sheet");
        ArrayList<String> tt = new ArrayList<String>();
        tt.add(ChatColor.GRAY + "Sound of Life");
        bs.setLore(tt);
        blankSheet.setItemMeta(bs);
        //Player will get a blank sheet for music sheet
        BlankMusicSheet = new ShapedRecipe (new ItemStack(blankSheet));
        //crafting formula for blank music sheet
        BlankMusicSheet.shape("*#*","*%*","*#*").setIngredient('*', Material.STRING).setIngredient('#', Material.INK_SACK).setIngredient('%', Material.BOOK_AND_QUILL);
        //add the recipe
        Bukkit.getServer().addRecipe(BlankMusicSheet);
       
        
        //Crafting recipe in order to make a drum
        ItemStack drum = new ItemStack(Material.PISTON_BASE, 1);
        ItemMeta dr = drum.getItemMeta();
        dr.setDisplayName (ChatColor.GOLD + "Drum");
        drum.setItemMeta(dr);
        Drum = new ShapedRecipe (new ItemStack(drum));
        Drum.shape("   ","***","#^#").setIngredient('*', Material.LEATHER).setIngredient('#', Material.LOG).setIngredient('^', Material.WOOD_PLATE);
        Bukkit.getServer().addRecipe(Drum);
       
        //Crafting Recipe in order to craft a lute
        ItemStack lute = new ItemStack(Material.BOW, 1);
        ItemMeta lu = lute.getItemMeta();
        lu.setDisplayName (ChatColor.GOLD + "Lute");
        lute.setItemMeta(lu);
        Lute = new ShapedRecipe (new ItemStack(lute));
        Lute.shape("*# "," %*","*# ").setIngredient('*', Material.STICK).setIngredient('#', Material.STRING).setIngredient('%', Material.FEATHER);
        Bukkit.getServer().addRecipe(Lute);
       
        //Crafting recipe in order to craft a cello
        ItemStack cello = new ItemStack(Material.NOTE_BLOCK, 1);
        //Add description 
        ItemMeta ce = cello.getItemMeta();
        ce.setDisplayName (ChatColor.GOLD + "Cello");
        cello.setItemMeta(ce);
        //Recipe
        Cello = new ShapedRecipe (new ItemStack(cello));
        Cello.shape("*#*","*#*","o#o").setIngredient('*', Material.STICK).setIngredient('#', Material.STRING).setIngredient('o', Material.LOG);
        Bukkit.getServer().addRecipe(Cello);
       
        //Crafting recipe in order to craft a ....?
        
        ItemStack claves = new ItemStack(Material.TIPPED_ARROW, 1);
        ItemMeta cl = claves.getItemMeta();
        cl.setDisplayName (ChatColor.GOLD + "Claves");
        claves.setItemMeta(cl);
        Claves = new ShapedRecipe (new ItemStack(claves));
        Claves.shape("* *","# #","* *").setIngredient('*', Material.LOG).setIngredient('#', Material.STICK);
        Bukkit.getServer().addRecipe(Claves);
        
        ItemStack dulcimer = new ItemStack(Material.NOTE_BLOCK, 1);
        ItemMeta du = dulcimer.getItemMeta();
        du.setDisplayName (ChatColor.GOLD + "Dulcimer");
        dulcimer.setItemMeta(du);
        Dulcimer = new ShapedRecipe (new ItemStack(dulcimer));
        Dulcimer.shape("*^#","^#^","#^*").setIngredient('#', Material.LOG).setIngredient('*', Material.STICK).setIngredient('^', Material.STRING);
        Bukkit.getServer().addRecipe(Dulcimer);
       
        ItemStack mara = new ItemStack(Material.REDSTONE_TORCH_ON, 1);
        ItemMeta ma = mara.getItemMeta();
        ma.setDisplayName (ChatColor.GOLD + "Maracas");
        mara.setItemMeta(ma);
        Maracas = new ShapedRecipe (new ItemStack(mara));
        Maracas.shape("# #","* *","* *").setIngredient('*', Material.STICK).setIngredient('#', Material.REDSTONE);
        Bukkit.getServer().addRecipe(Maracas);
        Logger log = Bukkit.getLogger();
        log.info("WORKING RECIPES");
		
    	menu.whenEnabled(drum, cello, mara, lute, dulcimer, claves, mpb);
	}
	
	@Override
	public void onDisable(){
		crafting.whenDisabled();
		menu.whenDsiabled();
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public boolean onCommand(CommandSender sender,Command cmd,String label,String[] args) {
	
		//The player variable to whom is performing the command
		final Player p = (Player) sender;
				
		//When Player enters /music in console
		if(cmd.getName().equalsIgnoreCase("music")){   
			//When there are no arguments after music
			if(args.length == 0){
				p.sendMessage(ChatColor.WHITE + "=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=");
				p.sendMessage(ChatColor.GOLD + "Sound Of Life : Version " + version + "");
				p.sendMessage(ChatColor.DARK_GREEN + "Commands:");
				p.sendMessage(ChatColor.GREEN + "/Music: "+ ChatColor.RED +"[Crafting] [Create] [Practice] [Perform] [Listen]");
				p.sendMessage(ChatColor.WHITE + "=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=");
				return true;
			}
			else if(args.length >= 1){
//----Crafting--------------------------------------------------------------------------------------------------
				if(args[0].equalsIgnoreCase("Crafting")){
					if(args.length == 2){
						if(args[1].equalsIgnoreCase("Lute") || args[1].equalsIgnoreCase("Drum") || args[1].equalsIgnoreCase("Cello") || args[1].equalsIgnoreCase("Maracas") || args[1].equalsIgnoreCase("MusicSheet") || args[1].equalsIgnoreCase("Claves") || args[1].equalsIgnoreCase("Dulcimer")){
							displayRecipe(args[1].toString(), p);
						}
						else{
							p.sendMessage(ChatColor.WHITE + "=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=");
							p.sendMessage(ChatColor.GOLD + "Sound Of Life : Version " + version + "");
							p.sendMessage(ChatColor.DARK_GREEN + "Commands:");
							p.sendMessage(ChatColor.GREEN + "/Music Crafting: " + ChatColor.RED +"[Cello] [Claves] [Drum] [Dulcimer] [Lute] [Maracas] [MusicSheet]");
							p.sendMessage(ChatColor.WHITE + "=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=");
						}
					}
					else{
						p.sendMessage(ChatColor.WHITE + "=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=");
						p.sendMessage(ChatColor.GOLD + "Sound Of Life : Version " + version + "");
						p.sendMessage(ChatColor.DARK_GREEN + "Commands:");
						p.sendMessage(ChatColor.GREEN + "/Music Crafting: " + ChatColor.RED +"[Cello] [Claves] [Drum] [Dulcimer] [Lute] [Maracas] [MusicSheet]");						
						p.sendMessage(ChatColor.WHITE + "=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=");
					}
					return true;
				}
//----Compose--------------------------------------------------------------------------------------------------
				else if(args[0].equalsIgnoreCase("Create")){
					if(p.getInventory().getItemInHand() != null){
						if(p.getInventory().getItemInHand().getType().equals(Material.MAP)){
							if(p.getInventory().getItemInHand().hasItemMeta()){
								if(p.getInventory().getItemInHand().getItemMeta().getLore().get(0).contains("Sound of Life")){
									
									if(p.getInventory().getItemInHand().getItemMeta().getDisplayName().equals(ChatColor.GOLD + "Untitled Music Sheet")){
										if(args.length == 1){
											p.sendMessage(ChatColor.AQUA + "To name your song, type: /music create " + ChatColor.WHITE + "[Song Name]");
										}
										if(args.length > 1){
											String thefinalstring = "";
											for (int i = 1; i < args.length; i++) {
												if(i == args.length - 1){
													thefinalstring += args[i];
												}else{
													thefinalstring += args[i] + ' ';
												}
											    
											}
											
											boolean check = mpb.checkName(thefinalstring, p);
											p.sendMessage(ChatColor.AQUA + "" + thefinalstring);
											if(check){
												ItemMeta im = p.getInventory().getItemInHand().getItemMeta();
												im.setDisplayName(thefinalstring);
												p.getInventory().getItemInHand().setItemMeta(im);
												p.sendMessage(ChatColor.AQUA + "" + thefinalstring + "" + ChatColor.GOLD +" has been created!");
											}
											else{
												p.sendMessage(ChatColor.AQUA + "Song title has been taken.");
											}
										}
									}else{p.sendMessage(ChatColor.RED + "This song has already been registered.");}
								}else{p.sendMessage(ChatColor.RED + "You must hold a blank music sheet to name it.");}
							}else{p.sendMessage(ChatColor.RED + "You must hold a blank music sheet to name it.");}
						}else{p.sendMessage(ChatColor.RED + "You  must hold a blank music sheet to name it.");}
					}else{p.sendMessage(ChatColor.RED + "You must hold a blank music sheet to name it.");}
					return true;
				}
//----Practice--------------------------------------------------------------------------------------------------
			else if(args[0].equalsIgnoreCase("Practice")){
				String b = p.getInventory().getItemInHand().getItemMeta().getDisplayName();
				mpb.getInfo(p, b, Integer.parseInt(args[1]));
			}
//----Perform--------------------------------------------------------------------------------------------------
			else if(args[0].equalsIgnoreCase("Perform")){
				
			}
//----Listen--------------------------------------------------------------------------------------------------
			else if(args[0].equalsIgnoreCase("Listen")){
				
			}
				
			}
		
		}
		p.sendMessage(ChatColor.WHITE + "=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=");
		p.sendMessage(ChatColor.GOLD + "Sound Of Life : Version " + version + "");
		p.sendMessage(ChatColor.DARK_GREEN + "Commands:");
		p.sendMessage(ChatColor.GREEN + "/Music: "+ ChatColor.RED +"[Crafting] [Create] [Practice] [Perform] [Listen]");
		p.sendMessage(ChatColor.WHITE + "=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=");
		return false;
	}
//----Crafting Handler-----------------------------------------------------------------------------------------	
	private boolean displayRecipe(String item, Player p){
		if(item.equalsIgnoreCase("lute")){
		
				crafting.showVisualRecipe(p, Lute);

			
		}else if (item.equalsIgnoreCase("cello")){
			crafting.showVisualRecipe(p, Cello);
		}else if (item.equalsIgnoreCase("MusicSheet")){
			crafting.showVisualRecipe(p, BlankMusicSheet);
		}else if (item.equalsIgnoreCase("Maracas")){
			crafting.showVisualRecipe(p, Maracas);
		}else if (item.equalsIgnoreCase("Drum")){
			crafting.showVisualRecipe(p, Drum);
		}else if (item.equalsIgnoreCase("claves")){
			crafting.showVisualRecipe(p, Claves);
		}else if (item.equalsIgnoreCase("Dulcimer")){
			crafting.showVisualRecipe(p, Dulcimer);
		}
	
		else{
			p.sendMessage("No other");
		}
		
		
		return true;
	}
	
//----Compose Handler-----------------------------------------------------------------------------------------	
	
}
	
	

	
	

