package me.quick.feather;

public class Feather {
	
	public static Feather INSTANCE = new Feather();
	
	public String VERSION = "1.0-beta";

	public ModManager modManager;
	public ModLoader loader;
	
	public void init() {
		System.out.println("Initialized Feather " + VERSION);
		
		modManager = new ModManager();
		
		loader = new ModLoader();
		loader.loadMods();
		
		modManager.preInitMods();
	}
	
	public void start() {
		System.out.println("Started Feather " + VERSION);
		
		modManager.initMods();
	}
	
	public void postInit() {
		
		modManager.postInitMods();
	}
	
	public void shutdown() {
		System.out.println("Stopping Feather " + VERSION);
		
		modManager.shutdownMods();
	}

}
