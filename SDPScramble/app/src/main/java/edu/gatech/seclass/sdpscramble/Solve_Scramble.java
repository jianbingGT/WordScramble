package edu.gatech.seclass.sdpscramble;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Solve_Scramble extends AppCompatActivity {
    private Main_App main = Main_App.get_instance();
    private Puzzle selected_puzzle;
    private EditText onPlayingScramble;
    private EditText clue;
    private EditText guess;
    private Button solve;
    private Button back_to_find;
    private String input_guess;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solve__scramble);

        //Get the scramble ID from last UI intent
        Intent i = getIntent();
        final String ScrambleID = i.getStringExtra("Scramble");

        //get the unsolved and in_progree scrambles
        ArrayList<String> unsolved =
                main.get_unsolved_scrambles(main.get_curr_player());
        ArrayList<String> in_Prog =
                main.get_in_prog_scrambles();

        //Check the selected scramble is from which container: unsolved or in_progress, then set the current puzzle
        if (unsolved.contains(ScrambleID)) {
            selected_puzzle = main.select_new_scramble(ScrambleID);
        } else if (in_Prog.contains(ScrambleID)) {
            selected_puzzle = main.select_in_prog_scramble(ScrambleID);
        }

        //Set and show scramble phrase in the EditText for ongoing Scramble
        onPlayingScramble = (EditText) findViewById(R.id.txt_scrambled_pharse);
        onPlayingScramble.setFocusable(false);
        onPlayingScramble.setText(selected_puzzle.get_scramble());

        //Set the clue for ongoing Scramble
        clue = (EditText) findViewById(R.id.txt_clue);
        clue.setFocusable(false);
        clue.setText(selected_puzzle.get_clue());

        //Set the guess phrase, with default value from last guess, but remain the EditText be able to manually input
        guess = (EditText) findViewById(R.id.txt_guess);
        guess.setText(main.get_curr_puzzle().get_guess());

        //Define the Button Solve, to check the guess is right or not
        solve = (Button) findViewById(R.id.btn_solve);
        solve.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view){
                input_guess = guess.getText().toString();
                // if guess is right, show scramble sloved and back to find UI
                if (selected_puzzle.submit_solution(input_guess)) {
                    Toast.makeText(Solve_Scramble.this, "Scramble Solved " + ScrambleID,
                    Toast.LENGTH_SHORT).show();
                    main.report_solved();
                    Intent solved = new Intent(view.getContext(), onPlaying.class);
                    solved.putExtra("Activity", "Find");
                    startActivity(solved);
                }
                // if the guess is wrong, just show wrong guess
                else {
                    Toast.makeText(Solve_Scramble.this, "Wrong Guess",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

        //Define the button back to find
        back_to_find = (Button) findViewById(R.id.btn_back_to_Find);
        back_to_find.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view){
                input_guess = guess.getText().toString();
                selected_puzzle.submit_solution(input_guess);
                main.before_exit();
                Intent solved = new Intent(view.getContext(), onPlaying.class);
                solved.putExtra("Activity", "Find");
                startActivity(solved);
            }
        });

    }
}
