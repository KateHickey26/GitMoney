package commandline;

import java.io.*;
import java.util.NoSuchElementException;
import java.util.Scanner;
import commandline.model.*;
import database.*;

/**
 * Top Trumps command line application
 */
public class TopTrumpsCLIApplication {

	/**
	 * This main method is called by TopTrumps.java when the user specifies that
	 * they want to run in command line mode. The contents of args[0] is whether we
	 * should write game logs to a file.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {

		/* Initialising variables & objects needed for dialogue */
		Scanner scanner = new Scanner(System.in);
		int userInput;
		int numberOfPlayers;
		int roundCounter;
		GameModel game;
		boolean gameOver;
		boolean humanIsActivePlayer;
		CategoryTypes chosenCategory = null;
		int roundWinner;
		Deck deckOfAllCards;
		String loserEliminatedMessage;
		String isGameOverMessage;
		String activePlayerName = "";
		GameData gameData = new GameData();

		/**
		 * boolean writeGameLogsToFile = false; // Should we write game logs to file? if
		 * (args[0].equalsIgnoreCase("true")) writeGameLogsToFile=true; // Command line
		 * selection
		 */
		// State
		boolean userWantsToQuit = false; // flag to check whether the user wants to quit the application

		// Loop until the user wants to exit the game
		while (!userWantsToQuit) {

			deckOfAllCards = new Deck();
			deckOfAllCards = inputTxt("GlasgowBars.txt");
			System.out.println("deck of all cards ready");
			System.out.println("Size of deck = " + deckOfAllCards.sizeOfDeck());

			// prints all the cards in the deck
				//for (int k = 0; k < deckOfAllCards.sizeOfDeck(); k++) {
				//System.out.println(deckOfAllCards.seeCard(k).getName()); }
			

			/*
			 * Dialogue #1: ask user if he wants to see game statistics or play the game or
			 * quit
			 */
			userInput = promptUserInput("Do you want to see past results or play a game?\n"
					+ "   1: Print Game Statistics\n" + "   2: Play game\n" + "Enter the number for your selection: ",
					new int[] { 1, 2, 3 });

			if (userInput == 1) {
				/**
				 * prints the game statistics if chosen 
				 * then asks if the user wants to continue to the game selection screen or quit
				 */
				System.out.println("\nTotal number of Games: " + DatabaseAccess.getTotalNumerOfGames()
						+ "\nThe average number of Draws: " + DatabaseAccess.getAvgNoDraws()
						+ "\nThe Max number of Rounds Played: " + DatabaseAccess.getMaxNoRounds()
						+ "\nThe Number of AI Wins: " + DatabaseAccess.getNumberOfComputerWins()
						+ "\nThe Number of User Wins: " + DatabaseAccess.getNumberOfUserWins());
				System.out.println("\n\nDo you want to continue to selection screen?\nIf yes enter 1.\nIf no enter 2.");
				if (scanner.nextInt() == 1) {
					continue;
				} else {
					userWantsToQuit = true;
					break;
				}
			}
			/* Dialogue #2: ask user how many AI players to include in the game */
			numberOfPlayers = promptUserInput(
					"\nHow many players do you want in the game?\n"
							+ "Minimum of 2 and maximum of 5 players permitted.\n" + "Enter the number of players: ",
					new int[] { 2, 3, 4, 5 });

			/* Initialising game below */

			game = new GameModel(numberOfPlayers, deckOfAllCards);

			System.out.println("\n\nGame Start");
			roundCounter = 1;
			gameOver = false;
			
			while (!gameOver) {

				// Printing number of players 
				 System.out.println("\nThere are "+game.numberOfPlayers()+" players in the game");
				 System.out.println("\nPlayers played the following "+game.getMainDeck().sizeOfDeck()+" cards:"); 
				 	for (int k = 0; k < game.getMainDeck().sizeOfDeck();k++) { 
						 System.out.println(game.getMainDeck().seeCard(k).getName()); }
				 // Printing active player
				System.out.println("Active player: "+game.getActivePlayer());

				// finds value for humanIsActivePlayer variable
				humanIsActivePlayer = false;
				if (game.getPlayer().get(game.getActivePlayer()).getName() == "You") {
					humanIsActivePlayer = true;
				}
				
				// AI PLAYER LOOP
				// Loop for AI player being active player
				while (!humanIsActivePlayer) {
					System.out.println("Round " + roundCounter);
					System.out.println("Round " + roundCounter + ": Players have drawn their cards");

					if (game.getPlayer().get(0).getName() == "You") {
						System.out.println("You drew '" + game.getPlayer().get(0).getDeck().seeCard(0).getName()
								+ "':\n" + "   > " + game.getPlayer().get(0).getDeck().seeCard(0).categoryName(0) + ": "
								+ game.getPlayer().get(0).getDeck().seeCard(0).categoryValue(0) + "\n" + "   > "
								+ game.getPlayer().get(0).getDeck().seeCard(0).categoryName(1) + ": "
								+ game.getPlayer().get(0).getDeck().seeCard(0).categoryValue(1) + "\n" + "   > "
								+ game.getPlayer().get(0).getDeck().seeCard(0).categoryName(2) + ": "
								+ game.getPlayer().get(0).getDeck().seeCard(0).categoryValue(2) + "\n" + "   > "
								+ game.getPlayer().get(0).getDeck().seeCard(0).categoryName(3) + ": "
								+ game.getPlayer().get(0).getDeck().seeCard(0).categoryValue(3) + "\n" + "   > "
								+ game.getPlayer().get(0).getDeck().seeCard(0).categoryName(4) + ": "
								+ game.getPlayer().get(0).getDeck().seeCard(0).categoryValue(4));
						System.out.println(
								"There are " + game.getPlayer().get(0).getDeck().sizeOfDeck() + " cards in your deck");
					}
					chosenCategory = game.AIPlayerTopCategory(game.getActivePlayer());
					

					// chosenCategory =
					// game.getPlayer().get(0).getDeck().getTopCard().getCats()[0].getType();

					game.collectTopCards();
					roundWinner = game.getRoundWinner(chosenCategory);
					System.out.println("Roundwinner: " + roundWinner);

					if (roundWinner > -1) {
						gameData.winnerCounter(game.getPlayer().get(roundWinner));
						System.out.println("\nRound " + roundCounter + ": Player "
								+ game.getPlayer().get(roundWinner).getName() + " won this round");

						System.out.println("\nThe winning card was '" + game.getRoundWinningCard().getName() + "':\n"
								+ "   > " + game.getRoundWinningCard().categoryName(0) + ": "
								+ game.getRoundWinningCard().categoryValue(0) + "\n" + "   > "
								+ game.getRoundWinningCard().categoryName(1) + ": "
								+ game.getRoundWinningCard().categoryValue(1) + "\n" + "   > "
								+ game.getRoundWinningCard().categoryName(2) + ": "
								+ game.getRoundWinningCard().categoryValue(2) + "\n" + "   > "
								+ game.getRoundWinningCard().categoryName(3) + ": "
								+ game.getRoundWinningCard().categoryValue(3) + "\n" + "   > "
								+ game.getRoundWinningCard().categoryName(4) + ": "
								+ game.getRoundWinningCard().categoryValue(4));
						System.out.println("\nComparison category: " + chosenCategory.getName());
					}
					
					System.out.println("\nWinner: "+roundWinner);

					// testing below
					//System.out.println("Chosen category: "+chosenCategory.getName());

					game.transferCards(roundWinner);
					// print below for testing
					System.out.println("Transfer successful");

					if (roundWinner > -1) {
						game.setActivePlayer(roundWinner);
					 	activePlayerName = game.getPlayer().get(game.getActivePlayer()).getName();
					}

					if (roundWinner == -1) {
						gameData.addOneNoOfDraws();
						System.out.println("\nRound " + roundCounter + ": This round was a Draw, communal deck now has "
								+ game.getCommunalDeck().sizeOfDeck() + " cards");
					}

					loserEliminatedMessage = game.eliminateLoser();
					System.out.println(loserEliminatedMessage);

					isGameOverMessage = game.winnerCheck();
					System.out.println(isGameOverMessage);
													
					//activePlayerName=game.getPlayer().get(game.getActivePlayer()).getName();
					
					if (activePlayerName != "") {
						game.setActivePlayer(game.findPlayerIndex(activePlayerName));
					}

					if (game.getGameWinner() != null) {
						gameOver = true;
						break;
					}

					 //System.out.println("Active Player = " + game.getActivePlayer());
					 //System.out.println(game.getPlayer().get(game.getActivePlayer()).getName());

					if (game.getPlayer().get(game.getActivePlayer()).getName() == "You") {
						humanIsActivePlayer = true;
					}

					roundCounter++;
					gameData.addOneNoOfRounds();
				}



				// HUMAN PLAYER LOOP
				if (!gameOver) {
					System.out.println("Round " + roundCounter);
					System.out.println("\nRound " + roundCounter + ": Players have drawn their cards");

					if (game.getPlayer().get(0).getName() == "You") {
						System.out.println("\nYou drew '" + game.getPlayer().get(0).getDeck().seeCard(0).getName()
								+ "':\n" + "   > " + game.getPlayer().get(0).getDeck().seeCard(0).categoryName(0) + ": "
								+ game.getPlayer().get(0).getDeck().seeCard(0).categoryValue(0) + "\n" + "   > "
								+ game.getPlayer().get(0).getDeck().seeCard(0).categoryName(1) + ": "
								+ game.getPlayer().get(0).getDeck().seeCard(0).categoryValue(1) + "\n" + "   > "
								+ game.getPlayer().get(0).getDeck().seeCard(0).categoryName(2) + ": "
								+ game.getPlayer().get(0).getDeck().seeCard(0).categoryValue(2) + "\n" + "   > "
								+ game.getPlayer().get(0).getDeck().seeCard(0).categoryName(3) + ": "
								+ game.getPlayer().get(0).getDeck().seeCard(0).categoryValue(3) + "\n" + "   > "
								+ game.getPlayer().get(0).getDeck().seeCard(0).categoryName(4) + ": "
								+ game.getPlayer().get(0).getDeck().seeCard(0).categoryValue(4));
						System.out.println(
								"\nThere are " + game.getPlayer().get(0).getDeck().sizeOfDeck() + " cards in your deck");
					}

					userInput = promptUserInput("\nIt is your turn to select a category, the categories are:\n" + "   1: "
							+ game.getPlayer().get(0).getDeck().seeCard(0).categoryName(0) + "\n" + "   2: "
							+ game.getPlayer().get(0).getDeck().seeCard(0).categoryName(1) + "\n" + "   3: "
							+ game.getPlayer().get(0).getDeck().seeCard(0).categoryName(2) + "\n" + "   4: "
							+ game.getPlayer().get(0).getDeck().seeCard(0).categoryName(3) + "\n" + "   5: "
							+ game.getPlayer().get(0).getDeck().seeCard(0).categoryName(4) + "\n"
							+ "Enter the number for your attribute: ", new int[] { 1, 2, 3, 4, 5 });

					chosenCategory = game.getPlayer().get(0).getDeck().seeCard(0).categoryType(userInput - 1);

					game.collectTopCards();

					roundWinner = game.getRoundWinner(chosenCategory);
					// testing below
					System.out.println("Roundwinner: " + roundWinner);

					if (roundWinner > -1) {
						gameData.winnerCounter(game.getPlayer().get(roundWinner));
						System.out.println("Round " + roundCounter + ": Player "
								+ game.getPlayer().get(roundWinner).getName() + " won this round");

						System.out.println("\nThe winning card was '" + game.getRoundWinningCard().getName() + "':\n"
								+ "   > " + game.getRoundWinningCard().categoryName(0) + ": "
								+ game.getRoundWinningCard().categoryValue(0) + "\n" + "   > "
								+ game.getRoundWinningCard().categoryName(1) + ": "
								+ game.getRoundWinningCard().categoryValue(1) + "\n" + "   > "
								+ game.getRoundWinningCard().categoryName(2) + ": "
								+ game.getRoundWinningCard().categoryValue(2) + "\n" + "   > "
								+ game.getRoundWinningCard().categoryName(3) + ": "
								+ game.getRoundWinningCard().categoryValue(3) + "\n" + "   > "
								+ game.getRoundWinningCard().categoryName(4) + ": "
								+ game.getRoundWinningCard().categoryValue(4));
						System.out.println("\nComparison category: " + chosenCategory.getName());
					}
					// game.removeTopCards();

					game.transferCards(roundWinner);
					// testing below
					//System.out.println("Transfer successful");

					if (roundWinner > -1) {
						game.setActivePlayer(roundWinner);
						activePlayerName = game.getPlayer().get(game.getActivePlayer()).getName();
						game.setActivePlayer(game.findPlayerIndex(activePlayerName));
					}

					if (roundWinner == -1) {
						gameData.addOneNoOfDraws();
						System.out.println("\nRound " + roundCounter + ": This round was a Draw, common pile now has "
								+ game.getCommunalDeck().sizeOfDeck() + " cards");
					}

					loserEliminatedMessage = game.eliminateLoser();
					System.out.println(loserEliminatedMessage);


					isGameOverMessage = game.winnerCheck();
					System.out.println(isGameOverMessage);
					
					//activePlayerName=game.getPlayer().get(game.getActivePlayer()).getName();
					if (activePlayerName != "") {
						game.setActivePlayer(game.findPlayerIndex(activePlayerName));
					}

					if (game.getGameWinner() != null) {
						gameOver = true;
						break;
					}

					if (game.getPlayer().get(game.getActivePlayer()).getName() != "You") {
						humanIsActivePlayer = false;
					}

					roundCounter++;
					gameData.addOneNoOfRounds();
				}

			}
			/**
			 * adds the overall winner to the data object
			 * then passes the object to the upload method for the database
			 */
		gameData.setOverallWinner(game.getGameWinner());
		DatabaseAccess.uploadData(gameData);

		}
		scanner.close();

	}

	// Method for prompting user for input until user types valid input
	private static int promptUserInput(String userMessage, int[] userOptions) {
		Scanner scanner = new Scanner(System.in);
		int userInput;
		while (true) {
			System.out.print(userMessage);
			userInput = scanner.nextInt();
			scanner.nextLine();
			if (!(userInput == 0)) {
				if (contains(userOptions, userInput)) {
					return userInput;
				}
			}
			scanner.close();
			System.out.println("Invalid user input. Please enter one of the options provided.");
		}
	}

	// Method for checking if an array contains a value
	public static boolean contains(int[] array, int value) {
		for (int item : array) {
			if (item == value) {
				return true;
			}
		}
		return false;
	}

	public static Deck inputTxt(String pathName) {
		/*
		 * inputs attributes into card objects and into deck need to change file name
		 * accordingly
		 */
		Deck inputDeck = new Deck();
		// System.out.println("file found");
		// StandardCharsets.UTF_8.name())
		try {
			Scanner scanner2 = new Scanner(new BufferedReader(new FileReader(pathName)));
			while (scanner2.hasNextLine()) {
				String name = scanner2.next();
				int sticky = (int) Integer.parseInt(scanner2.next());
				int pintPrice = (int) Integer.parseInt(scanner2.next());
				int pubQuiz = (int) Integer.parseInt(scanner2.next());
				int atmosphere = (int) Integer.parseInt(scanner2.next());
				int music = (int) Integer.parseInt(scanner2.next());
				// System.out.println("here");
				inputDeck.addCard(new Card(name, sticky, pintPrice, pubQuiz, atmosphere, music));
			}
			scanner2.close();
		} catch (FileNotFoundException ex) {
			ex.printStackTrace();
		} catch (NoSuchElementException ex2) {
			ex2.printStackTrace();
		}
		/*
		 * catch (FileNotFoundException e) { e.printStackTrace(); }
		 */
		return inputDeck;
	}

}

// additional notes:
// game is expected to have a 'test log' that stores a log of every action that
// happens in the game