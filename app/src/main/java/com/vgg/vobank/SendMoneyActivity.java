package com.vgg.vobank;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class SendMoneyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_money);

        final EditText find = findViewById(R.id.editUang);
        final Button teman = findViewById(R.id.teman);
        final Button riwayat = findViewById(R.id.riwayat);
        final Button user = findViewById(R.id.user1);
        final TextView userNotFound = findViewById(R.id.user_not_found);
        Button cariUser = findViewById(R.id.cariUser);
        user.setVisibility(View.INVISIBLE);
        userNotFound.setVisibility(View.INVISIBLE);

        cariUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nama = find.getText().toString();
                if(nama.matches("Athina(.*)")){
                    userNotFound.setVisibility(View.INVISIBLE);
                    user.setVisibility(View.VISIBLE);
                }
                else {
                    user.setVisibility(View.INVISIBLE);
                    userNotFound.setVisibility(View.VISIBLE);
                }
            }
        });

        user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), TransferActivity.class);
                startActivity(i);
            }
        });

        riwayat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), HistoryActivity.class);
                startActivity(i);
            }
        });
    }
}
