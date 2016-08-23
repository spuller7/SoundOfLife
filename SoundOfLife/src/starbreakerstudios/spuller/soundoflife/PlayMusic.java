package starbreakerstudios.spuller.soundoflife;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

public class PlayMusic extends BukkitRunnable{

	Player p;
	int songSpot;
	ArrayList<ArrayList<Integer>> musicList = new ArrayList<ArrayList<Integer>>();
	Hashtable<Integer, Float> quickPitch = new Hashtable<Integer, Float>();
	
    Sound thisSound;
	
	final int maxMeasure = 7;
	int current;
	
	public PlayMusic(Plugin plugin, HashMap<String, HashMap<Integer, HashMap<Integer, ArrayList<Integer>>>> newNoteData, String songName, boolean allInstruments, boolean allSong, int currentMeasure, String selectedInstrument, Player p, Sound thisSound){
		this.thisSound = thisSound;
		this.p = p;
		for(Notes note: Notes.values()){
			int o = note.getPlacement();
			float f = note.getPitch();
			quickPitch.put(o, f);
		
		}
		
		if(allSong){
			if(allInstruments){
				if(allInstruments){
					p.sendMessage("Not made yet");
				}
			}
			else{
				if(allInstruments){
					p.sendMessage("Not made yet");
				}
			}
		}
		else{
			if(allInstruments){
				p.sendMessage("Not made yet");
			}
			else{
				ArrayList<Integer> instrumentList = new ArrayList<Integer>();
				ArrayList<Integer> instrumentList1 = new ArrayList<Integer>();
			
					for(int a = 0; a < 8; a++){
						int b = newNoteData
								.get(selectedInstrument)
								.get(currentMeasure)
								.get(1)
								.get(a);
						instrumentList.add(b);
						int c = newNoteData
								.get(selectedInstrument)
								.get(currentMeasure)
								.get(2)
								.get(a);
						instrumentList1.add(c);
					}
				songSpot = 0;
				newNoteData.get("lute").get(currentMeasure).get(1).set(0, 19);
				musicList.add(0, instrumentList);
				musicList.add(1, instrumentList1);
				p.sendMessage("asd");
				for(int s = 0; s <8; s++){
					p.sendMessage(musicList.get(0).get(s) + "");
					p.sendMessage(musicList.get(1).get(s) + "");
				}
			}
		}
	
	}

	
	@Override
	public void run(){
		
		int max = 7;
		int current = songSpot;
			if(current > max){
				p.sendMessage("end");
				Bukkit.getScheduler().cancelTask(this.getTaskId());		
		
			}
			else{
				
				if(musicList.get(0).get(current)!=0){
					p.playSound(p.getLocation(), thisSound, 1.0f, quickPitch.get(musicList.get(0).get(current)));
				}else{
					p.sendMessage("is zero");
				}
				if(musicList.get(1).get(current) != 0){
					p.playSound(p.getLocation(), thisSound, 1.0f, quickPitch.get(musicList.get(1).get(current)));
				}else{
					p.sendMessage("is zero");
				}
				songSpot = songSpot + 1;
				p.sendMessage(songSpot + "!");
			}
	}
}
