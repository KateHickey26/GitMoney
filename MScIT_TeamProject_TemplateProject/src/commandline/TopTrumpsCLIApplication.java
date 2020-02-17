package commandline;

import java.io.*;
import java.sql.Timestamp;
import java.util.Calendar;
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
		Timestamp currentTimestamp = new Timestamp(Calendar.getInstance().getTime().getTime());


		boolean writeGameLogsToFile = false; // Should we write game logs to file?
		if (args[0].equalsIgnoreCase("true")) writeGameLogsToFile=true; // Command line selection
		
		// State
		boolean userWantsToQuit = false; // flag to check whether the user wants to quit the application
		
		// Loop until the user wants to exit the game
		// State
		// Loop until the user wants to exit the game
		while (!userWantsToQuit) {

			/**
			 * initiates the main deck of cards and sets the loaction of the 
			 * text file to scan in all the cards
			 */
			deckOfAllCards = new Deck();
			deckOfAllCards = inputTxt("/Users/markmorrison/Desktop/GlasgowBars2.txt");
			/*
			 * Dialogue #1: ask user if he wants to see game statistics or play the game or
			 * quit
			 */
			userInput = promptUserInput("Do you want to see past results or play a game?\n"
					+ "   1: Print Game Statistics\n" + "   2: Play game\n" + "Enter the number for your selection: ",
					new int[] { 1, 2, 3 });


			if (userInput == 1) {
				/**
				 * prints the game statistcics if chosen 
				 * then asks if the user wants to continue to the game selection screen or quit
				 */
				System.out.println("\nTotal number of Games: " + DatabaseAccess.getTotalNumerOfGames()
						+ "\nThe average number of Draws: " + DatabaseAccess.getAvgNoDraws()
						+ "\nThe Max number of Rounds Played: " + DatabaseAccess.getMaxNoRounds()
						+ "\nThe Number of AI Wins: " + DatabaseAccess.getNumberOfComputerWins()
						+ "\nThe Number of User Wins: " + DatabaseAccess.getNumberOfUserWins());
				System.out.println("\n\nDo you want to continue to selection screen?\n	1: Selection Screen\n	2: Quit Game");
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

			/**
			 * initialising gamne below
			 * take input of the number of ai players that has been chosen
			 * and also takes the full deck of cards to be dealt out
			 */
			game = new GameModel(numberOfPlayers, deckOfAllCards);
			

			/*
			 * Dialogue Loop: On each round: -- if the user is active player, show their top
			 * card and then ask them to choose category -- if an AI player is active, play
			 * the rounds automatically, so that the AI chooses category and then cards are
			 * compared
			 */

			System.out.println("\n\nGame Start");
			if(writeGameLogsToFile == true){
				updateTestLogFile("TestLog.txt", "Game Start at "+ currentTimestamp +"\n");
				updateTestLogFile("TestLog.txt", "The game has "+numberOfPlayers+" AI players\n");
			}

			roundCounter = 1;
			gameOver = false;
			while (!gameOver) {
				/**
				 * this will continue to loop while the user has not decided they want to quit
				 */
				humanIsActivePlayer = false;
				if (game.getPlayerArray().get(game.getActivePlayer()).getName() == "You") {
					humanIsActivePlayer = true;
				}
				while (!humanIsActivePlayer) {

					System.out.println("Round " + roundCounter);
					System.out.println("Round " + roundCounter + ": Players have drawn their cards");
					if(writeGameLogsToFile == true){
						updateTestLogFile("TestLog.txt", "\nRound "+roundCounter+": "+
						game.getPlayerArray().get(game.getActivePlayer()).getName()+" is active player ###############");
					}

					if (game.getPlayerArray().get(0).getName() == "You") {
						/**
						 * gets all the category names and values to print out the users card
						 * probably should have made this a stand alone methtod as is used multiple times
						 */
						System.out.println("Your drew '" + game.getPlayerArray().get(0).getDeck().seeCard(0).getName()
								+ "':\n" + "   > " + game.getPlayerArray().get(0).getDeck().seeCard(0).categoryName(0) + ": "
								+ game.getPlayerArray().get(0).getDeck().seeCard(0).categoryValue(0) + "\n" + "   > "
								+ game.getPlayerArray().get(0).getDeck().seeCard(0).categoryName(1) + ": "
								+ game.getPlayerArray().get(0).getDeck().seeCard(0).categoryValue(1) + "\n" + "   > "
								+ game.getPlayerArray().get(0).getDeck().seeCard(0).categoryName(2) + ": "
								+ game.getPlayerArray().get(0).getDeck().seeCard(0).categoryValue(2) + "\n" + "   > "
								+ game.getPlayerArray().get(0).getDeck().seeCard(0).categoryName(3) + ": "
								+ game.getPlayerArray().get(0).getDeck().seeCard(0).categoryValue(3) + "\n" + "   > "
								+ game.getPlayerArray().get(0).getDeck().seeCard(0).categoryName(4) + ": "
								+ game.getPlayerArray().get(0).getDeck().seeCard(0).categoryValue(4));
						System.out.println(
								"There are " + game.getPlayerArray().get(0).getDeck().sizeOfDeck() + " cards in your deck");
					}
					/**
					 * for the ai player chooses the highest value on the card
					 */
					chosenCategory = game.AIPlayerTopCategory(game.getActivePlayer());
					/**
					 * takes all the top cards of the active players and adds them to a lit
					 * so they can be checked for the winner
					 */
					game.collectTopCards();
					/**
					 * passes the chosen category and checks for a winning card
					 */
					roundWinner = game.getRoundWinner(chosenCategory);
					/**
					 * checks if there is a round winner
					 * if the value is not -1 then there must be a winner
					 */
					if (roundWinner > -1) {
						/**
						 * again prints out the winner and the values of the winning card
						 */
						gameData.winnerCounter(game.getPlayerArray().get(roundWinner));
						System.out.println("Round " + roundCounter + ": Player "
								+ game.getPlayerArray().get(roundWinner).getName() + " won this round");

						System.out.println("The winning card was '" + game.getRoundWinningCard().getName() + "':\n"
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
						System.out.println("Comparison category: " + chosenCategory.getName());
					}
					
					/**
					 * will transfer the card to the winners deck and if there is a draw
					 * will keep them in the communal deck
					 */
					game.transferCards(roundWinner);

					/**
					 * sets the active player as the winner if there has been a winner
					 * 
					 */
					if (roundWinner > -1) {
						game.setActivePlayer(roundWinner);
						activePlayerName = game.getPlayerArray().get(game.getActivePlayer()).getName();
					}

					if (roundWinner == -1) {
						gameData.addOneNoOfDraws();
						System.out.println("Round " + roundCounter + ": This round was a Draw, common pile now has "
								+ game.getCommunalDeck().sizeOfDeck() + " cards");
					}

					loserEliminatedMessage = game.eliminateLoser();
					// need method public String eliminateLosers()
					// which iterates through remaining players and removes them from the ListArray
					// if they have no cards left
					// method should also return a String message which says which players have been
					// eliminated


					/**
					 * moved the game log file writer to after eliminated player because players would have 0 cards 
					 * and it would be trying to get top card causimng out of bounds exception
					 */
					if(writeGameLogsToFile == true){
						updateTestLogFile("TestLog.txt","Active player chose category: "+chosenCategory.getName());
						updateTestLogFile("TestLog.txt", "Players and their decks: ");

						//test print ************************************
						System.out.println("Array size" + game.getPlayerArray().size());

						for (int k = 0; k < game.getPlayerArray().size(); k++) {
							updateTestLogFile("TestLog.txt", game.getPlayerArray().get(k).getName() + " has "
									+ game.getPlayerArray().get(k).getDeck().sizeOfDeck() + " cards. " + "Top card is " +
									"Top card is "+game.getPlayerArray().get(k).getPlayersTopCard());
						}
					}

					System.out.println(loserEliminatedMessage);

					// ###### IMPORTANT: consider here what to do if there is a draw in the final
					// game and no cards

					isGameOverMessage = game.isGameOver();
					// need method public String isGameOver()
					// which returns a message to the user if there is only one player left (winner)

					System.out.println(isGameOverMessage);
					// need method public int nextActivePlayer()
					// which selects next active player - use this method also in constructor for
					// consistency & simplicity
					// increase round count here

					// activePlayerName=game.getPlayer().get(game.getActivePlayer()).getName();
					if (activePlayerName != "") {
						game.setActivePlayer(game.findPlayerIndex(activePlayerName));
					}

					if (game.getGameWinner() != null) {
						gameOver = true;
						break;
					}

					// System.out.println(game.getActivePlayer());
					// System.out.println(game.getPlayer().get(game.getActivePlayer()).getName());

					if (game.getPlayerArray().get(game.getActivePlayer()).getName() == "You") {
						humanIsActivePlayer = true;
					}

					roundCounter++;
					gameData.addOneNoOfRounds();
					// if(roundCounter>5){ System.exit(-2);}
				}

				if (!gameOver) {
					System.out.println("Round " + roundCounter);
					System.out.println("Round " + roundCounter + ": Players have drawn their cards");
					if(writeGameLogsToFile == true){
						updateTestLogFile("TestLog.txt", "\nRound "+roundCounter+": "+
						game.getPlayerArray().get(game.getActivePlayer()).getName()+" is active player ###############");
					}

					if (game.getPlayerArray().get(0).getName() == "You") {
						System.out.println("Your drew '" + game.getPlayerArray().get(0).getDeck().seeCard(0).getName()
								+ "':\n" + "   > " + game.getPlayerArray().get(0).getDeck().seeCard(0).categoryName(0) + ": "
								+ game.getPlayerArray().get(0).getDeck().seeCard(0).categoryValue(0) + "\n" + "   > "
								+ game.getPlayerArray().get(0).getDeck().seeCard(0).categoryName(1) + ": "
								+ game.getPlayerArray().get(0).getDeck().seeCard(0).categoryValue(1) + "\n" + "   > "
								+ game.getPlayerArray().get(0).getDeck().seeCard(0).categoryName(2) + ": "
								+ game.getPlayerArray().get(0).getDeck().seeCard(0).categoryValue(2) + "\n" + "   > "
								+ game.getPlayerArray().get(0).getDeck().seeCard(0).categoryName(3) + ": "
								+ game.getPlayerArray().get(0).getDeck().seeCard(0).categoryValue(3) + "\n" + "   > "
								+ game.getPlayerArray().get(0).getDeck().seeCard(0).categoryName(4) + ": "
								+ game.getPlayerArray().get(0).getDeck().seeCard(0).categoryValue(4));
						System.out.println(
								"There are " + game.getPlayerArray().get(0).getDeck().sizeOfDeck() + " cards in your deck");
					}

					// needs method public String displayUserTopCard()
					// which returns user's top card - write a method in both Card and GameModel
					// classes for completeness

					userInput = promptUserInput("It is your turn to select a category, the categories are:\n" + "   1: "
							+ game.getPlayerArray().get(0).getDeck().seeCard(0).categoryName(0) + "\n" + "   2: "
							+ game.getPlayerArray().get(0).getDeck().seeCard(0).categoryName(1) + "\n" + "   3: "
							+ game.getPlayerArray().get(0).getDeck().seeCard(0).categoryName(2) + "\n" + "   4: "
							+ game.getPlayerArray().get(0).getDeck().seeCard(0).categoryName(3) + "\n" + "   5: "
							+ game.getPlayerArray().get(0).getDeck().seeCard(0).categoryName(4) + "\n"
							+ "Enter the number for your attribute: ", new int[] { 1, 2, 3, 4, 5 });

					chosenCategory = game.getPlayerArray().get(0).getDeck().seeCard(0).categoryType(userInput - 1);

					game.collectTopCards();

					if(writeGameLogsToFile == true){
						updateTestLogFile("TestLog.txt","Active player chose category: "+chosenCategory.getName());
						updateTestLogFile("TestLog.txt", "Players and their decks: ");

						//test print ************************************
						System.out.println("Array size" + game.getPlayerArray().size());

						for (int k = 0; k < game.getPlayerArray().size(); k++) {
							updateTestLogFile("TestLog.txt",
									game.getPlayerArray().get(k).getName()+" has "+
											game.getPlayerArray().get(k).getDeck().sizeOfDeck()+" cards. " +
											"Top card is "+game.getPlayerArray().get(k).getPlayersTopCard());
						}
					}

					// need method public void collectTopCards()
					// which collects and puts everyone's top card in mainDeck (aka activeDeck) here

					// include a line here for displaying the user's choice of category to the user
					// - NOT NEEDED

					// reuse method public void collectTopCards() - NOT NEEDED

					roundWinner = game.getRoundWinner(chosenCategory);

					// reuse method public int getRoundWinner(Category object chosen by active AI
					// player)
					// System.out.println("Roundwinner: " + roundWinner);

					/*
					 * if(roundWinner==-1){ System.out.println("############# Draw"); }
					 */

					if (roundWinner > -1) {
						gameData.winnerCounter(game.getPlayerArray().get(roundWinner));
						System.out.println("Round " + roundCounter + ": Player "
								+ game.getPlayerArray().get(roundWinner).getName() + " won this round");

						System.out.println("The winning card was '" + game.getRoundWinningCard().getName() + "':\n"
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
						System.out.println("Comparison category: " + chosenCategory.getName());
					}
					// game.removeTopCards();

					game.transferCards(roundWinner);
					// System.out.println("Transfer successful");
					// reuse method public void giveCardsToRoundWinner()

					if (roundWinner > -1) {
						game.setActivePlayer(roundWinner);
						activePlayerName = game.getPlayerArray().get(game.getActivePlayer()).getName();
						// game.setActivePlayer(game.findPlayerIndex(activePlayerName));
					}

					if (roundWinner == -1) {
						/**
						 * adds one to the number of draws in the data object
						 */
						gameData.addOneNoOfDraws();
						System.out.println("Round " + roundCounter + ": This round was a Draw, common pile now has "
								+ game.getCommunalDeck().sizeOfDeck() + " cards");
					}

					loserEliminatedMessage = game.eliminateLoser();
					System.out.println(loserEliminatedMessage);
					// reuse method public String eliminateLosers()

					// ###### IMPORTANT: consider here what to do if there is a draw in the final
					// game and no cards

					isGameOverMessage = game.isGameOver();
					System.out.println(isGameOverMessage);
					// reuse method public String isGameOver()

					// activePlayerName=game.getPlayer().get(game.getActivePlayer()).getName();
					if (activePlayerName != "") {
						game.setActivePlayer(game.findPlayerIndex(activePlayerName));
					}

					if (game.getGameWinner() != null) {
						gameOver = true;
						break;
					}

					if (game.getPlayerArray().get(game.getActivePlayer()).getName() != "You") {
						humanIsActivePlayer = false;
					}

					// reuse method public int nextActivePlayer()

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
		return inputDeck;
	}

	public static void updateTestLogFile(String fileName, String inputTextLine) {
		File logFile = new File(fileName);
		try {
			if (!logFile.exists()) {
				logFile.createNewFile();
			}
			PrintWriter outputFile = new PrintWriter(new FileWriter(fileName, true), Boolean.parseBoolean("UTF-8"));
			outputFile.append(inputTextLine+"\n");
			outputFile.close();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}

}

// additional notes:
// game is expected to have a 'test log' that stores a log of every action that
// happens in the game