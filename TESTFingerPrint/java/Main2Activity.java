package com.vobank.testfingerprint;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class Main2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        startActivity(new Intent(Main2Activity.this, LoginActivity.class));
    }

    @Override
    protected void onResume() {
        super.onResume();

        GlobalFlags gf = (GlobalFlags) getApplicationContext();
        if (gf.getGlobalFingerprintCheck() == "1") {
            Toast.makeText(Main2Activity.this, "Hore", Toast.LENGTH_SHORT).show();
        }
    }
}
