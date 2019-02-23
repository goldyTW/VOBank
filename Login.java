//***********************************************************************************************************************************
//
//                                                  Makanin Application
//                                              Made by Goldy Tanjung Wijaya
//                                        Last Update: 12 November 2018 23.08 GMT+7
//
//***********************************************************************************************************************************

package com.vgg.makanin;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.content.SharedPreferences;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.vgg.makanin.models.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONException;
import org.json.JSONObject;

public class Login extends AppCompatActivity {
    public static final String MyPREFERENCES = "MyPrefs" ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
        final SharedPreferences sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        /********************** inisialisasi variabel tombol login, isian email, password ************************* */
        final Button loginButtton = (Button) findViewById(R.id.btnContinue);
        final Button cancelButtton = (Button) findViewById(R.id.btnCancel);
        final EditText dataEmail2 = (EditText) findViewById(R.id.dataEmail2);
        final EditText dataPass2 = (EditText) findViewById(R.id.dataPass2);
        final TextView errorMessage = (TextView) findViewById(R.id.errorMessage);
        final ProgressBar pgbar2 = (ProgressBar) findViewById(R.id.progressBar);

        pgbar2.setVisibility(View.INVISIBLE);
        //******************** apabila tombol login ditekan *************************
        loginButtton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pgbar2.setVisibility(View.VISIBLE);
                //******************** konversi data yang didapat menjadi data string *************************
                final String email = dataEmail2.getText().toString();
                final String password = dataPass2.getText().toString();

                //******************** membuat pembanding respon  *************************
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            //membuat variabel boolean berdasarkan response JSON
                            //apabila string email terdapat pada database, dengan kombinasi password yang benar, boolean akan bernilai 1 (success)
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");

                            //******************** apabila variabel boolean success*************************
                            if (success){
                                String nama = jsonResponse.getString("nama");
                                String email1 = jsonResponse.getString("email");
                                String alamat = jsonResponse.getString("alamat");
                                String userID = jsonResponse.getString("userID");

//                                User daftar = new User(nama,email,alamat);
                                    final DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child("UserProfile").child(userID);
                                    mDatabase.child("Nama").setValue(nama);
                                mDatabase.child("email").setValue(email1);
                                mDatabase.child("Alamat").setValue(alamat);
                                Intent next = new Intent(Login.this, layarBerhasil.class);
                                next.putExtra("nama", nama);
                                next.putExtra("userID", userID);
                                next.putExtra("email", email1);
                                next.putExtra("alamat", alamat);
                                SharedPreferences.Editor editor = sharedpreferences.edit();
                                editor.putString("nama", nama);
                                editor.putString("userID", userID);
                                editor.putString("email", email1);
                                editor.putString("alamat", alamat);
                                editor.commit();
                                startActivity(next);
                                finish();
                            }
                            //*************************************apabila variabel boolean gagal
                            else{
                                Intent layarFail = new Intent(Login.this, gagalLogin.class);
                                startActivity(layarFail);
                                //AlertDialog.Builder builder = new AlertDialog.Builder(Login.this);
                                //builder.setMessage("Login Failed").setNegativeButton("Retry", null).create().show();
                                //Toast.makeText(getApplicationContext(), "Login Gagal!", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };

                LoginRequest loginRequest = new LoginRequest(email,password,responseListener);
                RequestQueue queue = Volley.newRequestQueue(Login.this);
                queue.add(loginRequest);
            }
        });

        //******************** apabila tombol cancel ditekan *************************
        cancelButtton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toLayarUtama = new Intent(Login.this, LayarUtama.class);
                startActivity(toLayarUtama);
            }
        });
    }
}