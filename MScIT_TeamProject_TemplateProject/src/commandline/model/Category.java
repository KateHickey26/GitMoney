package commandline.model;

public class Category {
	private CategoryTypes type;
	private int score;
	
	public Category(CategoryTypes t, int s) {
		this.type = t;
		this.score = s;
	}
	
	public int compareTo(Object o) {
		// this - 1; other - 2; draw - 0
		int thisIsHigher = 0;
		if (o instanceof Category) {
			Category c = (Category) o;
			if(this.score > c.getScore()) {
				thisIsHigher = 1;
			} else if (this.score < c.getScore()){
				thisIsHigher = 2;
			} else { // draw
				thisIsHigher = 0;
			}
		}
		
		return thisIsHigher;
	}


	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public CategoryTypes getType() { return type; }

	public void setType(CategoryTypes type) {
		this.type = type;
	}
	
	
	
	

}
