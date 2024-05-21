package com.tamim.smartbholaadmin;

import static android.content.Context.MODE_PRIVATE;
import static android.view.View.VISIBLE;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
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

public class tutionpost extends Fragment implements SearchView.OnQueryTextListener {

    LottieAnimationView lottie;
    RecyclerView recyclerView;

    MyAdapter myAdapter;
    ArrayList<HashMap<String, String>> arrayList = new ArrayList<>();
    ArrayList<HashMap<String, String>> originalList = new ArrayList<>(); // Store original data

    private static final String PREF_NAME = "MyPrefs";


    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View myView = inflater.inflate(R.layout.fragment_tutionpost2, container, false);

        lottie = myView.findViewById(R.id.lottie);
        recyclerView = myView.findViewById(R.id.recyclerView);


        myAdapter = new MyAdapter();
        recyclerView.setAdapter(myAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));



        ambulence();

        return myView;
    }

    private void ambulence() {
        arrayList.clear();
        lottie.setVisibility(View.VISIBLE);

        String url = "https://wikipediabangla.com/apps/tutionpost3.php";

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
                                String adress = jsonObject.getString("adress");
                                String mobile = jsonObject.getString("mobile");
                                String images = jsonObject.getString("images");
                                String id = jsonObject.getString("id");
                                String uid = jsonObject.getString("uid");
                                String title = jsonObject.getString("title");
                                String subject = jsonObject.getString("subject");
                                String gender = jsonObject.getString("gender");
                                String time = jsonObject.getString("time");
                                String day = jsonObject.getString("day");
                                String totaltimex = jsonObject.getString("totaltimex");
                                String classs = jsonObject.getString("classs");
                                String salary = jsonObject.getString("salary");
                                String posttime = jsonObject.getString("posttime");



                                HashMap<String, String> hashMap = new HashMap<>();
                                hashMap.put("name", name);
                                hashMap.put("mobile", mobile);
                                hashMap.put("images", images);
                                hashMap.put("adress", adress);
                                hashMap.put("id", id);
                                hashMap.put("uid", uid);
                                hashMap.put("title", title);
                                hashMap.put("gender", gender);
                                hashMap.put("subject", subject);
                                hashMap.put("time", time);
                                hashMap.put("day", day);
                                hashMap.put("totaltime", totaltimex);
                                hashMap.put("classs", classs);
                                hashMap.put("salary", salary);
                                hashMap.put("posttime", posttime);

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
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.tutionpost, parent, false);
            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
            HashMap<String, String> hashMap = arrayList.get(position);

            String namex = hashMap.get("name");
            String adress = hashMap.get("adress");
            String numberx = hashMap.get("mobile");
            String images = hashMap.get("images");
            String id = hashMap.get("id");
            String uid = hashMap.get("uid");
            String title = hashMap.get("title");
            String gender = hashMap.get("gender");
            String subject = hashMap.get("subject");
            String time = hashMap.get("time");
            String totaltimex = hashMap.get("totaltime");
            String classs = hashMap.get("classs");
            String salary = hashMap.get("salary");
            String day = hashMap.get("day");
            String posttime = hashMap.get("posttime");



            holder.name.setText(namex);
            holder.time1.setText(posttime);
            holder.title.setText(title);
            holder.subject.setText(subject);
            holder.time.setText(time+", "+totaltimex);
            holder.salary.setText(salary+" ৳ (মাসিক)");
            holder.tid.setText("Tution: #"+id);




            holder.profilepic.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    uidData.UID=uid;
                    startActivity(new Intent(getContext(), uidData.class));
                }
            });

            holder.card.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {





                    Dialog dialog;
                    dialog = new Dialog(requireContext());
                    dialog.setContentView(R.layout.tutionpost3);
                    dialog.getWindow().setBackgroundDrawable(getResources().getDrawable(R.drawable.alertbackground));
                    dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    dialog.setCancelable(true);
                    dialog.show();

                    TextView name1, time1, title1, subject1, time2, day1, gender1, salary1, adress1,classs1;
                    ImageView profilepic1, cross;

                    CardView call, delete;

                    name1 = dialog.findViewById(R.id.name);
                    gender1 = dialog.findViewById(R.id.gender);
                    subject1 = dialog.findViewById(R.id.subject);
                    time1 = dialog.findViewById(R.id.time1);
                    time2 = dialog.findViewById(R.id.time);
                    day1 = dialog.findViewById(R.id.day);
                    salary1 = dialog.findViewById(R.id.salary);
                    title1 = dialog.findViewById(R.id.title);
                    classs1 = dialog.findViewById(R.id.classs);
                    profilepic1 = dialog.findViewById(R.id.profilepic);
                    call = dialog.findViewById(R.id.call);
                    cross = dialog.findViewById(R.id.cross);
                    delete = dialog.findViewById(R.id.delete);



                    name1.setText(namex);
                    gender1.setText(gender);
                    subject1.setText(subject);
                    time1.setText(posttime);
                    time2.setText(time+", "+totaltimex);
                    day1.setText(day);
                    salary1.setText(salary+" ৳ (মাসিক)");
                    title1.setText(title);
                    classs1.setText(classs);




                    String urlx = "https://wikipediabangla.com/apps/Images/"+images;


                    Picasso.get()
                            .load(urlx)
                            .placeholder(R.drawable.profile)
                            .error(R.drawable.profile)
                            .into(profilepic1);







                    delete.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {


                            dialog.cancel();

                            lottie.setVisibility(VISIBLE);


                            HashMap<String,String> hashMap = arrayList.get(position);
                            String idx = hashMap.get("id");


                            String url = "https://wikipediabangla.com/apps/delete.php?id="+idx+"&table=tsaccount";


                            StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {

                                    lottie.setVisibility(View.GONE);

                                    ambulence();

                                    if (response.contains("deleted")){





                                        String url1 = "https://wikipediabangla.com/apps/firebase/fcm3.php?body="+"Your tution post was deleted by Smart Bhola Authority because of false information."+"&title="+"Hello "+namex+"&id="+uid;
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










                    call.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            dialog.cancel();

                            // Format the number to remove any non-numeric characters
                            String formattedNumber = numberx.replaceAll("[^0-9]", "");

                            // Create an Intent with ACTION_DIAL and set the data to the formatted number
                            Intent intent = new Intent(Intent.ACTION_DIAL);
                            intent.setData(Uri.parse("tel:" + formattedNumber));

                            // Start the activity to initiate the call
                            startActivity(intent);



                        }
                    });





                    cross.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.cancel();
                        }
                    });


                }
            });







            String urlx = "https://wikipediabangla.com/apps/Images/"+images;


            Picasso.get()
                    .load(urlx)
                    .placeholder(R.drawable.profile)
                    .error(R.drawable.profile)
                    .into(holder.profilepic);






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
                    if (item.get("subject").toLowerCase().contains(text.toLowerCase())) {
                        arrayList.add(item);
                    }
                }
            }
            notifyDataSetChanged();
        }

        private class MyViewHolder extends RecyclerView.ViewHolder {
            TextView name, time1, subject, title, time, salary,tid;
            ImageView profilepic;


            CardView card;
            public MyViewHolder(@NonNull View itemView) {
                super(itemView);
                name = itemView.findViewById(R.id.name);
                time1 = itemView.findViewById(R.id.time1);
                subject = itemView.findViewById(R.id.subject);
                title = itemView.findViewById(R.id.title);
                time = itemView.findViewById(R.id.time);
                salary = itemView.findViewById(R.id.salary);
                profilepic = itemView.findViewById(R.id.profilepic);
                card = itemView.findViewById(R.id.card);
                tid = itemView.findViewById(R.id.tid);

            }
        }
    }
}
