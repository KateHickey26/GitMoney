package commandline.model;

public enum CategoryTypes {
	FLOOR ("Floor Stickiness"), 
	PINT ("Pint Price"), 
	QUIZ ("Pub Quiz Quality"), 
	VIBES ("Atmosphere"), 
	TUNES ("Playlist Groovability");
	
	private final String name;
	
	CategoryTypes(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
	
	

}
