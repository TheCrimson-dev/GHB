package com.example.ghb_draft;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
// Select which account to send e-transfer to, by entering email of user and then selecting bank account from list
public class ReceiveEtransfer extends AppCompatActivity {
    private ImageButton btn_home;
    private Button btn_load;
    private EditText et_receivingEmail;
    private ListView lv_receivingTransfer;
    ArrayAdapter accountArrayAdapter;
    DataBaseHelper dataBaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_etransfer2);

        lv_receivingTransfer = (ListView) findViewById(R.id.lv_receivingEtransfer);
        et_receivingEmail = (EditText) findViewById(R.id.et_recipientEmail);
        btn_home = (ImageButton) findViewById(R.id.btn_home);
        btn_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openHomePage();
            }
        });
        lv_receivingTransfer.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Accounts clickedAccount = (Accounts) parent.getItemAtPosition(position);
                DataBaseHelper.setRECIEVING(clickedAccount);
                Toast.makeText(getApplicationContext(), "Receiving account set", Toast.LENGTH_SHORT).show();
                openConfirmSendEtransfer();
            }
        });
        btn_load = (Button) findViewById(R.id.btn_load);
        btn_load.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = et_receivingEmail.getText().toString();
                ShowAccountsOnListView(dataBaseHelper, email);
            }
        });
        dataBaseHelper = new DataBaseHelper(ReceiveEtransfer.this);



    }
    private void ShowAccountsOnListView(DataBaseHelper dataBaseHelper2, String email) {
        accountArrayAdapter = new ArrayAdapter<>(ReceiveEtransfer.this, android.R.layout.simple_list_item_1, dataBaseHelper2.getReceivingAccounts(email));
        lv_receivingTransfer.setAdapter((accountArrayAdapter));
    }
    public void openHomePage() {
        Intent intent = new Intent(this, Main_Page.class);
        startActivity(intent);
    }
    public void openConfirmSendEtransfer(){
        Intent intent = new Intent(this, ConfirmSendEtransfer.class);
        startActivity(intent);
    }
}