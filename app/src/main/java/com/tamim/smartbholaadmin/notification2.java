package com.tamim.smartbholaadmin;

import static android.content.Context.MODE_PRIVATE;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;


public class notification2 extends Fragment {

    TextInputEditText title, body, uid;
    CardView send;
    LottieAnimationView lottie;


    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View myView = inflater.inflate(R.layout.fragment_notification2, container, false);


        title = myView.findViewById(R.id.title);
        body = myView.findViewById(R.id.body);
        lottie = myView.findViewById(R.id.lottie);
        send = myView.findViewById(R.id.send);
        uid = myView.findViewById(R.id.uid);



        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String myTitle = title.getText().toString();
                String myBody = body.getText().toString();
                String uidx = uid.getText().toString();

                if (!uidx.isEmpty()){

                    if (!myBody.isEmpty()){



                        lottie.setVisibility(View.VISIBLE);






                        String url1 = "https://wikipediabangla.com/apps/firebase/fcm3.php?body="+myBody+"&title="+myTitle+"&id="+uidx;
                        StringRequest stringRequest = new StringRequest(0, url1, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                                lottie.setVisibility(View.GONE);

                                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
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

                        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
                        requestQueue.add(stringRequest);











                    } else {

                        body.setError("Empty");
                    }

                } else {
                    title.setError("Empty");

                }




            }
        });





        return myView;
    }
}