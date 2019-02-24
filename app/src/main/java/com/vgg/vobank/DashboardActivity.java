package com.vgg.vobank;

import com.vgg.vobank.library.TTSManager;

import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

public class DashboardActivity extends AppCompatActivity {
    TTSManager ttsManager = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);



        startActivity(new Intent(DashboardActivity.this, LoginActivity.class));


    }

    @Override
    protected void onResume() {
        super.onResume();

        GlobalFlags gf = (GlobalFlags) getApplicationContext();
        if (gf.getGlobalFingerprintCheck() == "1") {
            //Toast.makeText(DashboardActivity.this, "Hore", Toast.LENGTH_SHORT).show();
            Button btnSendMoney = findViewById(R.id.send_money);
            Button btnBuyAirtime = findViewById(R.id.buy_airtime);
            Button btnCashOut = findViewById(R.id.cash_out);
            Button btnMakePayment = findViewById(R.id.make_payment);
            Button btnRequestMoney = findViewById(R.id.request_money);
            Button btnReferToFriend = findViewById(R.id.refer_to_friend);

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

            btnSendMoney.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(getApplicationContext(), SendMoneyActivity.class);
                    startActivity(i);
                }
            });
            btnBuyAirtime.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(getApplicationContext(), BuyAirtimeActivity.class);
                    startActivity(i);
                }
            });
            btnCashOut.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(getApplicationContext(), CashOutActivity.class);
                    startActivity(i);
                }
            });
            btnMakePayment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(getApplicationContext(), MakePaymentActivity.class);
                    startActivity(i);
                }
            });
            btnRequestMoney.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(getApplicationContext(), RequestMoneyActivity.class);
                    startActivity(i);
                }
            });
            btnReferToFriend.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(getApplicationContext(), ReferToFriendActivity.class);
                    startActivity(i);
                }
            });
        }
    }

    public void onActivityResult(int request_code, int result_code, Intent data){
        if (request_code==100){
            ArrayList<String> hasil;
            hasil = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            String teks = hasil.get(0);
            if (teks.equals("send money")){
                Intent toPay = new Intent(DashboardActivity.this, SendMoneyActivity.class);
                startActivity(toPay);
            }
            else if (teks.equals("Payment")){
                Intent toTrans = new Intent(DashboardActivity.this, BuyAirtimeActivity.class);
                startActivity(toTrans);
            }
            else if (teks.equals("Balance check")){
                Intent toTrans = new Intent(DashboardActivity.this, CashOutActivity.class);
                startActivity(toTrans);
            }
            else if (teks.equals("notification")){
                Intent toTrans = new Intent(DashboardActivity.this, MakePaymentActivity.class);
                startActivity(toTrans);
            }
            else if (teks.equals("Refresh")){
                Intent toTrans = new Intent(DashboardActivity.this, RequestMoneyActivity.class);
                startActivity(toTrans);
            }
            else if (teks.equals("Refer To Friend")){
                Intent toTrans = new Intent(DashboardActivity.this, ReferToFriendActivity.class);
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
