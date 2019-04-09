package edu.gatech.seclass.sdpscramble;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by Team80 on 10/12/2017.
 * This tests the Player class
 */
public class PlayerTest {

    private Player myPlayer;

    /**
     * Run before all tests
     */
    @Before
    public void setUp() {

        // create test Player object
        myPlayer = new Player("nico134", "Nicholas", "Williams", "nico@gmail.com");
    }

    /**
     * Run after all tests
     */
    @After
    public void tearDown() {

        // destroy object
        myPlayer = null;
    }

    /**
     * Test get_username method
     */
    @Test
    public void get_username() {

        // check that expected username is returned
        assertEquals("nico134", myPlayer.get_username());
    }

    /**
     * Test get_first_name method
     */
    @Test
    public void get_first_name() {

        // check that expected first name is returned
        assertEquals("Nicholas", myPlayer.get_first_name());
    }

    /**
     * Test get_last_name method
     */
    @Test
    public void get_last_name() {

        // check that expected last name is returned
        assertEquals("Williams", myPlayer.get_last_name());
    }

    /**
     * Test get_email method
     */
    @Test
    public void get_email() {

        // check that expected email is returned
        assertEquals("nico@gmail.com", myPlayer.get_email());
    }

    /**
     * Test get_solved_scrambles when empty
     */
    @Test
    public void get_solved_scrambles_empty() {

        // create empty list
        List<String> test_list = new ArrayList<>();

        // check that returned list is empty
        assertEquals(test_list, myPlayer.get_solved_scrambles());
    }

    /**
     * Test append_to_solved_scrambles method
     */
    @Test
    public void append_to_solved_scrambles() {

        // append new id
        myPlayer.append_to_solved_scrambles("Scram01");

        // create new list
        List<String> test_list = new ArrayList<>();
        test_list.add("Scram01");

        // check that returned list matches expected
        assertEquals(test_list, myPlayer.get_solved_scrambles());
    }

}