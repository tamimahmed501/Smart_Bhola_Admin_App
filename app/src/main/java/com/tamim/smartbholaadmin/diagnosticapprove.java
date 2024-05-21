package com.tamim.smartbholaadmin;

import static android.view.View.VISIBLE;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;


public class diagnosticapprove extends Fragment {

    RecyclerView recyclerView;
    LottieAnimationView lottie;



    MyAdapter myAdapter;

    ArrayList<HashMap<String, String>> arrayList = new ArrayList<>();

    HashMap<String,String> hashMap;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View myView = inflater.inflate(R.layout.fragment_diagnosticapprove, container, false);



        recyclerView = myView.findViewById(R.id.recyclerView);
        lottie = myView.findViewById(R.id.lottie); // Corrected ID here


        myAdapter = new MyAdapter();
        recyclerView.setAdapter(myAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));


        school();





        return myView;
    }
    private void school() {

        lottie.setVisibility(VISIBLE);

        arrayList = new ArrayList<>();

        String url = "https://wikipediabangla.com/apps/dapply3.php";

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                com.android.volley.Request.Method.GET, url, null,
                response -> {


                    lottie.setVisibility(View.GONE);

                    // Log the number of data items received
                    Log.d("DataCount", "Number of data items received: " + response.length());

                    for (int x = 0; x < response.length(); x++) {
                        try {
                            JSONObject jsonObject = response.getJSONObject(x);
                            String id = jsonObject.getString("id");
                            String name = jsonObject.getString("name");
                            String address = jsonObject.getString("adress");
                            String mobile = jsonObject.getString("mobile");
                            String password = jsonObject.getString("password");
                            String registration = jsonObject.getString("registration");
                            String uid = jsonObject.getString("uid");


                            HashMap<String, String> hashMap = new HashMap<>();
                            hashMap.put("id", id);
                            hashMap.put("name", name);
                            hashMap.put("mobile", mobile);
                            hashMap.put("adress", address);
                            hashMap.put("password", password);
                            hashMap.put("registration", registration);
                            hashMap.put("uid", uid);


                            // Add each item to the ArrayList
                            arrayList.add(hashMap);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    // Notify the adapter that the data set has changed
                    myAdapter.notifyDataSetChanged();
                },
                error -> {
                    // Handle error response

                    lottie.setVisibility(View.GONE);
                    Toast.makeText(getContext(), "Error Response: " + error.toString(), Toast.LENGTH_SHORT).show();
                });

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(jsonArrayRequest);
    }



    private class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder>{



        private class MyViewHolder extends RecyclerView.ViewHolder{


            TextView name1, adress1,mobile1,registration;
            CardView cancel, approve;

            CardView card;

            ImageView imageView1;


            public MyViewHolder(@NonNull View itemView) {
                super(itemView);




                name1 = itemView.findViewById(R.id.name);
                adress1 = itemView.findViewById(R.id.adress);
                mobile1 = itemView.findViewById(R.id.mobile);
                card = itemView.findViewById(R.id.card);
                imageView1 = itemView.findViewById(R.id.imageView1);
                cancel = itemView.findViewById(R.id.cancel);
                approve = itemView.findViewById(R.id.approve);
                registration = itemView.findViewById(R.id.registration);


            }
        }



        @NonNull
        @Override
        public MyAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            LayoutInflater layoutInflater = getLayoutInflater();
            View myView = layoutInflater.inflate(R.layout.diagnostic2, parent, false);








            return new MyAdapter.MyViewHolder(myView);
        }

        @Override
        public void onBindViewHolder(@NonNull MyAdapter.MyViewHolder holder, int position) {


            HashMap<String, String> hashMap = arrayList.get(position);
            String namex = hashMap.get("name");
            String adressx = hashMap.get("adress");
            String password = hashMap.get("password");
            String idx = hashMap.get("id");
            String mobile = hashMap.get("mobile");
            String registration = hashMap.get("registration");
            String uid1 = hashMap.get("uid");


            holder.name1.setText(namex);
            holder.adress1.setText(adressx);
            holder.mobile1.setText("Mobile: "+mobile);
            holder.registration.setText("Regi: "+registration);




            holder.imageView1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    uidData.UID=uid1;
                    startActivity(new Intent(getContext(), uidData.class));
                }
            });


            holder.approve.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    lottie.setVisibility(VISIBLE);


                    String url1 = "https://wikipediabangla.com/apps/dapprove.php?id="+idx+"&status=1"+"&name="+namex+"&mobile="+mobile+"&registration="+registration+"&adress="+adressx;
                    StringRequest stringRequest = new StringRequest(0, url1, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            lottie.setVisibility(View.GONE);

                            school();







                            String title = "Congratulations";
                            String body = "Your diagnostic registration request was approved. Now you can manage your diagnostic on Smart Diagnostic APP. Happy journey with Smart Bhola.";



                            String url1 = "https://wikipediabangla.com/apps/firebase/fcm3.php?body="+body+"&title="+title+"&id="+uid1;
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



                            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                            builder.setTitle("Status").setMessage(response).setCancelable(false).setPositiveButton("Close", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int id) {

                                }
                            });
                            AlertDialog alert = builder.create();
                            alert.show();




                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                            lottie.setVisibility(View.GONE);

                        }
                    });

                    RequestQueue requestQueue = Volley.newRequestQueue(getContext());
                    requestQueue.add(stringRequest);







                }
            });




            holder.cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {





                    String url1 = "https://wikipediabangla.com/apps/dcancel.php?id="+idx+"&status=Cancel";
                    StringRequest stringRequest = new StringRequest(0, url1, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            lottie.setVisibility(View.GONE);


                            school();

                            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                            builder.setTitle("Status").setMessage(response).setCancelable(false).setPositiveButton("Close", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int id) {

                                }
                            });
                            AlertDialog alert = builder.create();
                            alert.show();




                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                            lottie.setVisibility(View.GONE);

                        }
                    });

                    RequestQueue requestQueue = Volley.newRequestQueue(getContext());
                    requestQueue.add(stringRequest);




                }
            });


        }

        @Override
        public int getItemCount() {
            return arrayList.size();
        }




    }
}