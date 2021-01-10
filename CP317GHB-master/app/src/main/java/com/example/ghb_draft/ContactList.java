package com.example.ghb_draft;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ContactList extends AppCompatActivity {
    // references to buttons and other controls on the layout
    // references to buttons and other controls on the layout
    Button btn_add;
    ImageButton btn_home;
    EditText et_name, et_email;
    ListView lv_customerList;

    ArrayAdapter customerArrayAdapter;
    DataBaseHelper dataBaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_list);

        btn_add = findViewById(R.id.btn_add);
        et_email = findViewById(R.id.et_email);
        et_name = findViewById(R.id.et_name);
        lv_customerList = findViewById(R.id.lv_customerList);

        dataBaseHelper = new DataBaseHelper(ContactList.this);
        ShowCustomersOnListView(dataBaseHelper);

        btn_home = (ImageButton) findViewById(R.id.btn_home);
        btn_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openHomePage();
            }
        });

        // button listeners for the add and view all buttons
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DataBaseHelper dataBaseHelper = new DataBaseHelper(ContactList.this);
                CustomerModel customerModel;
                try {
                    // Checks to see if the user put anything in the text edits, the if statement means they are empty so it tells the user their was an error adding to the contact list
                    if (et_email.getText().toString().isEmpty() || et_name.getText().toString().isEmpty()) {
                        Toast.makeText(ContactList.this, "Error creating customer", Toast.LENGTH_SHORT).show();
                    // If the user correctly puts in the information the else statement will run
                    } else {
                        // Lets the system know that this is the active user, so that the contact information gets added to the correct account
                        String active = dataBaseHelper.getActiveUser();
                        customerModel = new CustomerModel(active, et_name.getText().toString(), et_email.getText().toString());
                        dataBaseHelper.addOne(customerModel);
                        et_name.setText("");
                        et_email.setText("");
                        Toast.makeText(ContactList.this, "Customer added " + active, Toast.LENGTH_SHORT).show();
                    }
                }
                catch (Exception e){
                    Toast.makeText(ContactList.this, "Error creating customer", Toast.LENGTH_SHORT).show();
                }



                ShowCustomersOnListView(dataBaseHelper);

            }
        });

        lv_customerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            // If the user clicks on a saved contact in the listview they will be deleted from the saved contacts and the database
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CustomerModel clickedCustomer = (CustomerModel) parent.getItemAtPosition(position);
                dataBaseHelper.deleteOne(clickedCustomer);
                ShowCustomersOnListView(dataBaseHelper);
                Toast.makeText(ContactList.this, "Deleted" + clickedCustomer.toString(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void ShowCustomersOnListView(DataBaseHelper dataBaseHelper2) {
        customerArrayAdapter = new ArrayAdapter<>(ContactList.this, android.R.layout.simple_list_item_1, dataBaseHelper2.getEveryone());
        lv_customerList.setAdapter((customerArrayAdapter));
    }

    public void openHomePage() {
        Intent intent = new Intent(this, Main_Page.class);
        startActivity(intent);
    }
}