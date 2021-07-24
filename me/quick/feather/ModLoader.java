package me.quick.feather;

import java.io.File;
import java.util.Objects;

import me.quick.feather.api.mod.FeatherModBase;
import net.minecraft.client.Minecraft;

public class ModLoader {
	
	public File dir;
	public File modDir;
	
	public ModLoader() {
		
		dir = new File(Minecraft.getMinecraft().mcDataDir, "Feather");
		modDir = new File(dir, "mods");
		if(!dir.exists()) {
			dir.mkdir();
		}
		if(!modDir.exists()) {
			modDir.mkdir();
		}
		
	}

    public void loadMods() {
    	for (File jar : modDir.listFiles()) {
    		//System.out.println(jar.getName() + " " + jar.getAbsolutePath());
	        ModLoaderUtil<FeatherModBase> loader = new ModLoaderUtil<>();
	        FeatherModBase loadingIn = null;
	        
	        try {
	            loadingIn = loader.loadClass(modDir.toString(), "me.quick.feather.api.FeatherMod", FeatherModBase.class, jar);
	        } catch (ClassNotFoundException | ClassCastException e) {
	            e.printStackTrace();
	        }
	        
	        Feather.INSTANCE.modManager.mods.add(loadingIn);
    	}
    }

}
