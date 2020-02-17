package online;

public class OCTest {
	
	public static void main (String [] args) {
		OnlineController oController = new OnlineController();
		oController.startGame();
		oController.getRoundInfo().print(System.out);
		System.out.println("");
		System.out.println(oController.getHumanCard().toString());
		
		oController.playRoundAI();
		oController.getRoundInfo().print(System.out);
		System.out.println("");
		System.out.println(oController.getHumanCard().toString());
		oController.playRoundAI();
		oController.playRoundAI();
		oController.playRoundAI();
		oController.playRoundAI();
		oController.playRoundAI();
		
		
		
		
	}

}
