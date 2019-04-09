package edu.gatech.seclass.sdpscramble;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;

public class onPlaying extends AppCompatActivity {
    //initiate the Main_App instance and EWS
    private Main_App main = Main_App.get_instance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_on_playing);

        //display the Username at the top of UI
        String Username = main.get_curr_player().get_username();
        TextView username = (TextView) findViewById(R.id.txt_current_user);
        username.setText(Username);

        //Initiate the linearlayout UI option for different selection from button in Main_Menu
        LinearLayout UI_Find = (LinearLayout) findViewById(R.id.UI_Find_Scramble);
        LinearLayout UI_Scramble = (LinearLayout) findViewById(R.id.UI_Create_Scramble);
        LinearLayout UI_Player_Stat = (LinearLayout) findViewById(R.id.UI_Player_Statistics);
        LinearLayout UI_Scramble_Stat = (LinearLayout) findViewById(R.id.UI_Scramble_Statistics);

        //Initiate the local database for storing palyer and scramble
        main.create_db_handler(this.getApplicationContext());

        //Initiate the linearlayout for in_prog Title and Scramble list
        LinearLayout in_prog_title = (LinearLayout) findViewById(R.id.in_prog_linear_title) ;
        LinearLayout in_prog_ListView = (LinearLayout) findViewById(R.id.in_prog_linear_listview);

        // Get the Activity variable passed from Main_Menu UI
        Intent i = getIntent();
        String activity = i.getStringExtra("Activity");

        //Set Visibility for Find_Scramble linearlayout according to different activity from Main_Menu
        if (activity.equals("Find")) {
            // Show the find_Scramble UI
            UI_Find.setVisibility(View.VISIBLE);
            UI_Scramble.setVisibility(View.GONE);
            UI_Player_Stat.setVisibility(View.GONE);
            UI_Scramble_Stat.setVisibility(View.GONE);

            //Initiate the listview to show unsloved Scramble list and in_prog list
            final ListView ScrambleList = (ListView) findViewById(R.id.Scrambles_List);
            final ListView InProgressList = (ListView) findViewById(R.id.in_progress_list);

            //Set the listView for unsloved and in_prog Scrambles
            try {
                final ArrayList<String> unsolved =
                        main.get_unsolved_scrambles(main.get_curr_player());
                final ArrayList<String> in_Prog =
                        main.get_in_prog_scrambles();

                //set the list of unsolved sramble for showing in listView
                String[] unsolvedlist = new String[unsolved.size()];
                for (int j = 0; j < unsolved.size(); j++) {
                    String scramble = unsolved.get(j);
                    unsolvedlist[j] = scramble;
                }

                //set the list of in_prog sramble for showing in listView
                String[] in_prog_list = new String[in_Prog.size()];
                for (int j = 0; j < in_Prog.size(); j++) {
                    String scramble = in_Prog.get(j);
                    in_prog_list[j] = scramble;
                }

                //hide the in_progress layout if it is empty
                if (in_Prog.isEmpty()) {
                    in_prog_title.setVisibility(View.GONE);
                    in_prog_ListView.setVisibility(View.GONE);
                }

                //ArrayAdapter for unsloved scramble list
                ArrayAdapter unsolvedadapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1,
                        unsolvedlist);

                //ArrayAdapter for unsolovd Scramble
                ArrayAdapter inprogadapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1,
                        in_prog_list);
                ScrambleList.setAdapter(unsolvedadapter);
                InProgressList.setAdapter(inprogadapter);

                //Set the unsloved scramble list clickable and switch to Solve_Scramble UI
                ScrambleList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        String selected_scrambleID = (String) ScrambleList.getItemAtPosition(i);
                        Intent play = new Intent(view.getContext(), Solve_Scramble.class);
                        //pass the selectedID value to next Solve Scramble UI
                        play.putExtra("Scramble", selected_scrambleID);
                        startActivity(play);
                    }
                });

                //Set the in_progress scramble list clickable and switch to Solve_Scramble UI
                InProgressList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        String selected_scrambleID = (String) InProgressList.getItemAtPosition(i);
                        Intent play = new Intent(view.getContext(), Solve_Scramble.class);
                        //pass the selectedID value to next Solve Scramble UI
                        play.putExtra("Scramble", selected_scrambleID);
                        startActivity(play);
                    }
                });
            } catch (Exception e) {
            }
        }
        //Set Visibility for Create_Scramble linearlayout according to different activity from Main_Menu
        else if (activity.equals("Create")) {
            UI_Find.setVisibility(View.GONE);
            UI_Scramble.setVisibility(View.VISIBLE);
            UI_Player_Stat.setVisibility(View.GONE);
            UI_Scramble_Stat.setVisibility(View.GONE);

            //initiate the input EditText for creating Scramble
            final EditText phrase = (EditText) findViewById(R.id.txt_input_phrase);
            final EditText scrambled_phrase = (EditText) findViewById(R.id.txt_scrambled_pharse);
            final EditText clue = (EditText) findViewById(R.id.txt_clue);
            final EditText creater = (EditText) findViewById(R.id.txt_created_user);

            // do not allow to manually input the scrambled phrase
            scrambled_phrase.setFocusable(false);

            // Input the username(creater)
            creater.setText(Username);
            creater.setFocusable(false);

            //define the button scramble, to set the scramble phrase
            Button scramble_phrase = (Button) findViewById(R.id.btn_scramble);
            scramble_phrase.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    if (!phrase.getText().toString().isEmpty()) {
                        scrambled_phrase.setText(main.scramble_phrase(phrase.getText().toString()));
                    }
                }
            });

            // define the create scramble, to set the scramble phrase, and return the Scramble ID for toast.show
            Button Create = (Button) findViewById(R.id.btn_create);
            Create.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    try {
                        String created_id = main.create_scramble(phrase.getText().toString(), scrambled_phrase.getText().toString(),
                                clue.getText().toString(), creater.getText().toString());
                        Toast.makeText(onPlaying.this, "Word Scramble Created with ID: " + created_id,
                                Toast.LENGTH_LONG).show();
                    } catch (SocketTimeoutException e) {
                    }
                }
            });
        }
        //Set Visibility for showing Player_Stat linearlayout according to different activity from Main_Menu
        else if (activity.equals("Player_Stat")) {
            UI_Find.setVisibility(View.GONE);
            UI_Scramble.setVisibility(View.GONE);
            UI_Player_Stat.setVisibility(View.VISIBLE);
            UI_Scramble_Stat.setVisibility(View.GONE);

            //Set up a TableLayout to show the player_stats
            TableLayout table_player_stat = (TableLayout) findViewById(R.id.table_player);

            //get the player_stats
            List<List<String>> player_stats = main.get_all_player_stats();

            //set up the top row the show the item names
            TableRow tbrow0 = new TableRow(this);
            TextView tv0 = new TextView(this);
            tv0.setText("First Name");
            tbrow0.addView(tv0);
            TextView tv1 = new TextView(this);
            tv1.setText("Last Name");
            tbrow0.addView(tv1);
            TextView tv2 = new TextView(this);
            tv2.setText("Solved NO.");
            tbrow0.addView(tv2);
            TextView tv3 = new TextView(this);
            tv3.setText("Created N0.");
            tbrow0.addView(tv3);
            TextView tv4 = new TextView(this);
            tv4.setText("Solved/Created");
            tbrow0.addView(tv4);

            // show row0 (name) in the TableLayout
            table_player_stat.addView(tbrow0);

            // Show the item in List<List<String>> in the TableLayout
            for (List<String> player_info: player_stats) {
                TableRow tbrow = new TableRow(this);
                for (int j = 0; j < player_info.size(); j++) {
                    TextView tv_value = new TextView(this);
                    tv_value.setText(player_info.get(j));
                    tbrow.addView(tv_value);
                }
                table_player_stat.addView(tbrow);
            }
        }
        //Set Visibility for showing scrambles_Stat linearlayout according to different activity from Main_Menu
        else if (activity.equals("Scramble_Stat")) {
            UI_Find.setVisibility(View.GONE);
            UI_Scramble.setVisibility(View.GONE);
            UI_Player_Stat.setVisibility(View.GONE);
            UI_Scramble_Stat.setVisibility(View.VISIBLE);

            //Set up a TableLayout to show the player_stats
            TableLayout table_scramble_stat = (TableLayout) findViewById(R.id.table_Scrambles);

            //get the scramble player_stats
            List<List<String>> Scramble_stats = main.get_scramble_stats(main.get_curr_player());

            //set up the top row the show the item names
            TableRow tbrow0 = new TableRow(this);
            TextView tv0 = new TextView(this);
            tv0.setText("ScrambleID");
            tbrow0.addView(tv0);
            TextView tv1 = new TextView(this);
            tv1.setText("Status");
            tbrow0.addView(tv1);
            TextView tv2 = new TextView(this);
            tv2.setText("Solved Times");
            tbrow0.addView(tv2);

            // show row0 (name) in the TableLayout
            table_scramble_stat.addView(tbrow0);

            // Show the item in List<List<String>> in the TableLayout
            for (List<String> scramble_info: Scramble_stats) {
                TableRow tbrow = new TableRow(this);
                for (int j = 0; j < scramble_info.size(); j++) {
                    TextView tv_value = new TextView(this);
                    tv_value.setText(scramble_info.get(j));
                    tbrow.addView(tv_value);
                }
                table_scramble_stat.addView(tbrow);
            }
        }
    }
}