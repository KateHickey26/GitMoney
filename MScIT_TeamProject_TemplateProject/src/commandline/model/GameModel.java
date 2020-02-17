package commandline.model;

import java.util.ArrayList;
import java.util.Random;

public class GameModel {

	private Deck communalDeck;
	private Deck mainDeck;
	private ArrayList<Player> player;
	private int Player;
	private int numOfRounds; 
	private int numOfDraws; 
	private Player gameWinner;
	private Card roundWinningCard;
	private int activePlayer;



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
		player = new ArrayList<Player>();
		/**
		 * the user is always initated as player 0
		 * the ai players and then initiated based on the number chosen
		 */
		player.add(new Player());
		player.get(0).setName("You");
		player.get(0).setPlayerID(0);
		
		for (int i = 1; i < numAIPlayers; i++) {
			player.add(new Player());
			player.get(i).setName("AI Player " + i);
			player.get(i).setPlayerID(i);
		}
		/**
		 * takes the initial deck of all 40 cards and deals it out to all of the players evenlky
		 */
		int k = 0;
		do{
			player.get(k).addOneCard(mainDeck.getAndRemoveTopCard());
			k++;
			if(k>=numAIPlayers){
				k=0;
			}
		}while(mainDeck.sizeOfDeck()>0);

		activePlayer = randomFirstPlayer(numAIPlayers);
	}


	public int randomFirstPlayer(int numAIPlayers) {
		/**
		 * picks a random int to decide who the first player will be
		 */
		return new Random().nextInt(numAIPlayers);
	}

	
	public CategoryTypes AIPlayerTopCategory(int activePlayer) {

		CategoryTypes topCategory = CategoryTypes.FLOOR;
		/**
		 * gets the top category for the ai player when its there turn to choose
		 */
		for (int i = 0; i < player.size(); i++) {
			if (player.get(i).getPlayerID() == activePlayer) {
				topCategory = player.get(i).getDeck().getTopCard().getTopCategory().getType();
			}
		}
		return topCategory;
	}


	
	public void collectTopCards() {
		/**
		 * takes all th4 players top cards from there deck and moves them to the main deck
		 * this is so they be checked for a winner
		 */
		for (int i = 0; i < player.size(); i++) {
			//System.out.println(player.get(i).getName());
			mainDeck.addCard(player.get(i).getDeck().getAndRemoveTopCard());
			//System.out.println("maindeck size:"+mainDeck.sizeOfDeck());
		}
	}

	// getting all players top cards and moving them to main deck
	public void removeTopCards() {
		for (int i = 0; i < player.size(); i++) {
			player.get(i).getDeck().removeTopCard();
		}
	}

	// finding round winner and returning their ID number; returns -1 if draw
	// finding round winner and returning their ID number; returns -1 if draw
	public int getRoundWinner(CategoryTypes chosenCategory) {
		int roundWinner=-2;
		int roundWinnerCount=0;
		Card winningCard=null;
		int highestScore = mainDeck.seeCard(0).matchCategory(chosenCategory).getScore();
		for (int i = 0; i < mainDeck.getDeckArray().size(); i++) {
			if(mainDeck.seeCard(i).matchCategory(chosenCategory).getScore()>highestScore){
				highestScore=mainDeck.seeCard(i).matchCategory(chosenCategory).getScore();
			}
		}
		//System.out.println("maindeck size:"+mainDeck.getMainDeck().size());
		for (int k = 0; k < mainDeck.getDeckArray().size(); k++) {
			if(mainDeck.seeCard(k).matchCategory(chosenCategory).getScore()==highestScore){
				roundWinner=k;
				winningCard=mainDeck.seeCard(k);
				roundWinnerCount++;
			}
		}
		if(roundWinnerCount>1){
			roundWinner=-1;
		}
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
       if (resultInt == -1) {
            System.out.println("There has been a draw"); // test line
            // axctive player stays the same // sets activePlayer to index number of winner
            //player.get(resultInt).addCards(communalDeck);
            System.out.println("Communal deck has " + communalDeck.sizeOfDeck());
        } else{
			System.out.println("The winner is " + resultInt);
            activePlayer = player.get(resultInt).getPlayerID(); //sets active player to number of index winner
			player.get(resultInt).addCards(communalDeck);
			emptyCommunal();  
        }
    }


	
	public void transferToCommunal(Deck cards) {
		/**
		 * adds a the set of cards passed to the communal deck
		 * these shuffles these cards
		 */
		communalDeck.addSetOfCards(cards);
		communalDeck.shuffleDeck();
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
		for(int i = 0; i<player.size(); i++) {
			if(player.get(i).isEmpty()) {
				eliminated += player.get(i).getName() + " has been ELIMINATED\n";
				player.remove(i);
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
		if(player.size()==1){
			gameWinner = player.get(0);
			winner="Game end\n\nThe overall winner was "+player.get(0).getName();
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
		return this.activePlayer;
	}

	public void setActivePlayer(int activePlayer) {
		this.activePlayer=activePlayer;
	}

	public ArrayList<Player> getPlayerArray() {
		return player;
	}

	public Deck getMainDeck() {
		return mainDeck;
	}

	public int numberOfPlayers(){
		return player.size();
	}

	public Card getRoundWinningCard() {
		return roundWinningCard;
	}

	public int findPlayerIndex(String playerName){
		int result=-1;
		for (int i = 0; i < player.size(); i++) {
			if(player.get(i).getName().equalsIgnoreCase(playerName)){
				result=i;
			}
		}
		return result;
	}

	public Deck getCommunalDeck(){
		return communalDeck;
	}


}