package commandline.model;

import java.util.ArrayList;
import java.util.Random;

public class GameModel {

	private Deck communalDeck;
	private Deck mainDeck;
	private ArrayList<Player> playerArray;
	private int activePlayer;
	private int numOfRounds; 
	private int numOfDraws;
	private Player gameWinner;
	private Card roundWinningCard;


	// constructor for game instance
	public GameModel(int numAIPlayers, Deck inputDeck) {
		/**
		 * intitates the game model based on the number of AI players and alse the intput deck
		 * which is the initial deck of all 40 cards. 
		 * 
		 * Shuffles al the cards after they have been passed as well
		 */
		this.mainDeck = inputDeck;
		this.communalDeck = new Deck();
		this.mainDeck.shuffleDeck();
		playerArray = new ArrayList<Player>();
		/**
		 * the user is always initated as player 0
		 * the ai players and then initiated based on the number chosen
		 */
		playerArray.add(new Player());
		playerArray.get(0).setName("You");
		playerArray.get(0).setPlayerID(0);
	
		for (int i = 1; i < numAIPlayers; i++) {
			playerArray.add(new Player());
			playerArray.get(i).setName("AI Player " + i);
			playerArray.get(i).setPlayerID(i);
		}
		/**
		 * takes the initial deck of all 40 cards and deals it out to all of the players evenlky
		 */
		int k = 0;
		do{
			playerArray.get(k).addOneCard(mainDeck.getAndRemoveTopCard());
			k++;
			if(k>=numAIPlayers){
				k=0;
			}
		}while(mainDeck.sizeOfDeck()>0);

		activePlayer = randomFirstPlayer(numAIPlayers);
	}


	public int randomFirstPlayer(int numAIPlayers) {
		/**
		 * picks a randomint to decide who the first player will be
		 */
		return new Random().nextInt(numAIPlayers);
	}

	public CategoryTypes AIPlayerTopCategory(int activePlayer) {
		/**
		 * gets the top category for the ai player when its there turn to choose
		 */
		CategoryTypes topCategory = playerArray.get(activePlayer).getDeck().getTopCard().getTopCategory().getType();
		return topCategory;
	}

	public void collectTopCards() {
		/**
		 * takes all th4 players top cards from there deck and moves them to the main deck
		 * this is so they be checked for a winner
		 */
		for (int i = 0; i < playerArray.size(); i++) {
			//System.out.println(player.get(i).getName());
			mainDeck.addCard(playerArray.get(i).getDeck().getAndRemoveTopCard());
			//System.out.println("maindeck size:"+mainDeck.sizeOfDeck());
		}
	}

	// finding round winner and returning their ID number; returns -1 if draw
	public int getRoundWinner(CategoryTypes chosenCategory) {
		int roundWinner=-2;
		Card winningCard=null;
		/**
		 * gets the highest score for the player at position 0 and uses that as the benchmark
		 */
		int highestScore = mainDeck.seeCard(0).matchCategory(chosenCategory).getScore();
		/**
		 * finds if there are any higher values compared to index 0
		 */
		for (int i = 0; i < mainDeck.getDeckArray().size(); i++) {
			if(mainDeck.seeCard(i).matchCategory(chosenCategory).getScore() > highestScore) {
				highestScore = mainDeck.seeCard(i).matchCategory(chosenCategory).getScore();
				roundWinner = i;
				winningCard=mainDeck.seeCard(i);
			}
		}
		/**
		 * checks id there are any matching values
		 * if there is a matching value sets round winner to -1
		 */
		for (int k = 0; k < mainDeck.getDeckArray().size(); k++) {
			if(k != roundWinner){
				if(mainDeck.seeCard(k).matchCategory(chosenCategory).getScore() == highestScore){
					roundWinner= -1;}
				
				}
			}
		/**
		 * sets the round winning card - or equal round winning card
		 * returns the value of round winner whihc is the index in the player array or -1 for draw
		 */
		this.roundWinningCard=winningCard;
		return roundWinner;
	}

	public void transferCards(int resultInt) {
		/**
		 * moves all the cards from the main deck to the communal deck
		 * this allows them to be shuffled beofre being placed back into the winners deck
		 * or they can be stored there is a draw
		 */
		transferToCommunal(mainDeck);
		emptyMainDeck();
		if (resultInt == -2) {
			System.out.println("we fucked up"); // take out later
		} else if (resultInt > -1) {
			activePlayer = resultInt; // sets activePlayer to index number of winner
			playerArray.get(resultInt).addCards(communalDeck);
			emptyCommunal();
		}
	}

	public void transferToCommunal(Deck cards) {
		/**
		 * adds a the set of cards passed to the communal deck
		 * these shuffles these cards
		 */
		this.communalDeck.addSetOfCards(cards);
		this.communalDeck.shuffleDeck();
	}

	public void emptyCommunal() {
		/**
		 * clears the communal deck of all cards
		 */
		this.communalDeck.getDeckArray().clear();
	}

	public void emptyMainDeck() {
		/**
		 * clears the main deck of all cards
		 */
		this.mainDeck.getDeckArray().clear();
	}


	public String eliminateLoser() {
		/** 
		 * check if player has no cards left, returns String of all players eliminated
		 * removes eliminated players from player arraylist
		 */
		String eliminated = "";
		for(int i = 0; i<playerArray.size(); i++) {
			if(playerArray.get(i).isEmpty()) {
				eliminated += playerArray.get(i).getName() + " has been ELIMINATED\n";
				playerArray.remove(i);
				i--;
			}
		}return eliminated;
	}


	public String isGameOver() {
		/**
		 * checks if there is only one player left
		 * then returns a string declaring the winner
		 */
		String winner = "";
		if(playerArray.size()==1){
			gameWinner = playerArray.get(0);
			winner="Game end\n\nThe overall winner was "+playerArray.get(0).getName();
		}
		return winner;
	}


	public int getNumOfRounds() {
		return numOfRounds;
	}
	public void incrementNumOfRounds() {
		this.numOfRounds++;
	}

	public int getNumOfDraws() {
		return numOfDraws;
	}

	public Player getGameWinner() {
		return gameWinner;
	}

	public int getActivePlayer() {
		return activePlayer;
	}

	public void setActivePlayer(int activePlayer) {
		this.activePlayer=activePlayer;
	}

	public ArrayList<Player> getPlayerArray() {
		return playerArray;
	}

	public Deck getMainDeck() {
		return mainDeck;
	}

	public int numberOfPlayers(){
		return playerArray.size();
	}

	public Card getRoundWinningCard() {
		return roundWinningCard;
	}

	public int findPlayerIndex(String playerName){
		int result=-1;
		for (int i = 0; i < playerArray.size(); i++) {
			if(playerArray.get(i).getName().equalsIgnoreCase(playerName)){
				result=i;
			}
		}
		return result;
	}

	public Deck getCommunalDeck(){
		return communalDeck;
	}


}