package com.tamim.smartbholaadmin;

import static android.view.View.VISIBLE;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
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
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

public class adverstisment extends AppCompatActivity {




    Bitmap bitmap;
    String encodedImage;

    LottieAnimationView lottie;

    ImageView imageView;



    int MY_REQUEST_CODE = 1;



    public static String SECTION = "";




    TextInputEditText status;



    ImageView  backbutton;
    CardView send, submit;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adverstisment);

        imageView = findViewById(R.id.image_view);
        send = findViewById(R.id.send);
        backbutton = findViewById(R.id.backbutton);
        submit = findViewById(R.id.submit);
        status = findViewById(R.id.status);
        lottie = findViewById(R.id.lottie);



        imageView.setOnClickListener(view -> {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            pickImages.launch(intent);
        });





        send.setOnClickListener(v -> {

            lottie.setVisibility(View.VISIBLE);



            if (encodedImage != null) {
                String url2 = "https://wikipediabangla.com/apps/adimages.php";


                    uploadDataToServer(url2, encodedImage);


            } else {
                lottie.setVisibility(View.GONE);

                Toast.makeText(adverstisment.this, "No image selected", Toast.LENGTH_SHORT).show();
            }
        });



        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String statusx = status.getText().toString();






                lottie.setVisibility(VISIBLE);

                String url5 = "https://wikipediabangla.com/apps/adstatus.php?status="+statusx;

                StringRequest stringRequest = new StringRequest(Request.Method.GET, url5, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {



                        lottie.setVisibility(View.GONE);


                        if (response.contains("success")){


                            new AlertDialog.Builder(adverstisment.this)
                                    .setTitle("Alert!")
                                    .setMessage("Ad is "+statusx+" !")
                                    .show();




                        }


                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        new AlertDialog.Builder(adverstisment.this)
                                .setTitle("Server response")
                                .setMessage("Something went wrong!")
                                .show();


                        lottie.setVisibility(View.GONE);

                    }
                });


                RequestQueue requestQueue = Volley.newRequestQueue(adverstisment.this);
                requestQueue.add(stringRequest);





            }
        });


    }
    private void uploadDataToServer(String url, String encodedImage) {
        try {
            JSONObject jsonBody = new JSONObject();

            lottie.setVisibility(View.VISIBLE);

            jsonBody.put("images", encodedImage);


            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, jsonBody,
                    response -> {
                        lottie.setVisibility(View.GONE);

                        try {
                            // Check the status directly
                            String status = response.getString("status");

                            if ("success".equals(status)) {

                                new AlertDialog.Builder(adverstisment.this)
                                        .setMessage("Images Added Successfully")
                                        .setPositiveButton("OK", null)
                                        .show();




                            } else {
                                // If status is not success, assume it's an error
                                Toast.makeText(adverstisment.this, "Upload Failed", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(adverstisment.this, "Error parsing server response", Toast.LENGTH_SHORT).show();
                        }
                    },
                    error -> {
                        lottie.setVisibility(View.GONE);


                        Toast.makeText(adverstisment.this, "Error Response: " + error.toString(), Toast.LENGTH_SHORT).show();

                    });



            RequestQueue requestQueue = Volley.newRequestQueue(adverstisment.this);
            requestQueue.add(jsonObjectRequest);



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
                        Toast.makeText(adverstisment.this, "Error loading image", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(adverstisment.this, "No image selected", Toast.LENGTH_SHORT).show();
                }
            });

}