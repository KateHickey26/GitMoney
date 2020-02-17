package commandline.model;

import java.util.ArrayList;
import java.util.Collections;

public class Deck {
	private ArrayList<Card> mainDeck;
	private Card blankCard;

	public Deck() {
		mainDeck = new ArrayList<Card>();

		blankCard = new Card("ELIMINATED", 0,0,0,0,0);
	}
	
	public Deck(ArrayList<Card> cards) {
		this.mainDeck = cards;
	}

	public void shuffleDeck() {
		/*
		 * shuffles list
		 */
		Collections.shuffle(mainDeck);
	}

	public void addCard(Card x) {
		/*
		 * adds card to end of list
		 */
		mainDeck.add(x);
	}
	
	// I added this getter
	public ArrayList<Card> getDeckArray() {
		return this.mainDeck;
	}
	
	public Card getTopCard() {

		if (mainDeck.isEmpty()) {
			return blankCard;
		}
		return mainDeck.get(0);
		
	}

	public Card getAndRemoveTopCard() {
		/*
		 * this will return the top card but also remove it from the list
		 * can be used when dealing the cards into each players hands
		 * 
		 * can use similar method for playing and moving into common pile
		 */
		Card firstCard = getTopCard();

		if (!mainDeck.isEmpty()) {
			mainDeck.remove(0);
			return firstCard;
		}
		return blankCard;
	}

	public void removeTopCard() {
		/*
		 * this will return the top card but also remove it from the list
		 * can be used when dealing the cards into each players hands
		 *
		 * can use similar method for playing and moving into common pile
		 */
		mainDeck.remove(0);
	}

	public void addSetOfCards(Deck cards){
		/*
		 *adds a lost of cards to the current deck
		 *such as adding the communal deck to a players hand
		 */
		this.mainDeck.addAll(cards.getDeckArray());
		//this.mainDeck.addAll(cards.getMainDeck());
	}


	public Card seeCard(int x) {
		/*
		 * just returns the card at a given index
		 * card will remain in list
		 */
		
		Card one = mainDeck.get(x);
		return one;
	}

//	public boolean isLoser(){
//		/*
//		 * checks is deck is empty
//		 * can be used to see if there is a loser
//		 * as a player lose if there hand is empty
//		 */
//		if(mainDeck.size() == 0){
//			return true;
//		}
//		else return false;
//	}
//	public boolean isWinner(){
//		/*
//		 * check to see if there is 40 cards in the list
//		 * as someone wins when they accumulate all 40 cards
//		 */
//		if(mainDeck.size() == 40){
//			return true;
//		}
//		else return false;
//	}

	public int sizeOfDeck(){
		if (mainDeck.isEmpty()) {
			return 0;
		}
		return mainDeck.size();
	}
}
