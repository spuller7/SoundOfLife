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

import java.util.ArrayList;
import java.util.HashMap;

public class musicPlayBack implements Listener{

	Hashtable<Player, Integer> songSpot = new Hashtable<Player, Integer>();
	
	Hashtable<Integer, Float> quickPitch = new Hashtable<Integer, Float>();
	Hashtable<Integer, Integer> quickFind = new Hashtable<Integer, Integer>();
	Hashtable<String, Integer> playerAmount = new Hashtable<String, Integer>();
	HashMap<String, songData> masterSongList = new HashMap<String, songData>();
	Plugin mainPlugin;
	
	public musicPlayBack(Plugin p){
   	 Bukkit.getServer().getPluginManager().registerEvents(this, p);
   	 mainPlugin = p;
	}
	
	public void whenEnabled(){
		for(Notes note: Notes.values()){
			int o = note.getPlacement();
			float f = note.getPitch();
			quickPitch.put(o, f);
		}
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
	
	@SuppressWarnings("deprecation")
	public void sampleMusic(String songName, boolean allInstruments, boolean allSong, int currentMeasure, String selectedInstrument, Player p){
		p.sendMessage("Made it");
		if( masterSongList.containsKey(songName)){
			if(allSong){
				if(allInstruments){
					p.sendMessage("Yes all instru");
					ArrayList<Integer> luteList = new ArrayList<Integer>();
					ArrayList<Integer> luteList1 = new ArrayList<Integer>();
					
					ArrayList<Integer> celloList = new ArrayList<Integer>();
					ArrayList<Integer> celloList1 = new ArrayList<Integer>();

					ArrayList<Integer> clavesList = new ArrayList<Integer>();
					ArrayList<Integer> clavesList1 = new ArrayList<Integer>();

					ArrayList<Integer> dulcimerList = new ArrayList<Integer>();
					ArrayList<Integer> dulcimerList1 = new ArrayList<Integer>();

					ArrayList<Integer> drumList = new ArrayList<Integer>();
					ArrayList<Integer> drumList1 = new ArrayList<Integer>();

					ArrayList<Integer> maracasList = new ArrayList<Integer>();
					ArrayList<Integer> maracasList1 = new ArrayList<Integer>();

					for(int i = 0; i < 32; i++){
						for(int a = 0; a < 8; a++){
							int b = masterSongList.get(songName).getNoteData().get("lute").get(i).get(1).get(a);
							luteList.add(b);
							int c = masterSongList.get(songName).getNoteData().get("lute").get(i).get(2).get(a);
							luteList1.add(c);
							
							int d = masterSongList.get(songName).getNoteData().get("cello").get(i).get(1).get(a);
							celloList.add(d);
							int e = masterSongList.get(songName).getNoteData().get("cello").get(i).get(2).get(a);
							celloList1.add(e);
							
							int f = masterSongList.get(songName).getNoteData().get("claves").get(i).get(1).get(a);
							clavesList.add(f);
							int g = masterSongList.get(songName).getNoteData().get("claves").get(i).get(2).get(a);
							clavesList1.add(g);
							
							int h = masterSongList.get(songName).getNoteData().get("dulcimer").get(i).get(1).get(a);
							dulcimerList.add(h);
							int j = masterSongList.get(songName).getNoteData().get("dulcimer").get(i).get(2).get(a);
							dulcimerList1.add(j);
							
							int k = masterSongList.get(songName).getNoteData().get("drum").get(i).get(1).get(a);
							drumList.add(k);
							int l = masterSongList.get(songName).getNoteData().get("drum").get(i).get(2).get(a);
							drumList1.add(l);
							
							int m = masterSongList.get(songName).getNoteData().get("maracas").get(i).get(1).get(a);
							maracasList.add(m);
							int n = masterSongList.get(songName).getNoteData().get("maracas").get(i).get(2).get(a);
							maracasList1.add(n);
							
						}
					}
					for(int k = 0; k < 8 * masterSongList.get(songName).getMeasures(); k++){
						p.playSound(p.getLocation(), Sound.BLOCK_NOTE_HARP, 1.0f, quickPitch.get(luteList.get(k)));
						p.playSound(p.getLocation(), Sound.BLOCK_NOTE_HARP, 1.0f, quickPitch.get(luteList1.get(k)));
						p.playSound(p.getLocation(), Sound.BLOCK_NOTE_HARP, 1.0f, quickPitch.get(celloList.get(k)));
						p.playSound(p.getLocation(), Sound.BLOCK_NOTE_HARP, 1.0f, quickPitch.get(celloList1.get(k)));
						p.playSound(p.getLocation(), Sound.BLOCK_NOTE_HARP, 1.0f, quickPitch.get(clavesList.get(k)));
						p.playSound(p.getLocation(), Sound.BLOCK_NOTE_HARP, 1.0f, quickPitch.get(clavesList1.get(k)));
						p.playSound(p.getLocation(), Sound.BLOCK_NOTE_HARP, 1.0f, quickPitch.get(dulcimerList.get(k)));
						p.playSound(p.getLocation(), Sound.BLOCK_NOTE_HARP, 1.0f, quickPitch.get(dulcimerList1.get(k)));
						p.playSound(p.getLocation(), Sound.BLOCK_NOTE_HARP, 1.0f, quickPitch.get(drumList.get(k)));
						p.playSound(p.getLocation(), Sound.BLOCK_NOTE_HARP, 1.0f, quickPitch.get(drumList1.get(k)));
						p.playSound(p.getLocation(), Sound.BLOCK_NOTE_HARP, 1.0f, quickPitch.get(maracasList.get(k)));
						p.playSound(p.getLocation(), Sound.BLOCK_NOTE_HARP, 1.0f, quickPitch.get(maracasList1.get(k)));
					}
				
				}else{
					p.sendMessage("Not all instru");
					ArrayList<Integer> instrumentList = new ArrayList<Integer>();
					ArrayList<Integer> instrumentList1 = new ArrayList<Integer>();
					p.sendMessage("Process Check 11");
					for(int i = 0; i < 32; i++){
						for(int a = 0; a < 8; a++){
							int b = masterSongList
									.get(songName)
									.getNoteData()
									.get(selectedInstrument)
									.get(i + 1)
									.get(1)
									.get(a);
							instrumentList.add(b);
							int c = masterSongList.get(songName).getNoteData().get(selectedInstrument).get(i + 1).get(2).get(a);
							instrumentList1.add(c);
						}
					}
					//This may cause an error because there it starts at 0
		
					songSpot.put(p, 0);
					mainPlugin.getServer().getScheduler().scheduleAsyncRepeatingTask(mainPlugin, new BukkitRunnable(){
						
						
						@Override
						public void run(){
							int max = 8 * masterSongList.get(songName).getMeasures() - 1;
							int current = songSpot.get(p);
								if(current > max){
									cancel();
								}
								if(instrumentList.get(current) != 0){
									p.playSound(p.getLocation(), Sound.BLOCK_NOTE_HARP, 1.0f, quickPitch.get(instrumentList.get(current)));
								}
								if(instrumentList1.get(current) != 0){
									p.playSound(p.getLocation(), Sound.BLOCK_NOTE_HARP, 1.0f, quickPitch.get(instrumentList1.get(current)));
								}
							
								
								songSpot.replace(p, songSpot.get(p)+1);
								p.sendMessage(songSpot.get(p) + "!");
							
						}
					}, 0L, 20L);
					
						        	
									
						        
						   
						 
						
					
				}
				
			//Sample one measure
			}else{
				p.sendMessage("NO");
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
			ArrayList<Integer> noteIndex = new ArrayList<Integer>();
			ArrayList<Integer> noteIndex2 = new ArrayList<Integer>();
		
			for(int a = 0; a < 8; a++){
				noteIndex.add(a, 0);
				noteIndex2.add(a, 0);
			}
			
			HashMap<Integer, ArrayList<Integer>> prelimHash = new HashMap<Integer, ArrayList<Integer>>();
			prelimHash.put(1, noteIndex);
			prelimHash.put(2, noteIndex2);
			HashMap<Integer, HashMap<Integer, ArrayList<Integer>>> measure = new HashMap<Integer, HashMap<Integer, ArrayList<Integer>>>();
			for(int b = 1; b < 33; b++){
				measure.put(b, prelimHash);
			}
			
			masterSongList.get(requestedName).getNoteData().put(instru, measure);
		}
	}
	
}

class songData{
	private HashMap<String, HashMap<Integer, HashMap<Integer, ArrayList<Integer>>>> noteData = new HashMap<String, HashMap<Integer, HashMap<Integer, ArrayList<Integer>>>>();
	private String playerOwner;
	private int measureCount;
	private int tempo;
	
	public songData(int measureCount,String playerOwner, HashMap<String, HashMap<Integer, HashMap<Integer, ArrayList<Integer>>>> noteData, int tempo){
        this.noteData = noteData;
        this.playerOwner = playerOwner;
        this.measureCount = measureCount;
        this.tempo = tempo;
    }
	public int getTempo(){
		return tempo;
	}
	
	public String getOwner(){
		return playerOwner;
	}
	public HashMap<String, HashMap<Integer, HashMap<Integer, ArrayList<Integer>>>> getNoteData(){
		return noteData;
	}
	public int getMeasures(){
		return measureCount;
	}
}
