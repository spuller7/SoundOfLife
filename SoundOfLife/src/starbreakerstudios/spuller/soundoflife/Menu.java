package starbreakerstudios.spuller.soundoflife;


import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.Wool;
import org.bukkit.plugin.Plugin;

import net.md_5.bungee.api.ChatColor;


public class Menu implements Listener{

	Hashtable<Integer, ItemStack> quickNoteID = new Hashtable<Integer, ItemStack>();
	private Hashtable<Player, playerInterfaceInfo> intInfo = new Hashtable <Player, playerInterfaceInfo>();
	private HashMap<Player, ItemStack[]> editingPlayer = new HashMap<Player, ItemStack[]>();
	private HashMap<Player, ItemStack[]> editingPlayerArmour = new HashMap<Player, ItemStack[]>();

	Logger log = Bukkit.getLogger();	

	File settingsfile;
	FileConfiguration settings;
	
	private ItemStack lute;
	private ItemStack cello;
	private ItemStack maracas;
	private ItemStack drum;
	private ItemStack claves;
	private ItemStack dulcimer;

	
	private musicPlayBack musicPB;
	
    public Menu(Plugin p) {
    
           
    		
            Bukkit.getServer().getPluginManager().registerEvents(this, p);
    }
    public void whenEnabled(ItemStack dr, ItemStack ce, ItemStack ma, ItemStack lu, ItemStack du, ItemStack cl, musicPlayBack mpb){
    
    	musicPB = mpb;
    	
        lute = lu;
        drum = dr;
        maracas = ma;
        cello = ce;
        dulcimer = du;
        claves = cl;
        
        
        for(Notes note: Notes.values()){
			int o = note.getPlacement();
			ItemStack is  = new Wool(note.getColor()).toItemStack(1);
			ItemMeta imm = is.getItemMeta();
			imm.setDisplayName(note.getName());
			ArrayList<String> lore = new ArrayList<String>();
			lore.add("Right-Click note to select and hear,");
			lore.add("then Right-Click location in music.");
			imm.setLore(lore);
			is.setItemMeta(imm);
			quickNoteID.put(o, is);
		}
        Bukkit.getServer().broadcastMessage("Clear editing list");
        editingPlayer.clear();
        editingPlayerArmour.clear();
        
    }
    
    public void whenDsiabled() {
		saveSettings();
		for (Player p : Bukkit.getServer().getOnlinePlayers()){
			if (editingPlayer.containsKey(p.getName())){
				saveWork(p);
			}
		}
	}
    private void loadSettings() {
    	Bukkit.getServer().broadcastMessage("you");
		/*settingsfile = new File(getDataFolder(), "config.yml");
		settings = YamlConfiguration.loadConfiguration(settingsfile);
		if(settingsfile.exists()) {
			try {
				}
			 catch(Exception e) { log.severe("[Sound of Life] Encountered critical error while loading files."); }
			//log.info("[Dwarven Antag] Dwarven Antag file successfully loaded.");
		} else {
			log.info("[Sound of Life] SOL file not found, default file generated.");
		}*/
	}
	//Save updated to script
	private void saveSettings() {
		
		try {
			
			
			settings.save(settingsfile);
			//log.info("[Dwarven Antag] File successfully saved.");
		} catch(Exception e) { //log.severe("[Dwarven Antag] Encountered critical error while saving files."); 
		}
		
	}
	
