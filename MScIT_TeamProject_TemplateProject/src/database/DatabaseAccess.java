package database;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Connection;
import commandline.model.GameData;

public class DatabaseAccess {

    public static void uploadData(GameData gd) {
        int noOfDraws = gd.getNoOfDraws();
        int noOfRounds = gd.getNoOfRounds();
        int overallWinner = gd.getOverallWinner();
        int userRoundWins = gd.getUserRoundWins();
        int ai1RoundWins = gd.getAi1RoundWins();
        int ai2RoundWins = gd.getAi2RoundWins();
        int ai3RoundWins = gd.getAi3RoundWins();
        int ai4RoundWins = gd.getAi4RoundWins();
        Connection c = null;

        try {

            Class.forName("org.postgresql.Driver");
            c = DriverManager.getConnection("dbc:postgresql://localhost/postgres", "postgres", "postgres");

        } catch (Exception e) {

            e.printStackTrace();

            System.err.println(e.getClass().getName() + ": " + e.getMessage());

            System.exit(0);
        }

        try {
            String query = "INSERT INTO game_data \n" +
            /*
             * Adds the values stored in the GameData object when passed The first value is
             * an sql sequence whihc will increse each time a new set is added and can be
             * used to identify each game
             *
             * The value for overall winner has to be 1-5
             */
                    "VALUES " + "(nextval('one'), " + noOfDraws + ", " + noOfRounds + ", " + overallWinner + ", "
                    + userRoundWins + ", " + ai1RoundWins + ", " + ai2RoundWins + ", " + ai3RoundWins + ", "
                    + ai4RoundWins + ");";

            java.sql.Statement stmt = c.createStatement();
            stmt.executeUpdate(query);
            System.out.print("Done");

            c.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    

    public static int getTotalNumerOfGames() {
        /**
         * this connects to the database and then runs a query to get the
         * total number of games that have been played and returns as an int
         */
        int totalGames = -1;
        Connection c = null;

        try {

            Class.forName("org.postgresql.Driver");
            c = DriverManager.getConnection("dbc:postgresql://localhost/postgres", "postgres", "postgres");

        } catch (Exception e) {

            e.printStackTrace();

            System.err.println(e.getClass().getName() + ": " + e.getMessage());

            System.exit(0);
        }
        try {
            // Number of games played overall:
            String query = "SELECT COUNT(*)\n" + "FROM game_data";
            java.sql.Statement stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            rs.next();
            totalGames = rs.getInt("count");
            c.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return totalGames;
    }

    public static int getNumberOfComputerWins() {
        /**
         * this connects to the database and then runs a query to get the
         * total number of computer wins and returns as an int
         */
        int computerWins = -1;
        Connection c = null;

        try {

            Class.forName("org.postgresql.Driver");
            c = DriverManager.getConnection("dbc:postgresql://localhost/postgres", "postgres", "postgres");

        } catch (Exception e) {

            e.printStackTrace();

            System.err.println(e.getClass().getName() + ": " + e.getMessage());

            System.exit(0);
        }
        try {
            String query = "SELECT COUNT(*)\n" + "FROM game_data\n" + "WHERE overall_winner != 1";
            java.sql.Statement stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            rs.next();
            computerWins = rs.getInt("count");
            c.close();
            ;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return computerWins;
    }

    public static int getNumberOfUserWins() {
        /**
         * this connects to the database and then runs a query to get the
         * total number of user/human wins and returns as an int
         */
        int userWins = -1;
        Connection c = null;

        try {

            Class.forName("org.postgresql.Driver");
            c = DriverManager.getConnection("dbc:postgresql://localhost/postgres", "postgres", "postgres");

        } catch (Exception e) {

            e.printStackTrace();

            System.err.println(e.getClass().getName() + ": " + e.getMessage());

            System.exit(0);
        }

        try {
            // How many times has the user won:
            String query = "SELECT COUNT(*)\n" + "FROM game_data\n" + "WHERE overall_winner = 1";

            java.sql.Statement stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            rs.next();
            userWins = rs.getInt("count");
            c.close();
            ;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return userWins;
    }

    public static int getAvgNoDraws() {
        /**
         * this connects to the database and then runs a query to get the
         * average number of draws per game and returns as an int
         */
        int avgNoDraws = -1;
        Connection c = null;

        try {

            Class.forName("org.postgresql.Driver");
            c = DriverManager.getConnection("dbc:postgresql://localhost/postgres", "postgres", "postgres");

        } catch (Exception e) {

            e.printStackTrace();

            System.err.println(e.getClass().getName() + ": " + e.getMessage());

            System.exit(0);
        }
        try {
            // The average number of draws:
            String query = "SELECT AVG(no_of_draws)\n" + "FROM game_data\n";

            java.sql.Statement stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            rs.next();
            avgNoDraws = rs.getInt("avg");
            c.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return avgNoDraws;
    }

    public static int getMaxNoRounds() {
        /**
         * this connects to the database and then runs a query to get the
         * max number of rounds that have been played and returns as an int
         */
        int maxRounds = -1;
        Connection c = null;

        try {

            Class.forName("org.postgresql.Driver");
            c = DriverManager.getConnection("dbc:postgresql://localhost/postgres", "postgres", "postgres");

        } catch (Exception e) {

            e.printStackTrace();

            System.err.println(e.getClass().getName() + ": " + e.getMessage());

            System.exit(0);
        }
        try {
            // The largest number of rounds played:
            String query = "SELECT MAX(no_of_rounds)\n" + "FROM game_data\n";

            java.sql.Statement stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            rs.next();
            maxRounds = rs.getInt("max");
            c.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return maxRounds;

    }
}