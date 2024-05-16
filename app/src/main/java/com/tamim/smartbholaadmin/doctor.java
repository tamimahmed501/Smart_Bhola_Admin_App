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


public class doctor extends AppCompatActivity {



    TextInputEditText name,expert,education,cember,time,reginumber;
    CardView adddata,adddata2;
    LinearLayout  imagelay;
    ImageView imageView;
    ProgressBar progressBar;

    Bitmap bitmap;
    String encodedImage;

    LottieAnimationView lottie;

    ListView listView;

    ArrayList<HashMap<String,String>>arrayList=new ArrayList<>();
    HashMap<String,String>hashMap;

    int MY_REQUEST_CODE = 1;

    LinearLayout listlay;
    ScrollView addlay;
    TabLayout tabLayout;

    TextView toptext,toptext2;

    public static String SECTION = "মেডিসিন বিশেষজ্ঞ";

    Spinner spinner;

    String[] strings = {"মেডিসিন বিশেষজ্ঞ", "গাইনি বিশেষজ্ঞ", "শিশু বিশেষজ্ঞ", "দন্ত বিশেষজ্ঞ","নাক কান গলা","অর্থপেডিক্স","ডায়াবেটিক বিশেষজ্ঞ", "হৃদ্রোগ বিশেষজ্ঞহ","চক্ষুরোগ বিশেষজ্ঞ", "কিডনি রোগ বিশেষজ্ঞহ", "চর্মরোগ বিশেষজ্ঞহ", "গ্যাস্ট্রোএন্ট্রোলজি"};



    private static long lastRequestQueueTime = 0;
    private static String lastResponse = ""; // To store the last response
    private static final long REQUEST_QUEUE_THRESHOLD = 3000; // 5 seconds in milliseconds


    @SuppressLint({"MissingInflatedId", "WrongViewCast"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor);

        name = findViewById(R.id.name);
        adddata = findViewById(R.id.adddata);
        imageView = findViewById(R.id.image_view);
        progressBar = findViewById(R.id.progrerssbar);
        expert = findViewById(R.id.expert);
        education = findViewById(R.id.education);
        cember = findViewById(R.id.cember);
        time = findViewById(R.id.time);
        reginumber = findViewById(R.id.reginumber);
        spinner = findViewById(R.id.spinner);
        lottie = findViewById(R.id.lottie);
        listlay = findViewById(R.id.listLay);
        listView= findViewById(R.id.listView);
        addlay = findViewById(R.id.addlay);
        tabLayout = findViewById(R.id.tabLayout);
        toptext=findViewById(R.id.toptext);
        toptext2=findViewById(R.id.toptext2);
        adddata2=findViewById(R.id.adddata2);
        imagelay=findViewById(R.id.imagelay);



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

        listlay.setVisibility(View.GONE);


        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(doctor.this, android.R.layout.simple_spinner_item, strings);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);







        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String value = parent.getItemAtPosition(position).toString();


