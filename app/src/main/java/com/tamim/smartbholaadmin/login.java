package com.tamim.smartbholaadmin;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class login extends AppCompatActivity {

    public static String STATUS = "";

    public static String NUMBER = "";
    public static String PASSWORD = "";

    TextView toptext,signup;

    TextInputEditText mobile,password;

    CardView login;

    ImageView back;

    LottieAnimationView lottie,lottie2,lottie3;



    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        toptext = findViewById(R.id.toptext);
        mobile = findViewById(R.id.mobile);
        password = findViewById(R.id.password);
        login = findViewById(R.id.signin);
        back = findViewById(R.id.back);
        lottie = findViewById(R.id.lottie);
        lottie2 = findViewById(R.id.lottie2);
        lottie3 = findViewById(R.id.lottie3);





        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                finishAndRemoveTask();
                finish();
                Animatoo.animateSwipeRight(login.this);

            }
        });


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {






                String mobilex  = mobile.getText().toString();
                String passwordx = password.getText().toString();


                if (mobilex.length() < 10) {
                    mobile.setError("Invalid Number");
                } else if (passwordx.length() < 5) {
                    password.setError("Wrong Password");
                }else {






                    lottie3.setVisibility(View.VISIBLE);



                    SharedPreferences preferences = getSharedPreferences(getString(R.string.app_name), Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();





                    String url = "https://wikipediabangla.com/apps/adminlogin.php?password="+passwordx+"&mobile="+mobilex;

                    JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray response) {
                            try {
                                JSONObject jsonObject = response.getJSONObject(0);
                                String name = jsonObject.getString("name");
                                String id = jsonObject.getString("id");
                                String mobile = jsonObject.getString("mobile");
                                String password = jsonObject.getString("password");



                                editor.putString("name", name);
                                editor.putString("mobile", mobile);
                                editor.putString("uid", id);
                                editor.putString("password", password);

                                editor.apply();










                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                            lottie2.setVisibility(View.GONE);

                        }
                    });

                    RequestQueue requestQueuex = Volley.newRequestQueue(login.this);
                    requestQueuex.add(jsonArrayRequest);

                    String url5 = "https://wikipediabangla.com/apps/adminlogin2.php?password=" + passwordx + "&mobile=" + mobilex;

                    lottie3.setVisibility(View.VISIBLE);

                    StringRequest stringRequest = new StringRequest(Request.Method.GET, url5, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            if (response.equals("Login successful!")) {


                                lottie3.setVisibility(View.GONE);
                                lottie.setVisibility(View.VISIBLE);

                                new Handler().postDelayed(() -> {
                                    startActivity(new Intent(login.this, MainActivity.class));
                                    lottie.setVisibility(View.GONE);

                                }, 2000);

                                SharedPreferences preferences = getSharedPreferences(getString(R.string.app_name), Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = preferences.edit();
                                editor.putBoolean("isLoggedIn", true);
                                editor.apply();
                            } else {

                                password.setError("Password is incorrect!");
                                lottie3.setVisibility(View.GONE);
                            }

                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                            lottie2.setVisibility(View.GONE);

                        }
                    });

                    RequestQueue requestQueue = Volley.newRequestQueue(login.this);
                    requestQueue.add(stringRequest);



                }




            }
        });




    }

    @Override
    public void onBackPressed() {

        super.onBackPressed();
        finish();
        finishAndRemoveTask();
        Animatoo.animateSwipeRight(login.this);

    }
}