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
					/**
					 * checks if there is player to be elminated
					 * ie. has no cards left in the deck
					 */
					loserEliminatedMessage = game.eliminateLoser();
					/**
					 * if the test logger is selected then will write data to the log including the 
					 * active players category choice and top card
					 */
					if(writeGameLogsToFile == true){
						updateTestLogFile("TestLog.txt","Active player chose category: "+chosenCategory.getName());
						updateTestLogFile("TestLog.txt", "Players and their decks: ");
						for (int k = 0; k < game.getPlayerArray().size(); k++) {
							updateTestLogFile("TestLog.txt", game.getPlayerArray().get(k).getName() + " has "
									+ game.getPlayerArray().get(k).getDeck().sizeOfDeck() + " cards. " + "Top card is "
									 + game.getPlayerArray().get(k).getPlayersTopCard());
						}
					}
					/**
					 * prints the message signifying the player that has been elminated player
					 */
					System.out.println(loserEliminatedMessage);

					/**
					 * checks to see if the game is over and there is only one player left
					 */
					isGameOverMessage = game.isGameOver();
					System.out.println(isGameOverMessage);

			
					if (activePlayerName != "") {
						game.setActivePlayer(game.findPlayerIndex(activePlayerName));
					}

					if (game.getGameWinner() != null) {
						gameOver = true;
						break;
					}

					/**
					 * checks to see if the human is the active player
					 */
					if (game.getPlayerArray().get(game.getActivePlayer()).getName() == "You") {
						humanIsActivePlayer = true;
					}
					/**
					 * adds to the round counter and aslo the database incrementer
					 */
					roundCounter++;
					gameData.addOneNoOfRounds();
				}


				if (!gameOver) {
					System.out.println("Round " + roundCounter);
					System.out.println("Round " + roundCounter + ": Players have drawn their cards");
					if(writeGameLogsToFile == true){
						updateTestLogFile("TestLog.txt", "\nRound "+roundCounter+": "+
						game.getPlayerArray().get(game.getActivePlayer()).getName()+" is active player ###############");
					}
					/**
					 * prints the users card
					 */
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
					/**
					 * prints the category selection choice
					 */
					userInput = promptUserInput("It is your turn to select a category, the categories are:\n" + "   1: "
							+ game.getPlayerArray().get(0).getDeck().seeCard(0).categoryName(0) + "\n" + "   2: "
							+ game.getPlayerArray().get(0).getDeck().seeCard(0).categoryName(1) + "\n" + "   3: "
							+ game.getPlayerArray().get(0).getDeck().seeCard(0).categoryName(2) + "\n" + "   4: "
							+ game.getPlayerArray().get(0).getDeck().seeCard(0).categoryName(3) + "\n" + "   5: "
							+ game.getPlayerArray().get(0).getDeck().seeCard(0).categoryName(4) + "\n"
							+ "Enter the number for your attribute: ", new int[] { 1, 2, 3, 4, 5 });
						/**
						 * removes one from the category choice from the input so it works
						 * with the array numbers starting with 0
						 */
					chosenCategory = game.getPlayerArray().get(0).getDeck().seeCard(0).categoryType(userInput - 1);

					/**
					 * collects all the cards from the players and puts them in the main deck to check for winner
					 */
					game.collectTopCards();
					/**
					 * if log is active will write in the chosen category and the active player top card
					 */
					if(writeGameLogsToFile == true){
						updateTestLogFile("TestLog.txt","Active player chose category: "+chosenCategory.getName());
						updateTestLogFile("TestLog.txt", "Players and their decks: ");
						for (int k = 0; k < game.getPlayerArray().size(); k++) {
							updateTestLogFile("TestLog.txt",
									game.getPlayerArray().get(k).getName()+" has "+
											game.getPlayerArray().get(k).getDeck().sizeOfDeck()+" cards. " +
											"Top card is "+game.getPlayerArray().get(k).getPlayersTopCard());
						}
					}
					/**
					 * takes the chosen category and checks all the top cards for winner
					 */
					roundWinner = game.getRoundWinner(chosenCategory);
					
					/**
					 * if the card come back with a winner then will print out the winning player and what
					 * they're winning card is
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

					game.transferCards(roundWinner);
					/**
					 * if there is a winner will transfer all the card to them and if not will
					 * keep them in the communal deck until the next winner
					 */
					if (roundWinner > -1) {
						/**
						 * sets the active player to the winner if there was a winner
						 */
						game.setActivePlayer(roundWinner);
						activePlayerName = game.getPlayerArray().get(game.getActivePlayer()).getName();
					}

					if (roundWinner == -1) {
						/**
						 * adds one to the number of draws in the data object and also prints out the 
						 * draw statements and how many card are in the common pile
						 */
						gameData.addOneNoOfDraws();
						System.out.println("Round " + roundCounter + ": This round was a Draw, common pile now has "
								+ game.getCommunalDeck().sizeOfDeck() + " cards");
					}

					/**
					 * cehcks if anyone has been eliminated
					 * ie has 0 cards left
					 * if they have will return eliminated message to be printed
					 */
					loserEliminatedMessage = game.eliminateLoser();
					System.out.println(loserEliminatedMessage);

					/**
					 * checks if the game is over and there is only player remaining and 
					 * if there is an overall winner prints message
					 */
					isGameOverMessage = game.isGameOver();
					System.out.println(isGameOverMessage);
					
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


	/**
	 * method called if the user inputs the wrong type of string
	 * eg too many players at start
	 * will keep promting until the user inputs an accecptable input
	 * @param userMessage
	 * @param userOptions
	 * @return
	 */
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

	public static boolean contains(int[] array, int value) {
		/**
		 * method to check is a given array contains a given int value
		 */
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
		/**
		 * inputs strings to the test log
		 * method called throughout the program if the test log version is called at startup
		 */
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