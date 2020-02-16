package online.dwResources;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import online.OnlineController;
import online.configuration.TopTrumpsJSONConfiguration;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.google.gson.Gson;
import database.DatabaseAccess;
import online.OnlineController;
import commandline.model.GameModel;
import commandline.model.*;

@Path("/toptrumps") // Resources specified here should be hosted at http://localhost:7777/toptrumps
@Produces(MediaType.APPLICATION_JSON) // This resource returns JSON content
@Consumes(MediaType.APPLICATION_JSON) // This resource can take JSON content as input
/**
 * This is a Dropwizard Resource that specifies what to provide when a user
 * requests a particular URL. In this case, the URLs are associated to the
 * different REST API methods that you will need to expose the game commands
 * to the Web page.
 * 
 * Below are provided some sample methods that illustrate how to create
 * REST API methods in Dropwizard. You will need to replace these with
 * methods that allow a TopTrumps game to be controlled from a Web page.
 */
public class TopTrumpsRESTAPI {
// This API has the following attributes:
// an online controller

OnlineController oController;
GameModel game;


	/** A Jackson Object writer. It allows us to turn Java objects
	 * into JSON strings easily. */
	private int NUMBER_OF_PLAYERS;
    /**
     * A Jackson Object writer. It allows us to turn Java objects
     * into JSON strings easily.
     */
    private ObjectWriter oWriter;
	private String deckFile;
	OnlineController controller;
	
	/**
	 * Contructor method for the REST API. This is called first. It provides
	 * a TopTrumpsJSONConfiguration from which you can get the location of
	 * the deck file and the number of AI players.
	 * @param conf
	 */
	public TopTrumpsRESTAPI(TopTrumpsJSONConfiguration conf) {
		oWriter = new ObjectMapper().writerWithDefaultPrettyPrinter();
        deckFile = conf.getDeckFile();
		NUMBER_OF_PLAYERS = conf.getNumAIPlayers() + 1;
		controller = new OnlineController(4);

	}
	
	// ----------------------------------------------------
	// Add relevant API methods here
	// ----------------------------------------------------

	@GET
	@Path("/gamestart")
	/**
	 * 
	 * 
	 * calls the method to initialise the start of the game in the controller
	 */
	public String startGame() throws JsonProcessingException{
		controller.startGame();
		Gson gson = new Gson();
		String json = gson.toJson(controller.getRoundInfo());
		return json;
	}

	@GET
	@Path("/roundinfo")
	/**
	 * 
	 * @return all the info in the round info object
	 *
	 * @throws JsonProcessingException
	 */

	public String roundInfo() throws JsonProcessingException{
		Gson gson = new Gson();
		String json = gson.toJson(controller.getRoundInfo());
		return json;
	}
	@GET
	@Path("/chosencategory")
	/**
	 * 
	 * @return the chosen category
	 *
	 * @throws JsonProcessingException
	 */

	public String chosenCategory() throws JsonProcessingException{
		Map<String, String> category = new HashMap<String, String>();
        category.put("chosenCategory", controller.getChosenCatergory().getName());
		Gson gson = new Gson();
		String json = gson.toJson(category);
		return json;
	}





	@GET
	@Path("/userround")
	/**
	 * plays a user round
	 * @param category takes the category as an int 
	 * 
	 * this will be chosen by the user from the dropdown when prompted
	 */

	public void playHumanRound(@QueryParam("category") int category){
				controller.playRoundHuman(category);
				//maybe need to return something not sure yet

	}


	@GET
	@Path("/computerround")
	/**
	 * 
	 */
	public void playComputerRound(){
		controller.playRoundAI();
		//maybe need to return something not sure yet
	}

	@GET
	@Path("/humancard")

	public String returnHumanCard(){
		Gson gson = new Gson();
		String json = gson.toJson(controller.getHumanCard());
		return json;
	}

	@GET
	@Path("/database")
	/**
	 * this takes all the databse queries and puts them into a hashmap
	 * the hashmap is then converted into a json object and returned on method call
	 * 
	 * @return - database values as string
	 * @throws JsonProcessingException
	 */
	public String databaseStats() throws JsonProcessingException{
		Map<String, String> stats = new HashMap<String, String>();
		stats.put("totalGamesPlayed", "" + DatabaseAccess.getTotalNumerOfGames());
		stats.put("numberOfUserWins", "" + DatabaseAccess.getNumberOfUserWins());
		stats.put("numberOfComputerWins", "" + DatabaseAccess.getNumberOfComputerWins());
		stats.put("avgNumberOfDraws", "" + DatabaseAccess.getAvgNoDraws());
		stats.put("maxNumberOfRounds", "" + DatabaseAccess.getMaxNoRounds());


		Gson gson = new Gson();
		String json = gson.toJson(stats);
		return json;
	}

	@GET
	@Path("/ai1topcard")
	/**
	 * 
	 * @return
	 */
	public String ai1TopCard(){
		Gson gson = new Gson();
		String json = gson.toJson(controller.getAi1TopCard());
		return json;
	}
	@GET
	@Path("/ai2topcard")
	/**
	 * 
	 * @return
	 */
	public String ai2TopCard(){
		Gson gson = new Gson();
		String json = gson.toJson(controller.getAi2TopCard());
		return json;
	}
	@GET
	@Path("/ai3topcard")
	/**
	 * 
	 * @return
	 */
	public String ai3TopCard(){
		Gson gson = new Gson();
		String json = gson.toJson(controller.getAi3TopCard());
		return json;
	}
	@GET
	@Path("/ai4topcard")
	/**
	 * 
	 * @return
	 */
	public String ai4TopCard(){
		Gson gson = new Gson();
		String json = gson.toJson(controller.getAi4TopCard());
		return json;
	}

	
	@GET
	@Path("/helloJSONList")
	/**
	 * Here is an example of a simple REST get request that returns a String.
	 * We also illustrate here how we can convert Java objects to JSON strings.
	 * @return - List of words as JSON
	 * @throws IOException
	 */
	public String helloJSONList() throws IOException {
		
		List<String> listOfWords = new ArrayList<String>();
		listOfWords.add("Hello");
		listOfWords.add("World!");
		
		// We can turn arbatory Java objects directly into JSON strings using
		// Jackson seralization, assuming that the Java objects are not too complex.
		String listAsJSONString = oWriter.writeValueAsString(listOfWords);
		
		return listAsJSONString;
	}
	
	@GET
	@Path("/helloWord")
	/**
	 * Here is an example of how to read parameters provided in an HTML Get request.
	 * @param Word - A word
	 * @return - A String
	 * @throws IOException
	 */
	public String helloWord(@QueryParam("Word") String Word) throws IOException {
		return "Hello "+Word;
	}
	
}