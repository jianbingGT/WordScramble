package edu.gatech.seclass.sdpscramble;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Team80 on 10/13/2017.
 */
public class Main_AppTest {

    private Main_App myMain;
    private EWS_Facade facade;

    /**
     * Run before each test
     */
    @Before
    public void setUp() {

        // get Main_App instance
        myMain = Main_App.get_instance();
        facade = EWS_Facade.get_instance();
    }

    /**
     * Run after each test
     */
    @After
    public void tearDown() {

        // remove Main_App instance
        myMain = null;
        facade = null;
    }

    /**
     * Test get current puzzle
     */
    @Test
    public void get_curr_puzzle() {

        // get first WordScramble from EWS
        WordScramble scramble = facade.retrieve_scrambles().get(0);

        // make this scramble the current puzzle
        myMain.select_new_scramble(scramble.get_scramble_id());

        // test that these are the same scramble
        assertEquals(scramble.get_scramble_id(), myMain.get_curr_puzzle().get_scramble_id());
    }

    /**
     * Test get current player and login player
     */
    @Test
    public void get_curr_player_and_login() {

        // get first player from EWS
        Player player = facade.retrieve_players().get(0);

        // make this player the current player
        myMain.login_player(player.get_username());

        // test that these are the same player
        assertEquals(player.get_username(), myMain.get_curr_player().get_username());
    }

    /**
     * Test get current player and create player
     */
    @Test
    public void get_curr_player_and_create_player() throws Exception {

        // get first player from EWS
        Player player = facade.retrieve_players().get(0);

        // make this player the current player
        myMain.create_player(player.get_first_name(), player.get_last_name(),
                player.get_email(), player.get_username());

        // test that these are the same player
        assertEquals(player.get_last_name(), myMain.get_curr_player().get_last_name());
    }

    /**
     * Test scramble phrase and create scramble
     * @throws Exception
     */
    @Test
    public void scramble_phrase_and_create_scramble() throws Exception {
        // get phrase to be scrambled
        String phrase = "The quick brown fox jumps over the lazy dog!";

        // scramble it
        String scrambled = myMain.scramble_phrase(phrase);

        // get a user
        Player player = facade.retrieve_players().get(0);

        // create a scramble
        String scram_id = myMain.create_scramble(phrase, scrambled,
                "the whole alphabet", player.get_username());

        // select the scramble
        myMain.select_new_scramble(scram_id);

        // test if they are the same
        assertEquals(scrambled, myMain.get_curr_puzzle().get_scramble());
    }

}