package com.tamim.smartbholaadmin;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONException;
import org.json.JSONObject;


public class busadd extends Fragment {

    TextInputEditText route1, route2, distance, student, local;
    AutoCompleteTextView way;
    LottieAnimationView lottie;

    CardView adddata;;
    public static String WAY = "";


    String[] strings3 = {"Local","Direct","National1"};


    AutoCompleteTextView gender,group,upozila,union;
    ArrayAdapter<String> arrayAdapter;


    public static String ROUTE1 = "";
    public static String ROUTE2 = "";
    public static String DISTANCE = "";

    public static String ID = "";

    public static String UID = "";


    private static long lastRequestQueueTime = 0;
    private static String lastResponse = ""; // To store the last response
    private static final long REQUEST_QUEUE_THRESHOLD = 3000; // 5 seconds in milliseconds

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       View myView = inflater.inflate(R.layout.fragment_busadd, container, false);


       route1 = myView.findViewById(R.id.route1);
       route2 = myView.findViewById(R.id.route2);
       way = myView.findViewById(R.id.way);
       distance = myView.findViewById(R.id.distance);
       student = myView.findViewById(R.id.student);
       local = myView.findViewById(R.id.local);
       lottie = myView.findViewById(R.id.lottie);
       adddata = myView.findViewById(R.id.adddata);





        arrayAdapter = new ArrayAdapter<>(getContext(), R.layout.list_item, strings3);

        way.setAdapter(arrayAdapter);



        way.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String item = parent.getItemAtPosition(position).toString();

                WAY=item;


            }
        });



        if (ID.contains("1")){


            route1.setText(ROUTE1);
            route2.setText(ROUTE2);
            way.setText(WAY);
            distance.setText(DISTANCE);


        } else {};


       adddata.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {




               String r1 = route1.getText().toString();
               String r2 = route2.getText().toString();
               String distance1 = distance.getText().toString();
               String student1 = student.getText().toString();
               String local1 = local.getText().toString();




               if (r1!= null) {

                   String url2;

                   if (ID.contains("1")){

                        url2 = "https://wikipediabangla.com/apps/busedit.php";


                   } else {

                        url2 = "https://wikipediabangla.com/apps/busadd.php";


                   }



                   long currentTime = System.currentTimeMillis();

                   if (currentTime - lastRequestQueueTime < REQUEST_QUEUE_THRESHOLD) {
                       // Show the last response in the toast message
                       Toast.makeText(getContext(), "too quickly: " + lastResponse, Toast.LENGTH_SHORT).show();
                       lastRequestQueueTime = System.currentTimeMillis();





                   } else {



                       lottie.setVisibility(View.VISIBLE);


                       uploadDataToServer(url2, r1, r2, distance1,student1, local1);



                   }



               } else {
                   lottie.setVisibility(View.GONE);
                   adddata.setClickable(true);

               }





           }


       });


        return myView;
    }

    private void uploadDataToServer(String url, String r1, String r2,String distance1, String student1, String local1) {
        try {
            JSONObject jsonBody = new JSONObject();
            jsonBody.put("route1", r1);
            jsonBody.put("route2", r2);
            jsonBody.put("distance", distance1);
            jsonBody.put("student", "0");
            jsonBody.put("local", "0");
            jsonBody.put("way", WAY);
            jsonBody.put("id", UID);



            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, jsonBody,
                    response -> {
                        lottie.setVisibility(View.GONE);
                        adddata.setClickable(true);

                        try {
                            // Check the status directly
                            String status = response.getString("status");

                            if ("success".equals(status)) {


                                if (ID.contains("1")){


                                    new AlertDialog.Builder(getContext())
                                            .setMessage("Data Updated Successfully")
                                            .setPositiveButton("OK", null)
                                            .show();

                                } else {

                                    new AlertDialog.Builder(getContext())
                                            .setMessage("Data Added Successfully")
                                            .setPositiveButton("OK", null)
                                            .show();


                                }

                                route1.getText().clear();
                                route2.getText().clear();
                                distance.getText().clear();
                                local.getText().clear();
                                student.getText().clear();

                                route1.requestFocus();




                            } else {
                                // If status is not success, assume it's an error
                                Toast.makeText(getContext(), "Upload Failed", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getContext(), "Error parsing server response", Toast.LENGTH_SHORT).show();
                        }
                    },
                    error -> {
                        lottie.setVisibility(View.GONE);
                        adddata.setClickable(true);

                        Toast.makeText(getContext(), "Error Response: " + error.toString(), Toast.LENGTH_SHORT).show();

                    });



            RequestQueue requestQueue = Volley.newRequestQueue(getContext());
            requestQueue.add(jsonObjectRequest);

            lastRequestQueueTime = System.currentTimeMillis(); // Update the last request time


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


}