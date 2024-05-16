package com.tamim.smartbholaadmin;

import static android.view.View.VISIBLE;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
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
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.textfield.TextInputEditText;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class ambulenceapprove extends Fragment {


    LottieAnimationView lottie;

    ListView listView;

    ArrayList<HashMap<String,String>> arrayList=new ArrayList<>();
    HashMap<String,String>hashMap;

    int MY_REQUEST_CODE = 1;


    Myadapter myadapter;

    ScrollView addlay;
    TabLayout tabLayout;

    TextView toptext,toptext2;


    private static long lastRequestQueueTime = 0;
    private static String lastResponse = ""; // To store the last response
    private static final long REQUEST_QUEUE_THRESHOLD = 3000; // 5 seconds in milliseconds



    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View myView = inflater.inflate(R.layout.fragment_ambulenceapprove, container, false);



        lottie = myView.findViewById(R.id.lottie);
        listView= myView.findViewById(R.id.listView);









        myadapter = new Myadapter();
        listView.setAdapter(myadapter);


        school();











        return myView;
    }



    private void school() {

        arrayList.clear();

        lottie.setVisibility(VISIBLE);



        String url = "https://wikipediabangla.com/apps/ambulence3.php";




        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                com.android.volley.Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        lottie.setVisibility(View.GONE);


                        for (int x = 0; x < response.length(); x++) {
                            try {
                                JSONObject jsonObject = response.getJSONObject(x);
                                String name = jsonObject.getString("name");
                                String id = jsonObject.getString("id");
                                String area = jsonObject.getString("area");
                                String number = jsonObject.getString("number");
                                String pname = jsonObject.getString("pname");
                                String images = jsonObject.getString("images");






                                HashMap<String, String> hashMap = new HashMap<>();
                                hashMap.put("name", name);
                                hashMap.put("area", area);
                                hashMap.put("pname", pname);
                                hashMap.put("id", id);
                                hashMap.put("number", number);
                                hashMap.put("images", images);

                                arrayList.add(hashMap);





                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        myadapter.notifyDataSetChanged();

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
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

        @SuppressLint("MissingInflatedId")
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            LayoutInflater layoutInflater = (LayoutInflater) getLayoutInflater().getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View viewx = layoutInflater.inflate(R.layout.ambulence, parent, false);


            TextView name1, adress1, mobile1, upozila1,details1;
            name1 = viewx.findViewById(R.id.name);
            adress1 = viewx.findViewById(R.id.adress);
            mobile1 = viewx.findViewById(R.id.mobile);
            upozila1 = viewx.findViewById(R.id.registration);
            details1 = viewx.findViewById(R.id.details);

            ImageView imageView1 = viewx.findViewById(R.id.imageView1);

            CardView approve, call, cancel;
            call = viewx.findViewById(R.id.card);
            cancel = viewx.findViewById(R.id.cancel);
            approve = viewx.findViewById(R.id.approve);






            HashMap<String,String> hashMap = arrayList.get(position);
            String namex = hashMap.get("name");
            String areax = hashMap.get("area");
            String numberx = hashMap.get("number");
            String images = hashMap.get("images");
            String id = hashMap.get("id");
            String pnamex = hashMap.get("pname");




            name1.setText(namex);
            mobile1.setText("মোবাইলঃ  "+numberx);
            adress1.setText("সার্ভিস এরিয়াঃ  "+areax);
            upozila1.setText("প্রোপাইটরঃ  "+pnamex);



            String urlx = "https://wikipediabangla.com/apps/Images/"+images;


            Picasso.get()
                    .load(urlx)
                    .placeholder(R.drawable.imageicon)
                    .error(R.drawable.imgichon)
                    .into(imageView1);




            call.setOnClickListener(new View.OnClickListener() {
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



            cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    lottie.setVisibility(VISIBLE);


                    HashMap<String,String> hashMap = arrayList.get(position);
                    String idx = hashMap.get("id");


                    String url = "https://wikipediabangla.com/apps/delete.php?id="+idx+"&table=ambulence";


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



            approve.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    lottie.setVisibility(VISIBLE);


                    HashMap<String,String> hashMap = arrayList.get(position);
                    String idx = hashMap.get("id");


                    String url = "https://wikipediabangla.com/apps/ambulenceapprove.php?id="+idx+"&status=1";


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










            return viewx;
        }
    }


}