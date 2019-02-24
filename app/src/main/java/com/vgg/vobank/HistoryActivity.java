package com.vgg.vobank;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class HistoryActivity extends AppCompatActivity {

    private String saldoTerkirim;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        final Button teman = findViewById(R.id.teman);
        final Button riwayat = findViewById(R.id.riwayat);
        TextView history = findViewById(R.id.history);

        Intent saldoTerkirimIntent = getIntent();
        Bundle b = saldoTerkirimIntent.getExtras();
        if(b!=null){
            saldoTerkirim = b.getString("saldo");
        }

        if (saldoTerkirim != null){
            history.setText("Athina Maria Transfer "+saldoTerkirim);
        }

        teman.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), SendMoneyActivity.class);
                startActivity(i);
            }
        });
    }
}
