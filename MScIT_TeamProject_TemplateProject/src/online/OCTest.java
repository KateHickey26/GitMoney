package online;

public class OCTest {
    public static void main (String [] args){
        OnlineController oController = new OnlineController(4);

        oController.startGame();

        oController.getRoundInfo().print(System.out);





    }
}