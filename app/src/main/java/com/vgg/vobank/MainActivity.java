package com.vgg.vobank;
import com.vgg.vobank.TTSManager;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.speech.RecognizerIntent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    TTSManager ttsManager = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ttsManager = new TTSManager();
        ttsManager.init(this);

        final String myText1 = "V O Bank!";
        final String myText2 = "Voice Command Banking Solution";

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                ttsManager.initQueue(myText1);
                ttsManager.addQueue(myText2);
            }
        }, 1000);

        //buat redirect ke halaman menu
        Thread thread = new Thread(){
            @Override
            public void run() {
                try{
                    sleep(2*1000);
                    Intent toSignIn = new Intent(MainActivity.this, Sign_in.class);
                    startActivity(toSignIn);


                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        thread.start();

    }


}
