package me.quick.feather;


import java.util.concurrent.CopyOnWriteArrayList;

import me.quick.feather.api.mod.FeatherModBase;

public class ModManager {

   public CopyOnWriteArrayList<FeatherModBase> mods;
   
   public ModManager() {
	   mods = new CopyOnWriteArrayList<>();
   }
   
   public void addMod(FeatherModBase m) {
	   mods.add(m);
   }
   
   public void preInitMods() {
	   for(FeatherModBase m : mods) {
		   m.preInit();
	   }
   }
   
   public void initMods() {
	   for(FeatherModBase m : mods) {
		   m.init();
	   }
   }
   
   public void postInitMods() {
	   for(FeatherModBase m : mods) {
		   m.postInit();
	   }
   }
   
   public void shutdownMods() {
	   for(FeatherModBase m : mods) {
		   m.shutdown();
	   }
   }

}
