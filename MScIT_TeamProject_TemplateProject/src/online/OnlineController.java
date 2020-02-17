package online;

import java.io.BufferedReader;
import commandline.model.CategoryTypes;
import commandline.model.Deck;
import commandline.model.GameData;
import commandline.model.GameModel;
import commandline.model.Player;
import commandline.model.RoundInfo;
import database.DatabaseAccess;
import commandline.model.Card;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.ArrayList;




public class OnlineController {
    GameModel game;

    RoundInfo rInfo;
    Deck deck;
    Deck CommunalDeck = new Deck();
    int numPlayers;
    CategoryTypes chosenCategory = null;
    int roundWinner;
    Boolean humanIsActive;
    ArrayList<Player> playerList;
    GameData gameData;
    
    // user input chooses how many AI players will be in game
    // the deck is loaded 
    public OnlineController() {
        /**
         * intitates the online controller
         * always sets the nnumber of players to 5 as the online
         * is only set to work with this
         */
        deck = readInDeck("GlasgowBars.txt");
        numPlayers = 5;
        gameData = new GameData();

    }

    public void startGame(){/**
        * initiates the online version of the game 
        * creates an instance of the model and passes the number of players (5)
        * and also the deck which is written in from the text file (method at bottom of class)
        */
        game = new GameModel(numPlayers, deck);
         /**
         * initiates an instance of round info which is used to 
         * update the constant game data eg. round number, whos active etc
         */
        rInfo = new RoundInfo(game);
        playerList = game.getPlayerArray();
    }

    public void playRoundAI() {
        /**
         * this is the method strictly for an ai round as it takes in no category as param
         * 
         * the chosen category is selected by finding the top value on the active players card
         */
        chosenCategory = game.AIPlayerTopCategory(game.getActivePlayer());

         /**
         * all the top cards from the players are then collected 
         * the chosen category is then passed to the round winner method
         * to see if there has been any winner
         */
        game.collectTopCards();
        roundWinner = game.getRoundWinner(chosenCategory);
        int roundWinnerID = game.getPlayerArray().get(roundWinner).getPlayerID();

        // below method transfers the cards to communal deck, if its a draw (i.e
        // roundWinner == -1) then they stay there, otherwise
        // they are given to the player at index of roundWinner
        game.transferCards(roundWinner);

        /**
         * checks to see if any player has lost and needs to be eliminated
         */
        for (int i = 0; i < game.getPlayerArray().size(); i++) {
            if (game.getPlayerArray().get(i).isEmpty()) {
                game.getPlayerArray().remove(i);
                i--;
            }
        }
         /**
         * checks if there is only one player left
         */
        if (game.getPlayerArray().size() == 1) {
            roundWinner = 0;
            overallWinner();

        /** method transfers the cards to communal deck, if its a draw (i.e
         *  roundWinner == -1) then they stay there, otherwise
         *  they are given to the player at index of roundWinner
         *  game.transferCards(roundWinner);
         */
        } else if (roundWinner == -1) {
            game.removeTopCards();
            game.transferToCommunal(game.getMainDeck());
            gameData.addOneNoOfDraws();
        } else {
            for (int i = 0; i < game.getPlayerArray().size(); i++) {
                if (game.getPlayerArray().get(i).getPlayerID() == roundWinnerID) {
                    this.roundWinner = i;
                }
            }
            /**
             * adds the round winner to the game data object
             */
            gameData.winnerCounter(game.getPlayerArray().get(roundWinner));
        }

        /**
        * increments the number of rounds for the model and also the gamedata
        * also updates the round info object
        */
        game.incrementNumOfRounds();
        gameData.addOneNoOfRounds();
        rInfo.setRoundInfo(game);
    }

    
    public void playRoundHuman(int catChoice) {
        /**
         * method to play a round when the human player is active player 
         * human round method takes an integer as a parameter to dedide the category
         * this is done via the dropdown menu on the online view
         * 
         * will then set the category to the users choice
         */
        chosenCategory = game.getPlayerArray().get(0).getDeck().seeCard(0).categoryType(catChoice - 1);

        // collects the top cards from each player's deck
        game.collectTopCards();

        /**
         * finds the winner from all the top cards
         * finds the players id instead of index as need to check for
         * elminated players which would alter the index
         *
         */
        roundWinner = game.getRoundWinner(chosenCategory);
        int roundWinnerID = game.getPlayerArray().get(roundWinner).getPlayerID();

        /** method transfers the cards to communal deck, if its a draw (i.e
         *  roundWinner == -1) then they stay there, otherwise
         *  they are given to the player at index of roundWinner
         *  game.transferCards(roundWinner);
         */
        game.transferCards(roundWinner);
        /**
         * all the top cards from the players are then collected 
         * the chosen category is then passed to the round winner method
         * to see if there has been any winner
         */

        for (int j = 0; j < game.getPlayerArray().size(); j++) {
            if (game.getPlayerArray().get(j).isEmpty()) {
                game.getPlayerArray().remove(j);
                j--;
            }
        }

        if (game.getPlayerArray().size() == 1) {
            roundWinner = 0;
            overallWinner();
        } else {
            for (int i = 0; i < game.getPlayerArray().size(); i++) {
                if (game.getPlayerArray().get(i).getPlayerID() == roundWinnerID) {
                    this.roundWinner = i;
                    game.setActivePlayer(this.roundWinner);
                }
            }
            /**
             * adds the round winner to the game data object
             */
            gameData.winnerCounter(game.getPlayerArray().get(roundWinner));
        }
        /**
        * adds the round winner to the game data object
        */
        game.incrementNumOfRounds();
        gameData.addOneNoOfRounds();
        rInfo.setRoundInfo(game);
    }
    public void overallWinner(){
        /**
		 * adds the overall winner to the data object
		 * then passes the object to the upload method for the database
		*/
		gameData.setOverallWinner(game.getGameWinner());
		DatabaseAccess.uploadData(gameData);


    }

