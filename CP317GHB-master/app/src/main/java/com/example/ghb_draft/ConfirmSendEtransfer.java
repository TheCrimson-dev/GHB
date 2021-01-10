package com.example.ghb_draft;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
// Final send e-transfer page
public class ConfirmSendEtransfer extends AppCompatActivity {
    private ImageButton btn_home;
    private Button btn_transferRestart, btn_transferSend;
    private ListView lv_transferAccounts;
    private EditText transferAmount;
    ArrayAdapter accountArrayAdapter;
    DataBaseHelper dataBaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_etransfer3);

        btn_home = (ImageButton) findViewById(R.id.btn_home);
        btn_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openHomePage();
            }
        });
        lv_transferAccounts = (ListView) findViewById(R.id.lv_transferAccounts);
        dataBaseHelper = new DataBaseHelper(ConfirmSendEtransfer.this);
        ShowSelectedAccountsOnListView(dataBaseHelper);
        transferAmount = (EditText) findViewById(R.id.et_transferAmount);
        btn_transferRestart = (Button) findViewById(R.id.btn_transferRestart);
        btn_transferRestart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSendEtransfer();
            }
        });
        // Send button call required functions to send e-transfer
        btn_transferSend = (Button) findViewById(R.id.btn_transferSend);
        btn_transferSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Accounts outgoing = dataBaseHelper.getOUTGOING();
                Accounts receiving = dataBaseHelper.getRECIEVING();
                float sendAmount = Float.parseFloat(transferAmount.getText().toString());
                boolean send = dataBaseHelper.sendMoney(outgoing, receiving, sendAmount);
                if (send){
                    Etransfers etransfer = new Etransfers(outgoing.getEmail(), receiving.getEmail(), sendAmount);
                    dataBaseHelper.addEtransfer(etransfer);
                    Toast.makeText(ConfirmSendEtransfer.this, "Money Transfered", Toast.LENGTH_SHORT).show();
                    openHomePage();
                } else {
                    Toast.makeText(ConfirmSendEtransfer.this, "Error Sending Transfer", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void ShowSelectedAccountsOnListView(DataBaseHelper dataBaseHelper2) {
        accountArrayAdapter = new ArrayAdapter<>(ConfirmSendEtransfer.this, android.R.layout.simple_list_item_1, dataBaseHelper2.getSelectedAccounts());
        lv_transferAccounts.setAdapter((accountArrayAdapter));
    }

    public void openHomePage() {
        Intent intent = new Intent(this, Main_Page.class);
        startActivity(intent);
    }
    public void openSendEtransfer(){
        Intent intent = new Intent(this, SendEtransfer.class);
        startActivity(intent);
    }
}


