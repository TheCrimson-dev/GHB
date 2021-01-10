package com.example.ghb_draft;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;
// Final send funds page
public class ConfirmSendFunds extends AppCompatActivity {
    private ImageButton btn_home;
    private Button btn_restart, btn_confirm;
    private ListView lv_selectedAccounts;
    private EditText amount;
    ArrayAdapter accountArrayAdapter;
    DataBaseHelper dataBaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_funds3);

        btn_home = (ImageButton) findViewById(R.id.btn_home);
        btn_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openHomePage();
            }
        });
        lv_selectedAccounts = (ListView) findViewById(R.id.lv_transferAccounts);
        dataBaseHelper = new DataBaseHelper(ConfirmSendFunds.this);
        ShowSelectedAccountsOnListView(dataBaseHelper);
        amount = (EditText) findViewById(R.id.et_transferAmount);
        btn_restart = (Button) findViewById(R.id.btn_transferRestart);
        btn_restart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSendFunds();
            }
        });
        // Send button calls required functions to send e-transfer
        btn_confirm = (Button) findViewById(R.id.btn_transferSend);
        btn_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Accounts outgoing = dataBaseHelper.getOUTGOING();
                Accounts receiving = dataBaseHelper.getRECIEVING();
                float sendAmount = Float.parseFloat(amount.getText().toString());
                boolean send = dataBaseHelper.sendMoney(outgoing, receiving, sendAmount);
                if (send){
                    Toast.makeText(ConfirmSendFunds.this, "Money Transfered", Toast.LENGTH_SHORT).show();
                    openHomePage();
                } else {
                    Toast.makeText(ConfirmSendFunds.this, "Error Sending Transfer", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void ShowSelectedAccountsOnListView(DataBaseHelper dataBaseHelper2) {
        accountArrayAdapter = new ArrayAdapter<>(ConfirmSendFunds.this, android.R.layout.simple_list_item_1, dataBaseHelper2.getSelectedAccounts());
        lv_selectedAccounts.setAdapter((accountArrayAdapter));
    }

    public void openHomePage() {
        Intent intent = new Intent(this, Main_Page.class);
        startActivity(intent);
    }
    public void openSendFunds(){
        Intent intent = new Intent(this, SendFunds.class);
        startActivity(intent);
    }
}


