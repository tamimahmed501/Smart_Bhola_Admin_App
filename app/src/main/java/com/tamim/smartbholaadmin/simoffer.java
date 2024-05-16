package com.tamim.smartbholaadmin;


import static android.view.View.VISIBLE;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.textfield.TextInputEditText;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class simoffer extends AppCompatActivity {



    TextInputEditText offername,validity,recharge,price;
    CardView adddata;
    ProgressBar progressBar;

    Bitmap bitmap;
    String encodedImage;

    LottieAnimationView lottie;

    public static String TYPE = "";


    public static String PACKAGENAME = "";
    public static  String PRICE = "";
    public static String RECHARGE = "";
    public static String MYID = "";

    public static String VALIDITY = "";

    ArrayList<HashMap<String,String>>arrayList=new ArrayList<>();
    HashMap<String,String>hashMap;

    int MY_REQUEST_CODE = 1;


    ScrollView addlay;
    LinearLayout allsim;
    TabLayout tabLayout, tablyout2;

    TextView toptext,toptext2;

    CardView robi,gp,bl,airtel,skitto, teletalk;

    public static String SIM = "";

    Spinner spinner,spinner2;
    String[] strings = {"GP", "ROBI", "AIRTEL", "BANGLALINK","TELETALK","SKITTO"};
    String[] strings2 = {"combo", "internet", "minutes"};



    private static long lastRequestQueueTime = 0;
    private static String lastResponse = ""; // To store the last response
    private static final long REQUEST_QUEUE_THRESHOLD = 3000; // 5 seconds in milliseconds


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simoffer);

        offername = findViewById(R.id.offername);
        adddata = findViewById(R.id.adddata);
        price = findViewById(R.id.price);
        validity = findViewById(R.id.validity);
        recharge = findViewById(R.id.recharge);
        allsim= findViewById(R.id.allsim);
        addlay = findViewById(R.id.addlay);
        tabLayout = findViewById(R.id.tabLayout);
        toptext=findViewById(R.id.toptext);
        spinner=findViewById(R.id.spinner);
        lottie=findViewById(R.id.lottie);
        spinner2 = findViewById(R.id.spinner2);

        robi=findViewById(R.id.robi);
        bl=findViewById(R.id.banglalink);
        gp=findViewById(R.id.gp);
        airtel=findViewById(R.id.airtel);
        skitto=findViewById(R.id.skitto);
        teletalk=findViewById(R.id.teletalk);


        ImageView back = findViewById(R.id.backbutton);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });



        robi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                simoffer2.SIM="ROBI";
                startActivity(new Intent(simoffer.this,simoffer2.class));
            }
        });



        airtel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                simoffer2.SIM="AIRTEL";
                startActivity(new Intent(simoffer.this,simoffer2.class));
            }
        });

        gp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                simoffer2.SIM="GP";
                startActivity(new Intent(simoffer.this,simoffer2.class));
            }
        });

        bl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                simoffer2.SIM="BANGLALINK";
                startActivity(new Intent(simoffer.this,simoffer2.class));
            }
        });

        skitto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                simoffer2.SIM="SKITTO";
                startActivity(new Intent(simoffer.this,simoffer2.class));
            }
        });

        teletalk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                simoffer2.SIM="TELETALK";
                startActivity(new Intent(simoffer.this,simoffer2.class));
            }
        });






        if (!MYID.isEmpty()){

            validity.setText(VALIDITY);
            recharge.setText(RECHARGE);
            price.setText(PRICE);
            offername.setText(PACKAGENAME);




        } else {}



        addlay.setVisibility(VISIBLE);

        allsim.setVisibility(View.GONE);


        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                if (position == 0) {


                    addlay.setVisibility(VISIBLE);
                    allsim.setVisibility(View.GONE);
                    lottie.setVisibility(View.GONE);


                } else if (position == 1) {

                    MYID="";
                    addlay.setVisibility(View.GONE);
                    allsim.setVisibility(VISIBLE);



                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                // Do nothing when a tab is unselected
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                // Handle tab reselection if needed
            }
        });

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(simoffer.this, android.R.layout.simple_spinner_item, strings2);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String value = parent.getItemAtPosition(position).toString();

                SIM=value;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Handle no selection if needed
            }
        });


        ArrayAdapter<String> arrayAdapter2 = new ArrayAdapter<>(simoffer.this, android.R.layout.simple_spinner_item, strings);
        arrayAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(arrayAdapter2);

        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String value = parent.getItemAtPosition(position).toString();

             TYPE = value;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Handle no selection if needed
            }
        });

        adddata.setOnClickListener(v -> {

            adddata.setClickable(false);

            String offernamex = offername.getText().toString();
            String rechargex = recharge.getText().toString();

            String validityx = validity.getText().toString();
            String pricex = price.getText().toString();




            if (pricex != null) {

                String url2;

                if (!MYID.isEmpty()){

                    url2 = "https://wikipediabangla.com/apps/addoffer4.php";

                } else {

                    url2 = "https://wikipediabangla.com/apps/addoffer.php";


                }


                lottie.setVisibility(VISIBLE);

                long currentTime = System.currentTimeMillis();

                if (currentTime - lastRequestQueueTime < REQUEST_QUEUE_THRESHOLD) {
                    // Show the last response in the toast message
                    Toast.makeText(simoffer.this, "too quickly: " + lastResponse, Toast.LENGTH_SHORT).show();
                    lastRequestQueueTime = System.currentTimeMillis();


                } else {


                    uploadDataToServer(url2, offernamex,validityx,rechargex,pricex);


                }



            } else {
                lottie.setVisibility(View.GONE);
                adddata.setClickable(true);

                Toast.makeText(simoffer.this, "Empty Filed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void uploadDataToServer(String url, String offernamex, String validityx, String rechargex, String pricex) {
        String fullUrl = url +
                "?packagename=" + offernamex +
                "&validity=" + validityx +
                "&recharge=" + rechargex +
                "&price=" + pricex +
                "&sim=" + TYPE + "&catagory="+SIM+"&id="+MYID;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, fullUrl, null,
                response -> {
                    lottie.setVisibility(View.GONE);
                    adddata.setClickable(true);

                    try {
                        String status = response.getString("status");



                        if ("success".equals(status)) {



                            if (!MYID.isEmpty()){

                                new AlertDialog.Builder(simoffer.this)
                                        .setMessage("Data Updated Successfully")
                                        .setPositiveButton("OK", null)
                                        .show();



                            } else {


                                new AlertDialog.Builder(simoffer.this)
                                        .setMessage("Data Added Successfully")
                                        .setPositiveButton("OK", null)
                                        .show();



                            }

                            // Clear EditText fields
                            offername.getText().clear();
                            validity.getText().clear();
                            price.getText().clear();
                            recharge.getText().clear();
                            recharge.requestFocus();

                        } else {
                            Toast.makeText(simoffer.this, "Upload Failed: " + response.getString("message"), Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(simoffer.this, "Error parsing server response", Toast.LENGTH_SHORT).show();
                    }
                },
                error -> {
                    lottie.setVisibility(View.GONE);
                    adddata.setClickable(true);

                    // Print the raw error response to help diagnose the issue
                    if (error.networkResponse != null && error.networkResponse.data != null) {
                        String errorResponse = new String(error.networkResponse.data);
                        Log.e("Volley Error", "Error Response: " + errorResponse);
                    }

                    Toast.makeText(simoffer.this, "Error Response: " + error.toString(), Toast.LENGTH_SHORT).show();
                }) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<>();
                // Add any required headers here
                return headers;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(simoffer.this);
        requestQueue.add(jsonObjectRequest);

        lastRequestQueueTime = System.currentTimeMillis(); // Update the last request time
    }





}
