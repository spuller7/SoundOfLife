package starbreakerstudios.spuller.soundoflife;

import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.plugin.Plugin;

public class craftingRecipe implements Listener {

       
       HashMap<Player, Integer> schedual = new HashMap<Player, Integer>();
       HashMap<Player, Recipe> inInventory = new HashMap<Player, Recipe>();
       HashMap<Player, List<Recipe>> playerRecipes = new HashMap<Player, List<Recipe>>();

       
  
   	public void whenEnabled() {
  
   	}
   	public void whenDisabled() {
   		for(Player p : inInventory.keySet()) {
   			p.getOpenInventory().getTopInventory().clear();
   			p.closeInventory();
   		}
   		inInventory.clear();
   		for(Player p : playerRecipes.keySet()) {
   			p.getOpenInventory().getTopInventory().clear();
   			p.closeInventory();
   		}
   		playerRecipes.clear();
   	}
       
       public craftingRecipe(Plugin p) {
    	 Bukkit.getServer().getPluginManager().registerEvents(this, p);
       }
    
       @EventHandler
   	public void invOpen(final InventoryOpenEvent e) {
   		if(!inInventory.containsKey(e.getPlayer()) && !playerRecipes.containsKey(e.getPlayer())) return;
   		Recipe r = inInventory.get(e.getPlayer());
   		if(r == null) {
   			e.getPlayer().sendMessage("Error: Reconfigure Source");
   		} else showInventoryRecipe(r, e.getView());
   	}
       
       
       public boolean showVisualRecipe(Player p, Recipe r) {
    	   p.sendMessage("Made it");
   		if(r instanceof ShapedRecipe) {
   			inInventory.put(p, r);
   			//thus this triggers invOpen event
   			p.openWorkbench(null, true);
   			return true;
   		}
   		return false;
       }
       
       private boolean showInventoryRecipe(Recipe r, InventoryView i) {
   		i.getTopInventory().clear();
   		ItemStack item;
   		if(r instanceof ShapedRecipe) {
   			ShapedRecipe sr = (ShapedRecipe) r;
   			int x, y = recipeHeight(sr) == 3?0:1;
   			for(String s : sr.getShape()) {
   				x = recipeWidth(sr) == 3? 0:1;
   				for(Character c: s.toCharArray()) {
   					item = sr.getIngredientMap().get(c);
   					if(item!=null) {
   						item = item.clone();
   						if(item.getDurability() == -1) item.setDurability((short) 0);
   					}
   					i.getTopInventory().setItem(x++ + (y)*3 + 1, item);
   				}
   				y++;
   			}
   		} else {
   			return false;
   		}
   	
   		return true;
   	}
       
   	public int recipeWidth(ShapedRecipe sr) {
		int ret = 0;
		for(String s: sr.getShape()) ret = Math.max(ret, s.length());
		return ret;
	}
	public int recipeHeight(ShapedRecipe sr) {
		return sr.getShape().length;
	}

       @EventHandler
   	public void invClose(InventoryCloseEvent e) {
   		if(inInventory.containsKey(e.getPlayer()) || playerRecipes.containsKey(e.getPlayer())) e.getInventory().clear();
   		inInventory.remove(e.getPlayer());
   		playerRecipes.remove(e.getPlayer());
   		if(schedual.containsKey(e.getPlayer())) {
   			Bukkit.getScheduler().cancelTask(schedual.get(e.getPlayer()));
   			schedual.remove(e.getPlayer());
   		}
   	}
       @EventHandler (priority = EventPriority.HIGHEST)
   	public void invClick(InventoryClickEvent e) {
   		if(!(e.getWhoClicked() instanceof Player)) return;
   		if(inInventory.containsKey((Player)e.getWhoClicked())) e.setCancelled(true);
   		if(playerRecipes.containsKey((Player)e.getWhoClicked())) e.setCancelled(true);
   	}
}


