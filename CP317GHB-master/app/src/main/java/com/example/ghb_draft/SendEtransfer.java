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
// Send e-transfer page where user selects which bank account to send money from
public class SendEtransfer extends AppCompatActivity {
    private ImageButton btn_home;
    private ListView lv_outgoingTransfer;
    ArrayAdapter accountArrayAdapter;
    DataBaseHelper dataBaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_etransfer);

        lv_outgoingTransfer = (ListView) findViewById(R.id.lv_receivingEtransfer);

        btn_home = (ImageButton) findViewById(R.id.btn_home);
        btn_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openHomePage();
            }
        });
        lv_outgoingTransfer.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Accounts clickedAccount = (Accounts) parent.getItemAtPosition(position);
                DataBaseHelper.setOUTGOING(clickedAccount);
                Toast.makeText(getApplicationContext(), "Outgoing account set", Toast.LENGTH_SHORT).show();
                openReceiveTransfer();
            }
        });
        dataBaseHelper = new DataBaseHelper(SendEtransfer.this);
        ShowAccountsOnListView(dataBaseHelper);


    }
    private void ShowAccountsOnListView(DataBaseHelper dataBaseHelper2) {
        accountArrayAdapter = new ArrayAdapter<>(SendEtransfer.this, android.R.layout.simple_list_item_1, dataBaseHelper2.getOutgoingAccounts());
        lv_outgoingTransfer.setAdapter((accountArrayAdapter));
    }
    public void openHomePage() {
        Intent intent = new Intent(this, Main_Page.class);
        startActivity(intent);
    }
    public void openReceiveTransfer(){
        Intent intent = new Intent(this, ReceiveEtransfer.class);
        startActivity(intent);
    }
}