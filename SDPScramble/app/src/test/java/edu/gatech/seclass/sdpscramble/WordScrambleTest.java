package edu.gatech.seclass.sdpscramble;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Team80 on 10/12/2017.
 * Tests for the WordScramble Class
 */
public class WordScrambleTest {

    private WordScramble myScramble;

    /**
     * Run before each test
     */
    @Before
    public void setUp(){

        // create new WordScramble object
        myScramble = new WordScramble("krab", "bark", "talk like a dog", "nico", "Scram01");
    }

    /**
     * Run after each test
     */
    @After
    public void tearDown() {

        // destroy myScramble
        myScramble = null;
    }

    /**
     * Test that get_scramble returns expected value
     */
    @Test
    public void get_scramble() {

        // test returned value
        assertEquals("krab", myScramble.get_scramble());
    }

    /**
     * Test that get_solution returns expected value
     */
    @Test
    public void get_solution() {

        // test returned value
        assertEquals("bark", myScramble.get_solution());
    }

    /**
     * Test that get_clue returns expected value
     */
    @Test
    public void get_clue() {

        // test returned value
        assertEquals("talk like a dog", myScramble.get_clue());
    }

    /**
     * Test that get_create_by returns expected value
     */
    @Test
    public void get_created_by() {

        // test returned value
        assertEquals("nico", myScramble.get_created_by());
    }

    /**
     * Test that get_scramble_id returns expected value
     */
    @Test
    public void get_scramble_id() {

        // test returned value
        assertEquals("Scram01", myScramble.get_scramble_id());
    }

}