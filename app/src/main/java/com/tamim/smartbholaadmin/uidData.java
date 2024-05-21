package com.tamim.smartbholaadmin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class uidData extends AppCompatActivity {


    public static String UID = "";
    public static String NUMBER = "";


    TextView name, balance, mobile, adress, uid ;
    CardView call, notify;
    ImageView profile, cross;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uid_data);



        name = findViewById(R.id.name);
        balance = findViewById(R.id.balance);
        mobile = findViewById(R.id.mobile);
        adress = findViewById(R.id.adress);
        call = findViewById(R.id.call);
        notify = findViewById(R.id.notification);
        profile =findViewById(R.id.imageView1);
        cross = findViewById(R.id.cross);
        uid = findViewById(R.id.uid);


        cross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                finishAndRemoveTask();
                Animatoo.animateSwipeRight(uidData.this);
            }
        });



        notify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                uidNotification.UID=UID;
                startActivity(new Intent(uidData.this, uidNotification.class));
            }
        });

        String url = "https://wikipediabangla.com/apps/user2.php?uid="+UID;

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        for (int x = 0; x < response.length(); x++) {
                            try {
                                JSONObject jsonObject = response.getJSONObject(x);
                                String name1 = jsonObject.getString("name");
                                String adress1 = jsonObject.getString("upazila");
                                String mobile1 = jsonObject.getString("mobile");
                                String images1 = jsonObject.getString("images");
                                String id1 = jsonObject.getString("id");
                                String mybalance1 = jsonObject.getString("mybalance");




                                uid.setText("UID = "+id1);
                                name.setText(name1);
                                balance.setText("Account Balance: "+mybalance1+" ৳");
                                mobile.setText("Mobile: "+mobile1);

                                if (adress1.length()<2){

                                    adress.setText("Address: Not Updated");

                                } else {


                                    adress.setText("Address: "+adress1);

                                }

                                call.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {



                                        String formattedNumber = mobile1.replaceAll("[^0-9]", "");

                                        // Create an Intent with ACTION_DIAL and set the data to the formatted number
                                        Intent intent = new Intent(Intent.ACTION_DIAL);
                                        intent.setData(Uri.parse("tel:" + formattedNumber));

                                        // Start the activity to initiate the call
                                        startActivity(intent);





                                    }
                                });


                                String urlx = "https://wikipediabangla.com/apps/Images/"+images1;


                                Picasso.get()
                                        .load(urlx)
                                        .placeholder(R.drawable.profile)
                                        .error(R.drawable.profile)
                                        .into(profile);








                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(uidData.this, "No Internet Connection", Toast.LENGTH_SHORT).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(uidData.this);
        requestQueue.add(jsonArrayRequest);




    }
}