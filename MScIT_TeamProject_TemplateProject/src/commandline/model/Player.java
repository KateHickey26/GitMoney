package commandline.model;

public class Player {
	private String name;
	private Deck playerDeck;

	public Player() {
		playerDeck = new Deck();
	}
	
	public void setDeck(Deck c) {
		playerDeck = c;	
	}

	public Deck getDeck() {
		return playerDeck;
	}
	

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	// adds cards to the bottom of the deck (probably);
	public void addCards(Deck d) {
		playerDeck.addSetOfCards(d);
	}
	
	public void addOneCard(Card c) {
		playerDeck.addCard(c);
	}
	
	
	//method to check if the player is the winner - they have all the cards 
	public Boolean isFull() {
		// 
		if (playerDeck.getMainDeck().size() == 40) {
			return true;
		} else
			return false;
	}
	
	// method to check if the player has lost - if they have 0 cards 
	public Boolean isEmpty() {
		if (playerDeck.getMainDeck().size() == 0) {
			return true;
		} else
			return false;
	}

}