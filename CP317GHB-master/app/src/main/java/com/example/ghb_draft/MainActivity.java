package com.example.ghb_draft;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.net.Inet4Address;

public class MainActivity extends AppCompatActivity {
    DataBaseHelper dataBaseHelper;
    private EditText Name;
    private EditText Password, email;
    private TextView Attempts;
    private Button Login;
    private Button Register;
    private int counter = 5;
    //check
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dataBaseHelper = new DataBaseHelper(MainActivity.this);
        Name = (EditText) findViewById(R.id.et_Name);
        Password = (EditText) findViewById(R.id.et_Password);
        Attempts = (TextView) findViewById(R.id.tv_attmepts);
        Login = (Button) findViewById(R.id.btn_Login);
        Register = (Button) findViewById(R.id.btn_Register);

        Attempts.setText("Number of Attempts Remaining: " + String.valueOf(counter));

        // When the user clicks the login button this onCLickListener gets called
        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // This is to check when an admin is logging in, if the if is true then it sets isAdmin to true telling the system a admin is using it
                // Else, it checks for a user to login in
                if (Name.getText().toString().equals("Admin") && Password.getText().toString().equals("password")) {
                    // calls the database function isAdmin
                   dataBaseHelper.isAdmin(Name.getText().toString());
                   // Opens the Admin main page rather the the user main page
                   openAdminMainPage();
                } else {
                    try {
                        // Stores a true/false to the variable x, by calling the function in the database to see if this email and password exists in the database
                        boolean x = dataBaseHelper.isValidEmailAndPassword(Name.getText().toString(), Password.getText().toString());

                        // If the user fails to enter the right information, a counter decreases whioh is the amount of attempts the user has left to enter their information
                        if (!x) {
                            Toast.makeText(MainActivity.this, "Email or Password is not recognized.", Toast.LENGTH_SHORT).show();
                            counter--;
                            Attempts.setText("Number of Attempts Remaining: " + String.valueOf(counter));
                            // When this method is called, it is checking ig the amount of attempts left is equal to zero
                            noAttempts();
                        } else {
                            // If the user enters the correct information to enter their account they will be taken to the main page
                            openMainPage();
                        }

                    } catch (Exception e) {
                        Toast.makeText(MainActivity.this, "Error with registration", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });


        // If the register button is selected then it will take the user to the register form
        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openRegister();
            }
        });

    }

    // When this method is called, it will check if the counter is equal to 0, if it is, then the login button will get disabled so that the user can not login in anymore
    public void noAttempts() {
        if (counter == 0) {
            Login.setEnabled(false);
            Toast.makeText(MainActivity.this, "No more attempts. Refresh App or Close App to Try Again.", Toast.LENGTH_LONG).show();
        }
    }

    public void openRegister() {
        Intent intent = new Intent(this, Registration.class);
        startActivity(intent);
    }

    public void openMainPage() {
        Intent intent = new Intent(this, Main_Page.class);
        startActivity(intent);
    }

    public void openAdminMainPage() {
        Intent intent = new Intent(this, AdminUserLogin.class);
        startActivity(intent);
    }
}