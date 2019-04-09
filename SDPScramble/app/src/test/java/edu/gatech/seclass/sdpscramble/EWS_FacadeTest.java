package edu.gatech.seclass.sdpscramble;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by Team80 on 10/12/2017.
 */
public class EWS_FacadeTest {

    private EWS_Facade myFacade;

    /**
     * Run before each test
     */
    @Before
    public void setUp() {

        // get instance of EWS_Facade
        myFacade = EWS_Facade.get_instance();
    }

    /**
     * Run after each test
     */
    @After
    public void tearDown() {

        // set myFacade reference to null
        myFacade = null;
    }

    /**
     * Test that facade retrieves scrambles from EWS
     */
    @Test
    public void retrieve_scrambles() {
        // use initialize to add 2 scrambles to the EWS
        List<List<String>> init_list = new ArrayList<>();
        List<String> scramble_list = Arrays.asList("bark", "krab", "talk like a dog", "nico");
        init_list.add(scramble_list);
        scramble_list = Arrays.asList("meow", "woem", "talk like a cat", "nico");
        init_list.add(scramble_list);
        myFacade.initialize_scrambles(init_list);

        // check if at least 2 scrambles are retrieved
        assertTrue(myFacade.retrieve_scrambles().size() >= 2);

    }

    /**
     * Test that facade retrieves players from the EWS
     */
    @Test
    public void retrieve_players() {
        // use initialize to add 2 scrambles to the EWS
        List<List<String>> init_list = new ArrayList<>();
        List<String> player_list = Arrays.asList("bob23", "Robert", "Williams", "bob@aol.com");
        init_list.add(player_list);
        player_list = Arrays.asList("carlo33", "Carlos", "Stevens", "carlo@yahoo.com");
        init_list.add(player_list);
        myFacade.initialze_players(init_list);

        // check if at least 2 players are retrieved
        assertTrue(myFacade.retrieve_players().size() >= 2);

    }

    /**
     * Test that add new player adds a player
     * @throws Exception: SocketTimeoutException, IllegalArgumentException
     */
    @Test
    public void add_new_player() throws Exception {

        // create boolean for player found
        boolean found = false;

        // add player to the EWS
        Player myPlayer = myFacade.add_new_player("nico44", "Nicholas",
                "Flamelle", "nico@borderlands.com");

        // check if player has been added
        List<Player> players = myFacade.retrieve_players();

        for(Player player: players) {
            if(player.get_username().equals(myPlayer.get_username())) {
                found = true;
                break;
            }
        }

        // check if player was found
        assertTrue(found);

    }

    /**
     * Test that report solved add an entry for a solved puzzle to a player
     */
    @Test
    public void report_solved() {

        // get test player and scramble
        Player player_before = myFacade.retrieve_players().get(0);
        WordScramble test_scramble = myFacade.retrieve_scrambles().get(0);

        // report solved
        myFacade.report_solved(test_scramble, player_before);

        // get the same player
        Player player_after = null;

        List<Player> players = myFacade.retrieve_players();

        for(Player player: players) {

            // if this is the same player
            if(player_before.get_username().equals(player.get_username())) {

                // set player_after to this player
                player_after = player;
                break;
            }
        }

        // check that puzzle solved list is increased
        assertTrue(player_after.get_solved_scrambles().size() >
                player_before.get_solved_scrambles().size());


    }

    /**
     * Test adding a new scramble
     * @throws Exception: SocketTimeoutException, IllegalArgumentException
     */
    @Test
    public void add_new_scramble() throws Exception {

        // a player
        Player test_player = myFacade.retrieve_players().get(0);

        // get current number of scrambles
        int num_before = myFacade.retrieve_scrambles().size();

        // add a scramble
        myFacade.add_new_scramble("tweet", "teewt", "talk like a bird", test_player.get_username());

        // get new number of scrambles
        int num_after = myFacade.retrieve_scrambles().size();

        // check that new number is higher than before
        assertTrue(num_after > num_before);

    }

}