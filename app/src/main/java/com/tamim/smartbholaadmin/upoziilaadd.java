package com.tamim.smartbholaadmin;

import static android.app.Activity.RESULT_OK;
import static android.view.View.VISIBLE;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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
import com.android.volley.toolbox.JsonObjectRequest;
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

public class upoziilaadd extends Fragment {

    Spinner spinner3, spinner4;
    String[] strings = {"Select", "চরফ্যাশন", "তজুমদ্দিন", "দৌলতখান", "বোরহানউদ্দিন", "ভোলা সদর", "মনপুরা", "লালমোহন"};

    TextInputEditText name, village, mobile, podobi, member;
    CardView adddata;
    LinearLayout imagelay;
    ImageView imageView;
    ProgressBar progressBar;

    Bitmap bitmap;
    String encodedImage;

    LottieAnimationView lottie;

    ListView listView;

    ArrayList<HashMap<String, String>> arrayList = new ArrayList<>();
    HashMap<String, String> hashMap;

    int MY_REQUEST_CODE = 1;

    ScrollView addlay;
    TabLayout tabLayout;

    TextView toptext, toptext2;

    public static String SECTION = "";

    private static long lastRequestQueueTime = 0;
    private static String lastResponse = ""; // To store the last response
    private static final long REQUEST_QUEUE_THRESHOLD = 3000; // 5 seconds in milliseconds

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View myView = inflater.inflate(R.layout.fragment_upoziilaadd, container, false);

        name = myView.findViewById(R.id.name);
        adddata = myView.findViewById(R.id.adddata);
        imageView = myView.findViewById(R.id.image_view);
        progressBar = myView.findViewById(R.id.progrerssbar);
        podobi = myView.findViewById(R.id.podobi);
        mobile = myView.findViewById(R.id.mobile);
        lottie = myView.findViewById(R.id.lottie);
        addlay = myView.findViewById(R.id.addlay);
        member = myView.findViewById(R.id.member);
        imagelay = myView.findViewById(R.id.imagelay);
        spinner3 = myView.findViewById(R.id.spinner);


        if (SECTION.contains("1")) {
            toptext.setText("ইউনিয়ন পরিষদ");
        } else if (SECTION.contains("2")) {
            toptext.setText("উপজেলা পরিষদ");
        }


        ArrayAdapter<String> arrayAdapter3 = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, strings);
        arrayAdapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner3.setAdapter(arrayAdapter3);

        spinner3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String value = parent.getItemAtPosition(position).toString();
                String[] strings2;


                name.setText(value);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Handle no selection if needed
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
            String podobix = podobi.getText().toString();
            String memberx = member.getText().toString();



            if (namex != null) {
                String url2 = "https://wikipediabangla.com/apps/upozila.php";


                long currentTime = System.currentTimeMillis();

                if (currentTime - lastRequestQueueTime < REQUEST_QUEUE_THRESHOLD) {
                    // Show the last response in the toast message
                    Toast.makeText(getContext(), "too quickly: " + lastResponse, Toast.LENGTH_SHORT).show();
                    lastRequestQueueTime = System.currentTimeMillis();





                } else {


                    uploadDataToServer(url2, namex, encodedImage, mobilex,podobix,memberx);



                }



            } else {
                lottie.setVisibility(View.GONE);
                adddata.setClickable(true);

                Toast.makeText(getContext(), "No image selected", Toast.LENGTH_SHORT).show();
            }
        });

        return myView;
    }


    private void uploadDataToServer(String url, String namex, String encodedImage, String mobilex, String podobix, String memberx) {
        try {
            JSONObject jsonBody = new JSONObject();
            jsonBody.put("name", namex);
            jsonBody.put("images", encodedImage);
            jsonBody.put("mobile", mobilex);
            jsonBody.put("podobi", podobix);
            jsonBody.put("member", memberx);



            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, jsonBody,
                    response -> {
                        lottie.setVisibility(View.GONE);
                        adddata.setClickable(true);

                        try {
                            // Check the status directly
                            String status = response.getString("status");

                            if ("success".equals(status)) {
                                new AlertDialog.Builder(getContext())
                                        .setMessage("Data Added Successfully")
                                        .setPositiveButton("OK", null)
                                        .show();

                                // Clear EditText fields
                                name.getText().clear();
                                mobile.getText().clear();
                                podobi.getText().clear();
                                member.getText().clear();

                                // Set the image to "imageichon" (assuming it's a resource)
                                imageView.setImageResource(R.drawable.imageicon);

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    },
                    error -> {
                        lottie.setVisibility(View.GONE);
                        adddata.setClickable(true);
                        Toast.makeText(getContext(), "Error uploading data", Toast.LENGTH_SHORT).show();
                    });

            // Add the request to the RequestQueue



            RequestQueue requestQueue = Volley.newRequestQueue(getContext());
            requestQueue.add(jsonObjectRequest);





        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == MY_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            Uri filepath = data.getData();
            try {
                InputStream inputStream = getActivity().getContentResolver().openInputStream(filepath);
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
                        InputStream inputStream = getActivity().getContentResolver().openInputStream(imageUri);
                        bitmap = BitmapFactory.decodeStream(inputStream);
                        imageView.setImageBitmap(bitmap);
                        ImageStore(bitmap);
                    } catch (FileNotFoundException e) {
                        Toast.makeText(getContext(), "Error loading image", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getContext(), "No image selected", Toast.LENGTH_SHORT).show();
                }
            });
}
