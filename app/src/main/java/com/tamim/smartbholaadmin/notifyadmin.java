package com.tamim.smartbholaadmin;

import static android.view.View.VISIBLE;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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

public class notifyadmin extends AppCompatActivity {


    ListView listView;
    LottieAnimationView lottie;

    private static final String PREF_NAME = "MyPrefs";

    Myadapter myadapter;


    ImageView back;
    ArrayList<HashMap<String, String>> arrayList = new ArrayList<>();

    HashMap<String,String> hashMap;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifyadmin);



        back = findViewById(R.id.backbutton);
        listView = findViewById(R.id.listView);
        lottie = findViewById(R.id.lottie); // Corrected ID here

        myadapter = new Myadapter();
        listView.setAdapter(myadapter);


        notifydata();





    }


    private void notifydata() {

        arrayList.clear();
        lottie.setVisibility(VISIBLE);





        String url = "https://wikipediabangla.com/apps/notifydata.php";

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET, url, null,
                response -> {


                    lottie.setVisibility(View.GONE);



                    for (int x = 0; x < response.length(); x++) {
                        try {
                            JSONObject jsonObject = response.getJSONObject(x);
                            String uid = jsonObject.getString("uid");
                            String title = jsonObject.getString("title");
                            String body = jsonObject.getString("body");




                            HashMap<String, String> hashMap = new HashMap<>();
                            hashMap.put("uid", uid);
                            hashMap.put("title", title);
                            hashMap.put("body", body);


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
                    Toast.makeText(notifyadmin.this, "Error Response: " + error.toString(), Toast.LENGTH_SHORT).show();
                });

        RequestQueue requestQueue = Volley.newRequestQueue(notifyadmin.this);
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
                viewx = layoutInflater.inflate(R.layout.notifydata, parent, false);
            }


            TextView uid, title, body;


            CardView card;




            uid = viewx.findViewById(R.id.uid);
            title = viewx.findViewById(R.id.title);
            body = viewx.findViewById(R.id.body);
            card = viewx.findViewById(R.id.card);

            HashMap<String,String> hashMap = arrayList.get(position);
            String uid1 = hashMap.get("uid");
            String title1 = hashMap.get("title");
            String body1 = hashMap.get("body");




            uid.setText("Uid: "+uid1);
            title.setText(title1);
            body.setText(body1);

            card.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    uidData.UID=uid1;
                    startActivity(new Intent(notifyadmin.this, uidData.class));
                }
            });




            return viewx;
        }
    }

}