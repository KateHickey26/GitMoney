package online;
import commandline.*;
import java.util.ArrayList;

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

}