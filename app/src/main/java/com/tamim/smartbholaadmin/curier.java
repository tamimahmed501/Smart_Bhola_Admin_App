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


public class curier extends AppCompatActivity {



    TextInputEditText name,adress,upozila,number;
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


    Spinner spinner;
    String[] strings = {"Select","চরফ্যাশন","তজুমদ্দিন","দৌলতখান","বোরহানউদ্দিন","ভোলা সদর","মনপুরা","লালমোহন"};


    private static long lastRequestQueueTime = 0;
    private static String lastResponse = ""; // To store the last response
    private static final long REQUEST_QUEUE_THRESHOLD = 3000; // 5 seconds in milliseconds


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_curier);

        name = findViewById(R.id.name);
        adddata = findViewById(R.id.adddata);
        imageView = findViewById(R.id.image_view);
        progressBar = findViewById(R.id.progrerssbar);
        adress = findViewById(R.id.adress);
        upozila = findViewById(R.id.upozila);
        number = findViewById(R.id.mobile);
        lottie = findViewById(R.id.lottie);
        listView= findViewById(R.id.listView);
        addlay = findViewById(R.id.addlay);
        tabLayout = findViewById(R.id.tabLayout);
        toptext=findViewById(R.id.toptext);
        toptext2=findViewById(R.id.toptext2);
        spinner=findViewById(R.id.spinner);
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




        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(curier.this, android.R.layout.simple_spinner_item, strings);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);







        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String value = parent.getItemAtPosition(position).toString();


                if (value.contains("লালমোহন")) {
                    upozila.setText("লালমোহন");
                } else if (value.contains("মনপুরা")) {
                    upozila.setText("মনপুরা");
                } else if (value.contains("ভোলা সদর")) {
                    upozila.setText("ভোলা সদর");
                }else if (value.contains("বোরহানউদ্দিন")) {
                    upozila.setText("বোরহানউদ্দিন");
                } else if (value.contains("দৌলতখান")) {
                    upozila.setText("দৌলতখান");
                }else if (value.contains("তজুমদ্দিন")) {
                    upozila.setText("তজুমদ্দিন");
                }else if (value.contains("চরফ্যাশন")) {
                    upozila.setText("চরফ্যাশন");
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
                    listView.setVisibility(View.GONE);
                    lottie.setVisibility(View.GONE);


                    // Clear EditText fields
                    name.getText().clear();
                    upozila.getText().clear();
                    adress.getText().clear();
                    number.getText().clear();
                    name.requestFocus();


                    adddata2.setVisibility(View.GONE);
                    adddata.setVisibility(VISIBLE);


                } else if (position == 1) {


                    addlay.setVisibility(View.GONE);
                    listView.setVisibility(View.GONE);

                    Toast.makeText(curier.this,"No Approval Request",Toast.LENGTH_SHORT).show();



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
            String adressx = adress.getText().toString();
            String upozilax = upozila.getText().toString();
            String numberx = number.getText().toString();

            if (encodedImage != null) {
                String url2 = "https://wikipediabangla.com/apps/curier.php";

                long currentTime = System.currentTimeMillis();

                if (currentTime - lastRequestQueueTime < REQUEST_QUEUE_THRESHOLD) {
                    // Show the last response in the toast message
                    Toast.makeText(curier.this, "Too quickly: " + lastResponse, Toast.LENGTH_SHORT).show();
                    lastRequestQueueTime = System.currentTimeMillis();
                } else {
                    uploadDataToServer(url2, namex, encodedImage, adressx, upozilax, numberx);
                }
            } else {
                lottie.setVisibility(View.GONE);
                adddata.setClickable(true);
                Toast.makeText(curier.this, "No image selected", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void uploadDataToServer(String url2, String namex, String encodedImage, String adressx, String upozilax, String numberx) {
        try {
            JSONObject jsonBody = new JSONObject();
            jsonBody.put("name", namex);
            jsonBody.put("images", encodedImage);
            jsonBody.put("upozila", upozilax);
            jsonBody.put("adress", adressx);
            jsonBody.put("number", numberx);

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url2, jsonBody,
                    response -> {
                        lottie.setVisibility(View.GONE);
                        adddata.setClickable(true);

                        try {
                            // Check the status directly
                            String status = response.getString("status");

                            if ("success".equals(status)) {
                                new AlertDialog.Builder(curier.this)
                                        .setMessage("Data Added Successfully")
                                        .setPositiveButton("OK", null)
                                        .show();

                                // Clear EditText fields
                                name.getText().clear();
                                upozila.getText().clear();
                                adress.getText().clear();
                                number.getText().clear();
                                name.requestFocus();

                                // Set the image to "imageichon" (assuming it's a resource)
                                imageView.setImageResource(R.drawable.imageicon);

                                school();
                            } else {
                                // If status is not success, assume it's an error
                                Toast.makeText(curier.this, "Upload Failed", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(curier.this, "Error parsing server response", Toast.LENGTH_SHORT).show();
                        }
                    },
                    error -> {
                        lottie.setVisibility(View.GONE);
                        adddata.setClickable(true);
                        Toast.makeText(curier.this, "Error Response: " + error.toString(), Toast.LENGTH_SHORT).show();
                    });

            RequestQueue requestQueue = Volley.newRequestQueue(curier.this);
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
                        Toast.makeText(curier.this, "Error loading image", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(curier.this, "No image selected", Toast.LENGTH_SHORT).show();
                }
            });


    private void school() {

        arrayList.clear();

        arrayList = new ArrayList<>();


        String url = "https://wikipediabangla.com/apps/curier2.php";




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
                                String id = jsonObject.getString("id");
                                String adress = jsonObject.getString("adress");
                                String number = jsonObject.getString("number");
                                String upozila = jsonObject.getString("upozila");
                                String images = jsonObject.getString("images");






                                HashMap<String, String> hashMap = new HashMap<>();
                                hashMap.put("name", name);
                                hashMap.put("adress", adress);
                                hashMap.put("upozila", upozila);
                                hashMap.put("id", id);
                                hashMap.put("number", number);
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

        RequestQueue requestQueue = Volley.newRequestQueue(curier.this);
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
            View viewx = layoutInflater.inflate(R.layout.diagnostic, parent, false);


            TextView name1, adress1, mobile1, upozila1,details1;
            name1 = viewx.findViewById(R.id.name);
            adress1 = viewx.findViewById(R.id.adress);
            mobile1 = viewx.findViewById(R.id.mobile);
            upozila1 = viewx.findViewById(R.id.registration);
            details1 = viewx.findViewById(R.id.details);

            ImageView imageView1 = viewx.findViewById(R.id.imageView1);







            HashMap<String,String> hashMap = arrayList.get(position);
            String namex = hashMap.get("name");
            String adressx = hashMap.get("adress");
            String numberx = hashMap.get("number");
            String images = hashMap.get("images");
            String id = hashMap.get("id");
            String upozilax = hashMap.get("upozila");




            name1.setText(namex);
            mobile1.setText("মোবাইলঃ  "+numberx);
            adress1.setText("ঠিকানাঃ  "+adressx);
            upozila1.setText("উপজেলাঃ  "+upozilax);



            String urlx = "https://wikipediabangla.com/apps/Images/"+images;


            Picasso.get()
                    .load(urlx)
                    .placeholder(R.drawable.imageicon)
                    .error(R.drawable.imgichon)
                    .into(imageView1);

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


                    String url = "https://wikipediabangla.com/apps/delete.php?id="+idx+"&table=curier";


                    StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            if (response.contains("deleted")){

                                cardView.setVisibility(View.GONE);

                                android.app.AlertDialog alertDialog = new android.app.AlertDialog.Builder(curier.this).create();
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


                                school();




                            } else {

                                Toast.makeText(curier.this,"Something went wrong!",Toast.LENGTH_SHORT).show();

                            };







                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                            lottie.setVisibility(View.GONE);

                        }
                    });


                    RequestQueue requestQueue = Volley.newRequestQueue(curier.this);
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
                    adress.setText(adressx);
                    upozila.setText(upozilax);
                    number.setText(numberx);




                    adddata2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {



                            String namex = name.getText().toString();
                            String adressx = adress.getText().toString();
                            String upozilax = upozila.getText().toString();
                            String numberx = number.getText().toString();

                            lottie.setVisibility(VISIBLE);


                            HashMap<String,String> hashMap = arrayList.get(position);
                            String idx = hashMap.get("id");


                            String url = "https://wikipediabangla.com/apps/curier4.php?id="+idx+"&name="+namex+"&adress="+adressx+"&upozila="+upozilax+"&number="+numberx;


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


                                        android.app.AlertDialog alertDialog = new android.app.AlertDialog.Builder(curier.this).create();
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

                                        Toast.makeText(curier.this,"Something went wrong!",Toast.LENGTH_SHORT).show();

                                    };







                                }
                            }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {

                                    lottie.setVisibility(View.GONE);

                                }
                            });


                            RequestQueue requestQueue = Volley.newRequestQueue(curier.this);
                            requestQueue.add(stringRequest);













                        }
                    });



                }
            });








            return viewx;
        }
    }



}
