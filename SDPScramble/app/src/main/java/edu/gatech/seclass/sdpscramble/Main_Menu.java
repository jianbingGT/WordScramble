package edu.gatech.seclass.sdpscramble;


import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Main_Menu extends AppCompatActivity {
    private TextView username;
    //initiate the instance for Main_App
    private Main_App main = Main_App.get_instance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main__menu);

        //Get the current username
        String Username = main.get_curr_player().get_username();

        //display the login Username from main_menu
        username = (TextView) findViewById(R.id.txt_current_user);
        username.setText(Username);
    }
    // OnClickListener method for each button. Click each button will start the onPlaying activity,
    // By passing the different String "Activity" value to next UI, the OnPlaying can display different UI option,
    public void onPlaying(View view) {
        Intent intent = new Intent(this, onPlaying.class);
        switch(view.getId()){
            case R.id.btn_find_scrambles:
                intent.putExtra("Activity", "Find");
                break;
            case R.id.btn_create_scramble:
                intent.putExtra("Activity", "Create");
                break;
            case R.id.btn_player_stats:
                intent.putExtra("Activity", "Player_Stat");
                break;
            case R.id.btn_scramble_stats:
                intent.putExtra("Activity", "Scramble_Stat");
                break;
        }
        startActivity(intent);
    }
}