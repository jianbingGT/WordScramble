package edu.gatech.seclass.sdpscramble;

        import android.content.ContentValues;
        import android.content.Context;
        import android.database.Cursor;
        import android.database.sqlite.SQLiteDatabase;
        import android.database.sqlite.SQLiteOpenHelper;

        import java.util.ArrayList;
        import java.util.List;

/**
 * Created by Team80 on 10/11/2017.
 * This class will handle interfacing with the database
 */

public class Database_Handler extends SQLiteOpenHelper {
    // attributes

    // database version number
    private static final int DATABASE_VERSION = 10;

    // database name
    private static final String DATABASE_NAME = "saved_puzzles";

    // table name
    private static final String TABLE_GAMES = "games";

    // table column names
    private static final String KEY_GAME_ID = "gameid";
    private static final String KEY_USER = "user";
    private static final String KEY_SCRAMBLE_ID  = "scrambleid";
    private static final String KEY_GUESS = "guess";

    /**
     * Private Database_Handler constructor
     * @param context: context
     */
    public Database_Handler(Context context) {
        // super class constructor
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * On creation of database
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        // create SQL command to create the games table
        String CREATE_GAMES_TABLE = "CREATE TABLE " + TABLE_GAMES + "( "
                + KEY_GAME_ID + " TEXT PRIMARY KEY, "
                + KEY_USER + " TEXT NOT NULL, "
                + KEY_SCRAMBLE_ID + " TEXT NOT NULL, "
                + KEY_GUESS + " TEXT NOT NULL );";

        // execute command
        db.execSQL(CREATE_GAMES_TABLE);
    }

    /**
     * When upgrading database
     * @param db: database to upgrade
     * @param oldVersion: old database version
     * @param newVersion: new database version
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // drop the games table if it exists
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_GAMES);

        // create default table
        onCreate(db);
    }

    /**
     * Add a record to the database
     * @param scramble: current scramble
     * @param user: current player
     */
    private void add_game(Puzzle scramble, Player user) {
        // get a writable database
        SQLiteDatabase db = this.getWritableDatabase();

        // create a content values object
        ContentValues values = new ContentValues();

        // set values to input
        values.put(KEY_GAME_ID, user.get_username() + "-" + scramble.get_scramble_id());
        values.put(KEY_USER, user.get_username());
        values.put(KEY_SCRAMBLE_ID, scramble.get_scramble_id());
        values.put(KEY_GUESS, scramble.get_guess());

        // insert record
        db.insert(TABLE_GAMES, null, values);
    }

    /**
     * Removes a game for the current user
     * @param user: current user
     * @param scramble_id: scramble id
     */
    public void remove_game(Player user, String scramble_id) {
        // get a writable database
        SQLiteDatabase db = this.getWritableDatabase();

        // run delete
        db.delete(TABLE_GAMES, KEY_GAME_ID + "=?",
                new String[]{user.get_username() + "-" + scramble_id});
    }

    /**
     * retrieves all scramble ids that this player has saved into the database
     * @param user: the current player
     * @return list of scramble ids for saved games
     */
    public ArrayList<String> get_games(Player user) {
        // create new list for scramble ids
        ArrayList<String> games_list = new ArrayList<String>();

        // get a readable database
        SQLiteDatabase db = this.getReadableDatabase();

        // set the cursor to the query results
        Cursor cursor = db.query(TABLE_GAMES, new String[] {KEY_SCRAMBLE_ID},
                KEY_USER + "=?", new String[] {user.get_username()}, null, null, null, null);

        // if the cursor is at the first position
        if(cursor.moveToFirst()) {

            do {
                // get the scramble id and add it to the list
                String to_add = cursor.getString(0);
                games_list.add(to_add);

                // continue as long as there are more results
            } while(cursor.moveToNext());
        }

        // close the cursor and database
        cursor.close();

        // return the games list
        return games_list;
    }

    /**
     * Get the save guess for a specific game
     * @param user: the current player
     * @param scramble_id: the current scramble id
     * @return the saved guess
     */
    public String get_saved_guess(Player user, String scramble_id) {
        // get a readable database
        SQLiteDatabase db = this.getReadableDatabase();

        // set the cursor to the query results
        Cursor cursor = db.query(TABLE_GAMES, new String[] {KEY_GUESS}, KEY_GAME_ID + "=?",
                new String[] {user.get_username() + "-" + scramble_id}, null, null, null, null);

        String guess = "";
        // go to first result
        if(cursor.getCount() > 0) {
            cursor.moveToFirst();

            // get the result
            guess = cursor.getString(0);

            // close the cursor and database
            cursor.close();
        }

        // return the guess
        return guess;
    }

    /**
     * update existing game
     * @param user: current player
     * @param scramble: scramble to update
     */
    private void update_game(Player user, Puzzle scramble) {
        // get a writable database
        SQLiteDatabase db = this.getWritableDatabase();

        // set values to be updated
        ContentValues values = new ContentValues();
        values.put(KEY_GUESS, scramble.get_guess());

        // run the update SQL command
        db.update(TABLE_GAMES, values, KEY_GAME_ID + "=?",
                new String[] {user.get_username() + "-" + scramble.get_scramble_id()});
    }

    /**
     * Check if a record exists
     * @param user: current user
     * @param scramble: current scramble
     * @return true if record exists, false otherwise
     */
    public boolean check_if_game_exists(Player user, Puzzle scramble) {
        // get a writable database
        SQLiteDatabase db = this.getReadableDatabase();

        // query database for record
        Cursor cursor = db.query(TABLE_GAMES, new String[] {KEY_GUESS}, KEY_GAME_ID + "=?",
                new String[] {user.get_username() + "-" + scramble.get_scramble_id()},
                null, null, null, null);

        // does cursor have results
        boolean results = false;
        if(cursor != null) {
            results = cursor.getCount() <= 0;


            // close cursor and database
            cursor.close();
        }

        // return
        return results;
    }

    /**
     * Saves a game base on whether it exists or not
     * @param user: current player
     * @param scramble: scramble to save
     */
    public void save_game(Player user, Puzzle scramble) {
        if(check_if_game_exists(user, scramble)) {
            add_game(scramble, user);
        }
        else{
            update_game(user, scramble);
        }
    }
}