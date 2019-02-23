package com.vobank.testfingerprint;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.system.ErrnoException;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Example of a call to a native method
        TextView tv = (TextView) findViewById(R.id.sample_text);

        GlobalFlags gf = (GlobalFlags) getApplicationContext();
        startActivity(new Intent(MainActivity.this, LoginActivity.class));
    }

    @Override
    protected void onResume() {
        super.onResume();

        GlobalFlags gf = (GlobalFlags) getApplicationContext();
        Toast.makeText(MainActivity.this, gf.getGlobalFingerprintCheck(), Toast.LENGTH_SHORT).show();

        if (gf.getGlobalFingerprintCheck() == "1") {
            Button b = (Button) findViewById(R.id.button);
            b.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    GlobalFlags gf = (GlobalFlags) getApplicationContext();
                    gf.setGlobalFingerprintCheck("0");
                    startActivity(new Intent(MainActivity.this, Main2Activity.class));
                }
            });
        }

    }
}
