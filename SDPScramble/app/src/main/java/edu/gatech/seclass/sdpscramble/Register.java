package edu.gatech.seclass.sdpscramble;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.net.SocketTimeoutException;

/*
 * register UI
 */


public class Register extends AppCompatActivity {
    private EditText inputFirstName;
    private EditText inputLastName;
    private EditText inputEmail;
    private EditText inputUsername;
    private Main_App main = Main_App.get_instance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //initiate the input string for register
        inputFirstName = (EditText) findViewById(R.id.input_first_name);
        inputLastName = (EditText) findViewById(R.id.input_last_name);
        inputEmail = (EditText) findViewById(R.id.input_email);
        inputUsername = (EditText) findViewById(R.id.input_username);

        //if the user input the wrong username during login, directly get the user name from Login UI for registration.
        Intent i = getIntent();
        String LogInUsername = i.getStringExtra("Username");
        inputUsername.setText(LogInUsername);

        //this one need to run only when valid username has been created
        Button register = (Button) findViewById(R.id.btn_register);

        // button for cancel the register
        Button Cancel = (Button) findViewById(R.id.btn_cancel);

        // OnClickListener to cancel register, go back to Start_Screeen
        Cancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view){
                Intent cancel = new Intent(view.getContext(), Start_Screen.class);
                startActivity(cancel);
            }
        });
    }

    //set up onclickerlistener method for Button Register. If the input is empty, prompt the error
    public void onRegister(View view) throws SocketTimeoutException {
        String FirstName = inputFirstName.getText().toString();
        String LastName = inputLastName.getText().toString();
        String Email = inputEmail.getText().toString();
        String Username = inputUsername.getText().toString();
        if (FirstName.isEmpty()){
            inputFirstName.setError("Empty First Name");}
        if (LastName.isEmpty()){
            inputLastName.setError("Empty Last Name");}
        if (Email.isEmpty()){
            inputEmail.setError("Empty Email");}
        if (Username.isEmpty()){
            inputUsername.setError("Empty Username");}
        if (inputFirstName.getError() != null || inputLastName.getError() != null ||
                inputUsername.getError() != null || inputEmail.getError() != null) {
            return;}
        main.create_player(FirstName,LastName,Email,Username);

        //Get the registered unique username from Main_App, and transit to LogInUI
        String username = main.get_curr_player().get_username();
        Intent login = new Intent(view.getContext(), Login.class);

        //Save the returned username and automatically put in Username in Login_UI
        login.putExtra("Username", username);
        startActivity(login);
    }
}
