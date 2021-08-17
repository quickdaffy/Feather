package me.quick.feather.api.mod;

import me.quick.feather.Feather;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.net.URISyntaxException;

public class FeatherModBase {
	
	public String name = "No mod name provided.";
	public String description = "No description provided.";
	public String version = "No version provided.";
	public String author = "No author provided.";

	public static Logger LOGGER;

	public FeatherModBase() {


		// We need to grab the name, author, and version from the feather.yml file
		try {
			InputStream in = getClass().getResourceAsStream("/feather.yml");
			if (in == null) {
				Feather.LOGGER.error("Feather Mod config file not provided!");
			} else {
				BufferedReader reader = new BufferedReader(new InputStreamReader(in));

				while (reader.ready()) {
					String line = reader.readLine();
					if (line.startsWith("name: ")) {
						name = line.substring("name: ".length());
					}

					if (line.startsWith("description: ")) {
						description = line.substring("description: ".length());
					}

					if (line.startsWith("version: ")) {
						version = line.substring("version: ".length());
					}

					if (line.startsWith("author: ")) {
						author = line.substring("author: ".length());
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		// IMPORTANT: Initalize LOGGER variable
		LOGGER = LogManager.getLogger(name);
	}
	
	public void preInit() {
		LOGGER.info("Pre-init called for " + name);
	}

	public void init() {
		LOGGER.info("Initialized " + name);
	}
	
	public void postInit() {
		LOGGER.info("Post-init called for " + name);
	}
	
	public void shutdown() {
		LOGGER.info("Shutting down " + name);
	}
	
	public String getName() {
		return name;
	}

	public String getVersion() {
		return version;
	}

	public String getAuthor() {
		return author;
	}

}
