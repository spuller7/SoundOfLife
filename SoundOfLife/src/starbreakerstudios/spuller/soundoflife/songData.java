package starbreakerstudios.spuller.soundoflife;

import java.util.ArrayList;
import java.util.HashMap;

public class songData{
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
