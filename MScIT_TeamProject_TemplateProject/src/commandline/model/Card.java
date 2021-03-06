package commandline.model;

import java.util.HashMap;
import java.util.Map;

public class Card {

	private String name;
	private Category[] cats;
	private int floorSticky, pintPrice, pubQuiz, atmosphere, music;

	public Card(String name, int floorSticky, int pintPrice, int pubQuiz, int atmosphere, int music) {
		this.name = name;
		this.cats = new Category[5];
		this.floorSticky = floorSticky;
		this.pintPrice = pintPrice;
		this.pubQuiz = pubQuiz;
		this.atmosphere = atmosphere;
		this.music = music;
		Category floor = new Category(CategoryTypes.FLOOR, floorSticky);
		Category pint = new Category(CategoryTypes.PINT, pintPrice);
		Category quiz = new Category(CategoryTypes.QUIZ, pubQuiz);
		Category vibes = new Category(CategoryTypes.VIBES, atmosphere);
		Category tunes = new Category(CategoryTypes.TUNES, music);
		
		this.cats[0] = floor;
		this.cats[1] = pint;
		this.cats[2] = quiz;
		this.cats[3] = vibes;
		this.cats[4] = tunes;

	}
	
	
	public Category getTopCategory(){
		/**
		 * finds the highest category for any given card
		 * this is used for the Ai player to decide which category to play
		 */
		Category maxCategory = cats[0];
		int maxScore = cats[0].getScore();;
		for(int i = 0; i < 5; i++) {
			if (cats[i].getScore() > maxScore) {
				maxCategory = cats[i];
				maxScore = cats[i].getScore();
			}
		}
		return maxCategory;
	}
	
	public Category matchCategory(CategoryTypes c) {
		/**
		 * returns the "category type" for a card
		 * this can then be used to get the score for that category
		 */
		Category matched = null;
		for (int i = 0; i < cats.length; i++) {
			if (c == cats[i].getType()) {
				matched = cats[i];
				break;
			}
		}
		return matched;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public Category[] getCats() {
		return cats;
	}


	public void setCats(Category[] cats) {
		this.cats = cats;
	}
	public Map getCardAsMap(){
		Map<String, String> card = new HashMap<String, String>();
        card.put("name", "" + this.name);
        card.put("floorSticky", "" + this.floorSticky);
        card.put("pintPrice", "" + this.pintPrice);
        card.put("pubQuiz", "" + this.pubQuiz);
        card.put("atmosphere", "" + this.atmosphere);
        card.put("playlist", "" + this.music);
        return card;
	}
	
	
	
	// compareTo?
	
	// need method for printing card info


	// public int getFloorSticky() {
	// 	return floorSticky;
	// }

	// public int getPintPrice() {
	// 	return pintPrice;
	// }

	// public int getPubQuiz() {
	// 	return pubQuiz;
	// }

	// public int getAtmosphere() {
	// 	return atmosphere;
	// }

	// public int getMusic() {
	// 	return music;
	// }

    public String toString(){
	    String cardContents="";
        cardContents+=name+" ";
        for (int k = 0; k < cats.length; k++) {
            cardContents+=cats[k].getType().getName()+":";
            cardContents+=cats[k].getScore()+" ";
        }
	    return cardContents;
    }

    public String categoryName(int index){
        return cats[index].getType().getName();
    }

    public int categoryValue(int index){
	    return cats[index].getScore();
    }

    public CategoryTypes categoryType(int index){
	    return cats[index].getType();
    }

}
