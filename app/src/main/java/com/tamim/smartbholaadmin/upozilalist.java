package com.tamim.smartbholaadmin;

import static android.view.View.VISIBLE;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

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

public class upozilalist extends Fragment {

    TextInputEditText name,village,mobile,podobi,member,upozila;
    CardView adddata,adddata2;
    LinearLayout imagelay;
    ImageView imageView;
    ProgressBar progressBar;

    Bitmap bitmap;
    String encodedImage;

    LottieAnimationView lottie;

    ListView listView;

    ArrayList<HashMap<String,String>>arrayList=new ArrayList<>();
    HashMap<String,String>hashMap;

    int MY_REQUEST_CODE = 1;


    ScrollView addlay;
    TabLayout tabLayout;

    TextView toptext,toptext2;

    public static String SECTION = "";


    private static long lastRequestQueueTime = 0;
    private static String lastResponse = ""; // To store the last response
    private static final long REQUEST_QUEUE_THRESHOLD = 3000; // 5 seconds in milliseconds





    Myadapter myadapter;


    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View myView = inflater.inflate(R.layout.fragment_upozilalist, container, false);


        name = myView.findViewById(R.id.name);
        adddata = myView.findViewById(R.id.adddata);
        village = myView.findViewById(R.id.village);
        podobi = myView.findViewById(R.id.podobi);
        mobile = myView.findViewById(R.id.mobile);
        lottie = myView.findViewById(R.id.lottie);
        listView= myView.findViewById(R.id.listView);
        addlay = myView.findViewById(R.id.addlay);
        member= myView.findViewById(R.id.member);
        adddata2= myView.findViewById(R.id.adddata2);
        upozila=myView.findViewById(R.id.upozila);
        imagelay = myView.findViewById(R.id.imagelay);

        listView.setVisibility(VISIBLE);


        myadapter = new Myadapter();
        listView.setAdapter(myadapter);


        adddata.setVisibility(View.GONE);
        adddata2.setVisibility(View.GONE);

        school();






        return myView;
    }




    private void school() {


        arrayList.clear();

        lottie.setVisibility(VISIBLE);



        String url = "https://wikipediabangla.com/apps/upozila2.php";



        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                com.android.volley.Request.Method.GET, url, null,
                response -> {


                    lottie.setVisibility(View.GONE);



                    for (int x = 0; x < response.length(); x++) {
                        try {
                            JSONObject jsonObject = response.getJSONObject(x);
                            String id = jsonObject.getString("id");
                            String name = jsonObject.getString("name");
                            String member = jsonObject.getString("member");
                            String podobi = jsonObject.getString("podobi");
                            String mobile = jsonObject.getString("mobile");
                            String images = jsonObject.getString("images");


                            HashMap<String, String> hashMap = new HashMap<>();
                            hashMap.put("name", name);
                            hashMap.put("mobile", mobile);
                            hashMap.put("member", member);
                            hashMap.put("images", images);
                            hashMap.put("podobi", podobi);
                            hashMap.put("id", id);


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

        @SuppressLint("MissingInflatedId")
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            LayoutInflater layoutInflater = (LayoutInflater) getLayoutInflater().getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View viewx = layoutInflater.inflate(R.layout.upozila, parent, false);

            TextView name1, upozila1,podobi1,mobile1;
            name1 = viewx.findViewById(R.id.name);
            upozila1 = viewx.findViewById(R.id.upozila);
            mobile1 = viewx.findViewById(R.id.mobile);
            podobi1 = viewx.findViewById(R.id.podobi);





            ImageView imageView1 = viewx.findViewById(R.id.imageView1);


            CardView cardView = viewx.findViewById(R.id.card);




            HashMap<String,String> hashMap = arrayList.get(position);
            String namex = hashMap.get("name");
            String mobilex = hashMap.get("mobile");
            String images = hashMap.get("images");
            String memberx = hashMap.get("member");
            String podobix = hashMap.get("podobi");
            String id = hashMap.get("id");


            name1.setText(memberx);
            podobi1.setText("পদবিঃ  "+podobix);
            upozila1.setText("উপজেলাঃ  "+namex);
            mobile1.setText("মোবাইলঃ  "+mobilex);

            String urlx = "https://wikipediabangla.com/apps/Images/"+images;


            Picasso.get()
                    .load(urlx)
                    .placeholder(R.drawable.imageicon)
                    .error(R.drawable.imgichon)
                    .into(imageView1);



            ImageView delete,edit;
            delete = viewx.findViewById(R.id.delete);
            edit = viewx.findViewById(R.id.edit);




            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    lottie.setVisibility(VISIBLE);


                    HashMap<String,String> hashMap = arrayList.get(position);
                    String idx = hashMap.get("id");


                    String url = "https://wikipediabangla.com/apps/upozila3.php?id="+idx;


                    StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            lottie.setVisibility(View.GONE);
                            cardView.setVisibility(View.GONE);

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


            edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    addlay.setVisibility(VISIBLE);
                    listView.setVisibility(View.GONE);
                    adddata.setVisibility(View.GONE);
                    adddata2.setVisibility(VISIBLE);
                    imagelay.setVisibility(View.GONE);





                    name.setText(namex);
                    mobile.setText(mobilex);
                    podobi.setText(podobix);
                    member.setText(memberx);




                    adddata2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {



                            String namex = name.getText().toString();
                            String mobilex = mobile.getText().toString();
                            String podobix = podobi.getText().toString();
                            String memberx = member.getText().toString();



                            lottie.setVisibility(VISIBLE);


                            HashMap<String,String> hashMap = arrayList.get(position);
                            String idx = hashMap.get("id");


                            String url = "https://wikipediabangla.com/apps/upozila4.php?id="+idx+"&name="+namex+"&podobi="+podobix+"&member="+memberx+"&mobile="+mobilex;


                            StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {

                                    lottie.setVisibility(View.GONE);

                                    if (response.contains("updated")){


                                        addlay.setVisibility(View.GONE);
                                        arrayList.clear();
                                        school();
                                        listView.setVisibility(VISIBLE);
                                        adddata.setVisibility(VISIBLE);
                                        adddata2.setVisibility(View.GONE);


                                        android.app.AlertDialog alertDialog = new android.app.AlertDialog.Builder(getContext()).create();
                                        alertDialog.setTitle("Alert");
                                        alertDialog.setMessage("Data updated successfully.");
                                        alertDialog.setButton(android.app.AlertDialog.BUTTON_NEUTRAL, "OK", new DialogInterface.OnClickListener() {
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






            return viewx;
        }
    }


}