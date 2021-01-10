package com.example.ghb_draft;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddNewAccounts extends AppCompatActivity {
    private Button savings, credit, student, save, back;
    private EditText student_num;
    DataBaseHelper dataBaseHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_accounts);
        back = (Button) findViewById(R.id.btn_add_back);
        save = (Button) findViewById(R.id.btn_add_save);
        savings = (Button) findViewById(R.id.btn_savings);
        student_num = (EditText) findViewById(R.id.editTextStudentNumber);
        dataBaseHelper = new DataBaseHelper(AddNewAccounts.this);
        savings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // A new savings account with 0$ gets added the the users account and will be added to the main page
                Accounts account = new Accounts(dataBaseHelper.getActiveUser(), (float) 0, "Savings", null, -1);
                dataBaseHelper.addAccount(account);
                openHomePage();
            }
        });
        credit = (Button) findViewById(R.id.btn_credit);
        credit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // A new credit account with 0$ gets added the the users account and will be added to the main page
                Accounts account = new Accounts(dataBaseHelper.getActiveUser(), (float) 0, "Credit", null, -1);
                dataBaseHelper.addAccount(account);
                openHomePage();
            }
        });
        student = (Button) findViewById(R.id.btn_student);
        student.setOnClickListener(new View.OnClickListener() {
            // When this button is selected the student number text edit will become visible
            @Override
            public void onClick(View v) {
                student_num.setVisibility(View.VISIBLE);
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openHomePage();
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            // When the user clicks the save button it will check with the database to check if the user already has a one card account, if they do it will tell them,
            // if they do not have an account, one will be added to the user's account with their student number
            @Override
            public void onClick(View v) {
                try {
                    if (dataBaseHelper.findStudentAccount()) {
                        int st_num = Integer.parseInt(student_num.getText().toString());
                        Accounts account = new Accounts(dataBaseHelper.getActiveUser(), (float) 0, "One Card", st_num, -1);
                        dataBaseHelper.addAccount(account);
                        openHomePage();
                    } else {
                        Toast.makeText(getApplicationContext(), "One Card Account Already Exists", Toast.LENGTH_LONG).show();
                        student_num.setText("");
                        student_num.setVisibility(View.INVISIBLE);
                    }
                } catch (NumberFormatException e){
                    Toast.makeText(getApplicationContext(), "Invalid Student Number", Toast.LENGTH_LONG).show();
                    student_num.setText("");
                }

            }
        });


    }
    public void openHomePage(){
        Intent intent = new Intent(this, Main_Page.class);
        startActivity(intent);
    }
}