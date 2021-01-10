package com.example.ghb_draft;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;
// Send funds initial page where user selects which bank account to send funds from
public class SendFunds extends AppCompatActivity {
    private ImageButton btn_home;
    private ListView lv_outgoingAccounts;
    ArrayAdapter accountArrayAdapter;
    DataBaseHelper dataBaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_funds);

        btn_home = (ImageButton) findViewById(R.id.btn_home);
        btn_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openHomePage();
            }
        });
        lv_outgoingAccounts = (ListView)findViewById (R.id.lv_transferAccounts);
        lv_outgoingAccounts.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Accounts clickedAccount = (Accounts) parent.getItemAtPosition(position);
                DataBaseHelper.setOUTGOING(clickedAccount);
                Toast.makeText(getApplicationContext(), "Outgoing account set", Toast.LENGTH_SHORT).show();
                openRecievePage();
            }
        });
        dataBaseHelper = new DataBaseHelper(SendFunds.this);
        ShowAccountsOnListView(dataBaseHelper);

    }
    private void ShowAccountsOnListView(DataBaseHelper dataBaseHelper2) {
        accountArrayAdapter = new ArrayAdapter<>(SendFunds.this, android.R.layout.simple_list_item_1, dataBaseHelper2.getOutgoingAccounts());
        lv_outgoingAccounts.setAdapter((accountArrayAdapter));
    }
    public void openHomePage() {
        Intent intent = new Intent(this, Main_Page.class);
        startActivity(intent);
    }
    public void openRecievePage(){
        Intent intent = new Intent(this, RecieveFunds.class);
        startActivity(intent);
    }
}