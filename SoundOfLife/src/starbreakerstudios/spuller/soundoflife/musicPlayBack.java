package starbreakerstudios.spuller.soundoflife;

import java.util.Hashtable;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.Wool;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;
import java.util.HashMap;

public class musicPlayBack implements Listener{

	Hashtable<Player, Integer> listenID = new Hashtable<Player, Integer>();
	
	Hashtable<Integer, Integer> quickFind = new Hashtable<Integer, Integer>();
	Hashtable<String, Integer> playerAmount = new Hashtable<String, Integer>();
	HashMap<String, songData> masterSongList = new HashMap<String, songData>();
	Plugin plugin;
	
	public musicPlayBack(Plugin plugin){
   	 
   	 this.plugin = plugin;
   	Bukkit.getServer().getPluginManager().registerEvents(this, plugin);
	}
	
	public void whenEnabled(){
		
		for(Notes note: Notes.values()){
			int o = note.getPlacement();
			quickFind.put(o, note.ordinal());
		}
	}
	
	public void saveNote(Player p, int measure, int noteValue, int notePosition, String instrument, int noteLevel, String songName){
		if(masterSongList.containsKey(songName)){
			if(p.getName() == masterSongList.get(songName).getOwner()){
				
				masterSongList
				.get(songName)
				.getNoteData()
				.get(instrument)
				.get(measure)
				.get(noteLevel)
				.set(notePosition, noteValue);
			}
			else{
				p.sendMessage(ChatColor.RED + "You are not the original composer of this song.  You may not edit it.");
			}
		}
		else{
			p.sendMessage(ChatColor.RED + "Critical ERROR! #512");
		}
	}
	
	public boolean checkName(String name, Player p){
		if(!masterSongList.containsKey(name)){
			if(name != "Untitled Music Sheet"){
				p.sendMessage("Setting up: " + name);
				setupNewMusicSheet(p, name);
				return true;
			}
			else{
				return false;
			}
			
		}else{
			return false;
		}
	}
	
	public void sampleMusic(String songName, boolean allInstruments, boolean allSong, int currentMeasure, String selectedInstrument, Player p, int tempo, Sound thisSound){
		p.sendMessage("Made it");
		if( masterSongList.containsKey(songName)){
			
			if(allSong){
				if(allInstruments){
				
				}else{
					p.sendMessage("Not all instru");
					if(masterSongList.containsKey(songName)){
						BukkitTask task = new PlayMusic(this.plugin, masterSongList.get(songName).getNoteData(), selectedInstrument, allSong, allSong, tempo, selectedInstrument, p, thisSound).runTaskTimer(this.plugin, 0L, tempo);
					}
					
					
				}
			}
				
			//Sample one measure
			else{
				if(allInstruments){
					
				}
				//only play selected instrument
				else{
					BukkitTask task = new PlayMusic(this.plugin, masterSongList.get(songName).getNoteData(), selectedInstrument, allSong, allSong, tempo, selectedInstrument, p, thisSound).runTaskTimer(this.plugin, 0L, tempo);

				}	
			}
		}
		else{
			p.sendMessage(ChatColor.RED + "Song has not been saved to database.  This is bad.");
		}
	}

	
	private void setupNewMusicSheet(Player p, String requestedName){
			if(playerAmount.containsKey(p.getName())){
				if(playerAmount.get(p.getName()) == 2){
					p.sendMessage("You have written too many songs.  Please edit a previously owned song");
					
				}
				else{
					int i = playerAmount.get(p.getName());
					playerAmount.get(p.getName()).equals(i+1);
					p.sendMessage("Setting upadswasw");
					addPreSong(requestedName, p);
				}
			}
			else{
				playerAmount.put(p.getName(), 1);
				addPreSong(requestedName, p);
			}
		

	}
	//delete this eventually
	public void getInfo(Player p, String songName, int measure){
		ArrayList<Integer> li =masterSongList.get(songName).getNoteData().get("lute").get(measure).get(1);
		for(Integer in : li){
			p.sendMessage("" + in);
		}
		p.sendMessage("Row 2:");
		ArrayList<Integer> lis = masterSongList.get(songName).getNoteData().get("lute").get(measure).get(2);
		for(Integer in : lis){
			p.sendMessage("" + in);
		}
	}
	
