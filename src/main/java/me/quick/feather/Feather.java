package me.quick.feather;

import me.quick.feather.api.event.bus.EventBus;
import me.quick.feather.api.event.bus.SubscribeEvent;
import me.quick.feather.api.event.impl.TickEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Feather {
	
	public static Feather INSTANCE = new Feather();
	public static Logger LOGGER = LogManager.getLogger("Feather");
	
	public String VERSION = "1.0-beta";

	public ModManager modManager;
	public ModLoader loader;

	public EventBus EVENT_BUS = new EventBus();
	
	public void init() {
		EVENT_BUS.register(this);

		LOGGER.info("Initialized Feather " + VERSION);
		
		modManager = new ModManager();
		
		loader = new ModLoader();
		loader.loadMods();
		
		modManager.preInitMods();
	}
	
	public void start() {
		LOGGER.info("Started Feather " + VERSION);
		
		modManager.initMods();
	}
	
	public void postInit() {
		
		modManager.postInitMods();
	}
	
	public void shutdown() {
		LOGGER.info("Stopping Feather " + VERSION);
		
		modManager.shutdownMods();
	}

	@SubscribeEvent
	public void onTick(TickEvent.ClientTickEvent e) {
		LOGGER.info("Client tick!");
		EVENT_BUS.shutdown();
	}

}
