package com.tamim.smartbholaadmin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.google.android.material.textfield.TextInputEditText;

public class uidNotification extends AppCompatActivity {
    TextInputEditText title, body;
    CardView send;
    LottieAnimationView lottie;

    ImageView cross;

    public static String UID = "";

    TextView uid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uid_notification);




        title = findViewById(R.id.title);
        body = findViewById(R.id.body);
        lottie = findViewById(R.id.lottie);
        send = findViewById(R.id.send);
        uid = findViewById(R.id.uid);
        cross = findViewById(R.id.cross);

        uid.setText("Uid = "+UID);

        cross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onBackPressed();
                finishAndRemoveTask();
                Animatoo.animateSwipeRight(uidNotification.this);


            }
        });

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String myTitle = title.getText().toString();
                String myBody = body.getText().toString();


                if (!myTitle.isEmpty()){

                    if (!myBody.isEmpty()){



                        lottie.setVisibility(View.VISIBLE);






                        String url1 = "https://wikipediabangla.com/apps/firebase/fcm3.php?body="+myBody+"&title="+myTitle+"&id="+UID;
                        StringRequest stringRequest = new StringRequest(0, url1, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                                lottie.setVisibility(View.GONE);

                                AlertDialog.Builder builder = new AlertDialog.Builder(uidNotification.this);
                                builder.setTitle("Notification Response").setMessage(response).setCancelable(false).setPositiveButton("Close", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int id) {

                                    }
                                });
                                AlertDialog alert = builder.create();
                                alert.show();


                                title.getText().clear();
                                body.getText().clear();
                                title.requestFocus();


                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                            }
                        });

                        RequestQueue requestQueue = Volley.newRequestQueue(uidNotification.this);
                        requestQueue.add(stringRequest);










                    } else {

                        body.setError("Empty");
                    }

                } else {
                    title.setError("Empty");

                }




            }
        });


    }
}