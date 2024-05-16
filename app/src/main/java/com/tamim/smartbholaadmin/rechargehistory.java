package com.tamim.smartbholaadmin;

import static android.content.Context.MODE_PRIVATE;
import static android.view.View.VISIBLE;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipboardManager;
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
import android.widget.LinearLayout;
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


public class rechargehistory extends Fragment {



    ListView listView;
    LottieAnimationView lottie;

    private static final String PREF_NAME = "MyPrefs";

    Myadapter myadapter;

    ImageView backbutton;

    ArrayList<HashMap<String, String>> arrayList = new ArrayList<>();

    HashMap<String,String> hashMap;





    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View myView = inflater.inflate(R.layout.fragment_rechargehistory, container, false);



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





        String url = "https://wikipediabangla.com/apps/offer/rechargesuccess.php";

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET, url, null,
                response -> {


                    lottie.setVisibility(View.GONE);



                    for (int x = 0; x < response.length(); x++) {
                        try {
                            JSONObject jsonObject = response.getJSONObject(x);
                            String id = jsonObject.getString("id");
                            String uid = jsonObject.getString("uid");
                            String name = jsonObject.getString("name");
                            String amount = jsonObject.getString("amount");
                            String time = jsonObject.getString("time");
                            String pastbalance = jsonObject.getString("pastbalance");
                            String status = jsonObject.getString("status");
                            String presentbalance = jsonObject.getString("presentbalance");
                            String number = jsonObject.getString("number");
                            String sim = jsonObject.getString("sim");
                            String type = jsonObject.getString("type");
                            String admin = jsonObject.getString("admin");



                            HashMap<String, String> hashMap = new HashMap<>();
                            hashMap.put("id", id);
                            hashMap.put("uid", uid);
                            hashMap.put("name", name);
                            hashMap.put("amount", amount);
                            hashMap.put("time", time);
                            hashMap.put("pastbalance", pastbalance);
                            hashMap.put("presentbalance", presentbalance);
                            hashMap.put("number", number);
                            hashMap.put("status", status);
                            hashMap.put("sim", sim);
                            hashMap.put("type", type);
                            hashMap.put("admin", admin);


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
                viewx = layoutInflater.inflate(R.layout.rechargehistory, parent, false);
            }


            TextView id1,name1,time1,status1,operator1,pastb,presentb,number1,type1,amount1,admin;

            ImageView images;



            name1 = viewx.findViewById(R.id.name);
            id1 = viewx.findViewById(R.id.id);
            time1 = viewx.findViewById(R.id.time);
            status1 = viewx.findViewById(R.id.status);
            operator1 = viewx.findViewById(R.id.operator);
            pastb = viewx.findViewById(R.id.pastbalance);
            presentb = viewx.findViewById(R.id.presentbalance);
            number1 = viewx.findViewById(R.id.number);
            type1 = viewx.findViewById(R.id.type);
            amount1 = viewx.findViewById(R.id.amount);
            images = viewx.findViewById(R.id.image);

            admin = viewx.findViewById(R.id.admin);

            HashMap<String,String> hashMap = arrayList.get(position);
            String id = hashMap.get("id");
            String uid = hashMap.get("uid");
            String name = hashMap.get("name");
            String amount = hashMap.get("amount");
            String status = hashMap.get("status");
            String pastbalance = hashMap.get("pastbalance");
            String presentbalance = hashMap.get("presentbalance");
            String time = hashMap.get("time");
            String sim = hashMap.get("sim");
            String number = hashMap.get("number");
            String type = hashMap.get("type");
            String adminx = hashMap.get("admin");




            id1.setText(id);
            name1.setText("Name:  "+name);
            time1.setText("Time: "+time);
            status1.setText("Status: "+status);
            amount1.setText(amount+" ৳");
            pastb.setText(pastbalance+" ৳");
            presentb.setText(presentbalance+" ৳");
            operator1.setText("Operator: "+sim);
            number1.setText(number);
            type1.setText("Type: "+type);



            admin.setText(status+" by "+adminx);


            if (sim.contains("ROBI")){

                images.setImageResource(R.drawable.robi);

            } else if (sim.contains("AIRTEL")){

                images.setImageResource(R.drawable.airtel);


            } else if (sim.contains("GP")){

                images.setImageResource(R.drawable.gp);


            }else if (sim.contains("BANGLALINK")){

                images.setImageResource(R.drawable.banglalink);


            }else if (sim.contains("SKITTO")){

                images.setImageResource(R.drawable.skitto);


            }else if (sim.contains("TELETALK")){

                images.setImageResource(R.drawable.teletalk);


            } else {

                Toast.makeText(getContext(),"Mistake",Toast.LENGTH_SHORT).show();

            }



            ImageView copy;
            copy = viewx.findViewById(R.id.copy);


            copy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    // Get the clipboard manager using the context
                    ClipboardManager clipboard = (ClipboardManager) v.getContext().getSystemService(Context.CLIPBOARD_SERVICE);

                    // Create a ClipData object to store the copied data
                    ClipData clip = ClipData.newPlainText("MobileX", number);

                    // Set the data to the clipboard
                    clipboard.setPrimaryClip(clip);

                }
            });


            return viewx;
        }
    }



    private void notification(){




        String url1 = "";
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