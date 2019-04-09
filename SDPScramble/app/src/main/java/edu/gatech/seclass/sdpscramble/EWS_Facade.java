package edu.gatech.seclass.sdpscramble;

import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;

import edu.gatech.seclass.utilities.ExternalWebService;

/**
 * Created by Team 80 on 10/9/2017.
 * EWS facade will act as the interface between the application and the EWS
 */

public class EWS_Facade {

    private static EWS_Facade INSTANCE;
    private ExternalWebService service;

    /**
     * Constructor
     */
    private EWS_Facade() {

        // get instance of EWS
        service = ExternalWebService.getInstance();
    }

    /**
     * Gets instance of EWS_Facade
     * @return instance of EWS_Facade
     */
    public static EWS_Facade get_instance() {
        // if no instance has been created yet
        if(INSTANCE == null) {

            // create an instance of EWS_Facade
            INSTANCE = new EWS_Facade();
        }

        // return this instance of EWS_Facade
        return INSTANCE;
    }

    /**
     * Returns list of WordScrambles that are on EWS
     * @return List of WordScrambles
     */
    public List<WordScramble> retrieve_scrambles() {
        // retrieve list of scrambles from EWS
        List<List<String>> scrambles_in = service.retrieveScrambleService();

        // create list of scrambles to return at end of method
        List<WordScramble> scrambles_out = new ArrayList<WordScramble>();

        // for each scramble retrieved
        for(int i = 0; i < scrambles_in.size(); i++) {

            // get the current scramble
            List<String> curr_scramble = scrambles_in.get(i);

            // make a new WordScramble and add it to the list
            // order of items from EWS is [ID, unscrambled, scrambled, clue, creator]
            scrambles_out.add(new WordScramble(curr_scramble.get(2), curr_scramble.get(1),
                    curr_scramble.get(3), curr_scramble.get(4), curr_scramble.get(0)));
        }

        // return converted list
        return scrambles_out;
    }

    /**
     * Returns list of players that are on EWS
     * @return List of players that are on EWS
     */
    public List<Player> retrieve_players() {
        // retrieve list of players from EWS
        List<List<String>> players_in = service.retrievePlayerListService();

        // create list of Players
        List<Player> players_out = new ArrayList<Player>();

        // for each player retrieved
        for(int i = 0; i < players_in.size(); i++) {

            // get the current player
            List<String> curr_player = players_in.get(i);

            // make a new Player
            Player temp_player = new Player(curr_player.get(0), curr_player.get(1), curr_player.get(2),
                    curr_player.get(3));

            // for each solved scramble
            for(int j = 4; j < curr_player.size(); j++) {

                // add the scramble to the player
                temp_player.append_to_solved_scrambles(curr_player.get(j));
            }

            // add player to list
            players_out.add(temp_player);
        }

        // return list of Players
        return players_out;
    }

    /**
     * Adds a new player to EWS
     * @param username: String, desired user name of player
     * @param firstname: String, first name of player
     * @param lastname: String, last name of player
     * @param email: String, email address of player
     * @return Player object of information that was added to EWS
     * @throws SocketTimeoutException: because the EWS does
     */
    public Player add_new_player(String username, String firstname, String lastname,
                                 String email) throws SocketTimeoutException {

        // add new player
        String new_user = service.newPlayerService(username, firstname, lastname, email);

        // create player
        Player new_player = new Player(new_user, firstname, lastname, email);

        // return new player's username
        return new_player;
    }

    /**
     * Report a solved scramble to EWS
     * @param scramble: scramble that was solved
     * @param user: user that solved the scramble
     * @return boolean that solved scramble was added
     */
    public boolean report_solved(WordScramble scramble, Player user) {
        // get scramble id and username
        String id = scramble.get_scramble_id();
        String username = user.get_username();

        // report solved
        boolean solved = service.reportSolveService(id, username);

        // return update boolean
        return solved;
    }

    /**
     * Create a new scramble on EWS
     * @param solution: unscrambled phrase/word
     * @param scramble: scrambled phrase/word
     * @param clue: clue to solve scramble
     * @param user: creator of scramble
     * @return the new scramble's id
     * @throws SocketTimeoutException: because the EWS does
     */
    public String add_new_scramble(String solution, String scramble, String clue, String user)
    throws SocketTimeoutException {
        // Create new scramble and get scramble id
        String scramble_id = service.newScrambleService(solution, scramble, clue, user);

        // return scramble id
        return scramble_id;
    }

    /**
     * Initializes EWS with additional player information for testing
     * @param players_in: List of Lists of player information
     * @return boolean for if initialization worked
     */
    public boolean initialze_players(List<List<String>> players_in) {
        // initialize players for testing
        boolean initialized = service.initializePlayers(players_in);

        // return initialized
        return initialized;
    }

    /**
     * Initializes EWS with additional scramble information for testing
     * @param scrambles_in: List of Lists of scramble information
     * @return boolean for if initialization worked
     */
    public boolean initialize_scrambles(List<List<String>> scrambles_in) {
        // initialize scrambles for testing
        boolean initialized = service.initializeScramble(scrambles_in);

        // return initialized
        return initialized;
    }
}
