package edu.gatech.seclass.sdpscramble;

import android.content.Context;

import java.lang.reflect.Array;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Team 80 on 10/8/2017.
 * Main_App will handle main application functions.
 */

public class Main_App {

    // Attributes:
    private static Main_App INSTANCE;    // instance of Main_App
    private Player curr_player;          // current player
    private Puzzle curr_puzzle;          // current puzzle
    private EWS_Facade facade;           // EWS facade
    private Database_Handler db_handler; // Database handler

    // Methods:

    /**
     * Constructor
     */
    private Main_App() {
        // get facade
        facade = EWS_Facade.get_instance();
    }

    /**
     * Gets instance of Main_App
     * @return instance of Main_App
     */
    public static Main_App get_instance() {
        // if no instance has been created yet
        if(INSTANCE == null) {

            // create an instance of Main_App
            INSTANCE = new Main_App();
        }

        // return this instance of Main_App
        return INSTANCE;
    }

    /**
     * Create database handler if one doesn't already exist
     * @param in_context: application context
     */
    public void create_db_handler(Context in_context) {
        // if the database handler is null
        if(db_handler == null) {

            // create a new database handler
            db_handler = new Database_Handler(in_context);
        }
    }

    /**
     * Saves the in progress puzzle and exits to the main screen
     */
    public void save_in_prog() {
        // use database handler to this game
        db_handler.save_game(curr_player, curr_puzzle);
    }

    /**
     * Method to return current puzzle
     * @return current puzzle
     */
    public Puzzle get_curr_puzzle() {
        return curr_puzzle;
    }

    /**
     * Method to return current player
     * @return current player
     */
    public Player get_curr_player() {
        return curr_player;
    }

    /**
     * Existing player login
     * @param username: player username
     * @return player object
     */
    public boolean login_player(String username) {
        // get the list of players from EWS
        List<Player> players = facade.retrieve_players();

        // for each player in the list of players
        for(Player player: players) {

            // if the current player to test has the input username
            if(player.get_username().equals(username)) {

                // set the current player to test to the current player
                curr_player = player;

                // return true
                return true;
            }
        }

        // no player was found for this username, return false
        return false;
    }

    /**
     * Create a new player
     * @param firstname: player's first name
     * @param lastname: player's last name
     * @param email: player's email address
     * @param username: player's desired username
     */
    public void create_player(String firstname, String lastname, String email, String username)
            throws SocketTimeoutException{
        // add the new player and create a player object for it
        Player new_player = facade.add_new_player(username, firstname, lastname, email);

        // set the new player to the current player
        curr_player = new_player;
    }

    /**
     * Creates a new scramble
     * @param phrase: phrase to be scrambled
     * @param clue: clue to help solve scramble
     * @param username: username of player that created the scramble
     * @return the scramble id of the new scramble
     */
    public String create_scramble(String phrase, String scramble, String clue, String username)
            throws SocketTimeoutException {
        // create new scramble
        String scramble_id = facade.add_new_scramble(phrase, scramble, clue, username);

        // return new scramble's id
        return scramble_id;
    }

    /**
     * Scramble phrase for new scramble
     * @param phrase: phrase to be scrambled
     * @return scrambled phrase
     */
    public String scramble_phrase(String phrase) {

        // if the phrase was empty
        if(phrase.isEmpty()) {

            // return an empty string
            return "";
        }

        // split input phrase by non-alphabetic characters
        String[] split_phrase = phrase.split("(?![\\p{Alpha}+])");

        // create string builder
        StringBuilder builder = new StringBuilder();

        // for each phrase fragment
        for(String section: split_phrase) {

            // if this phrase isn't empty
            if (!section.isEmpty()) {

                // make character array from this section
                char[] char_array = section.toCharArray();

                // create Character list
                List<Character> char_list = new ArrayList<Character>();

                // for each character in the character array
                for (char c : char_array) {

                    // add it to the Character List
                    char_list.add(c);
                }

                // if the first character in the list is not alphabetic
                if(!Character.isLetter(char_list.get(0))) {

                    // add it to the builder
                    builder.append(char_list.get(0));

                    // remove it from the list
                    char_list.remove(0);
                }

                // if there are characters in the character list
                if(char_list.size() > 0) {

                    // create list of Booleans for capitalization
                    List<Boolean> capitalized = new ArrayList<Boolean>();

                    // check characters in list for capitalization
                    for(Character c: char_list) {

                        // if the character is uppercase
                        if(Character.isUpperCase(c)) {

                            // add true to the capitalized list
                            capitalized.add(true);
                        }
                        // otherwise
                        else {

                            // add false to the capitalized list
                            capitalized.add(false);
                        }
                    }

                    // scramble the characters
                    Collections.shuffle(char_list);

                    // for each character in the list
                    for(int k = 0; k < char_list.size(); k++) {

                        // if the character at that position was capitalized in the original
                        if(capitalized.get(k)) {

                            // add an upper case character to the builder
                            builder.append(Character.toUpperCase(char_list.get(k)));
                        }
                        // otherwise
                        else {

                            // add a lower case character to the builder
                            builder.append(Character.toLowerCase(char_list.get(k)));
                        }
                    }
                }
            }
        }

        // return the built string
        return builder.toString();
    }