    public ArrayList<Player> getPlayerList() {
        return this.playerList;
    }

    public RoundInfo getRoundInfo() {
        return rInfo;
    }

    public int getActivePlayer() {
        return game.getActivePlayer();
    }

    public CategoryTypes getChosenCategory() {
        return this.chosenCategory;
    }

    // Trying to get a method to show the winner of a round but who is it?????
    public String getRoundWinner() {
        this.game.getRoundWinningCard();
        int winner = this.game.getRoundWinner(chosenCategory);
        String winnerIs = " " + winner;
        return winnerIs;
    }

    // method to return the human player's card
    // method to return the human player's card
    public Map getHumanCard() {
        Player hPlayer = null;
        for (int i = 0; i < this.game.getPlayerArray().size(); i++) {
            if (this.game.getPlayerArray().get(i).getPlayerID() == 0) {
                hPlayer = this.game.getPlayerArray().get(i);
            }
        }

        Map humanCard = hPlayer.getDeck().getTopCard().getCardAsMap();
        humanCard.put("deckSize", "" + hPlayer.getDeck().sizeOfDeck());
        return humanCard;
        // making hashmap for card
    }

    public Map getAi1TopCard() {
        Player Ai1 = null;

        for (int i = 0; i < this.game.getPlayerArray().size(); i++) {
            if (this.game.getPlayerArray().get(i).getPlayerID() == 1) {
                Ai1 = this.game.getPlayerArray().get(i);
            }
        }

        this.game.getPlayerArray().get(1);
        Map Ai1Card = Ai1.getDeck().getTopCard().getCardAsMap();
        Ai1Card.put("deckSize", "" + Ai1.getDeck().sizeOfDeck());
        return Ai1Card;
        // making hashmap for card
    }

    public Map getAi2TopCard() {
        Player Ai2 = null;

        for (int i = 0; i < this.game.getPlayerArray().size(); i++) {
            if (this.game.getPlayerArray().get(i).getPlayerID() == 2) {
                Ai2 = this.game.getPlayerArray().get(i);
            }
        }

        Map Ai2Card = Ai2.getDeck().getTopCard().getCardAsMap();
        Ai2Card.put("deckSize", "" + Ai2.getDeck().sizeOfDeck());
        return Ai2Card;
        // making hashmap for card
    }

    public Map getAi3TopCard() {
        Player Ai3 = null;

        for (int i = 0; i < this.game.getPlayerArray().size(); i++) {
            if (this.game.getPlayerArray().get(i).getPlayerID() == 3) {
                Ai3 = this.game.getPlayerArray().get(i);
            }
        }

        Map Ai3Card = Ai3.getDeck().getTopCard().getCardAsMap();
        Ai3Card.put("deckSize", "" + Ai3.getDeck().sizeOfDeck());
        return Ai3Card;
        // making hashmap for card
    }

    public Map getAi4TopCard() {
        Player Ai4 = null;

        for (int i = 0; i < this.game.getPlayerArray().size(); i++) {
            if (this.game.getPlayerArray().get(i).getPlayerID() == 4) {
                Ai4 = this.game.getPlayerArray().get(i);
                }
            }
    
            Map Ai4Card = Ai4.getDeck().getTopCard().getCardAsMap();
            Ai4Card.put("deckSize", "" + Ai4.getDeck().sizeOfDeck());
            return Ai4Card;
            //making hashmap for card
        }
    
    private static Deck readInDeck(String pathName) {

        // will read in info from the txt file containing deck and create a deck object based on this
        Deck inputDeck = new Deck();
        try {
            Scanner scanner = new Scanner(new BufferedReader(new FileReader(pathName)));
            while(scanner.hasNextLine()) {
                String name = scanner.next();
				int sticky = (int) Integer.parseInt(scanner.next());
				int pintPrice = (int) Integer.parseInt(scanner.next());
				int pubQuiz = (int) Integer.parseInt(scanner.next());
				int atmosphere = (int) Integer.parseInt(scanner.next());
				int music = (int) Integer.parseInt(scanner.next());
				//System.out.println("here");
				inputDeck.addCard(new Card(name, sticky, pintPrice, pubQuiz, atmosphere, music));
            }
            scanner.close();
            

        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } 
        catch(NoSuchElementException ex2) {
            ex2.printStackTrace();
        } 
        
        return inputDeck;


        
    }



}