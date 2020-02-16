package online;
import commandline.*;
import commandline.model.GameModel;
import commandline.model.Player;

import java.util.ArrayList;
import java.io.PrintStream;

// this is a round info object - it should return relevant information about each round
// to be used in displaying round info in the online GUI 
public class RoundInfo {
    int numberOfPlayers;
    int roundCounter;
    int communalDeckSize;
    String activePlayer;

    public RoundInfo (GameModel g) {
        // creating instance of game already deals the cards 
        // user input needs to be passed to constructor - how many AI players
        roundCounter = g.getNumOfRounds();
        numberOfPlayers = g.getPlayer().size();
        
        // code to get the name of the active player 
        int p = g.getActivePlayer();
        ArrayList<Player> playerList = g.getPlayer();
        Player activeP = playerList.get(p);
        activePlayer = activeP.getName();

        communalDeckSize = g.getCommunalDeck().sizeOfDeck();
    }

    public void setRoundInfo (GameModel g) {
        // creating instance of game already deals the cards 
        // user input needs to be passed to constructor - how many AI players
        roundCounter = g.getNumOfRounds();
        numberOfPlayers = g.getPlayer().size();
        
        // code to get the name of the active player 
        int p = g.getActivePlayer();
        ArrayList<Player> playerList = g.getPlayer();
        Player activeP = playerList.get(p);
        activePlayer = activeP.getName();

        communalDeckSize = g.getCommunalDeck().sizeOfDeck();
    }

    public RoundInfo getRoundInfo(){
        return this;
    }

    public void print (PrintStream ps){
        ps.print(" active player is: " + this.activePlayer + " number of players: " + this.numberOfPlayers 
        + " round number: " + this.roundCounter +
        " Communal deck size: " + this.communalDeckSize );

    }

}