    /**
     * Get list of scrambles that this player has not solved
     * @param user: current player username
     * @return list of scrambles the player hasn't solved yet
     */
    public ArrayList<String> get_unsolved_scrambles(Player user) {
        // get list of scrambles from EWS
        List<WordScramble> scrambles = facade.retrieve_scrambles();

        // create an empty list of Strings
        ArrayList<String> unsolved_list = new ArrayList<String>();

        // get list of in progress and solved scrambles for current player
        List<String> in_prog = get_in_prog_scrambles();
        List<String> all_solved = user.get_solved_scrambles();

        // for each scramble in the EWS list
        for(WordScramble scramble: scrambles) {

            // set solved to false
            boolean solved = false;
            boolean prog = false;

            // get the current scramble's id
            String scramble_id = scramble.get_scramble_id();

            // for each scramble solved by the player
            for(String solved_puz: all_solved) {

                // if it matches the current scramble
                if(scramble_id.equals(solved_puz)) {

                    // set solved to true
                    solved = true;

                    // break from loop
                    break;
                }
            }

            // for each in progress scramble
            for(String prog_id: in_prog) {

                // if this scramble is on the list
                if(scramble_id.equals(prog_id)) {

                    // set in_prog flag
                    prog = true;

                    break;
                }
            }

            if(!solved && !prog){

                // add it to the unsolved list
                unsolved_list.add(scramble_id);
            }

        }


        // return the unsolved list
        return unsolved_list;
    }

    /**
     * Get list of scrambles that the current player has in progress
     * @return list of scrambles the player has in progress
     */
    public ArrayList<String> get_in_prog_scrambles() {
        // get list of in progress games for the current player from the database
        ArrayList<String> in_prog_games = db_handler.get_games(curr_player);

        // return the list
        return in_prog_games;
    }

    /**
     * Get list of WordScramble ids that have been created by the player
     * @param user: player to check
     * @return ids for WordScrambles created by the player
     */
    private ArrayList<String> get_created_by_player(Player user) {
        // get list of scrambles from EWS
        List<WordScramble> scrambles = facade.retrieve_scrambles();

        // initialize created by list
        ArrayList<String> created_by = new ArrayList<String>();

        // for each scramble
        for(int i = 0; i < scrambles.size(); i++) {

            // get the current scramble
            WordScramble test_scramble = scrambles.get(i);

            // check if the scramble was created by the player
            if(test_scramble.get_created_by().equals(user.get_username())) {

                // add it's id to the list if it was
                created_by.add(test_scramble.get_scramble_id());
            }
        }

        // return the list
        return created_by;
    }

    /**
     * Get a list of WordScramble ids that have been solved or created by the player
     * @return list of Word Scrambles that were solved of created by the player
     */
    private ArrayList<String> get_solved_by_player(Player user) {

        // return the list of scrambles solved by the current player
        return user.get_solved_scrambles();
    }

    /**
     * Return to an in progress scramble
     * @param scramble_id: id of in progress scramble from local database to open
     * @return a Puzzle for the appropriate scramble
     */
    public Puzzle select_in_prog_scramble(String scramble_id) {
        // Puzzle placeholder
        Puzzle new_puz = null;

        // get scrambles from EWS
        List<WordScramble> scrambles = facade.retrieve_scrambles();

        // for each returned scramble
        for(WordScramble scramble: scrambles) {

            // if the scramble has the correct id
            if(scramble.get_scramble_id().equals(scramble_id)) {

                // make a puzzle from this scramble
                new_puz = new Puzzle(scramble);

                // add the saved guess to the puzzle
                new_puz.submit_solution(db_handler.get_saved_guess(curr_player, scramble_id));

                // break out of the loop
                break;
            }

        }

        // set the current puzzle
        curr_puzzle = new_puz;

        // return the puzzle
        return new_puz;
    }

    /**
     * Start a new scramble
     * @param scramble_id: id of scramble to start
     * @return a Puzzle fo the appropriate scramble
     */
    public Puzzle select_new_scramble(String scramble_id) {
        // Puzzle placeholder
        Puzzle new_puz = null;

        // get scrambles from EWS
        List<WordScramble> scrambles = facade.retrieve_scrambles();

        // for each returned scramble
        for(WordScramble scramble: scrambles) {

            // if the scramble has the correct id
            if(scramble.get_scramble_id().equals(scramble_id)) {

                // make a new puzzle from this scramble
                new_puz = new Puzzle(scramble);

                // break out of loop
                break;
            }
        }

        // set the current puzzle
        curr_puzzle = new_puz;

        // return the puzzle
        return new_puz;
    }

