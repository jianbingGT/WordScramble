package edu.gatech.seclass.sdpscramble;

import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.lang.reflect.Array;
import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Created by Team80 on 10/14/2017.
 * Tests the Database Handler class.
 */

public class Database_HandlerTest {

    private static Database_Handler db;
    private static Player player;
    private static Puzzle scramble;

    /**
     * Run this before the tests start
     */
    @BeforeClass
    public static void startTest() {
        // set up Database handler to be tested
        db = new Database_Handler(InstrumentationRegistry.getTargetContext());

        // set up a player and puzzle object to use during testing
        player = new Player("bob32", "Robert", "England", "freddyK@dreamworld.com");
        scramble = new Puzzle("krab", "bark", "talk like a dog", "bob32", "Scram01");
        scramble.submit_solution("meow");
    }

    /**
     * Test get saved guess for a specific scramble
     */
    @Test
    public void get_saved_guess_empty() {

        // test the retrieved guess is the expected guess
        assertEquals("", db.get_saved_guess(player, scramble.get_scramble_id()));
    }

    /**
     * Test the save game function
     */
    @Test
    public void save_game() {

        // save a scramble for the player
        db.save_game(player, scramble);

        // test that the new guess is as expected
        assertEquals(scramble.get_guess(), db.get_saved_guess(player, scramble.get_scramble_id()));
    }

    /**
     * Test the method to get all games for a specific player
     */
    @Test
    public void get_games() {

        // get games from database
        ArrayList<String> saved = db.get_games(player);

        // set up boolean variable
        boolean found = false;

        // for each id returned
        for(String id: saved) {

            // if that id is the expected scramble id
            if(id.equals(scramble.get_scramble_id())) {

                // the game was found
                found = true;

                // break loop
                break;
            }
        }

        // check boolean
        assertTrue(found);
    }

    /**
     * Test get saved guess for a specific scramble
     */
    @Test
    public void get_saved_guess() {

        // test the retrieved guess is the expected guess
        assertEquals(scramble.get_guess(), db.get_saved_guess(player, scramble.get_scramble_id()));
    }

    /**
     * Test update and get saved guess to verify
     */
    @Test
    public void update_and_get_saved_guess() {

        // change guess
        scramble.submit_solution("tweet");

        // update the existing save to the new guess
        db.save_game(player, scramble);

        // check that the retrieved guess is the expected one
        assertEquals(scramble.get_guess(), db.get_saved_guess(player, scramble.get_scramble_id()));
    }

    /**
     * Run at end of tests
     */
    @AfterClass
    public static void endTest() {
        // remove test game from database
        db.remove_game(player, scramble.get_scramble_id());
        db = null;

        // destory other objects
        player = null;
        scramble = null;
    }

}