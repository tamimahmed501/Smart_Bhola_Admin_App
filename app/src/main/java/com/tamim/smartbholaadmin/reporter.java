package com.tamim.smartbholaadmin;


import static android.view.View.VISIBLE;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
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


public class reporter extends AppCompatActivity {



    ProgressBar progressBar;


    LottieAnimationView lottie;

    ListView listView;

    ArrayList<HashMap<String,String>>arrayList=new ArrayList<>();
    HashMap<String,String>hashMap;
    ImageView back;


    Myadapter myadapter;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reporter);

        lottie = findViewById(R.id.lottie);
        listView = findViewById(R.id.listView);
        progressBar = findViewById(R.id.progrerssbar);
        back = findViewById(R.id.backbutton);

         myadapter = new Myadapter();
        listView.setAdapter(myadapter);


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        school();

    }


    private void school() {


        lottie.setVisibility(VISIBLE);

        arrayList.clear();



        String url = "https://wikipediabangla.com/apps/reporter6.php";




        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                com.android.volley.Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        lottie.setVisibility(View.GONE);


                        for (int x = 0; x < response.length(); x++) {
                            try {
                                JSONObject jsonObject = response.getJSONObject(x);
                                String name = jsonObject.getString("name");
                                String adress = jsonObject.getString("adress");
                                String education = jsonObject.getString("education");
                                String id = jsonObject.getString("id");
                                String nid = jsonObject.getString("nid");
                                String email = jsonObject.getString("email");
                                String pname = jsonObject.getString("pname");
                                String details = jsonObject.getString("details");
                                String images = jsonObject.getString("images");
                                String mobile = jsonObject.getString("mobile");
                                String status = jsonObject.getString("status");





                                HashMap<String, String> hashMap = new HashMap<>();
                                hashMap.put("name", name);
                                hashMap.put("adress", adress);
                                hashMap.put("education", education);
                                hashMap.put("id", id);
                                hashMap.put("nid", nid);
                                hashMap.put("email", email);
                                hashMap.put("pname", pname);
                                hashMap.put("details", details);
                                hashMap.put("mobile", mobile);
                                hashMap.put("images", images);
                                hashMap.put("status", status);
                                arrayList.add(hashMap);


                                myadapter.notifyDataSetChanged();



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

        RequestQueue requestQueue = Volley.newRequestQueue(reporter.this);
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
            View viewx = layoutInflater.inflate(R.layout.reporter, parent, false);

            TextView name1, pname1,nid1,mobile1,email1,adress1,details1,education1;

            name1 = viewx.findViewById(R.id.name);
            pname1 = viewx.findViewById(R.id.pname);
            nid1 = viewx.findViewById(R.id.nid);
            education1 = viewx.findViewById(R.id.education);
            mobile1 = viewx.findViewById(R.id.mobile);
            email1 = viewx.findViewById(R.id.email);
            adress1 = viewx.findViewById(R.id.adress);
            details1 = viewx.findViewById(R.id.details);



            ImageView imageView1 = viewx.findViewById(R.id.imageView1);






            HashMap<String,String> hashMap = arrayList.get(position);
            String namex = hashMap.get("name");
            String nidx = hashMap.get("nid");
            String educationx = hashMap.get("education");
            String images = hashMap.get("images");
            String id = hashMap.get("id");
            String emailx = hashMap.get("email");
            String mobilex = hashMap.get("mobile");
            String adressx = hashMap.get("adress");
            String pnamex = hashMap.get("pname");
            String detailsx = hashMap.get("details");
            String status = hashMap.get("status");


            name1.setText(namex);
            pname1.setText("কর্মস্থানঃ  "+pnamex);
            nid1.setText("এন আই ডিঃ  "+nidx);
            email1.setText("ইমেইলঃ  "+emailx);
            mobile1.setText("মোবাইলঃ   "+mobilex);
            adress1.setText("ঠিকানাঃ  "+adressx);
            details1.setText("বিস্তারিতঃ  "+detailsx);
            education1.setText("শিক্ষাগত যোগ্যতাঃ  "+educationx);




            String urlx = "https://wikipediabangla.com/apps/Images/"+images;


            Picasso.get()
                    .load(urlx)
                    .placeholder(R.drawable.imageicon)
                    .error(R.drawable.imgichon)
                    .into(imageView1);


            CardView approve, call, cancel;
            call = viewx.findViewById(R.id.card);
            cancel = viewx.findViewById(R.id.cancel);
            approve = viewx.findViewById(R.id.approve);


            if (status.contains("1")){
                approve.setVisibility(View.INVISIBLE);
            } else {
                approve.setVisibility(VISIBLE);

            }

            call.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Format the number to remove any non-numeric characters
                    String formattedNumber = mobilex.replaceAll("[^0-9]", "");

                    // Create an Intent with ACTION_DIAL and set the data to the formatted number
                    Intent intent = new Intent(Intent.ACTION_DIAL);
                    intent.setData(Uri.parse("tel:" + formattedNumber));

                    // Start the activity to initiate the call
                    startActivity(intent);
                }
            });



            cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    lottie.setVisibility(VISIBLE);


                    HashMap<String,String> hashMap = arrayList.get(position);
                    String idx = hashMap.get("id");


                    String url = "https://wikipediabangla.com/apps/delete.php?id="+idx+"&table=reporter";


                    StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            if (response.contains("deleted")){


                                android.app.AlertDialog alertDialog = new android.app.AlertDialog.Builder(reporter.this).create();
                                alertDialog.setTitle("Alert");
                                alertDialog.setMessage("Data deleted successfully.");
                                alertDialog.setButton(android.app.AlertDialog.BUTTON_NEUTRAL, "OK", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();



                                    }
                                });
                                alertDialog.show();
                                alertDialog.setCancelable(true);


                                school();






                            } else {

                                Toast.makeText(reporter.this,"Something went wrong!",Toast.LENGTH_SHORT).show();

                            };







                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                            lottie.setVisibility(View.GONE);

                        }
                    });


                    RequestQueue requestQueue = Volley.newRequestQueue(reporter.this);
                    requestQueue.add(stringRequest);




                }
            });




            approve.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    lottie.setVisibility(VISIBLE);


                    HashMap<String,String> hashMap = arrayList.get(position);
                    String idx = hashMap.get("id");


                    String url = "https://wikipediabangla.com/apps/reporterapprove.php?id="+idx+"&status=1";


                    StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            if (response.contains("Success")){


                                android.app.AlertDialog alertDialog = new android.app.AlertDialog.Builder(reporter.this).create();
                                alertDialog.setTitle("Alert");
                                alertDialog.setMessage("Reporter Approved successfully.");
                                alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();




                                    }
                                });
                                alertDialog.show();
                                alertDialog.setCancelable(true);


                                school();






                            } else {

                                Toast.makeText(reporter.this,"Something went wrong!",Toast.LENGTH_SHORT).show();

                            };







                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                            lottie.setVisibility(View.GONE);

                        }
                    });


                    RequestQueue requestQueue = Volley.newRequestQueue(reporter.this);
                    requestQueue.add(stringRequest);




                }
            });








            return viewx;
        }
    }



}
