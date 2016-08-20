package starbreakerstudios.spuller.soundoflife;

import org.bukkit.DyeColor;

public enum Notes {

	FSharp0("F#", 0.5f, DyeColor.WHITE, 9),
	G0("G", 0.525f, DyeColor.WHITE, 10),
	GSharp0("G#", 0.55f, DyeColor.WHITE, 11),
	A0("A", 0.6f, DyeColor.WHITE, 12),
	ASharp0("A#", 0.65f, DyeColor.WHITE, 13),
	B0("B", 0.675f, DyeColor.WHITE, 14),
	C0("C", 0.7f, DyeColor.WHITE, 15),
	CSharp0("C#", 0.75f, DyeColor.WHITE, 16),
	D0("D", 0.8f, DyeColor.WHITE, 17),
	DSharp0("D#", 0.85f, DyeColor.GRAY, 18),
	E0("E", 0.9f, DyeColor.GRAY, 19),
	F0("F", 0.95f, DyeColor.GRAY, 20),
	FSharp("F#", 1.0f, DyeColor.GRAY, 21),
	G("G", 1.05f, DyeColor.GRAY, 22),
	GSharp("G#", 1.1f, DyeColor.GRAY, 23),
	A("A", 1.2f, DyeColor.GRAY, 24),
	ASharp("A#", 1.25f, DyeColor.GRAY, 25),
	B("B", 1.35f, DyeColor.GRAY, 26),
	C("C", 1.4f, DyeColor.BLACK, 28),
	CSharp("C#", 1.45f, DyeColor.BLACK, 29),
	D("D", 1.6f, DyeColor.BLACK, 30),
	DSharp("D#", 1.7f, DyeColor.BLACK, 31),
	E("E", 1.75f, DyeColor.BLACK, 32),
	F("F", 1.9f, DyeColor.BLACK, 33),
	FSharp1("F#", 2.0f, DyeColor.BLACK, 34);
	
	private final String name;
	private final Float pitch;
	private final DyeColor dyecolor;
	private final int placement;
	
	Notes(String nam, Float pitc, DyeColor dyecolo, int placemen){
		name = nam;
		pitch = pitc;
		dyecolor = dyecolo;
		placement = placemen;
	}
	
	public String getName(){
		return name;
	}
	public Float getPitch(){
		return pitch;
	}
	public DyeColor getColor(){
		return dyecolor;
	}
	public int getPlacement(){
		return placement;
	}
}