	//When leave editor, get inventory back
	private void saveWork(Player p) {
		p.sendMessage("Saving...");
		//returns inventory
		p.getInventory().clear();
		p.sendMessage(ChatColor.AQUA + "Returning your items. Enjoy!");
		p.getInventory().setContents(editingPlayer.get(p));
		//puts back on armour you had on
		p.getInventory().setArmorContents(editingPlayerArmour.get(p));
		editingPlayer.remove(p);
		editingPlayerArmour.remove(p);
		
	}
	//Event Handlers
	@EventHandler
	//if kicked off server, don't screw up
	public void onPlayerKick(PlayerKickEvent e){
		if (editingPlayer.containsKey(e.getPlayer())){
			saveWork(e.getPlayer());
		}
	}
	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent e){
		if (editingPlayer.containsKey(e.getPlayer())){
			saveWork(e.getPlayer());
		}
	}
	@EventHandler
	public void onCloseInventory(InventoryCloseEvent event){
		if(!(event.getPlayer() instanceof Player)) return;
		Player e = (Player)event.getPlayer();
		if (editingPlayer.containsKey(e.getPlayer())){
			saveWork(e.getPlayer());
		}
	}
	
	//Clear Inventory and assign new inventory with all the notes 
	public void list(Player p) {
		if (!editingPlayer.containsKey(p.getName())){
			editingPlayer.put(p, p.getInventory().getContents());
			p.sendMessage("Put name in");
			editingPlayerArmour.put(p, p.getInventory().getArmorContents());
			p.sendMessage(ChatColor.GOLD + "Your inventory has been saved and cleared.");
			p.getInventory().clear();
			
		}else{
			p.sendMessage("Somehow your name is already in?");
			p.getInventory().clear();
			//Add new inventory items
		
		}
	}
	private ItemStack createPrevious(String name, Player p){
		if(name == "Previous Measure"){
			ItemStack i;
			//If you are on the first measure, don't have a previous measure because there isn't.
	    	//Instead add obsidian as an obstruction
	    	if(intInfo.get(p).getMeasure() == 1){
	    		i = new ItemStack(Material.OBSIDIAN);
	    		ItemMeta im = i.getItemMeta();
	    		im.setDisplayName(ChatColor.RED + "Start of Song");
	    		i.setItemMeta(im);
	    		return i;
	    	}
	    	else{
	    		i = new ItemStack(Material.PAPER);
				ItemMeta im = i.getItemMeta();
				im.setDisplayName(name);
				i.setItemMeta(im);
				return i;
	    	}
		}else{
			return null;
		}
	}
	private ItemStack createToggleAll(DyeColor dc, String name, Player p){
		ItemStack i;
		i = new Wool(dc).toItemStack(1);
		ItemMeta im = i.getItemMeta();
		im.setDisplayName(name);
		ArrayList<String> lore = new ArrayList<String>();

		if(intInfo.get(p).getAllInstruments() == true){
			lore.add("Hear All Instruments [On]");
		}
		else{
			lore.add("Hear All Instruments [Off]");
		}
			
		lore.add("Right-Click Toggle");
		im.setLore(lore);
		i.setItemMeta(im);
		return i;
	}
    private ItemStack createItem(DyeColor dc, String name, Player p){
		ItemStack i;
		if(name == "Next Measure" ||  name == "Current Measure"){
			i = new ItemStack(Material.PAPER);
			ItemMeta im = i.getItemMeta();
			im.setDisplayName(name);
			i.setItemMeta(im);
			return i;
		}
		
		
		else if(name == " "){
			i = new ItemStack(Material.JUKEBOX);
			ItemMeta im = i.getItemMeta();
			im.setDisplayName(name);
			i.setItemMeta(im);
			return i;
		}
		else if(name == "Toggle Delete [OFF]"){
			i = new ItemStack(Material.TNT);
			ItemMeta im = i.getItemMeta();
			im.setDisplayName(name);
			i.setItemMeta(im);
			return i;
		}
		else if(name == "Increase Tempo"){
			i = new ItemStack(Material.COMPASS);
			ItemMeta im = i.getItemMeta();
			im.setDisplayName(name);
			ArrayList<String> lore = new ArrayList<String>();
			lore.add(intInfo.get(p).getTempo() + " Beats per Tick");
			im.setLore(lore);
			i.setItemMeta(im);
			return i;
		}
		else if(name == "Decrease Tempo"){
			i = new ItemStack(Material.COMPASS);
			ItemMeta im = i.getItemMeta();
			im.setDisplayName(name);
			ArrayList<String> lore = new ArrayList<String>();
			lore.add(intInfo.get(p).getTempo() + " Beats per Tick");
			im.setLore(lore);
			i.setItemMeta(im);
			return i;
		}
		
		else{
			i = new Wool(dc).toItemStack(1);
			ItemMeta im = i.getItemMeta();
			im.setDisplayName(name);
			i.setItemMeta(im);
			return i;
		
		}
		
    }
    private Inventory topInventory(Player p){
    	
    	ItemStack next, back, listen1, listen2, exit, current, music, music1, delete, tempoP, tempoM;
    	Inventory inv;
    	
    	 inv = Bukkit.getServer().createInventory(null, 27, "Compose Music");
         
         delete = createItem(DyeColor.WHITE, "Toggle Delete [OFF]", p);
         next = createItem(DyeColor.WHITE, "Next Measure", p);
         next.setAmount(intInfo.get(p).getMeasure() + 1);
 		back = createPrevious("Previous Measure",p);
 		if(intInfo.get(p).getMeasure() - 1 != 0){
 			back.setAmount(intInfo.get(p).getMeasure() - 1);
 		}
 		current = createItem(DyeColor.WHITE, "Current Measure", p);
 		current.setAmount(intInfo.get(p).getMeasure());
 		listen1 = createToggleAll(DyeColor.GREEN, "Preview Song", p);
 		listen2 = createToggleAll(DyeColor.GREEN, "Preview Measure", p);
 		exit = createItem(DyeColor.RED, "Exit & Save", p);
 		music = createItem(DyeColor.BLACK, " ", p);
 		music1 = createItem(DyeColor.BLACK, " ", p);
 		tempoP = createItem(DyeColor.BLACK, "Decrease Tempo", p);
 		tempoM = createItem(DyeColor.BLACK, "Increase Tempo", p);

 		inv.setItem(19, tempoM);
 		inv.setItem(25, tempoP);
 		inv.setItem(20, delete);
 		inv.setItem(18, back);
 		inv.setItem(21, exit);
 		inv.setItem(22, current);
 		inv.setItem(23, listen1);
 		inv.setItem(24, listen2);
 		inv.setItem(26, next);
 		inv.setItem(0, music);
 		inv.setItem(9, music1);
 		
 		return inv;
    }
    
 
    private void show(Player p) {
    	//Whenever a new inventory needs be loaded (new measure) or startup
    	if(intInfo.get(p).getInv() != null){
    		p.sendMessage(ChatColor.MAGIC + "Clear");
    		intInfo.get(p).getInv().clear();
    	}
    	intInfo.get(p).setInv(topInventory(p));
    	p.openInventory(intInfo.get(p).getInv());
    	//Here we call musicPlayBack for the dyecolor and then return a wool to fill correct spot
    	//of new editor window
    	for(int i = 1; i < 18; i++){
    		if(i != 9){
    			ItemStack note = musicPB.returnNote(intInfo.get(p).getSongName(), intInfo.get(p).getMeasure(), i, p, intInfo.get(p).getInstrument());
 
    			if(note != null){
    				intInfo.get(p).getInv().setItem(i, note);
    			}
    		}
    	}
    
    	//Add all the notes at first startup
    	if(!editingPlayer.containsKey(p)){
    		list(p);
    		addLowerComponents(p);
 
        	
    	}	
    }
    
   //Click event to activate action when music sheet in hotbar is clicked
    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
    	//If it is not the inventory created by the plugin
    	if (!e.getInventory().getName().equals("Compose Music")) return;
    	
    	//Global player (yourself)
    	Player p = (Player)e.getWhoClicked();
    	if(!editingPlayer.containsKey(p))return;
    	//If the player clicks the editor, it will replace the note and then save it to their song array
    	if (e.getClickedInventory().getTitle() == "Compose Music"){
    		
    		if(e.getSlot() > 0 && e.getSlot() < 9 || e.getSlot() > 9 && e.getSlot() < 18){
    			e.setCancelled(true);
    			int i = e.getSlot();
    			if(0 < i && 9 > i){
    				e.getClickedInventory().setItem(i, quickNoteID.get(intInfo.get(p).getNote()));
    				
    				//When insert new note, save it
    				musicPB.saveNote(
    						//Player
    						p, 
    						//Measure number
    						intInfo.get(p).getMeasure(),
    						//Note id
    						intInfo.get(p).getNote(),
    						//note position
    						i - 1, 
    						//Instrument string
    						intInfo.get(p).getInstrument(),
    						//Note level
    						1,
    						//Song Name
    						intInfo.get(p).getSongName());

    			}
    			else if(i > 9 && i < 18){
    				e.getClickedInventory().setItem(i, quickNoteID.get(intInfo.get(p).getNote()));
    				musicPB.saveNote(
    						//Player
    						p, 
    						//Measure number
    						intInfo.get(p).getMeasure(),
    						//Note id
    						intInfo.get(p).getNote(),
    						//note position
    						i - 10, 
    						//Instrument string
    						intInfo.get(p).getInstrument(),
    						//Note level
    						2,
    						//Song Name
    						intInfo.get(p).getSongName());	
    			}
    		}
    		else if (e.getCurrentItem().getItemMeta().getDisplayName().equals("Decrease Tempo")) {
    			intInfo.get(p).setTempo(intInfo.get(p).getTempo() - 1);
    			show(p);
        		e.setCancelled(true);
        	}
    		else if (e.getCurrentItem().getItemMeta().getDisplayName().equals("Increase Tempo")) {
    			intInfo.get(p).setTempo(intInfo.get(p).getTempo() + 1);
    			show(p);
        		e.setCancelled(true);
        	}
    		else if (e.getCurrentItem().getItemMeta().getDisplayName().equals(" ")) {

        		e.setCancelled(true);
        	}
    		else if (e.getCurrentItem().getItemMeta().getDisplayName().contains("Previous")) {
        		intInfo.get(p).setMeasure(intInfo.get(p).getMeasure() - 1);
        		show(p);
        		e.setCancelled(true);
        	}
    		else if (e.getCurrentItem().getItemMeta().getDisplayName().contains("Next")) {
        		
        		intInfo.get(p).setMeasure(intInfo.get(p).getMeasure() + 1);
        		show(p);
        		e.setCancelled(true);
        	}
    		else if (e.getCurrentItem().getItemMeta().getDisplayName().contains("Current")) {
        		e.setCancelled(true);
        	}
    		else if (e.getCurrentItem().getItemMeta().getDisplayName().contains("Preview Measure")) {
    			if(e.isRightClick()){
    				p.sendMessage("Right Click");
    				intInfo.get(p).setAllInstruments(!intInfo.get(p).getAllInstruments());
    				ItemStack is = createToggleAll(DyeColor.GREEN, "Preview Measure", p);
    				intInfo.get(p).getInv().clear(24);
    				p.sendMessage("Clear");
    				intInfo.get(p).getInv().setItem(24, is);
    				ItemStack i = createToggleAll(DyeColor.GREEN, "Preview Song", p);
    				intInfo.get(p).getInv().clear(23);
    				intInfo.get(p).getInv().setItem(23, i);
    				
    			}
    			else if(e.isLeftClick()){
    				p.sendMessage("Go to sample");
    				musicPB.sampleMusic(intInfo.get(p).getSongName(), intInfo.get(p).getAllInstruments(),false, intInfo.get(p).getMeasure(), intInfo.get(p).getInstrument(), p, intInfo.get(p).getTempo(), intInfo.get(p).getSound());
    			}
    		
        		e.setCancelled(true);
        	}
    		else if (e.getCurrentItem().getItemMeta().getDisplayName().contains("Preview Song")) {
    			if(e.isRightClick()){
    				intInfo.get(p).setAllInstruments(!intInfo.get(p).getAllInstruments());
    				ItemStack i = createToggleAll(DyeColor.GREEN, "Preview Song", p);
    				intInfo.get(p).getInv().clear(23);
    				intInfo.get(p).getInv().setItem(23, i);
    				ItemStack is = createToggleAll(DyeColor.GREEN, "Preview Measure", p);
    				intInfo.get(p).getInv().clear(24);
    				p.sendMessage("Clear");
    				intInfo.get(p).getInv().setItem(24, is);
    				
    			}
    			else if(e.isLeftClick()){
    				p.sendMessage("Go to sample");
    				musicPB.sampleMusic(intInfo.get(p).getSongName(), intInfo.get(p).getAllInstruments(),true, intInfo.get(p).getMeasure(), intInfo.get(p).getInstrument(), p, intInfo.get(p).getTempo(), intInfo.get(p).getSound());
    			}
        		e.setCancelled(true);
            }
    		else if (e.getCurrentItem().getItemMeta().getDisplayName().contains("Save")) {
        		e.setCancelled(true);
        		e.getWhoClicked().closeInventory();
            }
    	}
    	else{
    		if(e.getSlot() == 1 || e.getSlot() == 2 || e.getSlot() == 3 || e.getSlot() == 5 || e.getSlot() == 6 || e.getSlot() == 7){
        	
        		Sound snd;
        		
        		if (e.getSlot() == 1) {
            		e.setCancelled(true);
            		snd = Sound.BLOCK_NOTE_HAT;
            		intInfo.get(p).setInstrument("claves");
                }
        		else if (e.getSlot() == 2) {
            		e.setCancelled(true);
            		snd = Sound.BLOCK_NOTE_HARP;
            		intInfo.get(p).setInstrument("lute");
                }
        		else if (e.getSlot() == 3) {
            		p.sendMessage("Pling 1");
            		e.setCancelled(true);
            		snd = Sound.BLOCK_NOTE_BASS;
            		intInfo.get(p).setInstrument("cello");
                }
        		else if (e.getSlot() == 5) {
            		p.sendMessage("Pling 2");
            		e.setCancelled(true);
            		snd = Sound.BLOCK_NOTE_BASEDRUM;
            		intInfo.get(p).setInstrument("drum");
                }
        		else if (e.getSlot() == 6) {
            		e.setCancelled(true);
            		snd = Sound.BLOCK_NOTE_SNARE;
            		intInfo.get(p).setInstrument("maracas");
                }
        		else if (e.getSlot() == 7){
            		e.setCancelled(true);
            		snd = Sound.BLOCK_NOTE_PLING;
            		intInfo.get(p).setInstrument("dulcimer");
            	}
        		else{
        			e.setCancelled(true);
            		snd = Sound.BLOCK_NOTE_HARP;
            		intInfo.get(p).setInstrument("lute");
        		}
            	
            	intInfo.get(p).setSound(snd);
            	p.sendMessage("Changing instrument");
            	show(p);
        	}
    		else if(e.getSlot() == 4){
        		e.setCancelled(true);
        	}
    		else if(e.getCurrentItem().getItemMeta().getLore().contains("Right-Click note to select and hear,")){
        		e.setCancelled(true);
        		
        		if(!e.isRightClick()) return;

        		for(Notes note : Notes.values()){
        			if(note.getName() == e.getCurrentItem().getItemMeta().getDisplayName()){
        				int in = e.getSlot();
        				if(note.getPlacement() == in){
        					intInfo.get(p).setNote(in);
        					p.playSound(p.getLocation(), intInfo.get(p).getSound(), 1.0f, note.getPitch() );
        				}
                			
        			}
        		}
        	}
    	}
    	
    }

	private void addLowerComponents(Player p){
		Inventory mainInv = p.getInventory();
		mainInv.setItem(2, lute);
		mainInv.setItem(3, cello);
		mainInv.setItem(5, drum);
		mainInv.setItem(6, maracas);
		ItemStack i = new ItemStack(Material.MAP);
		ItemMeta im = i.getItemMeta();
		im.setDisplayName(intInfo.get(p).getSongName());
		i.setItemMeta(im);
		mainInv.setItem(4, i);
		mainInv.setItem(1, claves);
		mainInv.setItem(7, dulcimer);
		
		//Cycle through notes
		for(Notes note: Notes.values()){

			ItemStack is  = new Wool(note.getColor()).toItemStack(1);
			ItemMeta imm = is.getItemMeta();
			imm.setDisplayName(note.getName());
			ArrayList<String> lore = new ArrayList<String>();
			lore.add("Right-Click note to select and hear,");
			lore.add("then Right-Click location in music.");
			imm.setLore(lore);
			is.setItemMeta(imm);
			mainInv.setItem(note.getPlacement(), is);
		}	
	}
	//When player holds the music sheet and clicks it
	@EventHandler
	public void onMusicSheetInteract(final PlayerInteractEvent e){
		if(e.getItem() != null){
			if(e.getItem().hasItemMeta()){
				if(e.getItem().getType().equals(Material.MAP)){

					if(e.getItem().getItemMeta().getLore().get(0).contains("Sound of Life")){
						//Player hasn't named a song, and I don't want people naming it untitled song. They are told to run the name command
						if(e.getItem().getItemMeta().getDisplayName().equals(ChatColor.GOLD + "Untitled Music Sheet")){
							e.getPlayer().sendMessage(ChatColor.AQUA + "To begin, type: /music create " + ChatColor.WHITE + "[Song Name]");
						}
						else{
							
							playerInterfaceInfo pII = new playerInterfaceInfo(Sound.BLOCK_NOTE_HARP, 1, 0, 10, "lute", e.getItem().getItemMeta().getDisplayName(), null, false);
							
							intInfo.put(e.getPlayer(), pII);
							
							
							
							
							show(e.getPlayer());
						}
					}
				}
			}
		}
	}
}

