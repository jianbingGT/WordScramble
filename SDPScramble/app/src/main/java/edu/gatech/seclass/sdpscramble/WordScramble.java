package edu.gatech.seclass.sdpscramble;

import java.util.List;

/**
 * Created by Team 80 on 10/8/2017.
 * WordScramble will handle keeping track of word scramble information
 */

public class WordScramble {

    // Attributes
    private String scramble;
    private String solution;
    private String clue;
    private String created_by;
    private String scramble_ID;
    private Integer solved_by;

    /**
     * Class constructor
     * @param in_scramble: scramble to be solved
     * @param in_solution: solution to scramble
     * @param in_clue: clue to help solve scramble
     * @param in_created_by: user that created scramble
     * @param in_scramble_id: id of scramble
     */
    WordScramble(String in_scramble, String in_solution,
                 String in_clue, String in_created_by, String in_scramble_id) {
        scramble = in_scramble;
        solution = in_solution;
        clue = in_clue;
        created_by = in_created_by;
        scramble_ID = in_scramble_id;
    }

    /**
     * Returns scramble to be solved
     * @return scramble to be solved
     */
    public String get_scramble() {
        return scramble;
    }

    /**
     * Returns solution for scramble
     * @return solution for scramble
     */
    public String get_solution() {
        return solution;
    }

    /**
     * Returns clue for scramble
     * @return clue for scramble
     */
    public String get_clue() {
        return clue;
    }

    /**
     * Returns user that created scramble
     * @return user that created scramble
     */
    public String get_created_by() {
        return created_by;
    }

    /**
     * Returns scramble ID
     * @return scramble ID
     */
    public String get_scramble_id() {
        return scramble_ID;
    }

    /**
     * Returns number of players that have solved this scramble
     * @return number of players that have solved this scramble
     */
    public Integer get_solved_by() {
        // calculate number of players that have solved
        calc_solved_by();

        // return calculated value
        return solved_by;
    }

    /**
     * Calculates the number of players that have solved the scramble
     */
    public void calc_solved_by() {

        // initialize solved by counter
        solved_by = 0;

        // get list of players
        EWS_Facade facade = EWS_Facade.get_instance();
        List<Player> players = facade.retrieve_players();

        // for each player in the player list
        for(int i = 0; i < players.size(); i++) {

            // get the current player
            Player curr_player = players.get(i);

            // for each scramble the player has solved
            for(int j = 0; j < curr_player.get_solved_scrambles().size(); j++) {

                // if it matches this scramble's id
                if(scramble_ID.equals(curr_player.get_solved_scrambles().get(j))) {

                    // increment solved by counter
                    solved_by += 1;

                    // break from loop
                    break;
                }
            }
        }
    }
}
