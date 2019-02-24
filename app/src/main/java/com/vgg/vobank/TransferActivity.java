package com.vgg.vobank;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class TransferActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transfer);

        final EditText editUang = findViewById(R.id.editUang);
        final Button kirim = findViewById(R.id.kirim_uang);

        kirim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alertadd = new AlertDialog.Builder(TransferActivity.this);
                LayoutInflater factory = LayoutInflater.from(TransferActivity.this);
                final View v = factory.inflate(R.layout.dialog_alert, null);
                alertadd.setView(v);
                alertadd.setNeutralButton("Success!", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dlg, int sumthin) {

                    }
                });

                alertadd.show();
                String saldoTerkirim = editUang.getText().toString();
                Intent i = new Intent(getApplicationContext(), HistoryActivity.class);
                i.putExtra("saldo",saldoTerkirim);
                startActivity(i);
            }
        });
    }
}
