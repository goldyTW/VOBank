package com.vgg.vobank;

import com.vgg.vobank.TTSManager;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.speech.RecognizerIntent;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    public TextView text1;
    public Button button1, button2;
    TTSManager ttsManager = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button1 = (Button) findViewById(R.id.button1);
        text1 = (TextView) findViewById(R.id.text1);
        button2 = (Button) findViewById(R.id.button2);

        ttsManager = new TTSManager();
        ttsManager.init(this);

        final String myText1 = "Welcome!";
        final String myText2 = "How can we help you?";

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                ttsManager.initQueue(myText1);
                ttsManager.addQueue(myText2);
            }
        }, 1000);

        text1.setVisibility(View.INVISIBLE);

        //buat redirect ke halaman menu
        Thread thread = new Thread(){
            @Override
            public void run() {
                try{
                    sleep(4*1000);
                    Intent i = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                    i.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                    i.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.ENGLISH);
                    i.putExtra(RecognizerIntent.EXTRA_PROMPT, "Mulai Bicara");
                    startActivityForResult(i,100);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        thread.start();

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                text1.setVisibility(View.INVISIBLE);
            }
        });
    }

    public void onActivityResult(int request_code, int result_code, Intent data){
                if (request_code==100){
                    ArrayList<String> hasil;
                        hasil = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                        String teks = hasil.get(0);
                        if (teks.equals("open pay")){
                            text1.setVisibility(View.VISIBLE);
                            Intent toPay = new Intent(MainActivity.this, pay.class);
                            startActivity(toPay);
                        }
                        else if (teks.equals("open transfer")){
                           text1.setVisibility(View.VISIBLE);
                          Intent toTrans = new Intent(MainActivity.this, Transfer.class);
                         startActivity(toTrans);
                        }
                }
        }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ttsManager.shutDown();
    }
    }
