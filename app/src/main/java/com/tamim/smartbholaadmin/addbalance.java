package com.tamim.smartbholaadmin;

import static android.view.View.VISIBLE;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
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
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class addbalance extends AppCompatActivity {
    LottieAnimationView lottie;
    private static final String PREF_NAME = "MyPrefs";

    ArrayList<HashMap<String, String>> arrayList = new ArrayList<>();
    HashMap<String,String> hashMap;


    ImageView back;


    MyAdapter myAdapter;





    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addbalance);

        lottie= findViewById(R.id.lottie);

        recyclerView =  findViewById(R.id.recyclerView);
        back = findViewById(R.id.backbutton);
        myAdapter = new MyAdapter();
        recyclerView.setAdapter(myAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(addbalance.this));


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onBackPressed();

            }
        });


        ambulence();


    }

    private void ambulence() {


        arrayList.clear();
        lottie.setVisibility(VISIBLE);

        String url = "https://wikipediabangla.com/apps/offer/addbalancelist.php";

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


                        Toast.makeText(addbalance.this,"No Internet Connection",Toast.LENGTH_SHORT).show();

                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(addbalance.this);
        requestQueue.add(jsonArrayRequest);
    }



    private class MyAdapter extends RecyclerView.Adapter<addbalance.MyAdapter.MyViewHolder>{



        private class MyViewHolder extends RecyclerView.ViewHolder{


            TextView uid, amount,time,tid,catagory,method,number;


            CardView card;



            public MyViewHolder(@NonNull View itemView) {
                super(itemView);




                uid = itemView.findViewById(R.id.uid);
                amount = itemView.findViewById(R.id.amount);
                method = itemView.findViewById(R.id.method);
                tid = itemView.findViewById(R.id.tid);
                catagory = itemView.findViewById(R.id.catagory);
                time = itemView.findViewById(R.id.time);
                number = itemView.findViewById(R.id.number);
                card = itemView.findViewById(R.id.card);



            }
        }



        @NonNull
        @Override
        public addbalance.MyAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            LayoutInflater layoutInflater = getLayoutInflater();
            View myView = layoutInflater.inflate(R.layout.addbalancelist, parent, false);








            return new MyViewHolder(myView);
        }

        @Override
        public void onBindViewHolder(@NonNull addbalance.MyAdapter.MyViewHolder holder, int position) {




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
            holder.tid.setText("Tid:  "+tidx);

            holder.method.setText("Method: "+methodx);
            holder.number.setText("Number: "+numberx);
            holder.time.setText("Time: "+timex);


            holder.card.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    uidData.UID=uidx;
                    startActivity(new Intent(addbalance.this, uidData.class));
                }
            });



        }

        @Override
        public int getItemCount() {
            return arrayList.size();
        }




    }
}