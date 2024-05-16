package com.tamim.smartbholaadmin;
import static android.view.View.VISIBLE;

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
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

public class user extends Fragment implements SearchView.OnQueryTextListener {

    LottieAnimationView lottie;
    RecyclerView recyclerView;
    SearchView searchView;
    MyAdapter myAdapter;
    ArrayList<HashMap<String, String>> arrayList = new ArrayList<>();
    ArrayList<HashMap<String, String>> originalList = new ArrayList<>(); // Store original data
    ImageView repeat;
    String UID = "1";

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View myView = inflater.inflate(R.layout.fragment_user, container, false);

        lottie = myView.findViewById(R.id.lottie);
        recyclerView = myView.findViewById(R.id.recyclerView);
        searchView = myView.findViewById(R.id.searchView);
        repeat = myView.findViewById(R.id.repeat);
        myAdapter = new MyAdapter();
        recyclerView.setAdapter(myAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Set the query text listener to the SearchView
        searchView.setOnQueryTextListener(this);

        ambulence();


        repeat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (UID.contains("0")){

                    UID = "1";
                    searchView.setQueryHint("User Name");
                } else if (UID.contains("1")){

                    UID = "0";
                    searchView.setQueryHint("User ID");

                }

            }
        });

        return myView;
    }

    private void ambulence() {


        arrayList.clear();
        lottie.setVisibility(View.VISIBLE);

        String url = "https://wikipediabangla.com/apps/user.php";

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        lottie.setVisibility(View.GONE);

                        for (int x = 0; x < response.length(); x++) {
                            try {
                                JSONObject jsonObject = response.getJSONObject(x);
                                String name = jsonObject.getString("name");
                                String adress = jsonObject.getString("upazila");
                                String mobile = jsonObject.getString("mobile");
                                String images = jsonObject.getString("images");
                                String id = jsonObject.getString("id");
                                String mybalance = jsonObject.getString("mybalance");

                                HashMap<String, String> hashMap = new HashMap<>();
                                hashMap.put("name", name);
                                hashMap.put("mobile", mobile);
                                hashMap.put("adress", adress);
                                hashMap.put("images", images);
                                hashMap.put("id", id);
                                hashMap.put("mybalance", mybalance);

                                arrayList.add(hashMap);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        originalList.addAll(arrayList); // Store original data
                        myAdapter.notifyDataSetChanged();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getContext(), "No Internet Connection", Toast.LENGTH_SHORT).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(jsonArrayRequest);
    }

    // Implement the onQueryTextSubmit method
    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    // Implement the onQueryTextChange method
    @Override
    public boolean onQueryTextChange(String newText) {
        myAdapter.filter(newText); // Call the filter method in your adapter
        return true;
    }

    private class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user, parent, false);
            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
            HashMap<String, String> hashMap = arrayList.get(position);

            String namex = hashMap.get("name");
            String adressx = hashMap.get("adress");
            String numberx = hashMap.get("mobile");
            String images = hashMap.get("images");
            String id = hashMap.get("id");
            String mybalance = hashMap.get("mybalance");

            holder.name.setText(namex);
            holder.mobile.setText("Number:  "+numberx);





            holder.id.setText("Id: "+id);

            if (adressx.length()<2){

                holder.adress.setText("Upazila: Not Updated");

            } else {


                holder.adress.setText("Upazila: "+adressx);

            }



            String urlx = "https://wikipediabangla.com/apps/Images/"+images;


            Picasso.get()
                    .load(urlx)
                    .placeholder(R.drawable.profile)
                    .error(R.drawable.profile)
                    .into(holder.profilepic);



            holder.card.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {






                    Dialog dialog;
                    dialog = new Dialog(requireContext());
                    dialog.setContentView(R.layout.userlay);
                    dialog.getWindow().setBackgroundDrawable(getResources().getDrawable(R.drawable.alertbackground));
                    dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    dialog.setCancelable(true);
                    dialog.show();



                    TextView name, balance, mobile, adress;
                    CardView call, delete, adjust;
                    ImageView profile, cross;



                    name = dialog.findViewById(R.id.name);
                    balance = dialog.findViewById(R.id.balance);
                    mobile = dialog.findViewById(R.id.mobile);
                    adress = dialog.findViewById(R.id.adress);
                    call = dialog.findViewById(R.id.call);
                    delete = dialog.findViewById(R.id.delete);
                    profile = dialog.findViewById(R.id.imageView1);
                    cross = dialog.findViewById(R.id.cross);
                    adjust = dialog.findViewById(R.id.adjust);



                    name.setText(namex);
                    balance.setText("Account Balance: "+mybalance+" ৳");
                    mobile.setText("Mobile: "+numberx);

                    if (adressx.length()<2){

                        adress.setText("Address: Not Updated");

                    } else {


                       adress.setText("Address: "+adressx);

                    }



                    String urlx = "https://wikipediabangla.com/apps/Images/"+images;


                    Picasso.get()
                            .load(urlx)
                            .placeholder(R.drawable.profile)
                            .error(R.drawable.profile)
                            .into(profile);




                    cross.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            dialog.cancel();


                        }
                    });






                    call.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            dialog.cancel();


                            String formattedNumber = numberx.replaceAll("[^0-9]", "");

                            // Create an Intent with ACTION_DIAL and set the data to the formatted number
                            Intent intent = new Intent(Intent.ACTION_DIAL);
                            intent.setData(Uri.parse("tel:" + formattedNumber));

                            // Start the activity to initiate the call
                            startActivity(intent);





                        }
                    });





                    adjust.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.cancel();

                            adjustbalance3.UID= id;

                            startActivity(new Intent(getContext(), adjustbalance3.class));





                        }
                    });



                    delete.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {


                            dialog.cancel();

                            lottie.setVisibility(VISIBLE);


                            HashMap<String,String> hashMap = arrayList.get(position);
                            String idx = hashMap.get("id");


                            String url = "https://wikipediabangla.com/apps/delete.php?id="+idx+"&table=account";


                            StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {

                                    lottie.setVisibility(View.GONE);

                                    ambulence();

                                    if (response.contains("deleted")){


                                        android.app.AlertDialog alertDialog = new android.app.AlertDialog.Builder(getContext()).create();
                                        alertDialog.setTitle("Alert");
                                        alertDialog.setMessage("Account deleted successfully.");
                                        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.dismiss();




                                            }
                                        });
                                        alertDialog.show();
                                        alertDialog.setCancelable(true);







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
            });



        }

        @Override
        public int getItemCount() {
            return arrayList.size();
        }

        // Filter method to filter data based on user's input
        public void filter(String text) {
            arrayList.clear();
            if (text.isEmpty()) { // If the search query is empty, show all data
                arrayList.addAll(originalList); // Restore original data
            } else {
                for (HashMap<String, String> item : originalList) {

                    if (UID.contains("1")){

                        if (item.get("name").toLowerCase().contains(text.toLowerCase())) {
                            arrayList.add(item);


                        }


                    } else if (UID.contains("0")){

                        if (item.get("id").toLowerCase().contains(text.toLowerCase())) {
                            arrayList.add(item);


                        }


                    }

                }
            }
            notifyDataSetChanged();
        }

        private class MyViewHolder extends RecyclerView.ViewHolder {
            TextView name, mobile, adress, id;
            ImageView profilepic;
            CardView card;


            public MyViewHolder(@NonNull View itemView) {
                super(itemView);
                name = itemView.findViewById(R.id.name);
                mobile = itemView.findViewById(R.id.mobile);
                adress = itemView.findViewById(R.id.adress);
                id = itemView.findViewById(R.id.id);
                profilepic = itemView.findViewById(R.id.profilepic);
                card = itemView.findViewById(R.id.card);




            }
        }
    }
}
