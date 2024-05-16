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
import android.widget.AutoCompleteTextView;
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


public class freedoctor extends AppCompatActivity {



    TextInputEditText namev,expert,education,number,time;
    CardView adddata,adddata2;
    LinearLayout imagelay;
    ImageView imageView;
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

    public static String SECTION = "মেডিসিন বিশেষজ্ঞ";

    Spinner spinner;

    String[] strings = {"মেডিসিন বিশেষজ্ঞ", "গাইনি বিশেষজ্ঞ", "শিশু বিশেষজ্ঞ", "দন্ত বিশেষজ্ঞ","নাক কান গলা","অর্থপেডিক্স","ডায়াবেটিক বিশেষজ্ঞ", "হৃদ্রোগ বিশেষজ্ঞহ","চক্ষুরোগ বিশেষজ্ঞ", "কিডনি রোগ বিশেষজ্ঞহ", "চর্মরোগ বিশেষজ্ঞহ", "গ্যাস্ট্রোএন্ট্রোলজি"};


    AutoCompleteTextView autoCompleteTextView;

    ArrayAdapter<String>arrayAdapter;


    private static long lastRequestQueueTime = 0;
    private static String lastResponse = ""; // To store the last response
    private static final long REQUEST_QUEUE_THRESHOLD = 1000; // 5 seconds in milliseconds


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_freedoctor);

        namev = findViewById(R.id.name);
        adddata = findViewById(R.id.adddata);
        imageView = findViewById(R.id.image_view);
        progressBar = findViewById(R.id.progrerssbar);
        expert = findViewById(R.id.expert);
        education = findViewById(R.id.education);
        number=findViewById(R.id.number);
        spinner = findViewById(R.id.spinner);
        lottie = findViewById(R.id.lottie);
        listView= findViewById(R.id.listView);
        addlay = findViewById(R.id.addlay);
        tabLayout = findViewById(R.id.tabLayout);
        toptext=findViewById(R.id.toptext);
        toptext2=findViewById(R.id.toptext2);
        time=findViewById(R.id.time);
        imagelay=findViewById(R.id.imagelay);
        adddata2=findViewById(R.id.adddata2);


        ImageView back = findViewById(R.id.backbutton);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });








        Myadapter myadapter = new Myadapter();
        listView.setAdapter(myadapter);

        school();


        addlay.setVisibility(VISIBLE);

        listView.setVisibility(View.GONE);


        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(freedoctor.this, android.R.layout.simple_spinner_item, strings);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);



        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String value = parent.getItemAtPosition(position).toString();


                if (value.contains("মেডিসিন বিশেষজ্ঞ")) {
                    freedoctor.SECTION="মেডিসিন বিশেষজ্ঞ";
                } else if (value.contains("গাইনি বিশেষজ্ঞ")) {
                    freedoctor.SECTION="গাইনি বিশেষজ্ঞ";
                } else if (value.contains("শিশু বিশেষজ্ঞ")) {
                    freedoctor.SECTION="শিশু বিশেষজ্ঞ";
                }else if (value.contains("অর্থপেডিক্স")) {
                    freedoctor.SECTION="অর্থপেডিক্স";
                } else if (value.contains("দন্ত বিশেষজ্ঞ")) {
                    freedoctor.SECTION="দন্ত বিশেষজ্ঞ";
                }else if (value.contains("নাক কান গলা")) {
                    freedoctor.SECTION="নাক কান গলা";
                }else if (value.contains("ডায়াবেটিক বিশেষজ্ঞ")) {
                    freedoctor.SECTION="ডায়াবেটিক বিশেষজ্ঞ";
                } else if (value.contains("হৃদরোগ বিশেষজ্ঞ")) {
                    freedoctor.SECTION="হৃদরোগ বিশেষজ্ঞ";
                }else if (value.contains("কিডনি রোগ বিশেষঞ")) {
                    freedoctor.SECTION="কিডনি রোগ বিশেষঞ";
                } else if (value.contains("চক্ষু রোগ বিশেষজ্ঞ")) {
                    freedoctor.SECTION="চক্ষু রোগ বিশেষজ্ঞ";
                }else if (value.contains("চর্মরোগ বিশেষজ্ঞ")) {
                    freedoctor.SECTION="চর্মরোগ বিশেষজ্ঞ";
                }else if (value.contains("গ্যাস্ট্রোএন্ট্রোলজি")) {
                    freedoctor.SECTION="গ্যাস্ট্রোএন্ট্রোলজি";
                }else if (value.contains("পশু ডাক্তার")) {
                    freedoctor.SECTION="পশু ডাক্তার";
                }else if (value.contains("হোমিও ডাক্তার")) {
                    freedoctor.SECTION="হোমিও ডাক্তার";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Handle no selection if needed
            }
        });


        adddata2.setVisibility(View.GONE);




        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                if (position == 0) {

                    addlay.setVisibility(VISIBLE);
                    lottie.setVisibility(View.GONE);
                    listView.setVisibility(View.GONE);



                    // Clear EditText fields
                    namev.getText().clear();
                    number.getText().clear();
                    expert.getText().clear();
                    education.getText().clear();
                    number.getText().clear();
                    time.getText().clear();


                    adddata.setVisibility(VISIBLE);
                    adddata2.setVisibility(View.GONE);
                    imagelay.setVisibility(VISIBLE);


                } else if (position == 1) {


                    addlay.setVisibility(View.GONE);
                    listView.setVisibility(View.GONE);

                    Toast.makeText(freedoctor.this,"No Approval Request",Toast.LENGTH_SHORT).show();



                }   else if (position == 2) {

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













        imageView.setOnClickListener(view -> {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            pickImages.launch(intent);
        });

        adddata.setOnClickListener(v -> {

            lottie.setVisibility(View.VISIBLE);
            adddata.setClickable(false);

            String namex = namev.getText().toString();
            String expertx = expert.getText().toString();
            String educationx = education.getText().toString();
            String numberx = number.getText().toString();
            String timex = time.getText().toString();



            if (encodedImage != null) {
                String url2 = "https://wikipediabangla.com/apps/freedoctor5.php";



                long currentTime = System.currentTimeMillis();

                if (currentTime - lastRequestQueueTime < REQUEST_QUEUE_THRESHOLD) {
                    // Show the last response in the toast message
                    Toast.makeText(freedoctor.this, "too quickly: " + lastResponse, Toast.LENGTH_SHORT).show();
                    lastRequestQueueTime = System.currentTimeMillis();





                } else {


                    uploadDataToServer(url2, namex, encodedImage, expertx,educationx,numberx,timex);



                }



            } else {
                lottie.setVisibility(View.GONE);
                adddata.setClickable(true);

                Toast.makeText(freedoctor.this, "No image selected", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void uploadDataToServer(String url2, String namex, String encodedImage, String expertx, String educationx, String numberx, String timex) {
        try {


            JSONObject jsonBody = new JSONObject();
            jsonBody.put("name", namex);
            jsonBody.put("images", encodedImage);
            jsonBody.put("expert", expertx);
            jsonBody.put("education", educationx);
            jsonBody.put("number", numberx);
            jsonBody.put("time", timex);
            jsonBody.put("section", SECTION);
            jsonBody.put("status", "0");
            jsonBody.put("uid", "1");



            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url2, jsonBody,
                    response -> {
                        lottie.setVisibility(View.GONE);
                        adddata.setClickable(true);

                        try {
                            // Check the status directly
                            String status = response.getString("status");

                            if ("success".equals(status)) {

                                new AlertDialog.Builder(freedoctor.this)
                                        .setMessage("Data Added Successfully")
                                        .setPositiveButton("OK", null)
                                        .show();



                                // Clear EditText fields
                                namev.getText().clear();
                                number.getText().clear();
                                expert.getText().clear();
                                education.getText().clear();
                                number.getText().clear();
                                time.getText().clear();

                                namev.requestFocus();

                                // Set the image to "imageichon" (assuming it's a resource)
                                imageView.setImageResource(R.drawable.imageicon);

                                school();


                            } else {
                                // If status is not success, assume it's an error
                                Toast.makeText(freedoctor.this, "Upload Failed", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(freedoctor.this, "Error parsing server response", Toast.LENGTH_SHORT).show();
                        }
                    },
                    error -> {
                        lottie.setVisibility(View.GONE);
                        adddata.setClickable(true);

                        Toast.makeText(freedoctor.this, "Error Response: " + error.toString(), Toast.LENGTH_SHORT).show();

                    });



            RequestQueue requestQueue = Volley.newRequestQueue(freedoctor.this);
            requestQueue.add(jsonObjectRequest);

            lastRequestQueueTime = System.currentTimeMillis(); // Update the last request time


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == MY_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            Uri filepath = data.getData();
            try {
                InputStream inputStream = getContentResolver().openInputStream(filepath);
                bitmap = BitmapFactory.decodeStream(inputStream);
                imageView.setImageBitmap(bitmap);
                ImageStore(bitmap);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void ImageStore(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        byte[] imageByte = stream.toByteArray();
        encodedImage = Base64.encodeToString(imageByte, Base64.DEFAULT);
    }

    ActivityResultLauncher<Intent> pickImages = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    Uri imageUri = result.getData().getData();
                    try {
                        InputStream inputStream = getContentResolver().openInputStream(imageUri);
                        bitmap = BitmapFactory.decodeStream(inputStream);
                        imageView.setImageBitmap(bitmap);
                        ImageStore(bitmap);
                    } catch (FileNotFoundException e) {
                        Toast.makeText(freedoctor.this, "Error loading image", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(freedoctor.this, "No image selected", Toast.LENGTH_SHORT).show();
                }
            });


    private void school() {


        arrayList.clear();

        arrayList = new ArrayList<>();

        String url = "https://wikipediabangla.com/apps/freedoctor2.php";




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
                                String expert = jsonObject.getString("expert");
                                String education = jsonObject.getString("education");
                                String mobile = jsonObject.getString("number");
                                String images = jsonObject.getString("images");
                                String id = jsonObject.getString("id");
                                String time = jsonObject.getString("time");
                                String section = jsonObject.getString("section");


                                HashMap<String, String> hashMap = new HashMap<>();
                                hashMap.put("name", name);
                                hashMap.put("mobile", mobile);
                                hashMap.put("expert", expert);
                                hashMap.put("education", education);
                                hashMap.put("images", images);
                                hashMap.put("id", id);
                                hashMap.put("time", time);
                                hashMap.put("section", section);

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

        RequestQueue requestQueue = Volley.newRequestQueue(freedoctor.this);
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
            View viewx = layoutInflater.inflate(R.layout.freehealth, parent, false);

            TextView name1, degree1,expert1,mobile1,time1,section1;
            name1 = viewx.findViewById(R.id.name);
            degree1 = viewx.findViewById(R.id.degree);
            mobile1 = viewx.findViewById(R.id.mobile);
            expert1 = viewx.findViewById(R.id.expert);
            CardView cardView = viewx.findViewById(R.id.card);
            time1 = viewx.findViewById(R.id.time);
            section1 = viewx.findViewById(R.id.section);


            ImageView imageView1 = viewx.findViewById(R.id.imageView1);






            HashMap<String,String> hashMap = arrayList.get(position);
            String namex = hashMap.get("name");
            String educationx = hashMap.get("education");
            String mobilex = hashMap.get("mobile");
            String expertx = hashMap.get("expert");
            String images = hashMap.get("images");
            String idx = hashMap.get("id");
            String timex = hashMap.get("time");
            String sectionx = hashMap.get("section");


            name1.setText(namex);
            degree1.setText("ডিগ্রিঃ  "+educationx);
            mobile1.setText("মোবাইলঃ  "+mobilex);
            expert1.setText("অভিজ্ঞতাঃ  "+expertx);



            time1.setText("সার্ভিসিং টাইমঃ  "+timex);
            section1.setText("ক্যাটাগরিঃ  "+sectionx);



            String urlx = "https://wikipediabangla.com/apps/Images/"+images;


            Picasso.get()
                    .load(urlx)
                    .placeholder(R.drawable.imageicon)
                    .error(R.drawable.imgichon)
                    .into(imageView1);



            ImageView delete,edit;
            delete = viewx.findViewById(R.id.delete);
            edit = viewx.findViewById(R.id.edit);




            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    lottie.setVisibility(VISIBLE);


                    HashMap<String,String> hashMap = arrayList.get(position);
                    String idx = hashMap.get("id");


                    String url = "https://wikipediabangla.com/apps/freedoctor3.php?id="+idx;


                    StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            lottie.setVisibility(View.GONE);
                            cardView.setVisibility(View.GONE);

                            if (response.contains("deleted")){


                                android.app.AlertDialog alertDialog = new android.app.AlertDialog.Builder(freedoctor.this).create();
                                alertDialog.setTitle("Alert");
                                alertDialog.setMessage("Data deleted successfully.");
                                alertDialog.setButton(android.app.AlertDialog.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();




                                    }
                                });
                                alertDialog.show();
                                alertDialog.setCancelable(true);







                            } else {

                                Toast.makeText(freedoctor.this,"Something went wrong!",Toast.LENGTH_SHORT).show();

                            };







                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                            lottie.setVisibility(View.GONE);

                        }
                    });


                    RequestQueue requestQueue = Volley.newRequestQueue(freedoctor.this);
                    requestQueue.add(stringRequest);




                }
            });


            edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    adddata.setVisibility(View.GONE);
                    imagelay.setVisibility(View.GONE);
                    adddata2.setVisibility(VISIBLE);
                    listView.setVisibility(View.GONE);
                    addlay.setVisibility(VISIBLE);


                    namev.setText(namex);
                    education.setText(educationx);
                    time.setText(timex);
                    number.setText(mobilex);
                    expert.setText(expertx);


                    adddata2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {




                            String namex = namev.getText().toString();
                            String expertx = expert.getText().toString();
                            String educationx = education.getText().toString();
                            String numberx = number.getText().toString();
                            String timex = time.getText().toString();



                            lottie.setVisibility(VISIBLE);


                            HashMap<String,String> hashMap = arrayList.get(position);
                            String idx = hashMap.get("id");


                            String url = "https://wikipediabangla.com/apps/freedoctor4.php?id="+idx+"&name="+namex+"&expert="+expertx+"&education="+educationx+"&number="+numberx+"&time="+timex;


                            StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {

                                    lottie.setVisibility(View.GONE);

                                    if (response.contains("updated")){


                                        addlay.setVisibility(View.GONE);
                                        school();
                                        listView.setVisibility(VISIBLE);


                                        android.app.AlertDialog alertDialog = new android.app.AlertDialog.Builder(freedoctor.this).create();
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

                                        Toast.makeText(freedoctor.this,"Something went wrong!",Toast.LENGTH_SHORT).show();

                                    };







                                }
                            }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {

                                    lottie.setVisibility(View.GONE);

                                }
                            });


                            RequestQueue requestQueue = Volley.newRequestQueue(freedoctor.this);
                            requestQueue.add(stringRequest);













                        }
                    });







                }
            });







            return viewx;
        }
    }



}
