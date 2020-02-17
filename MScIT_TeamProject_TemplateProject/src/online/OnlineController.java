package online;

import java.io.BufferedReader;
import commandline.model.CategoryTypes;
import commandline.model.Deck;
import commandline.model.GameModel;
import commandline.model.Player;
import commandline.model.RoundInfo;
import commandline.model.Card;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class OnlineController {
    GameModel game;

    RoundInfo rInfo;
    Deck deck;
    Deck CommunalDeck = new Deck();
    int aiPlayerNum;
    CategoryTypes chosenCategory = null;
    int roundWinner;
    Boolean humanIsActive;
    public OnlineController() {
        deck = readInDeck("/Users/markmorrison/Desktop/GlasgowBars2.txt");


        
        aiPlayerNum = 5;
    }

    public void startGame() {

        game = new GameModel(aiPlayerNum, deck);
        // creating a new instance of the game creates a communal deck and
        // shuffles the main deck
        // it creates an arraylist of players
        // it deals the deck between the players
        // randomly chooses an active player
        rInfo = new RoundInfo(game);
        // return rInfo; //maybe change return?? instead we could have a getRInfo method
        // that we call at any time
    }

    public void playRoundAI() {

        chosenCategory = game.AIPlayerTopCategory(game.getActivePlayer());

        // collect Top cards method takes each players top cards to be able to compare
        // them
        // this is handled within the game model (or should be)
        game.collectTopCards();
        roundWinner = game.getRoundWinner(chosenCategory);
        int roundWinnerID = game.getPlayerArray().get(roundWinner).getPlayerID();

        // below method transfers the cards to communal deck, if its a draw (i.e
        // roundWinner == -1) then they stay there, otherwise
        // they are given to the player at index of roundWinner
        game.transferCards(roundWinner);

        // checks if any of the players have no cards left. If so, they are eliminated.
        // i is decremented in that case so that no players are skipped
        for (int i = 0; i < game.getPlayerArray().size(); i++) {
            if (game.getPlayerArray().get(i).isEmpty()) {
                game.getPlayerArray().remove(i);
                i--;
            }
        }
        // check if the game has ended
        if (game.getPlayerArray().size() == 1) {
            roundWinner = 0;
        } else if (roundWinner == -1) {
            game.transferToCommunal(game.getMainDeck());
        } else {
            for (int i = 0; i < game.getPlayerArray().size(); i++) {
                if (game.getPlayerArray().get(i).getPlayerID() == roundWinnerID) {
                    this.roundWinner = i;
                }
            }
        }

        // remember to add persistent game stat method - MarksMethod1

        // increments rounds
        game.incrementNumOfRounds();

        rInfo.setRoundInfo(game);

        // return rInfo;
    }

    // method to play a round when the human player is active player

    // human round method takes an integer as a parameter - needs to be input via
    // actionEvent / actionEventlistener
    //
    public void playRoundHuman(int catChoice) {
        // sets the category as the players choice - retrieves top card info
        chosenCategory = game.getPlayerArray().get(0).getDeck().seeCard(0).categoryType(catChoice - 1);

        // collects the top cards from each player's deck
        game.collectTopCards();

        // sets the winner of the round
        roundWinner = game.getRoundWinner(chosenCategory);
        int roundWinnerID = game.getPlayerArray().get(roundWinner).getPlayerID();

        // transfers the cards to the round winner if there is no draw
        game.transferCards(roundWinner);

        for (int j = 0; j < game.getPlayerArray().size(); j++) {
            if (game.getPlayerArray().get(j).isEmpty()) {
                game.getPlayerArray().remove(j);
                j--;
            }
        }

        if (game.getPlayerArray().size() == 1) {
            roundWinner = 0;
            // maybe change to game winner idk?
        } else if (roundWinner != -1) {
            game.setActivePlayer(roundWinner);
        } else {
            for (int i = 0; i < game.getPlayerArray().size(); i++) {
                if (game.getPlayerArray().get(i).getPlayerID() == roundWinnerID) {
                    this.roundWinner = i;
                }
            }
        }

        game.incrementNumOfRounds();

        rInfo.setRoundInfo(game);
    }

    public RoundInfo getRoundInfo() {
        return rInfo;
    }

    public int getActivePlayer() {
        return game.getActivePlayer();
    }

    public CategoryTypes getChosenCatergory() {
        return this.chosenCategory;
    }

    // Trying to get a method to show the winner of a round but who is it?????
    public String getRoundWinner() {
        this.game.getRoundWinningCard();
        int winner = this.game.getRoundWinner(chosenCategory);
        String winnerIs = " " + winner;
        return winnerIs;
    }

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
        // making hashmap for card
    }

    private static Deck readInDeck(String pathName) {

        // will read in info from the txt file containing deck and create a deck object
        // based on this
        Deck inputDeck = new Deck();
        try {
            Scanner scanner = new Scanner(new BufferedReader(new FileReader(pathName)));
            while (scanner.hasNextLine()) {
                String name = scanner.next();
                int sticky = (int) Integer.parseInt(scanner.next());
                int pintPrice = (int) Integer.parseInt(scanner.next());
                int pubQuiz = (int) Integer.parseInt(scanner.next());
                int atmosphere = (int) Integer.parseInt(scanner.next());
                int music = (int) Integer.parseInt(scanner.next());
                // System.out.println("here");
                inputDeck.addCard(new Card(name, sticky, pintPrice, pubQuiz, atmosphere, music));
            }
            scanner.close();

        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (NoSuchElementException ex2) {
            ex2.printStackTrace();
        }

        return inputDeck;

    }

}