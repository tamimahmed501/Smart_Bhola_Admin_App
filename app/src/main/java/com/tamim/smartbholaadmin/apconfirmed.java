package com.tamim.smartbholaadmin;

import static android.content.Context.MODE_PRIVATE;
import static android.view.View.VISIBLE;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.airbnb.lottie.LottieAnimationView;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;


public class apconfirmed extends Fragment {



    ListView listView;
    LottieAnimationView lottie;

    private static final String PREF_NAME = "MyPrefs";

    Myadapter myadapter;

    ImageView backbutton;

    ArrayList<HashMap<String, String>> arrayList = new ArrayList<>();

    HashMap<String,String> hashMap;




    public static String TITLE = "";
    public static String UID = "";
    public static String BODY = "";




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View myView = inflater.inflate(R.layout.fragment_apconfirmed, container, false);



        listView = myView.findViewById(R.id.listView);
        lottie = myView.findViewById(R.id.lottie); // Corrected ID here

        myadapter = new Myadapter();
        listView.setAdapter(myadapter);


        school();



        return myView;
    }



    private void school() {

        arrayList.clear();
        lottie.setVisibility(VISIBLE);



        String url = "https://wikipediabangla.com/apps/appointment/apconfirm2.php";

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET, url, null,
                response -> {


                    lottie.setVisibility(View.GONE);



                    for (int x = 0; x < response.length(); x++) {
                        try {
                            JSONObject jsonObject = response.getJSONObject(x);
                            String id = jsonObject.getString("id");
                            String uid = jsonObject.getString("uid");
                            String doctor = jsonObject.getString("doctor");
                            String clinic = jsonObject.getString("clinic");
                            String time = jsonObject.getString("time");
                            String problem = jsonObject.getString("problem");
                            String status = jsonObject.getString("status");
                            String review = jsonObject.getString("review");
                            String rating = jsonObject.getString("rating");
                            String patient = jsonObject.getString("patient");
                            String mobile = jsonObject.getString("mobile");
                            String date = jsonObject.getString("date");



                            HashMap<String, String> hashMap = new HashMap<>();
                            hashMap.put("id", id);
                            hashMap.put("uid", uid);
                            hashMap.put("doctor", doctor);
                            hashMap.put("clinic", clinic);
                            hashMap.put("problem", problem);
                            hashMap.put("time", time);
                            hashMap.put("rating", rating);
                            hashMap.put("review", review);
                            hashMap.put("status", status);
                            hashMap.put("patient", patient);
                            hashMap.put("mobile", mobile);
                            hashMap.put("date", date);



                            arrayList.add(hashMap);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    // Notify the adapter that the data set has changed
                    myadapter.notifyDataSetChanged();
                },
                error -> {
                    // Handle error response

                    lottie.setVisibility(View.GONE);
                    Toast.makeText(getContext(), "Error Response: " + error.toString(), Toast.LENGTH_SHORT).show();
                });

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
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

        @SuppressLint({"MissingInflatedId", "ResourceAsColor"})
        @Override
        public View getView(int position, View viewx, ViewGroup parent) {
            if (viewx == null) {
                LayoutInflater layoutInflater = (LayoutInflater) getLayoutInflater().getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                viewx = layoutInflater.inflate(R.layout.confirmap, parent, false);
            }


            TextView name1,patient1,time1,status1,payment1,date1,problem1,mobile1,id1, clinic;

            CardView absent,success,call,notify;

            name1 = viewx.findViewById(R.id.name);
            patient1 = viewx.findViewById(R.id.patient);
            time1 = viewx.findViewById(R.id.time);
            status1 = viewx.findViewById(R.id.status);
            payment1 = viewx.findViewById(R.id.payment);
            date1 = viewx.findViewById(R.id.date);
            problem1 = viewx.findViewById(R.id.problem);
            mobile1 = viewx.findViewById(R.id.mobile);
            id1 = viewx.findViewById(R.id.id);
            success = viewx.findViewById(R.id.success);
            absent = viewx.findViewById(R.id.absent);
            call = viewx.findViewById(R.id.call);
            notify = viewx.findViewById(R.id.notify);
            clinic = viewx.findViewById(R.id.clinic);





            HashMap<String,String> hashMap = arrayList.get(position);
            String id = hashMap.get("id");
            String uid = hashMap.get("uid");
            String rate = hashMap.get("rating");
            String review = hashMap.get("review");
            String status = hashMap.get("status");
            String doctor = hashMap.get("doctor");
            String clinicx = hashMap.get("clinic");
            String time = hashMap.get("time");
            String problem = hashMap.get("problem");

            String patient = hashMap.get("patient");
            String mobile = hashMap.get("mobile");
            String date = hashMap.get("date");






            id1.setText(id);
            name1.setText("Doctor: "+doctor);
            time1.setText("Time: "+time);
            status1.setText("Status: "+status);
            patient1.setText("Patient: "+patient);
            payment1.setText("Payment: Unpaid");
            date1.setText("Appointment Date: "+date);
            problem1.setText("Problem: "+problem);
            mobile1.setText("Mobile: "+mobile);
            clinic.setText(clinicx);


            notify.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {



                    UID = uid;
                    TITLE = "Appointment Alert";
                    BODY = "Your Appointment is very soon. please come "+clinic+" as soon as possible.";

                    notification();


                }
            });



            success.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {



                    lottie.setVisibility(VISIBLE);

                    String url5 = "https://wikipediabangla.com/apps/appointment/apaccept.php?status=COMPLETE"+"&id="+id;

                    StringRequest stringRequest = new StringRequest(Request.Method.GET, url5, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {



                            lottie.setVisibility(View.GONE);


                            if (response.contains("success")){





                                UID = uid;
                                TITLE = "Appointment Succesfull";
                                BODY = "Please give a review for "+doctor;

                                notification();



                                school();

                            }


                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                            new AlertDialog.Builder(getContext())
                                    .setTitle("Server response")
                                    .setMessage("Something went wrong!")
                                    .show();


                            lottie.setVisibility(View.GONE);

                        }
                    });


                    RequestQueue requestQueue = Volley.newRequestQueue(getContext());
                    requestQueue.add(stringRequest);













                }
            });



            absent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {



                    lottie.setVisibility(VISIBLE);

                    String url5 = "https://wikipediabangla.com/apps/appointment/apaccept.php?status=cancelled"+"&id="+id;

                    StringRequest stringRequest = new StringRequest(Request.Method.GET, url5, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {



                            lottie.setVisibility(View.GONE);


                            if (response.contains("success")){


                                school();




                                UID = uid;
                                TITLE = "Appointment Cancelled";
                                BODY = "Doctor Name:"+doctor+", Appointment ID: "+id;

                                notification();


                            }


                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                            new AlertDialog.Builder(getContext())
                                    .setTitle("Server response")
                                    .setMessage("Something went wrong!")
                                    .show();


                            lottie.setVisibility(View.GONE);

                        }
                    });


                    RequestQueue requestQueue = Volley.newRequestQueue(getContext());
                    requestQueue.add(stringRequest);













                }
            });



            call.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {



                    String formattedNumber = mobile.replaceAll("[^0-9]", "");

                    // Create an Intent with ACTION_DIAL and set the data to the formatted number
                    Intent intent = new Intent(Intent.ACTION_DIAL);
                    intent.setData(Uri.parse("tel:" + formattedNumber));

                    // Start the activity to initiate the call
                    startActivity(intent);





                }
            });





            return viewx;
        }
    }






    private void notification(){







        String title = TITLE;
        String body = BODY;
        String uid = UID;



        String url1 = "https://wikipediabangla.com/apps/firebase/fcm3.php?body="+body+"&title="+title+"&id="+uid;
        StringRequest stringRequest = new StringRequest(0, url1, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);







    }





}