class playerInterfaceInfo{
	private int currentMeasure;
	private int selectedNote;
	private int tempo;
	private String currentInstrument;
	private String editingSong;
	private Inventory inv;
	private Sound instrumentSound;
	private Boolean allInstruments;
	
	public playerInterfaceInfo(Sound instrumentSound, int currentMeasure,int selectedNote, int tempo, String currentInstrument, String editingSong, Inventory inv, Boolean allInstruments){
        this.selectedNote = selectedNote;
        this.currentMeasure = currentMeasure;
        this.tempo = tempo;
        this.currentInstrument = currentInstrument;
        this.editingSong = editingSong;
        this.inv = inv;
        this.instrumentSound = instrumentSound;
        this.allInstruments = allInstruments;
    }
	public Boolean getAllInstruments(){
		return allInstruments;
	}
	public void setAllInstruments(Boolean allInstruments){
		this.allInstruments = allInstruments;
	}
	public Sound getSound(){
		return instrumentSound;
	}
	public void setSound(Sound instrumentSound) {
        this.instrumentSound = instrumentSound;
    }
	
	public Inventory getInv(){
		return inv;
	}
	public void setInv(Inventory inv){
		this.inv = inv;
	}
	
	public String getSongName(){
		return editingSong;
	}
	public void setSongName(String editingSong){
		this.editingSong = editingSong;
	}
	
	public String getInstrument(){
		return currentInstrument;
	}
	public void setInstrument(String currentInstrument){
		this.currentInstrument = currentInstrument;
	}
	
	public int getTempo(){
		return tempo;
	}
	public void setTempo(int tempo){
		this.tempo = tempo;
	}
	
	public int getMeasure(){
		return currentMeasure;
	}
	public void setMeasure(int currentMeasure){
		this.currentMeasure = currentMeasure;
	}
	
	public int getNote(){
		return selectedNote;
	}
	public void setNote(int selectedNote){
		this.selectedNote = selectedNote;
	}

}
