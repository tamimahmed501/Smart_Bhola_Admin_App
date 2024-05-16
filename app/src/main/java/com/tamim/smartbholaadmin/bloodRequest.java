package com.tamim.smartbholaadmin;

import static android.content.Context.MODE_PRIVATE;
import static android.view.View.VISIBLE;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
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
import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.textfield.TextInputEditText;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.zip.ZipInputStream;

public class bloodRequest extends Fragment {

    LottieAnimationView lottie;
    private static final String PREF_NAME = "MyPrefs";

    ArrayList<HashMap<String, String>> arrayList = new ArrayList<>();
    HashMap<String,String> hashMap;




    MyAdapter myAdapter;





    RecyclerView recyclerView;



    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View myView =inflater.inflate(R.layout.fragment_blood_request, container, false);

        lottie=myView.findViewById(R.id.lottie);

        recyclerView = myView.findViewById(R.id.recyclerView);

        myAdapter = new MyAdapter();
        recyclerView.setAdapter(myAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

















        ////////////////////////////////////////////////////////////////////////////////////////////////













        ambulence();



        return myView;
    }

    private void ambulence() {


        arrayList.clear();
        lottie.setVisibility(VISIBLE);

        String url = "https://wikipediabangla.com/apps/bloodrequest3.php?name=";

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
                                String patientname = jsonObject.getString("patientname");
                                String mobile = jsonObject.getString("mobile");
                                String bloodgroup = jsonObject.getString("bloodgroup");
                                String problem = jsonObject.getString("problem");
                                String id = jsonObject.getString("id");
                                String quantity = jsonObject.getString("quantity");
                                String date = jsonObject.getString("date");
                                String gender = jsonObject.getString("gender");
                                String upazila = jsonObject.getString("upazila");
                                String hospital = jsonObject.getString("hospital");
                                String status = jsonObject.getString("status");

                                HashMap<String, String> hashMap = new HashMap<>();
                                hashMap.put("name", name);
                                hashMap.put("mobile", mobile);
                                hashMap.put("patientname", patientname);
                                hashMap.put("bloodgroup", bloodgroup);
                                hashMap.put("problem", problem);
                                hashMap.put("id", id);
                                hashMap.put("quantity", quantity);
                                hashMap.put("date", date);
                                hashMap.put("gender", gender);
                                hashMap.put("hospital", hospital);
                                hashMap.put("upazila", upazila);
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


                        Toast.makeText(getContext(),"No Internet Connection",Toast.LENGTH_SHORT).show();

                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(jsonArrayRequest);
    }



    private class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder>{



        private class MyViewHolder extends RecyclerView.ViewHolder{


            TextView name1, patientname1,bloodgroup1,mobile1,problem1,date1,quantity1,gender1,upazila1,hospital1;

            CardView approve, delete, call;







            public MyViewHolder(@NonNull View itemView) {
                super(itemView);




                name1 = itemView.findViewById(R.id.name);
                patientname1 = itemView.findViewById(R.id.patientname);
                bloodgroup1 = itemView.findViewById(R.id.group);
                problem1 = itemView.findViewById(R.id.problem);
                date1 = itemView.findViewById(R.id.date);
                mobile1 = itemView.findViewById(R.id.mobile);
                quantity1 = itemView.findViewById(R.id.quantity);
                gender1 = itemView.findViewById(R.id.gender);
                hospital1 = itemView.findViewById(R.id.hospital);
                upazila1 = itemView.findViewById(R.id.upazila);

                approve = itemView.findViewById(R.id.approve);
                delete = itemView.findViewById(R.id.cancel);
                call = itemView.findViewById(R.id.call);


            }
        }



        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            LayoutInflater layoutInflater = getLayoutInflater();
            View myView = layoutInflater.inflate(R.layout.bloodrequest, parent, false);








            return new MyViewHolder(myView);
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {




            HashMap<String,String> hashMap = arrayList.get(position);
            String namex = hashMap.get("name");
            String problemx = hashMap.get("problem");
            String numberx = hashMap.get("mobile");
            String bloodgroupx = hashMap.get("bloodgroup");
            String patientname = hashMap.get("patientname");
            String quantity = hashMap.get("quantity");
            String id = hashMap.get("id");
            String hospital = hashMap.get("hospital");
            String date = hashMap.get("date");
            String genderx = hashMap.get("gender");
            String upazilax = hashMap.get("upazila");
            String status = hashMap.get("status");


            holder.name1.setText("Posted BY: "+namex);
            holder.patientname1.setText("রোগীর নামঃ  "+patientname);
            holder.date1.setText("তারিখঃ  "+date);
            holder.bloodgroup1.setText("রক্তের গ্রুপঃ  "+bloodgroupx);
            holder.problem1.setText("সমস্যাঃ  "+problemx);
            holder.quantity1.setText("পরিমাণঃ  "+quantity+" ব্যাগ");
            holder.upazila1.setText("উপজেলাঃ  "+upazilax);
            holder.hospital1.setText("হসপিটালঃ  "+hospital);



            if (status.contains("approve")){

                holder.approve.setClickable(false);
                holder.approve.setVisibility(View.INVISIBLE);

            } else {}





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



            holder.delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    lottie.setVisibility(VISIBLE);




                    String url = "https://wikipediabangla.com/apps/delete.php?id="+id+"&table=bloodrequest";


                    StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            if (response.contains("deleted")){


                                android.app.AlertDialog alertDialog = new android.app.AlertDialog.Builder(getContext()).create();
                                alertDialog.setTitle("Alert");
                                alertDialog.setMessage("Data deleted successfully.");
                                alertDialog.setButton(android.app.AlertDialog.BUTTON_NEUTRAL, "OK", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();




                                    }
                                });
                                alertDialog.show();
                                alertDialog.setCancelable(true);


                              ambulence();





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


                    String url = "https://wikipediabangla.com/apps/bloodapprove.php?id="+idx+"&status=approved";


                    StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            if (response.contains("Success")){


                                android.app.AlertDialog alertDialog = new android.app.AlertDialog.Builder(getContext()).create();
                                alertDialog.setTitle("Alert");
                                alertDialog.setMessage("Ambulence Approved successfully.");
                                alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();




                                    }
                                });
                                alertDialog.show();
                                alertDialog.setCancelable(true);


                                ambulence();






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
