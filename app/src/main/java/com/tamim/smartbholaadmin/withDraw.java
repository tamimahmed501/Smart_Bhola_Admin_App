package com.tamim.smartbholaadmin;

import static android.view.View.VISIBLE;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class withDraw extends AppCompatActivity {
    LottieAnimationView lottie;
    private static final String PREF_NAME = "MyPrefs";

    ArrayList<HashMap<String, String>> arrayList = new ArrayList<>();
    HashMap<String,String> hashMap;




    MyAdapter myAdapter;



    ImageView back;



    RecyclerView recyclerView;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_with_draw);

        lottie= findViewById(R.id.lottie);
        back = findViewById(R.id.backbutton);
        recyclerView =  findViewById(R.id.recyclerView);

        myAdapter = new MyAdapter();
        recyclerView.setAdapter(myAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(withDraw.this));


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                finishAndRemoveTask();
            }
        });



        ambulence();


    }

    private void ambulence() {


        arrayList.clear();
        lottie.setVisibility(VISIBLE);

        String url = "https://wikipediabangla.com/apps/offer/withdrawrequest.php";

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        lottie.setVisibility(View.GONE);

                        for (int x = 0; x < response.length(); x++) {
                            try {
                                JSONObject jsonObject = response.getJSONObject(x);
                                String id = jsonObject.getString("id");
                                String uid = jsonObject.getString("uid");
                                String method = jsonObject.getString("name");
                                String tid = jsonObject.getString("tid");
                                String amount = jsonObject.getString("amount");
                                String number = jsonObject.getString("number");
                                String reason = jsonObject.getString("reason");
                                String catagory = jsonObject.getString("catagory");
                                String time = jsonObject.getString("time");
                                String status = jsonObject.getString("status");


                                HashMap<String, String> hashMap = new HashMap<>();
                                hashMap.put("id", id);
                                hashMap.put("uid", uid);
                                hashMap.put("method", method);
                                hashMap.put("tid", tid);
                                hashMap.put("amount", amount);
                                hashMap.put("number", number);
                                hashMap.put("reason", reason);
                                hashMap.put("catagory", catagory);
                                hashMap.put("time", time);
                                hashMap.put("status", status);


                                arrayList.add(hashMap);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        myAdapter.notifyDataSetChanged();

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {


                        Toast.makeText(withDraw.this,"No Internet Connection",Toast.LENGTH_SHORT).show();

                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(withDraw.this);
        requestQueue.add(jsonArrayRequest);
    }



    private class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder>{



        private class MyViewHolder extends RecyclerView.ViewHolder{


            TextView uid, amount,time,reason,catagory,method,number;

            CardView approve, cancel, call;




            public MyViewHolder(@NonNull View itemView) {
                super(itemView);




                uid = itemView.findViewById(R.id.uid);
                amount = itemView.findViewById(R.id.amount);
                method = itemView.findViewById(R.id.method);
                reason = itemView.findViewById(R.id.reason);
                catagory = itemView.findViewById(R.id.catagory);
                time = itemView.findViewById(R.id.time);
                number = itemView.findViewById(R.id.number);
                approve = itemView.findViewById(R.id.approve);
                cancel = itemView.findViewById(R.id.cancel);
                call = itemView.findViewById(R.id.call);




            }
        }



        @NonNull
        @Override
        public withDraw.MyAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            LayoutInflater layoutInflater = getLayoutInflater();
            View myView = layoutInflater.inflate(R.layout.withdraw, parent, false);








            return new MyViewHolder(myView);
        }

        @Override
        public void onBindViewHolder(@NonNull withDraw.MyAdapter.MyViewHolder holder, @SuppressLint("RecyclerView") int position) {




            HashMap<String,String> hashMap = arrayList.get(position);
            String idx = hashMap.get("id");
            String uidx = hashMap.get("uid");
            String tidx = hashMap.get("tid");
            String methodx = hashMap.get("method");
            String amountx = hashMap.get("amount");
            String numberx = hashMap.get("number");
            String catagoryx = hashMap.get("catagory");
            String reasonx = hashMap.get("reason");
            String statusx = hashMap.get("status");
            String timex = hashMap.get("time");



            holder.uid.setText(uidx);
            holder.amount.setText("Amount: "+amountx+"  ৳");
            holder.catagory.setText("Catagory:  "+catagoryx);
            holder.reason.setText("Reason:  "+reasonx);

            holder.method.setText("Method: "+methodx);
            holder.number.setText("Number: "+numberx);
            holder.time.setText("Time: "+timex);




            if (statusx.contains("success")){

                holder.cancel.setVisibility(View.INVISIBLE);
                holder.approve.setVisibility(View.INVISIBLE);
            };



            if (statusx.contains("cancel")){

                holder.cancel.setVisibility(View.INVISIBLE);
                holder.approve.setVisibility(View.INVISIBLE);
            };

            holder.call.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Format the number to remove any non-numeric characters
                    String formattedNumber = numberx.replaceAll("[^0-9]", "");

                    // Create an Intent with ACTION_DIAL and set the data to the formatted number
                    Intent intent = new Intent(Intent.ACTION_DIAL);
                    intent.setData(Uri.parse("tel:" + formattedNumber));

                    // Start the activity to initiate the call
                    startActivity(intent);
                }
            });



            holder.cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {



                    lottie.setVisibility(VISIBLE);


                    HashMap<String,String> hashMap = arrayList.get(position);
                    String idx = hashMap.get("id");


                    String url = "https://wikipediabangla.com/apps/offer/withdrawapprove.php?id="+idx+"&status=cancel";


                    StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            if (response.contains("Success")){


                                android.app.AlertDialog alertDialog = new android.app.AlertDialog.Builder(withDraw.this).create();
                                alertDialog.setTitle("Alert");
                                alertDialog.setMessage("Withdraw Cancelled.");
                                alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();




                                    }
                                });
                                alertDialog.show();
                                alertDialog.setCancelable(true);


                               ambulence();






                            } else {

                                Toast.makeText(withDraw.this,"Something went wrong!",Toast.LENGTH_SHORT).show();

                            };







                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                            lottie.setVisibility(View.GONE);

                        }
                    });


                    RequestQueue requestQueue = Volley.newRequestQueue(withDraw.this);
                    requestQueue.add(stringRequest);





                }
            });



            holder.approve.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    lottie.setVisibility(VISIBLE);


                    HashMap<String,String> hashMap = arrayList.get(position);
                    String idx = hashMap.get("id");


                    String url = "https://wikipediabangla.com/apps/offer/withdrawapprove.php?id="+idx+"&status=success";


                    StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            if (response.contains("Success")){


                                android.app.AlertDialog alertDialog = new android.app.AlertDialog.Builder(withDraw.this).create();
                                alertDialog.setTitle("Alert");
                                alertDialog.setMessage("Withdraw Approved successfully.");
                                alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();




                                    }
                                });
                                alertDialog.show();
                                alertDialog.setCancelable(true);


                                ambulence();






                            } else {

                                Toast.makeText(withDraw.this,"Something went wrong!",Toast.LENGTH_SHORT).show();

                            };







                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                            lottie.setVisibility(View.GONE);

                        }
                    });


                    RequestQueue requestQueue = Volley.newRequestQueue(withDraw.this);
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