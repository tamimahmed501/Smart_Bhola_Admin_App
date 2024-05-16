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
import com.android.volley.Request;
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


public class diagnosticlist extends Fragment {

    RecyclerView recyclerView;
    LottieAnimationView lottie;



    MyAdapter myAdapter;

    ArrayList<HashMap<String, String>> arrayList = new ArrayList<>();

    HashMap<String,String> hashMap;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View myView = inflater.inflate(R.layout.fragment_diagnosticlist, container, false);



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

        String url = "https://wikipediabangla.com/apps/diagnostic2.php";

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
                            String open = jsonObject.getString("open");
                            String images = jsonObject.getString("images");

                            HashMap<String, String> hashMap = new HashMap<>();
                            hashMap.put("id", id);
                            hashMap.put("name", name);
                            hashMap.put("mobile", mobile);
                            hashMap.put("adress", address);
                            hashMap.put("open", open);
                            hashMap.put("images", images);
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


            TextView name1, adress1,mobile1;

            CardView card, delete;

            ImageView imageView1;


            public MyViewHolder(@NonNull View itemView) {
                super(itemView);




                name1 = itemView.findViewById(R.id.name);
                adress1 = itemView.findViewById(R.id.adress);
                mobile1 = itemView.findViewById(R.id.mobile);
                card = itemView.findViewById(R.id.card);
                imageView1 = itemView.findViewById(R.id.imageView1);
                delete = itemView.findViewById(R.id.delete);



            }
        }



        @NonNull
        @Override
        public MyAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            LayoutInflater layoutInflater = getLayoutInflater();
            View myView = layoutInflater.inflate(R.layout.diagnostic3, parent, false);








            return new MyAdapter.MyViewHolder(myView);
        }

        @Override
        public void onBindViewHolder(@NonNull MyAdapter.MyViewHolder holder, int position) {


            HashMap<String, String> hashMap = arrayList.get(position);
            String namex = hashMap.get("name");
            String adressx = hashMap.get("adress");
            String images = hashMap.get("images");
            String openx = hashMap.get("open");
            String idx = hashMap.get("id");
            String mobile = hashMap.get("mobile");


            holder.name1.setText(namex);
            holder.adress1.setText(adressx);
            holder.mobile1.setText("Mobile: "+mobile);




            String urlx = "https://wikipediabangla.com/apps/Images/"+images;

            Picasso.get()
                    .load(urlx)
                    .placeholder(R.drawable.imageicon)
                    .error(R.drawable.imageicon)
                    .into(holder.imageView1);





            holder.delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    lottie.setVisibility(VISIBLE);



                    String url = "https://wikipediabangla.com/apps/delete.php?id="+idx+"&table=diagnosticac";


                    StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            lottie.setVisibility(View.GONE);

                            if (response.contains("deleted")){


                                android.app.AlertDialog alertDialog = new android.app.AlertDialog.Builder(getContext()).create();
                                alertDialog.setTitle("Alert");
                                alertDialog.setMessage("Data deleted successfully.");
                                alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();





                                    }
                                });
                                alertDialog.show();
                                alertDialog.setCancelable(true);


                                school();




                            } else {

                                Toast.makeText(getContext(),"Something went wrong!",Toast.LENGTH_SHORT).show();

                            };







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