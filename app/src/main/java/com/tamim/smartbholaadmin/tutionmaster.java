package com.tamim.smartbholaadmin;

import static android.view.View.VISIBLE;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
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

public class tutionmaster extends Fragment implements SearchView.OnQueryTextListener {

    LottieAnimationView lottie;
    RecyclerView recyclerView;

    MyAdapter myAdapter;
    ArrayList<HashMap<String, String>> arrayList = new ArrayList<>();
    ArrayList<HashMap<String, String>> originalList = new ArrayList<>(); // Store original data

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View myView = inflater.inflate(R.layout.fragment_tutionmaster, container, false);

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

        String url = "https://wikipediabangla.com/apps/tutionapprove.php";

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
                                String upazila = jsonObject.getString("upazila");
                                String mobile = jsonObject.getString("mobile");
                                String images = jsonObject.getString("images");
                                String id = jsonObject.getString("id");
                                String uid = jsonObject.getString("uid");
                                String unions = jsonObject.getString("unions");
                                String gender = jsonObject.getString("gender");
                                String ssc = jsonObject.getString("ssc");
                                String hsc = jsonObject.getString("hsc");
                                String sscgpa = jsonObject.getString("sscgpa");
                                String hscgpa = jsonObject.getString("hscgpa");
                                String skill = jsonObject.getString("skill");
                                String subject = jsonObject.getString("subject");
                                String mygroup = jsonObject.getString("mygroup");

                                HashMap<String, String> hashMap = new HashMap<>();
                                hashMap.put("name", name);
                                hashMap.put("mobile", mobile);
                                hashMap.put("upazila", upazila);
                                hashMap.put("images", images);
                                hashMap.put("id", id);
                                hashMap.put("uid", uid);
                                hashMap.put("unions", unions);
                                hashMap.put("gender", gender);
                                hashMap.put("ssc", ssc);
                                hashMap.put("hsc", hsc);
                                hashMap.put("sscgpa", sscgpa);
                                hashMap.put("hscgpa", hscgpa);
                                hashMap.put("skill", skill);
                                hashMap.put("subject", subject);
                                hashMap.put("mygroup", mygroup);
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
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.tutionmaster, parent, false);
            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
            HashMap<String, String> hashMap = arrayList.get(position);

            String namex = hashMap.get("name");
            String upazila = hashMap.get("upazila");
            String numberx = hashMap.get("mobile");
            String images = hashMap.get("images");
            String id = hashMap.get("id");
            String uid = hashMap.get("uid");
            String unions = hashMap.get("unions");
            String gender = hashMap.get("gender");
            String ssc = hashMap.get("ssc");
            String hsc = hashMap.get("hsc");
            String sscgpa = hashMap.get("sscgpa");
            String hscgpa = hashMap.get("hscgpa");
            String skill = hashMap.get("skill");
            String subject = hashMap.get("subject");
            String mygroup = hashMap.get("mygroup");


            holder.name.setText(namex);
            holder.gender.setText(gender);
            holder.group.setText(mygroup);
            holder.subject.setText(subject);
            holder.ssc.setText(ssc);
            holder.hsc.setText(hsc);
            holder.sscgpa.setText(sscgpa);
            holder.hscgpa.setText(hscgpa);
            holder.adress.setText(unions+", "+upazila);
            holder.skill.setText(skill);





            String urlx = "https://wikipediabangla.com/apps/Images/"+images;


            Picasso.get()
                    .load(urlx)
                    .placeholder(R.drawable.profile)
                    .error(R.drawable.profile)
                    .into(holder.profilepic);




            holder.delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {



                    lottie.setVisibility(VISIBLE);


                    HashMap<String,String> hashMap = arrayList.get(position);
                    String idx = hashMap.get("id");


                    String url = "https://wikipediabangla.com/apps/delete.php?id="+idx+"&table=ttaccountidd";


                    StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            lottie.setVisibility(View.GONE);

                            ambulence();

                            if (response.contains("deleted")){





                                String url1 = "https://wikipediabangla.com/apps/firebase/fcm3.php?body="+"Your tution profile was deleted by Smart Bhola Authority because of false information."+"&title="+"Hello "+namex+"&id="+uid;
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




            holder.approve.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {



                    lottie.setVisibility(VISIBLE);


                    HashMap<String,String> hashMap = arrayList.get(position);
                    String idx = hashMap.get("id");


                    String url = "https://wikipediabangla.com/apps/tutionapprove2.php?id="+idx+"&status=2";


                    StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            if (response.contains("Success")){












                                String url1 = "https://wikipediabangla.com/apps/firebase/fcm3.php?body="+"Your tution profile was approved by Smart Bhola Authority."+"&title="+"Congratulations "+namex+"&id="+uid;
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
                                alertDialog.setMessage("Master Approved successfully.");
                                alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();




                                    }
                                });
                                alertDialog.show();
                                alertDialog.setCancelable(true);


                                ambulence();






                            } else {

                                lottie.setVisibility(View.GONE);

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


            holder.call.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {





                    // Format the number to remove any non-numeric characters
                    String formattedNumber = numberx.replaceAll("[^0-9]", "");

                    // Create an Intent with ACTION_DIAL and set the data to the formatted number
                    Intent intent = new Intent(Intent.ACTION_DIAL);
                    intent.setData(Uri.parse("tel:" + formattedNumber));

                    // Start the activity to initiate the call
                    startActivity(intent);




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
                    if (item.get("subject").toLowerCase().contains(text.toLowerCase())) {
                        arrayList.add(item);
                    }
                }
            }
            notifyDataSetChanged();
        }




        private class MyViewHolder extends RecyclerView.ViewHolder {
            TextView name, gender, group, subject, ssc, hsc, sscgpa, hscgpa,adress,skill;
            ImageView profilepic;

            CardView card, call, delete, approve;
            public MyViewHolder(@NonNull View itemView) {
                super(itemView);
                name = itemView.findViewById(R.id.name);
                gender = itemView.findViewById(R.id.gender);
                subject = itemView.findViewById(R.id.subject);
                group = itemView.findViewById(R.id.group);
                ssc = itemView.findViewById(R.id.ssc);
                hsc = itemView.findViewById(R.id.hsc);
                hscgpa = itemView.findViewById(R.id.hscgpa);
                sscgpa = itemView.findViewById(R.id.sscgpa);
                profilepic = itemView.findViewById(R.id.profilepic);
                card = itemView.findViewById(R.id.card);
                adress = itemView.findViewById(R.id.adress);
                skill = itemView.findViewById(R.id.skill);
                delete = itemView.findViewById(R.id.delete);
                call = itemView.findViewById(R.id.call);
                approve = itemView.findViewById(R.id.approve);

            }
        }
    }
}
