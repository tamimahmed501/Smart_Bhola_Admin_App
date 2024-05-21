package com.tamim.smartbholaadmin;

import static android.view.View.VISIBLE;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;


public class adjustbalance2 extends Fragment {


    ListView listView;
    LottieAnimationView lottie;

    private static final String PREF_NAME = "MyPrefs";

    Myadapter myadapter;


    ArrayList<HashMap<String, String>> arrayList = new ArrayList<>();

    HashMap<String,String> hashMap;


    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View myView = inflater.inflate(R.layout.fragment_adjustbalance2, container, false);





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





        String url = "https://wikipediabangla.com/apps/offer/adjustbalance2.php";

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET, url, null,
                response -> {


                    lottie.setVisibility(View.GONE);



                    for (int x = 0; x < response.length(); x++) {
                        try {
                            JSONObject jsonObject = response.getJSONObject(x);
                            String id = jsonObject.getString("id");
                            String uid = jsonObject.getString("uid");
                            String catagory = jsonObject.getString("catagory");
                            String amount = jsonObject.getString("amount");
                            String time = jsonObject.getString("time");
                            String note = jsonObject.getString("note");
                            String admin = jsonObject.getString("admin");




                            HashMap<String, String> hashMap = new HashMap<>();
                            hashMap.put("id", id);
                            hashMap.put("uid", uid);
                            hashMap.put("catagory", catagory);
                            hashMap.put("amount", amount);
                            hashMap.put("time", time);
                            hashMap.put("note", note);
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
                viewx = layoutInflater.inflate(R.layout.adjustbalance, parent, false);
            }


            TextView id1,amount1,time1,admin1,catagory1,note1;






            admin1 = viewx.findViewById(R.id.admin);
            id1 = viewx.findViewById(R.id.uid);
            time1 = viewx.findViewById(R.id.time);
            catagory1 = viewx.findViewById(R.id.catagory);
            note1 = viewx.findViewById(R.id.note);
            amount1 = viewx.findViewById(R.id.amount);


            HashMap<String,String> hashMap = arrayList.get(position);
            String admin = hashMap.get("admin");
            String id = hashMap.get("uid");
            String time = hashMap.get("time");
            String catagory = hashMap.get("catagory");
            String note = hashMap.get("note");
            String amount = hashMap.get("amount");




            id1.setText(id);
            time1.setText("Time: "+time);
            amount1.setText("Amount: "+amount+" ৳");
            catagory1.setText("Catagory: "+catagory);
            note1.setText("Note: "+note);


            admin1.setText("Submit by: "+admin);


            CardView card;
            card = viewx.findViewById(R.id.card);




            card.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    uidData.UID=id;
                    startActivity(new Intent(getContext(), uidData.class));
                }
            });




            return viewx;
        }
    }
}