package edu.gatech.seclass.sdpscramble;

import android.support.test.InstrumentationRegistry;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by Team80 on 10/17/2017.
 * Tests methods that require a database handler for Main_App class
 */
public class Main_AppTest {

    private static Main_App main;
    private static Player player;
    private static Puzzle scramble;

    /**
     * Run this before the tests start
     */
    @BeforeClass
    public static void startTest() {

        // set up main app and db
        main = Main_App.get_instance();
        main.create_db_handler(InstrumentationRegistry.getTargetContext());

        // set current player and puzzle
        String scram_id;
        try {
            main.create_player("Dalton", "Scott", "ds@aol.com", "dasc");
            player = main.get_curr_player();
            scram_id = main.create_scramble("quack", "kcauq", "talk like a duck",
                    player.get_username());
            main.select_new_scramble(scram_id);
        }
        catch (Exception e) {

        }
    }

    /**
     * Tests saving an in progress puzzle
     */
    @Test
    public void save_in_prog() {

        // submit a solution to the puzzle
        main.get_curr_puzzle().submit_solution("tweet");

        // save the in progress puzzle
        main.save_in_prog();

        // create a new puzzle
        scramble = main.select_in_prog_scramble(
                main.get_curr_puzzle().get_scramble_id());

        // check if guess on returned Puzzle is as expected
        assertEquals("tweet", scramble.get_guess());
    }

    /**
     * Tests getting the in progress scrambles list
     */
    @Test
    public void get_in_prog_scrambles() {

        // submit a solution to the puzzle
        main.get_curr_puzzle().submit_solution("bark");

        // save the in progress puzzle
        main.save_in_prog();

        // get the in progress scrambles
        List<String> in_prog = main.get_in_prog_scrambles();

        // check if the expected id is on the list
        assertTrue(in_prog.contains(main.get_curr_puzzle().get_scramble_id()));
    }

    /**
     * Tests the exit method, which saves off an in progress puzzle
     */
    @Test
    public void exit() {

        // add a new guess
        main.get_curr_puzzle().submit_solution("hisss");

        // run exit method
        main.exit();

        // get saved puzzle
        scramble = main.select_in_prog_scramble(main.get_curr_puzzle().get_scramble_id());

        // check that values match
        assertEquals("hisss", scramble.get_guess());

    }

    /**
     * Run at end of tests
     */
    @AfterClass
    public static void endTest() {

        // remove test game from database
        Database_Handler db = new Database_Handler(InstrumentationRegistry.getTargetContext());
        db.remove_game(player, scramble.get_scramble_id());

        // destroy other objects
        main = null;
        player = null;
        scramble = null;
    }

}