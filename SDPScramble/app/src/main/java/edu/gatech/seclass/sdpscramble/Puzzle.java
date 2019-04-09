package edu.gatech.seclass.sdpscramble;

/**
 * Created by Team 80 on 10/8/2017.
 * Puzzle will handle working with in progress word scrambles
 */

public class Puzzle extends WordScramble {

    // Attributes
    private String guess;

    // Methods

    /**
     * Creates a new puzzle, which is a WordScramble extended to include solving logic
     * @param in_scramble: scramble to be solved
     * @param in_solution: solution to scramble
     * @param in_clue: clue to help solve scramble
     * @param in_created_by: user that created this scramble
     * @param in_scramble_id: id of scramble
     */
    Puzzle(String in_scramble, String in_solution,
           String in_clue, String in_created_by, String in_scramble_id) {

        // Super class constructor
        super(in_scramble, in_solution, in_clue, in_created_by, in_scramble_id);
    }

    /**
     * Creat2s a new puzzle from a WordScramble
     * @param scramble: input scramble
     */
    Puzzle(WordScramble scramble) {

        // Super class constructor
        super(scramble.get_scramble(), scramble.get_solution(), scramble.get_clue(),
                scramble.get_created_by(), scramble.get_scramble_id());
    }

    /**
     * checks guess against solution
     * @param new_guess: guess to check
     * @return whether or not the guess matched the solution
     */
    public boolean submit_solution(String new_guess) {
        // set guess to the current guess
        guess = new_guess;
        // return if the guess was correct
        return guess.equals(get_solution());
    }

    /**
     * Reports the scramble as solved to the EWS and adds the scramble id to the player's
     * list of solved scrambles
     * @param user: the player to report having solved this scramble
     */
    public void puzzle_solved(Player user) {

        // report this WordScramble as solved
        EWS_Facade facade = EWS_Facade.get_instance();
        facade.report_solved(this, user);

        // add the scramble id to the current player
        user.append_to_solved_scrambles(get_scramble_id());
    }

    /**
     * Retrieves guess
     * @return the current guess
     */
    public String get_guess() {
        return guess;
    }
}