	public ItemStack returnNote(String songName, int measure, int position, Player p, String instrument){
	
		if(position > 0 && position < 9){

			if(masterSongList.containsKey(songName)){
				int b = masterSongList
						.get(songName)
						.getNoteData().get(instrument)
						.get(measure).get(1)
						.get(position - 1);
			
				if(b==0){
					return null;
				}
				else{
					ItemStack wuul;
					
					int note = quickFind.get(b);
					Notes value = Notes.values()[note];
					p.sendMessage("Note value : " + value.name());
					wuul  = new Wool(value.getColor()).toItemStack(1);
					p.sendMessage(ChatColor.GRAY + "Progress Confirm [109]");
					ItemMeta imm = wuul.getItemMeta();
					imm.setDisplayName(value.getName());
					ArrayList<String> lore = new ArrayList<String>();
					lore.add("Right-Click note to select and hear,");
					lore.add("then Right-Click location in music.");
					imm.setLore(lore);
					wuul.setItemMeta(imm);
					return wuul;
				}
				
				
			}
			else{
				p.sendMessage(ChatColor.MAGIC + "Poop something went horribly wrong!!");
			}
			
			
			
			
		}else if(position > 9 && position < 18){
			if(masterSongList.containsKey(songName)){
				int b = masterSongList
						.get(songName)
						.getNoteData().get(instrument)
						.get(measure).get(2)
						.get(position - 10);
				if(b==0){
					
					return null;
				}
				else{
					ItemStack wuul;

					int note = quickFind.get(b);
					Notes value = Notes.values()[note];
					wuul  = new Wool(value.getColor()).toItemStack(1);
					p.sendMessage(ChatColor.GRAY + "Progress Confirm [109]");
					ItemMeta imm = wuul.getItemMeta();
					imm.setDisplayName(value.getName());
					ArrayList<String> lore = new ArrayList<String>();
					lore.add("Right-Click note to select and hear,");
					lore.add("then Right-Click location in music.");
					imm.setLore(lore);
					wuul.setItemMeta(imm);
					return wuul;
				}
			}
		}
		p.sendMessage("Null HitS");
		return null;
		
	}

	private void addPreSong(String requestedName, Player p){
		//add new song to masterList 
		//also add owner and measure number
		HashMap<String, HashMap<Integer, HashMap<Integer, ArrayList<Integer>>>> newNoteData = new HashMap<String, HashMap<Integer, HashMap<Integer, ArrayList<Integer>>>>();
		songData nSD = new songData(32, p.getName(), newNoteData, 80);
		p.sendMessage(requestedName);
		
		masterSongList.put(requestedName,nSD);
		
		//now set all instrument measures to 000000000000000000000
		for(int i = 0; i < 6; i++){
		
			String instru = "";
			if(i == 0) instru = "lute";
			else if (i == 1) instru = "cello";
			else if (i == 2) instru = "maracas";
			else if (i == 3) instru = "dulcimer";
			else if (i == 4) instru = "claves";
			else if (i == 5) instru = "drum";
			
			HashMap<Integer, HashMap<Integer, ArrayList<Integer>>> measure = new HashMap<Integer, HashMap<Integer, ArrayList<Integer>>>();
			for(int b = 1; b < 33; b++){
				ArrayList<Integer> noteIndex = new ArrayList<Integer>();
				ArrayList<Integer> noteIndex2 = new ArrayList<Integer>();
				for(int a = 0; a < 8; a++){
					noteIndex.add(a, 0);
					noteIndex2.add(a, 0);
				}
				HashMap<Integer, ArrayList<Integer>> prelimHash = new HashMap<Integer, ArrayList<Integer>>();
				prelimHash.put(1, noteIndex);
				prelimHash.put(2, noteIndex2);
				measure.put(b, prelimHash);
			}
			
			
			masterSongList.get(requestedName).getNoteData().put(instru, measure);
		}
	}
	
}

