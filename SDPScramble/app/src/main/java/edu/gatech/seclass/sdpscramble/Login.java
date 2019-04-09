package edu.gatech.seclass.sdpscramble;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Login extends AppCompatActivity {
    private EditText username;
    private Main_App main = Main_App.get_instance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //define the place for username input
        username = (EditText) findViewById(R.id.input_username);

        //display the Username from register
        Intent i = getIntent();
        String registerUsername = i.getStringExtra("Username");
        username.setText(registerUsername);


        // setOnClickListener for login. If the username can be found in EWS, go ahead to login and Main_menu will be shown.
        Button LOGIN = (Button) findViewById(R.id.btn_login);
        LOGIN.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                String Username = username.getText().toString();
                if (main.login_player(Username)) {
                    Intent main_menu = new Intent(view.getContext(), Main_Menu.class);
                    startActivity(main_menu);
                }
                // show the error if the username can not be found.
                else {
                    username.setError("Username not found, please register");
                }
            }
        });

        Button Cancel = (Button) findViewById(R.id.btn_cancel);
        Cancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent cancel = new Intent(view.getContext(), Start_Screen.class);
                startActivity(cancel);
            }
        });
    }
}