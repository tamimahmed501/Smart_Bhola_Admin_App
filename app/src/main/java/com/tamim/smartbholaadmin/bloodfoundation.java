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
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
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


public class bloodfoundation extends AppCompatActivity {



    TextInputEditText name,adress,mobile,pname,details,registration;
    CardView adddata,adddata2;
    ProgressBar progressBar;

    Bitmap bitmap;
    String encodedImage;

    LottieAnimationView lottie;

    ListView listView;

    ArrayList<HashMap<String,String>>arrayList=new ArrayList<>();
    HashMap<String,String>hashMap;

    int MY_REQUEST_CODE = 1;


    ScrollView addlay;
    TabLayout tabLayout;

    TextView toptext,toptext2;

    public static String SECTION = "";


    private static long lastRequestQueueTime = 0;
    private static String lastResponse = ""; // To store the last response
    private static final long REQUEST_QUEUE_THRESHOLD = 3000; // 5 seconds in milliseconds


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bloodfoundation);

        name = findViewById(R.id.name);
        adddata = findViewById(R.id.adddata);
        progressBar = findViewById(R.id.progrerssbar);
        adress = findViewById(R.id.area);
        mobile = findViewById(R.id.mobile);
        pname = findViewById(R.id.pname);
        details = findViewById(R.id.details);
        registration = findViewById(R.id.registration);
        lottie = findViewById(R.id.lottie);
        listView= findViewById(R.id.listView);
        addlay = findViewById(R.id.addlay);
        tabLayout = findViewById(R.id.tabLayout);
        toptext=findViewById(R.id.toptext);
        toptext2=findViewById(R.id.toptext2);
        adddata2=findViewById(R.id.adddata2);









        Myadapter myadapter = new Myadapter();
        listView.setAdapter(myadapter);


        school();

        ImageView back = findViewById(R.id.backbutton);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });



        addlay.setVisibility(VISIBLE);

        listView.setVisibility(View.GONE);


        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                if (position == 0) {


                    addlay.setVisibility(VISIBLE);
                    listView.setVisibility(View.GONE);
                    lottie.setVisibility(View.GONE);

                    // Clear EditText fields
                    name.getText().clear();
                    mobile.getText().clear();
                    registration.getText().clear();
                    adress.getText().clear();
                    pname.getText().clear();



                    adddata2.setVisibility(View.GONE);
                    adddata.setVisibility(VISIBLE);


                } else if (position == 1) {


                    addlay.setVisibility(View.GONE);
                    listView.setVisibility(View.GONE);
                    Toast.makeText(bloodfoundation.this,"No Approval Request",Toast.LENGTH_SHORT).show();




                }else if (position == 2) {

                    addlay.setVisibility(View.GONE);
                    listView.setVisibility(VISIBLE);



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








        adddata.setOnClickListener(v -> {

            lottie.setVisibility(View.VISIBLE);
            adddata.setClickable(false);

            String namex = name.getText().toString();
            String mobilex = mobile.getText().toString();
            String adressx = adress.getText().toString();
            String detailsx = details.getText().toString();
            String registrationx = registration.getText().toString();
            String pnamex = pname.getText().toString();




            if (namex != null) {
                String url2 = "https://wikipediabangla.com/apps/social5.php";


                long currentTime = System.currentTimeMillis();

                if (currentTime - lastRequestQueueTime < REQUEST_QUEUE_THRESHOLD) {
                    // Show the last response in the toast message
                    Toast.makeText(bloodfoundation.this, "too quickly: " + lastResponse, Toast.LENGTH_SHORT).show();
                    lastRequestQueueTime = System.currentTimeMillis();





                } else {


                    uploadDataToServer(url2, namex, mobilex,adressx, pnamex, registrationx, detailsx);



                }



            } else {
                lottie.setVisibility(View.GONE);
                adddata.setClickable(true);

                Toast.makeText(bloodfoundation.this, "No image selected", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void uploadDataToServer(String url, String namex, String mobilex, String adressx, String pnamex, String registrationx, String details) {
        try {
            JSONObject jsonBody = new JSONObject();
            jsonBody.put("name", namex);
            jsonBody.put("mobile", mobilex);
            jsonBody.put("adress", adressx);
            jsonBody.put("pname", pnamex);
            jsonBody.put("registration", registrationx);
            jsonBody.put("details", details);
            jsonBody.put("status", "0");
            jsonBody.put("uid", "1");

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, jsonBody,
                    response -> {
                        lottie.setVisibility(View.GONE);
                        adddata.setClickable(true);

                        try {
                            // Check the status directly
                            String status = response.getString("status");

                            if ("success".equals(status)) {

                                new AlertDialog.Builder(bloodfoundation.this)
                                        .setMessage("Data Added Successfully")
                                        .setPositiveButton("OK", null)
                                        .show();



                                // Clear EditText fields
                                name.getText().clear();
                                mobile.getText().clear();
                                registration.getText().clear();
                                adress.getText().clear();
                                pname.getText().clear();


                                name.requestFocus();


                                school();


                            } else {
                                // If status is not success, assume it's an error
                                Toast.makeText(bloodfoundation.this, "Upload Failed", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(bloodfoundation.this, "Error parsing server response", Toast.LENGTH_SHORT).show();
                        }
                    },
                    error -> {
                        lottie.setVisibility(View.GONE);
                        adddata.setClickable(true);

                        Toast.makeText(bloodfoundation.this, "Error Response: " + error.toString(), Toast.LENGTH_SHORT).show();

                    });



            RequestQueue requestQueue = Volley.newRequestQueue(bloodfoundation.this);
            requestQueue.add(jsonObjectRequest);

            lastRequestQueueTime = System.currentTimeMillis(); // Update the last request time


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }




    private void school() {

        arrayList.clear();



        String url = "https://wikipediabangla.com/apps/social2.php";




        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                com.android.volley.Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        lottie.setVisibility(View.GONE);


                        Log.d("DataCount", "Number of data items received: " + response.length());



                        for (int x = 0; x < response.length(); x++) {
                            try {
                                JSONObject jsonObject = response.getJSONObject(x);
                                String name = jsonObject.getString("name");
                                String adress = jsonObject.getString("adress");
                                String mobile = jsonObject.getString("mobile");
                                String pname = jsonObject.getString("pname");
                                String id = jsonObject.getString("id");
                                String details = jsonObject.getString("details");
                                String registration = jsonObject.getString("registration");


                                HashMap<String, String> hashMap = new HashMap<>();
                                hashMap.put("name", name);
                                hashMap.put("mobile", mobile);
                                hashMap.put("adress", adress);
                                hashMap.put("pname", pname);
                                hashMap.put("id", id);
                                hashMap.put("details", details);
                                hashMap.put("registration", registration);

                                arrayList.add(hashMap);





                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(bloodfoundation.this);
        requestQueue.add(jsonArrayRequest);





    }

    private class Myadapter extends BaseAdapter {

        @Override
        public int getCount() {
            return arrayList.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @SuppressLint("MissingInflatedId")
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            LayoutInflater layoutInflater = (LayoutInflater) getLayoutInflater().getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View viewx = layoutInflater.inflate(R.layout.organization, parent, false);

            TextView name1,name11, adress1,mobile1,founder1,details1,registration1;
            name1 = viewx.findViewById(R.id.name);
            adress1 = viewx.findViewById(R.id.area);
            mobile1= viewx.findViewById(R.id.mobile);
            name11 = viewx.findViewById(R.id.name1);
            founder1 = viewx.findViewById(R.id.founder);
            details1 = viewx.findViewById(R.id.details);
            registration1 = viewx.findViewById(R.id.registration);








            HashMap<String,String> hashMap = arrayList.get(position);
            String namex = hashMap.get("name");
            String adressx = hashMap.get("adress");
            String mobilex = hashMap.get("mobile");
            String detailsx = hashMap.get("details");
            String idx = hashMap.get("id");
            String pnamex = hashMap.get("pname");
            String registrationx = hashMap.get("registration");

            name11.setText("প্রতিষ্ঠানঃ "+namex);
            adress1.setText("আওতাধীন এলাকাঃ  "+adressx);
            mobile1.setText("মোবাইলঃ  "+mobilex);

            name1.setText(namex);
            details1.setText("ধরনঃ "+detailsx);
            founder1.setText("প্রতিষ্ঠাতাঃ  "+pnamex);
            registration1.setText("রেজিষ্ট্রেশনঃ  "+registrationx);


            ImageView delete,edit;
            delete = viewx.findViewById(R.id.delete);
            edit = viewx.findViewById(R.id.edit);
            CardView cardView = viewx.findViewById(R.id.card);




            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    lottie.setVisibility(VISIBLE);


                    HashMap<String,String> hashMap = arrayList.get(position);
                    String idx = hashMap.get("id");


                    String url = "https://wikipediabangla.com/apps/social3.php?id="+idx;


                    StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            if (response.contains("deleted")){


                                lottie.setVisibility(View.GONE);


                                cardView.setVisibility(View.GONE);


                                android.app.AlertDialog alertDialog = new android.app.AlertDialog.Builder(bloodfoundation.this).create();
                                alertDialog.setTitle("Alert");
                                alertDialog.setMessage("Data deleted successfully.");
                                alertDialog.setButton(android.app.AlertDialog.BUTTON_NEUTRAL, "OK", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();




                                    }
                                });
                                alertDialog.show();
                                alertDialog.setCancelable(true);






                            } else {

                                Toast.makeText(bloodfoundation.this,"Something went wrong!",Toast.LENGTH_SHORT).show();

                            };







                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                            lottie.setVisibility(View.GONE);

                        }
                    });


                    RequestQueue requestQueue = Volley.newRequestQueue(bloodfoundation.this);
                    requestQueue.add(stringRequest);




                }
            });

            edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    addlay.setVisibility(VISIBLE);
                    listView.setVisibility(View.GONE);
                    adddata.setVisibility(View.GONE);
                    adddata2.setVisibility(VISIBLE);





                    name.setText(namex);
                    mobile.setText(mobilex);
                    adress.setText(adressx);
                    details.setText(detailsx);
                    registration.setText(registrationx);
                    pname.setText(pnamex);





                    adddata2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {




                            String namex = name.getText().toString();
                            String mobilex = mobile.getText().toString();
                            String adressx = adress.getText().toString();
                            String detailsx = details.getText().toString();
                            String registrationx = registration.getText().toString();
                            String pnamex = pname.getText().toString();


                            lottie.setVisibility(VISIBLE);


                            HashMap<String,String> hashMap = arrayList.get(position);
                            String idx = hashMap.get("id");


                            String url = "https://wikipediabangla.com/apps/social4.php?id="+idx+"&name="+namex+"&pname="+pnamex+"&adress="+adressx+"&details="+detailsx+"&registration="+registrationx+"&adress="+adressx+"&mobile="+mobilex;


                            StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {

                                    lottie.setVisibility(View.GONE);

                                    if (response.contains("updated")){


                                        addlay.setVisibility(View.GONE);
                                        school();
                                        listView.setVisibility(VISIBLE);
                                        adddata.setVisibility(VISIBLE);
                                        adddata2.setVisibility(View.GONE);


                                        android.app.AlertDialog alertDialog = new android.app.AlertDialog.Builder(bloodfoundation.this).create();
                                        alertDialog.setTitle("Alert");
                                        alertDialog.setMessage("Data updated successfully.");
                                        alertDialog.setButton(android.app.AlertDialog.BUTTON_NEUTRAL, "OK", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.dismiss();




                                            }
                                        });
                                        alertDialog.show();
                                        alertDialog.setCancelable(true);







                                    } else {

                                        Toast.makeText(bloodfoundation.this,"Something went wrong!",Toast.LENGTH_SHORT).show();

                                    };







                                }
                            }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {

                                    lottie.setVisibility(View.GONE);

                                }
                            });


                            RequestQueue requestQueue = Volley.newRequestQueue(bloodfoundation.this);
                            requestQueue.add(stringRequest);













                        }
                    });



                }
            });







            return viewx;
        }
    }



}
