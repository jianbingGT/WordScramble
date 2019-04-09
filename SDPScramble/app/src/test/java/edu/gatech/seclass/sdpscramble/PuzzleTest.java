package edu.gatech.seclass.sdpscramble;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Team80 on 10/12/2017.
 * This is the test for the Puzzle Class
 */
public class PuzzleTest {

    private Puzzle myPuzzle;

    /**
     * Run before each test
     */
    @Before
    public void setUp() {

        // create Puzzle object
        myPuzzle = new Puzzle("krab", "bark", "talk like a dog", "nico", "Scram01");
    }

    /**
     * Run after each test
     */
    @After
    public void tearDown() {

        // destroy Puzzle object
        myPuzzle = null;
    }

    /**
     * Test that submit_solution returns true when the solution is correct
     */
    @Test
    public void submit_solution_correct() {

        // check that result is true
        assertTrue(myPuzzle.submit_solution("bark"));
    }

    /**
     * Test that submit_solution returns false when the solution is incorrect
     */
    @Test
    public void submit_solution_incorrect() {

        // check that result is false
        assertFalse(myPuzzle.submit_solution("meow"));
    }

    /**
     * Test that returned guess is empty when no guess has been submitted
     */
    @Test
    public void get_guess_empty() {

        assertEquals(null, myPuzzle.get_guess());

    }

    /**
     * Test that returned guess is the expected value when a guess has been submitted
     */
    @Test
    public void get_guess_not_empty() {

        String test_string = "meow";

        myPuzzle.submit_solution(test_string);

        assertEquals(test_string, myPuzzle.get_guess());

    }

}