    /**
     * Get statistics for all scrambles
     * @param user: the current player
     * @return statistics for all scrambles per the requirements from assignment 5
     */
    public List<List<String>> get_scramble_stats(Player user) {

        // Create container for stats information
        List<List<String>> list_stats = new ArrayList<List<String>>();
        List<String> temp_stats;

        // get lists of solved and created scrambles for this player
        List<String> solved_by = get_solved_by_player(user);
        List<String> created_by = get_created_by_player(user);

        // get the list of scrambles
        List<WordScramble> scrambles = facade.retrieve_scrambles();

        // String builder placeholder
        StringBuilder builder = null;

        // for each scramble
        for(WordScramble scramble: scrambles) {

            // get a new String builder
            builder = new StringBuilder("");

            // create a new temporary list
            temp_stats = new ArrayList<String>();

            // add the scramble id to the temp stats
            temp_stats.add(scramble.get_scramble_id());

            // for each scramble solved by the player
            for(String solved_id: solved_by) {

                // if this scramble was solved by the player
                if(scramble.get_scramble_id().equals(solved_id)) {

                    // add that it was solved
                    builder.append("Solved");

                    // break
                    break;
                }
            }

            // if neither solved nor created by the player
            if(!(builder.length() > 0)) {

                // add that it is unsolved
                builder.append("Unsolved");
            }

            // for each scramble created by the player
            for(String created_id: created_by) {

                // if this scramble was created by the player
                if(scramble.get_scramble_id().equals(created_id)) {

                    // if there was already a status
                    if(builder.length() > 0) {

                        // add a slash delimiter
                        builder.append("/");
                    }

                    // add that it was created
                    builder.append("Created");

                    // break
                    break;
                }

            }

            // add status
            temp_stats.add(builder.toString());

            // add number of times solved by any player
            temp_stats.add(Integer.toString(scramble.get_solved_by()));

            // add this list to the container
            list_stats.add(temp_stats);
        }

        // return scrambles stats list
        return list_stats;
    }

    /**
     * Get statistics for all players per the requirements from assignment 5
     * @return statistics for all players
     */
    public List<List<String>> get_all_player_stats() {

        // container for player statistics
        List<List<String>> list_stats = new ArrayList<List<String>>();
        List<String> temp_stats;

        // Puzzle container
        Puzzle puz;

        // math variables
        int total_solved = 0;
        String average = "";

        // get list of players from EWS
        List<Player> players = facade.retrieve_players();

        // for each player
        for (Player player: players) {

            // reset temporary stats
            temp_stats = new ArrayList<String>();

            // add first and last name
            temp_stats.add(player.get_first_name());
            temp_stats.add(player.get_last_name());

            // add the number of scrambles the player has solved
            temp_stats.add(Integer.toString(get_solved_by_player(player).size()));

            // get the scrambles the player has created
            List<String> created_by = get_created_by_player(player);

            // add the number of scrambles this player has created
            temp_stats.add(Integer.toString(created_by.size()));

            // for each scramble created by this player
            for(String scramble_id:created_by) {

                // get the scramble
                puz = select_new_scramble(scramble_id);

                // add to the total
                total_solved += puz.get_solved_by();
            }

            // if the number of created scrambles was not 0
            if(!(created_by.size() == 0)) {

                // add total solved divided by the number of puzzles created
                average = String.format("%.2f", (double)total_solved / (double)created_by.size());
                temp_stats.add(average);
            }
            // otherwise
            else {

                // add 0
                temp_stats.add(Double.toString(0.00));
            }

            // add to stats container
            list_stats.add(temp_stats);

            // reset total_solved to 0
            total_solved = 0;
        }

        // return stats
        return list_stats;
    }

    /**
     * when puzzle is solved, remove save from database
     */
    public void report_solved() {

        // report puzzle solved to EWS
        curr_puzzle.puzzle_solved(curr_player);

        // get list of in progress puzzles from database
        List<String> in_prog = get_in_prog_scrambles();

        // for each in progress scramble
        for(String scramble_id: in_prog) {

            // if this is the puzzle that was solved
            if(scramble_id.equals(curr_puzzle.get_scramble_id())){

                // remove save from database
                db_handler.remove_game(curr_player, curr_puzzle.get_scramble_id());
            }
        }
    }

    /**
     * run before exiting app
     */
    public void before_exit() {

        // if there is an in progress puzzle
        if(curr_puzzle != null) {

            // if that puzzle has a guess
            if(!curr_puzzle.get_guess().isEmpty()) {

                // save the current puzzle before exiting
                save_in_prog();
            }
        }
    }
}