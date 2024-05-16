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


public class expert extends AppCompatActivity {



    TextInputEditText name,service,area,mobile,experience;
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



    private static long lastRequestQueueTime = 0;
    private static String lastResponse = ""; // To store the last response
    private static final long REQUEST_QUEUE_THRESHOLD = 3000; // 5 seconds in milliseconds


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expert);

        name = findViewById(R.id.name);
        adddata = findViewById(R.id.adddata);
        imageView = findViewById(R.id.image_view);
        progressBar = findViewById(R.id.progrerssbar);
        service = findViewById(R.id.service);
        area = findViewById(R.id.area);
        experience = findViewById(R.id.experience);
        mobile = findViewById(R.id.mobile);
        lottie = findViewById(R.id.lottie);
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

        listView.setVisibility(View.GONE);



        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                if (position == 0) {

                    addlay.setVisibility(VISIBLE);
                    listView.setVisibility(View.GONE);
                    lottie.setVisibility(View.GONE);
                    imagelay.setVisibility(VISIBLE);


                    // Clear EditText fields
                    name.getText().clear();
                    service.getText().clear();
                    mobile.getText().clear();
                    area.getText().clear();
                    experience.getText().clear();

                    adddata2.setVisibility(View.GONE);
                    adddata.setVisibility(VISIBLE);


                } else if (position == 1) {


                    addlay.setVisibility(View.GONE);
                    listView.setVisibility(View.GONE);

                    Toast.makeText(expert.this,"No Approval Request",Toast.LENGTH_SHORT).show();



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

            String namex = name.getText().toString();
            String mobilex = mobile.getText().toString();
            String servicex = service.getText().toString();
            String areax = area.getText().toString();
            String experiencex = experience.getText().toString();



            if (encodedImage != null) {
                String url2 = "https://wikipediabangla.com/apps/expert.php";



                long currentTime = System.currentTimeMillis();

                if (currentTime - lastRequestQueueTime < REQUEST_QUEUE_THRESHOLD) {
                    // Show the last response in the toast message
                    Toast.makeText(expert.this, "too quickly: " + lastResponse, Toast.LENGTH_SHORT).show();
                    lastRequestQueueTime = System.currentTimeMillis();





                } else {


                    uploadDataToServer(url2, namex, encodedImage, mobilex,servicex,experiencex, areax);



                }



            } else {
                lottie.setVisibility(View.GONE);
                adddata.setClickable(true);

                Toast.makeText(expert.this, "No image selected", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void uploadDataToServer(String url2, String namex, String encodedImage, String mobilex, String servicex, String experiencex, String areax) {
        try {

            // Add the following debug logs
            Log.d("DoctorActivity", "URL: " + url2);
            Log.d("DoctorActivity", "Encoded Image: " + encodedImage);

            JSONObject jsonBody = new JSONObject();
            jsonBody.put("name", namex);
            jsonBody.put("images", encodedImage);
            jsonBody.put("mobile", mobilex);
            jsonBody.put("area", areax);
            jsonBody.put("experience", experiencex);
            jsonBody.put("servicex", servicex);


            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url2, jsonBody,
                    response -> {
                        lottie.setVisibility(View.GONE);
                        adddata.setClickable(true);

                        try {
                            // Check the status directly
                            String status = response.getString("status");

                            if ("success".equals(status)) {

                                new AlertDialog.Builder(expert.this)
                                        .setMessage("Data Added Successfully")
                                        .setPositiveButton("OK", null)
                                        .show();



                                // Clear EditText fields
                                name.getText().clear();
                                service.getText().clear();
                                mobile.getText().clear();
                                area.getText().clear();
                                experience.getText().clear();

                                name.requestFocus();

                                // Set the image to "imageichon" (assuming it's a resource)
                                imageView.setImageResource(R.drawable.imageicon);

                                school();


                            } else {
                                // If status is not success, assume it's an error
                                Toast.makeText(expert.this, "Upload Failed", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(expert.this, "Error parsing server response", Toast.LENGTH_SHORT).show();
                        }
                    },
                    error -> {
                        lottie.setVisibility(View.GONE);
                        adddata.setClickable(true);

                        Toast.makeText(expert.this, "Error Response: " + error.toString(), Toast.LENGTH_SHORT).show();

                    });



            RequestQueue requestQueue = Volley.newRequestQueue(expert.this);
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
                        Toast.makeText(expert.this, "Error loading image", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(expert.this, "No image selected", Toast.LENGTH_SHORT).show();
                }
            });


    private void school() {


        arrayList.clear();
        arrayList = new ArrayList<>();


        String url = "https://wikipediabangla.com/apps/expert2.php";




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
                                String area = jsonObject.getString("area");
                                String mobile = jsonObject.getString("mobile");
                                String images = jsonObject.getString("images");
                                String experience = jsonObject.getString("experience");
                                String service = jsonObject.getString("servicex");
                                String id = jsonObject.getString("id");


                                HashMap<String, String> hashMap = new HashMap<>();
                                hashMap.put("name", name);
                                hashMap.put("mobile", mobile);
                                hashMap.put("area", area);
                                hashMap.put("images", images);
                                hashMap.put("id", id);
                                hashMap.put("service", service);
                                hashMap.put("experience", experience);

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

        RequestQueue requestQueue = Volley.newRequestQueue(expert.this);
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
            View viewx = layoutInflater.inflate(R.layout.service, parent, false);

            TextView name1, area1,mobile1,service1,experience1;
            name1 = viewx.findViewById(R.id.name);
            area1 = viewx.findViewById(R.id.area);
            mobile1 = viewx.findViewById(R.id.mobile);
            service1 = viewx.findViewById(R.id.service);
            experience1 = viewx.findViewById(R.id.experience);


            ImageView imageView1 = viewx.findViewById(R.id.imageView1);






            HashMap<String,String> hashMap = arrayList.get(position);
            String namex = hashMap.get("name");
            String areax = hashMap.get("area");
            String mobilex = hashMap.get("mobile");
            String images = hashMap.get("images");
            String id = hashMap.get("id");
            String servicex = hashMap.get("service");
            String experiencex = hashMap.get("experience");

            CardView cardView = viewx.findViewById(R.id.card);


            name1.setText(namex);
            area1.setText("সার্ভয়স এরিয়াঃ  "+areax);
            mobile1.setText("মোবাইলঃ  "+mobilex);
            service1.setText("ক্যাটাগরিঃ "+servicex);
            experience1.setText("অভিজ্ঞতাঃ  "+experiencex);


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


                    String url = "https://wikipediabangla.com/apps/expert3.php?id="+idx;


                    StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            lottie.setVisibility(View.GONE);
                            cardView.setVisibility(View.GONE);

                            if (response.contains("deleted")){


                                android.app.AlertDialog alertDialog = new android.app.AlertDialog.Builder(expert.this).create();
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

                                Toast.makeText(expert.this,"Something went wrong!",Toast.LENGTH_SHORT).show();

                            };







                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                            lottie.setVisibility(View.GONE);

                        }
                    });


                    RequestQueue requestQueue = Volley.newRequestQueue(expert.this);
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
                    mobile.setText(mobilex);
                    area.setText(areax);
                    service.setText(servicex);
                    experience.setText(experiencex);





                    adddata2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {




                            String namex = name.getText().toString();
                            String mobilex = mobile.getText().toString();
                            String servicex = service.getText().toString();
                            String areax = area.getText().toString();
                            String experiencex = experience.getText().toString();




                            lottie.setVisibility(VISIBLE);


                            HashMap<String,String> hashMap = arrayList.get(position);
                            String idx = hashMap.get("id");


                            String url = "https://wikipediabangla.com/apps/expert4.php?id="+idx+"&name="+namex+"&mobile="+mobilex+"&area="+areax+"&service="+servicex+"&experience="+experiencex;


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


                                        android.app.AlertDialog alertDialog = new android.app.AlertDialog.Builder(expert.this).create();
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

                                        Toast.makeText(expert.this,"Something went wrong!",Toast.LENGTH_SHORT).show();

                                    };







                                }
                            }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {

                                    lottie.setVisibility(View.GONE);

                                }
                            });


                            RequestQueue requestQueue = Volley.newRequestQueue(expert.this);
                            requestQueue.add(stringRequest);













                        }
                    });



                }
            });






            return viewx;
        }
    }



}
