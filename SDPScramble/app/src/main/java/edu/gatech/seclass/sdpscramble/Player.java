package edu.gatech.seclass.sdpscramble;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Team 80 on 10/8/2017.
 * Player will handle keeping track of player information
 */

public class Player {

    // Attributes
    private String username;
    private String first_name;
    private String last_name;
    private String email_address;
    private ArrayList<String> solved_scrambles;

    // Methods

    /**
     * Class constructor
     * @param user: username for the player
     * @param first: first name for the player
     * @param last: last name for the player
     * @param email: email address for the player
     */
    Player(String user, String first, String last, String email) {
        username = user;
        first_name = first;
        last_name = last;
        email_address = email;
        solved_scrambles = new ArrayList<String>();
    }

    /**
     * Returns the player's username as a String
     * @return player's username
     */
    public String get_username(){
        return username;
    }

    /**
     * Returns the player's first name as a String
     * @return player's first_name
     */
    public String get_first_name(){
        return first_name;
    }

    /**
     * Returns the player's last name
     * @return player's last_name
     */
    public String get_last_name() {
        return last_name;
    }

    /**
     * Returns the players email address
     * @return player's email_address
     */
    public String get_email() {
        return email_address;
    }

    /**
     * Returns a list of scramble ids for the scrambles the player has solved
     * @return player's list of solved scramble ids
     */
    public ArrayList<String> get_solved_scrambles() {
        return solved_scrambles;
    }

    /**
     * Appends a new scramble id to the player's list of solved scrambles
     * @param scramble_id: new scramble id to be added to the solved_scrambles list
     */
    public void append_to_solved_scrambles(String scramble_id) {
        solved_scrambles.add(scramble_id);
    }

}