                if (value.contains("মেডিসিন বিশেষজ্ঞ")) {
                    doctor.SECTION="মেডিসিন বিশেষজ্ঞ";
                } else if (value.contains("গাইনি বিশেষজ্ঞ")) {
                    doctor.SECTION="গাইনি বিশেষজ্ঞ";
                } else if (value.contains("শিশু বিশেষজ্ঞ")) {
                    doctor.SECTION="শিশু বিশেষজ্ঞ";
                }else if (value.contains("অর্থপেডিক্স")) {
                    doctor.SECTION="অর্থপেডিক্স";
                } else if (value.contains("দন্ত বিশেষজ্ঞ")) {
                    doctor.SECTION="দন্ত বিশেষজ্ঞ";
                }else if (value.contains("নাক কান গলা")) {
                    doctor.SECTION="নাক কান গলা";
                }else if (value.contains("ডায়াবেটিক বিশেষজ্ঞ")) {
                    doctor.SECTION="ডায়াবেটিক বিশেষজ্ঞ";
                } else if (value.contains("হৃদরোগ বিশেষজ্ঞ")) {
                    doctor.SECTION="হৃদরোগ বিশেষজ্ঞ";
                }else if (value.contains("কিডনি রোগ বিশেষঞ")) {
                    doctor.SECTION="কিডনি রোগ বিশেষঞ";
                } else if (value.contains("চক্ষু রোগ বিশেষজ্ঞ")) {
                    doctor.SECTION="চক্ষু রোগ বিশেষজ্ঞ";
                }else if (value.contains("চর্মরোগ বিশেষজ্ঞ")) {
                    doctor.SECTION="চর্মরোগ বিশেষজ্ঞ";
                }else if (value.contains("গ্যাস্ট্রোএন্ট্রোলজি")) {
                    doctor.SECTION="গ্যাস্ট্রোএন্ট্রোলজি";
                }else if (value.contains("পশু ডাক্তার")) {
                    doctor.SECTION="পশু ডাক্তার";
                }else if (value.contains("হোমিও ডাক্তার")) {
                    doctor.SECTION="হোমিও ডাক্তার";
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Handle no selection if needed
            }
        });



        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                if (position == 0) {

                    addlay.setVisibility(VISIBLE);
                    listlay.setVisibility(View.GONE);
                    lottie.setVisibility(View.GONE);

                    adddata.setVisibility(VISIBLE);
                    adddata2.setVisibility(View.GONE);
                    imagelay.setVisibility(VISIBLE);


                    // Clear EditText fields
                    name.getText().clear();
                    cember.getText().clear();
                    reginumber.getText().clear();
                    time.getText().clear();
                    expert.getText().clear();
                    education.getText().clear();



                } else if (position == 1) {


                    addlay.setVisibility(View.GONE);
                    listlay.setVisibility(View.GONE);

                    Toast.makeText(doctor.this,"No Approval Request",Toast.LENGTH_SHORT).show();



                }   else if (position == 2) {

                    addlay.setVisibility(View.GONE);
                    listlay.setVisibility(VISIBLE);


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

            String namex = name.getText().toString();
            String expertx = expert.getText().toString();
            String educationx = education.getText().toString();
            String cemberx = cember.getText().toString();
            String timex = time.getText().toString();
            String reginumberx = reginumber.getText().toString();



            if (encodedImage != null) {
                String url2 = "https://wikipediabangla.com/apps/doctor.php";



                long currentTime = System.currentTimeMillis();

                if (currentTime - lastRequestQueueTime < REQUEST_QUEUE_THRESHOLD) {
                    // Show the last response in the toast message
                    Toast.makeText(doctor.this, "too quickly: " + lastResponse, Toast.LENGTH_SHORT).show();
                    lastRequestQueueTime = System.currentTimeMillis();





                } else {


                    uploadDataToServer(url2, namex, encodedImage, expertx,educationx,timex, reginumberx, cemberx);



                }



            } else {
                lottie.setVisibility(View.GONE);
                adddata.setClickable(true);

                Toast.makeText(doctor.this, "No image selected", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void uploadDataToServer(String url2, String namex, String encodedImage, String expertx, String educationx, String timex, String reginumberx, String cemberx) {
        try {

            // Add the following debug logs
            Log.d("DoctorActivity", "URL: " + url2);
            Log.d("DoctorActivity", "Encoded Image: " + encodedImage);

            JSONObject jsonBody = new JSONObject();
            jsonBody.put("name", namex);
            jsonBody.put("images", encodedImage);
            jsonBody.put("expert", expertx);
            jsonBody.put("education", educationx);
            jsonBody.put("time", timex);
            jsonBody.put("reginumber", reginumberx);
            jsonBody.put("cember", cemberx);
            jsonBody.put("section", SECTION);



            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url2, jsonBody,
                    response -> {
                        lottie.setVisibility(View.GONE);
                        adddata.setClickable(true);

                        try {
                            // Check the status directly
                            String status = response.getString("status");

                            if ("success".equals(status)) {

                                new AlertDialog.Builder(doctor.this)
                                        .setMessage("Data Added Successfully")
                                        .setPositiveButton("OK", null)
                                        .show();



                                // Clear EditText fields
                                name.getText().clear();
                                cember.getText().clear();
                                reginumber.getText().clear();
                                time.getText().clear();
                                expert.getText().clear();
                                education.getText().clear();

                                name.requestFocus();

                                // Set the image to "imageichon" (assuming it's a resource)
                                imageView.setImageResource(R.drawable.imageicon);

                                school();


                            } else {
                                // If status is not success, assume it's an error
                                Toast.makeText(doctor.this, "Upload Failed", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(doctor.this, "Error parsing server response", Toast.LENGTH_SHORT).show();
                        }
                    },
                    error -> {
                        lottie.setVisibility(View.GONE);
                        adddata.setClickable(true);

                        Toast.makeText(doctor.this, "Error Response: " + error.toString(), Toast.LENGTH_SHORT).show();

                    });



            RequestQueue requestQueue = Volley.newRequestQueue(doctor.this);
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
                        Toast.makeText(doctor.this, "Error loading image", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(doctor.this, "No image selected", Toast.LENGTH_SHORT).show();
                }
            });


    private void school() {

        arrayList.clear();

        arrayList = new ArrayList<>();


        String url = "https://wikipediabangla.com/apps/doctor2.php";




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
                                String id = jsonObject.getString("id");
                                String time = jsonObject.getString("time");
                                String reginumber = jsonObject.getString("reginumber");
                                String section = jsonObject.getString("section");
                                String cember = jsonObject.getString("cember");
                                String images = jsonObject.getString("images");






                                HashMap<String, String> hashMap = new HashMap<>();
                                hashMap.put("name", name);
                                hashMap.put("expert", expert);
                                hashMap.put("education", education);
                                hashMap.put("id", id);
                                hashMap.put("time", time);
                                hashMap.put("reginumber", reginumber);
                                hashMap.put("section", section);
                                hashMap.put("cember", cember);
                                hashMap.put("images", images);

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

        RequestQueue requestQueue = Volley.newRequestQueue(doctor.this);
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
            View viewx = layoutInflater.inflate(R.layout.doctor, parent, false);

            TextView name1, degree1,expert1,education1,cember1,time1,reginumber1;

            name1 = viewx.findViewById(R.id.name);
            degree1 = viewx.findViewById(R.id.degree);
            expert1 = viewx.findViewById(R.id.expert);
            education1 = viewx.findViewById(R.id.education);
            cember1 = viewx.findViewById(R.id.cember);
            time1 = viewx.findViewById(R.id.time);
            reginumber1 = viewx.findViewById(R.id.registration);
            CardView cardView = viewx.findViewById(R.id.card);



            ImageView imageView1 = viewx.findViewById(R.id.imageView1);






            HashMap<String,String> hashMap = arrayList.get(position);
            String namex = hashMap.get("name");
            String expertx = hashMap.get("expert");
            String educationx = hashMap.get("education");
            String images = hashMap.get("images");
            String id = hashMap.get("id");
            String timex = hashMap.get("time");
            String reginumberx = hashMap.get("reginumber");
            String cemberx = hashMap.get("cember");
            String sectionx = hashMap.get("section");


            name1.setText(namex);
            degree1.setText("ক্যাটাগরিঃ  "+sectionx);
            expert1.setText("অভিজ্ঞঃ  "+expertx);
            education1.setText("শিক্ষাগত যোহ্যতাঃ  "+educationx);
            cember1.setText("চেম্বারঃ  "+cemberx);
            time1.setText("সময়ঃ  "+timex);
            reginumber1.setText("রেজিঃনম্বরঃ  "+reginumberx);



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


                    String url = "https://wikipediabangla.com/apps/doctor3.php?id="+idx;


                    StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            if (response.contains("deleted")){

                                cardView.setVisibility(View.GONE);

                                android.app.AlertDialog alertDialog = new android.app.AlertDialog.Builder(doctor.this).create();
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

                                Toast.makeText(doctor.this,"Something went wrong!",Toast.LENGTH_SHORT).show();

                            };







                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                            lottie.setVisibility(View.GONE);

                        }
                    });


                    RequestQueue requestQueue = Volley.newRequestQueue(doctor.this);
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
                    imagelay.setVisibility(View.GONE);






                    name.setText(namex);
                    expert.setText(expertx);
                    education.setText(educationx);
                    cember.setText(cemberx);
                    time.setText(timex);
                    reginumber.setText(reginumberx);




                    adddata2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {




                            String namex = name.getText().toString();
                            String expertx = expert.getText().toString();
                            String educationx = education.getText().toString();
                            String cemberx = cember.getText().toString();
                            String timex = time.getText().toString();
                            String reginumberx = reginumber.getText().toString();






                            lottie.setVisibility(VISIBLE);


                            HashMap<String,String> hashMap = arrayList.get(position);
                            String idx = hashMap.get("id");


                            String url = "https://wikipediabangla.com/apps/doctor4.php?id="+idx+"&name="+namex+"&expert="+expertx+"&education="+educationx+"&cember="+cemberx+"&registration="+reginumberx+"&time="+timex;


                            StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {

                                    lottie.setVisibility(View.GONE);

                                    if (response.contains("updated")){


                                        addlay.setVisibility(View.GONE);
                                        arrayList.clear();
                                        school();
                                        listView.setVisibility(VISIBLE);
                                        adddata.setVisibility(VISIBLE);
                                        adddata2.setVisibility(View.GONE);


                                        android.app.AlertDialog alertDialog = new android.app.AlertDialog.Builder(doctor.this).create();
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

                                        Toast.makeText(doctor.this,"Something went wrong!",Toast.LENGTH_SHORT).show();

                                    };







                                }
                            }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {

                                    lottie.setVisibility(View.GONE);

                                }
                            });


                            RequestQueue requestQueue = Volley.newRequestQueue(doctor.this);
                            requestQueue.add(stringRequest);













                        }
                    });



                }
            });







            return viewx;
        }
    }



}
