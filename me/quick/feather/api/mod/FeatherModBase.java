package me.quick.feather.api.mod;

public class FeatherModBase {
	
	public String name;
	public String version;
	public String author;
	
	public FeatherModBase(String name, String version, String author) {
		this.name = name;
		this.version = version;
		this.author = author;
	}
	
	public void preInit() {
		System.out.println("Pre-init called for " + name);
	}

	public void init() {
		System.out.println("Initialized " + name);
	}
	
	public void postInit() {
		System.out.println("Post-init called for " + name);
	}
	
	public void shutdown() {
		System.out.println("Shutting down " + name);
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
