package com.vgg.vobank;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.vgg.vobank.LoginReqVOBank;
import com.vgg.vobank.TTSManager2;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.speech.RecognizerIntent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.Locale;

public class Sign_in extends AppCompatActivity {
    TTSManager2 ttsManager2 = null;
    public TextView cashtag;
    public ProgressBar pgbar2;
    public String cashtag1,cashtag2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        final Button btDaftar = (Button) findViewById(R.id.btDaftar);
        final Button btSignIN = (Button) findViewById(R.id.buttonMasuk);
        cashtag = (TextView) findViewById(R.id.editText);
         pgbar2 = (ProgressBar) findViewById(R.id.pgBar2);

        btDaftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toMenu = new Intent(Sign_in.this, Sign_up.class);
                startActivity(toMenu);
            }
        });

        ttsManager2 = new TTSManager2();
        ttsManager2.init(this);

        final String myText1 = "Please Enter your Cashtag!";
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                ttsManager2.initQueue(myText1);
            }
        }, 1000);

    //buat redirect ke halaman menu
        Thread thread = new Thread(){
            @Override
            public void run() {
                try{
                    sleep(3*1000);
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

        pgbar2.setVisibility(View.INVISIBLE);

        btSignIN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pgbar2.setVisibility(View.VISIBLE);
                cashtag1 = cashtag.getText().toString();
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            //membuat variabel boolean berdasarkan response JSON
                            //apabila string email terdapat pada database, dengan kombinasi password yang benar, boolean akan bernilai 1 (success)
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");

                            //******************** apabila variabel boolean success*************************
                            if (success) {
 //                               String nama = jsonResponse.getString("nama");
                                Intent toMenu = new Intent(Sign_in.this, DashboardActivity.class);
                                startActivity(toMenu);
                            }
                                //*************************************apabila variabel boolean gagal
                            else{
                                final String myText2 = "Your Cashtag is Wrong!";
                                final String myText3 = "Please enter your cashtag!";
                                final Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        ttsManager2.initQueue(myText2);
                                        ttsManager2.addQueue(myText3);
                                    }
                                }, 1000);
                                }
                            }
                        catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };

                LoginReqVOBank loginRequest = new LoginReqVOBank(cashtag1, responseListener);
                RequestQueue queue = Volley.newRequestQueue(Sign_in.this);
                queue.add(loginRequest);
            }
        });

    }

    public void onActivityResult(int request_code, int result_code, Intent data) {
        if (request_code == 100) {
            ArrayList<String> hasil;
            hasil = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            String teks = hasil.get(0);
            cashtag.setText(teks);
            cashtag2 = cashtag.getText().toString();
            Response.Listener<String> responseListener = new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        pgbar2.setVisibility(View.VISIBLE);
                        //membuat variabel boolean berdasarkan response JSON
                        //apabila string email terdapat pada database, dengan kombinasi password yang benar, boolean akan bernilai 1 (success)
                        JSONObject jsonResponse = new JSONObject(response);
                        boolean success = jsonResponse.getBoolean("success");

                        //******************** apabila variabel boolean success*************************
                        if (success) {
                            Intent toMenu = new Intent(Sign_in.this, DashboardActivity.class);
                            startActivity(toMenu);
                        }
                        //*************************************apabila variabel boolean gagal
                        else {
                            final String myText2 = "Your Cashtag is Wrong!";
                            final String myText3 = "Please enter your cashtag!";
                            final Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    ttsManager2.initQueue(myText2);
                                    ttsManager2.addQueue(myText3);
                                }
                            }, 1000);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            };
            LoginReqVOBank loginRequest = new LoginReqVOBank(cashtag2, responseListener);
            RequestQueue queue = Volley.newRequestQueue(Sign_in.this);
            queue.add(loginRequest);
        }}

    @Override
    public void onDestroy() {
        super.onDestroy();
        ttsManager2.shutDown();
    }

}
