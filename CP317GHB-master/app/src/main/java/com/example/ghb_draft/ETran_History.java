package com.example.ghb_draft;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;

public class ETran_History extends AppCompatActivity {
    private ImageButton btn_home;
    private ListView lv_etransferHistory, lv_etransferHistoryReceived;
    ArrayAdapter accountArrayAdapter;
    DataBaseHelper dataBaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_e_tran__history);
        dataBaseHelper = new DataBaseHelper(ETran_History.this);
        btn_home = (ImageButton) findViewById(R.id.btn_home);
        btn_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openHomePage();
            }
        });
        lv_etransferHistory = (ListView) findViewById(R.id.lv_etransferHistory);
        lv_etransferHistoryReceived = (ListView) findViewById(R.id.lv_etransfersReceived);

        // Calls methods that displays the received and outgoing transfers
        ShowEtransfersOnListView(dataBaseHelper);
        ShowEtransfers2OnListView(dataBaseHelper);
    }

    public void openHomePage() {
        Intent intent = new Intent(this, Main_Page.class);
        startActivity(intent);
    }
    // Gets the etransfers that the user sent
    private void ShowEtransfersOnListView(DataBaseHelper dataBaseHelper2) {
        accountArrayAdapter = new ArrayAdapter<>(ETran_History.this, android.R.layout.simple_list_item_1, dataBaseHelper2.getEtransfers1());
        lv_etransferHistory.setAdapter((accountArrayAdapter));
    }
    // Getrs the etransfers that the user received
    private void ShowEtransfers2OnListView(DataBaseHelper dataBaseHelper2) {
        accountArrayAdapter = new ArrayAdapter<>(ETran_History.this, android.R.layout.simple_list_item_1, dataBaseHelper2.getEtransfers2());
        lv_etransferHistoryReceived.setAdapter((accountArrayAdapter));
    }
}