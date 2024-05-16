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


public class driveoffer extends Fragment {



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

        View myView = inflater.inflate(R.layout.fragment_driveoffer, container, false);



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





        String url = "https://wikipediabangla.com/apps/offer/pendingoffer.php";

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
                            String packagename = jsonObject.getString("packagename");
                            String time = jsonObject.getString("time");
                            String balance = jsonObject.getString("balance");
                            String status = jsonObject.getString("status");
                            String balancenow = jsonObject.getString("balancenow");
                            String number = jsonObject.getString("number");
                            String operator = jsonObject.getString("operator");
                            String account = jsonObject.getString("account");
                            String admin = jsonObject.getString("admin");
                            String offerprice = jsonObject.getString("offerprice");
                            String regularprice = jsonObject.getString("regularprice");




                            HashMap<String, String> hashMap = new HashMap<>();
                            hashMap.put("id", id);
                            hashMap.put("uid", uid);
                            hashMap.put("name", name);
                            hashMap.put("packagename", packagename);
                            hashMap.put("time", time);
                            hashMap.put("balance", balance);
                            hashMap.put("balancenow", balancenow);
                            hashMap.put("number", number);
                            hashMap.put("status", status);
                            hashMap.put("operator", operator);
                            hashMap.put("account", account);
                            hashMap.put("admin", admin);
                            hashMap.put("offerprice", offerprice);
                            hashMap.put("regularprice", regularprice);


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
                viewx = layoutInflater.inflate(R.layout.driveoffer, parent, false);
            }


            TextView id1,name1,time1,operator1,pastb,presentb,number1,type1,offerprice,regularprice,packagename,admin;

            ImageView images;

            LinearLayout proccesslay, proccesslay2;
            CardView success, proccess, cancel;


            name1 = viewx.findViewById(R.id.name);
            id1 = viewx.findViewById(R.id.id);
            time1 = viewx.findViewById(R.id.time);
            offerprice = viewx.findViewById(R.id.offerprice);
            operator1 = viewx.findViewById(R.id.operator);
            pastb = viewx.findViewById(R.id.pastbalance);
            presentb = viewx.findViewById(R.id.presentbalance);
            number1 = viewx.findViewById(R.id.number);
            type1 = viewx.findViewById(R.id.type);
            regularprice = viewx.findViewById(R.id.regularprice);
            images = viewx.findViewById(R.id.image);
            packagename = viewx.findViewById(R.id.packagename);
            proccess = viewx.findViewById(R.id.proccessing);
            proccesslay = viewx.findViewById(R.id.proccesslay);
            proccesslay2 = viewx.findViewById(R.id.processlay2);
            success = viewx.findViewById(R.id.success);
            cancel = viewx.findViewById(R.id.cancel);
            admin = viewx.findViewById(R.id.admin);

            HashMap<String,String> hashMap = arrayList.get(position);
            String id = hashMap.get("id");
            String uid = hashMap.get("uid");
            String name = hashMap.get("name");
            String packagenamex = hashMap.get("packagename");
            String status = hashMap.get("status");
            String balance = hashMap.get("balance");
            String balancenow = hashMap.get("balancenow");
            String time = hashMap.get("time");
            String sim = hashMap.get("operator");
            String number = hashMap.get("number");
            String account = hashMap.get("account");
            String adminx = hashMap.get("admin");
            String regularpricex = hashMap.get("regularprice");
            String offerpricex = hashMap.get("offerprice");






            id1.setText("ID: "+id);
            name1.setText("Name:  "+name);
            time1.setText("Time: "+time);
            offerprice.setText(offerpricex+" ৳");
            pastb.setText(balance+" ৳");
            regularprice.setText(regularpricex+" ৳");
            presentb.setText(balancenow+" ৳");
            operator1.setText("Operator: "+sim);
            number1.setText(number);
            type1.setText("Type: "+account);
            packagename.setText(packagenamex);





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


            proccess.setClickable(false);

            if (!adminx.contains("0")){


                proccesslay2.setVisibility(VISIBLE);
                proccesslay.setVisibility(VISIBLE);
                proccess.setClickable(false);
                proccesslay.setClickable(false);
                admin.setText("Proccessing by "+adminx);
                admin.setBackgroundColor(R.color.white);

            } else {


                proccesslay.setVisibility(VISIBLE);
                proccess.setClickable(true);
                proccesslay2.setVisibility(View.GONE);



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


            proccess.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    lottie.setVisibility(VISIBLE);

                    SharedPreferences sharedPreferencesx = requireActivity().getSharedPreferences(getString(R.string.app_name), MODE_PRIVATE);


                    String namex = sharedPreferencesx.getString("name", "");




                    String url1 = "https://wikipediabangla.com/apps/offer/dproccessing.php?id="+id+"&admin="+namex;
                    StringRequest stringRequest = new StringRequest(0, url1, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            lottie.setVisibility(View.GONE);
                            school();


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



            success.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {




                    String url1 = "https://wikipediabangla.com/apps/offer/dproccessing2.php?id="+id+"&status="+"Success";
                    StringRequest stringRequest = new StringRequest(0, url1, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            lottie.setVisibility(View.GONE);
                            school();


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


            cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {




                    String url1 = "https://wikipediabangla.com/apps/offer/dproccessing2.php?id="+id+"&status="+"Cancel";
                    StringRequest stringRequest = new StringRequest(0, url1, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            lottie.setVisibility(View.GONE);
                            school();


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