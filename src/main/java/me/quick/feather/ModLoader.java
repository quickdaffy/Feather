package me.quick.feather;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Objects;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.jar.JarInputStream;

import me.quick.feather.api.mod.FeatherModBase;
import net.minecraft.client.Minecraft;

public class ModLoader {

	public File dir;
	public File modDir;

	public ModLoader() {

		dir = new File(Minecraft.getMinecraft().mcDataDir, "feather");
		modDir = new File(dir, "mods");
		if(!dir.exists()) {
			dir.mkdir();
		}
		if(!modDir.exists()) {
			modDir.mkdir();
		}

	}

	public void loadMods() {
		ArrayList<FeatherModBase> loadingMods = this.loadMods("/feather/mods");

		for (FeatherModBase m : loadingMods) {
			if (m != null) Feather.INSTANCE.modManager.mods.add(m);
		}
	}

	public ArrayList<FeatherModBase> loadMods(String directory) {
		File modsDir = new File(System.getProperty("user.dir") + directory);

		ArrayList<FeatherModBase> mods = new ArrayList<FeatherModBase>();

		for (File jar : Objects.requireNonNull(modsDir.listFiles())) {
			boolean shouldLoad = true;

			if (!jar.getName().endsWith(".jar")) shouldLoad = false; // Ignore files that are not JAR files


			String cp = null;

			URLClassLoader loader = new URLClassLoader(
				new URL[]{jar.toURI().toURL()},
				ClassLoader.getSystemClassLoader()
			);

			// Find feather.yml file to read classpath
			try {
				if (shouldLoad) {
					JarFile jarFile = new JarFile(jar.getAbsolutePath());

					Enumeration<JarEntry> entries = jarFile.entries();
					while (entries.hasMoreElements()) {
						final JarEntry entry = entries.nextElement();
						if (entry.getName().equals("feather.yml")) {
							JarEntry fileEntry = jarFile.getJarEntry(entry.getName());
							InputStream input = jarFile.getInputStream(fileEntry);

							BufferedReader reader = new BufferedReader(new InputStreamReader(input));

							while (reader.ready()) {
								String l = reader.readLine();
								if (l.startsWith("main: ")) {
									// We have found the line with the class entry
									cp = l.substring(6);
								}
							}

						}
					}
				}

				if (cp == null) {
					Feather.LOGGER.error("Feather mod config file not found in mod {}!", jar.getName());
					shouldLoad = false;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			if (shouldLoad) { // Check again, in case cp was null and a config file was not found.
				try {

						// Load class
						Class<?> newClass = loader.loadClass(cp);


						// Check if class extends FeatherModBase
						if (newClass.getConstructors()[0].newInstance() instanceof FeatherModBase)
							mods.add((FeatherModBase) newClass.getConstructors()[0].newInstance());
						else Feather.LOGGER.info("Failed to load mod " + jar.getName());

				} catch (ClassNotFoundException e) {
					throw new ClassNotFoundException("Failed to find main class of mod " + jar.getName() + ". Skipping this mod.");
				} catch (MalformedURLException | InvocationTargetException | IllegalAccessException | InstantiationException e) {
					e.printStackTrace();
				}
			}
		}

		return mods;
	}

}
