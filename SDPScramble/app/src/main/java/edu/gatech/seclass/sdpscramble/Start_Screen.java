package edu.gatech.seclass.sdpscramble;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
/*
Start_Screen_UI
 */
public class Start_Screen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_screen);

        //click the register button
        Button ToRegister = (Button) findViewById(R.id.btn_register);
        ToRegister.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent register = new Intent(view.getContext(), Register.class);
                startActivity(register);
            }
        });

        //click the login button
        Button ToLogIn = (Button) findViewById(R.id.btn_login);
        ToLogIn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent login = new Intent(view.getContext(), Login.class);
                startActivity(login);
            }
        });
    }

    // Switch to register UI: zhang//

}
