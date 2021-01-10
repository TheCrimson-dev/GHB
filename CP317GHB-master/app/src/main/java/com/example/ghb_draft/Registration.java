package com.example.ghb_draft;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.net.Inet4Address;

public class Registration extends AppCompatActivity {
    private ImageButton btn_back;
    Button btn_register, btn_clearRegister;
    EditText fullName, phoneNumber, emailAddress, password, rePassword;

    ArrayAdapter userArrayAdapter;
    DataBaseHelper dataBaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        btn_register = findViewById(R.id.btn_RegisterButton);
        btn_clearRegister = findViewById(R.id.btn_clearRegister);
        fullName = findViewById(R.id.et_registerFullName);
        phoneNumber = findViewById(R.id.et_registerPhoneNumber);
        emailAddress = findViewById(R.id.et_registerEmail);
        password = findViewById(R.id.et_registerPassword);
        rePassword = findViewById(R.id.et_registerConPassword);

        dataBaseHelper = new DataBaseHelper(Registration.this);

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = emailAddress.getText().toString();

                UserModel userModel;
                try {
                    // When the user is registering and the fields emailAddress, password, or rePassword are empty then the user will not be able to register and a toast messae
                    // Will tell them to fill in the required fields
                    if (emailAddress.getText().toString().equals("") || password.getText().toString().equals("") || rePassword.getText().toString().equals("")){
                        emailAddress.setText("");
                        password.setText("");
                        rePassword.setText("");
                        Toast.makeText(Registration.this, "Error, Please Fill In Required Fields", Toast.LENGTH_SHORT).show();
                    }
                    // This checks if the email already exists in the database or not, if it doesn't this else if runs
                    else if (dataBaseHelper.isEmail(email) == false) {
                        // This if statement checks if the re-enter password is equal to your password, if so this statement runs
                        if (password.getText().toString().equals(rePassword.getText().toString())) {
                            // This gives the registering user an bank account, each user gets to start with a savings account that has 250$
                            Accounts starterAccount = new Accounts(emailAddress.getText().toString(), (float) 250, "Savings", null, -1);
                            // Creates user account and adds it into the database
                            userModel = new UserModel(fullName.getText().toString(), phoneNumber.getText().toString(), emailAddress.getText().toString(), password.getText().toString());
                            Toast.makeText(Registration.this, userModel.toString(), Toast.LENGTH_SHORT).show();
                            dataBaseHelper.addUser(userModel);
                            dataBaseHelper.addAccount(starterAccount);
                            // Takes the user back to the login page after the registration is complete
                            openLoginScreen();

                        // If the password does not match the rePassword then this else will be called
                        } else {
                            password.setText("");
                            rePassword.setText("");
                            Toast.makeText(Registration.this, "Passwords don't match", Toast.LENGTH_SHORT).show();
                            //userModel = new UserModel("error", "error", "error", "error");

                        }

                    // If the email already exists in the system then this else statement will be called
                    } else {
                        emailAddress.setText("");
                        Toast.makeText(Registration.this, "Email exists in system already", Toast.LENGTH_SHORT).show();
                        //userModel = new UserModel("error", "error", "error", "error");
                    }

                }
                catch (Exception e){
                    Toast.makeText(Registration.this, "Error with registration", Toast.LENGTH_SHORT).show();
                    userModel = new UserModel("error", "error", "error", "error");
                }

                DataBaseHelper dataBaseHelper = new DataBaseHelper(Registration.this);

            }
        });

        btn_back = (ImageButton) findViewById(R.id.btn_back);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openLoginScreen();
            }
        });
    }



    public void openLoginScreen() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}