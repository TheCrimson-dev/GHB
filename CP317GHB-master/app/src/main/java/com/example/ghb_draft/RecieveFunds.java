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
// Send funds page where user selects which bank account from their list to send funds to
public class RecieveFunds extends AppCompatActivity {
    private ImageButton btn_home;
    private ListView lv_receivingAccounts;
    ArrayAdapter accountArrayAdapter;
    DataBaseHelper dataBaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_funds2);

        btn_home = (ImageButton) findViewById(R.id.btn_home);
        btn_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openHomePage();
            }
        });
        lv_receivingAccounts = (ListView) findViewById(R.id.lv_transferAccounts);
        lv_receivingAccounts.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Accounts clickedAccount = (Accounts) parent.getItemAtPosition(position);
                if (clickedAccount.getId() == DataBaseHelper.getOUTGOING().getId()) {
                    Toast.makeText(getApplicationContext(), "Outgoing Account selected, please select a different Account", Toast.LENGTH_SHORT).show();
                } else {
                    DataBaseHelper.setRECIEVING(clickedAccount);
                    Toast.makeText(getApplicationContext(), "Receiving account set", Toast.LENGTH_SHORT).show();
                    openConfirmSendFunds();
                }
            }
        });
        dataBaseHelper = new DataBaseHelper(RecieveFunds.this);
        ShowAccountsOnListView(dataBaseHelper);

    }

    private void ShowAccountsOnListView(DataBaseHelper dataBaseHelper2) {
        accountArrayAdapter = new ArrayAdapter<>(RecieveFunds.this, android.R.layout.simple_list_item_1, dataBaseHelper2.getAccounts());
        lv_receivingAccounts.setAdapter((accountArrayAdapter));
    }

    public void openHomePage() {
        Intent intent = new Intent(this, Main_Page.class);
        startActivity(intent);
    }
    public void openConfirmSendFunds(){
        Intent intent = new Intent(this, ConfirmSendFunds.class);
        startActivity(intent);
    }
}

