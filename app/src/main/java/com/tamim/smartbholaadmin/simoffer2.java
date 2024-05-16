package com.tamim.smartbholaadmin;

import static android.view.View.VISIBLE;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class simoffer2 extends AppCompatActivity {

    LottieAnimationView lottie;
    TextView toptext;
    ListView listView;
    ImageView back;

    Myadapter myadapter;
    public static String SIM = "";

    ArrayList<HashMap<String, String>> arrayList = new ArrayList<>();

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simoffer2);

        toptext = findViewById(R.id.toptext);
        listView = findViewById(R.id.listView);
        lottie = findViewById(R.id.lottie);
        back = findViewById(R.id.backbutton);

         myadapter = new Myadapter();
        listView.setAdapter(myadapter);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        toptext.setText(SIM);

        myClass();

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
            ViewHolder viewHolder;

            if (convertView == null) {
                LayoutInflater layoutInflater = LayoutInflater.from(simoffer2.this);
                convertView = layoutInflater.inflate(R.layout.simoffer, parent, false);

                viewHolder = new ViewHolder();
                viewHolder.packagename = convertView.findViewById(R.id.packagename);
                viewHolder.price = convertView.findViewById(R.id.price);
                viewHolder.validity = convertView.findViewById(R.id.validity);
                viewHolder.recharge = convertView.findViewById(R.id.recharge);
                viewHolder.sim = convertView.findViewById(R.id.sim);
                viewHolder.cardView = convertView.findViewById(R.id.card);
                viewHolder.delete = convertView.findViewById(R.id.delete);
                viewHolder.edit = convertView.findViewById(R.id.edit);
                viewHolder.type = convertView.findViewById(R.id.type);


                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }

            HashMap<String, String> hashMap = arrayList.get(position);
            String packagenamex = hashMap.get("packagename");
            String idx = hashMap.get("id");
            String pricex = hashMap.get("price");
            String rechargex = hashMap.get("recharge");
            String validityx = hashMap.get("validity");
            String simx = hashMap.get("sim");
            String type = hashMap.get("catagory");


            viewHolder.packagename.setText(packagenamex);
            viewHolder.validity.setText(validityx);
            viewHolder.price.setText(pricex + " টাকা");
            viewHolder.recharge.setText(rechargex + " টাকা");
            viewHolder.sim.setText(simx);
            viewHolder.type.setText(type);




            viewHolder.delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    lottie.setVisibility(VISIBLE);


                    HashMap<String,String> hashMap = arrayList.get(position);
                    String idx = hashMap.get("id");


                    String url = "https://wikipediabangla.com/apps/delete.php?id="+idx+"&table=simoffer";


                    StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            lottie.setVisibility(View.GONE);

                            myClass();

                            if (response.contains("deleted")){


                                android.app.AlertDialog alertDialog = new android.app.AlertDialog.Builder(simoffer2.this).create();
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

                                Toast.makeText(simoffer2.this,"Something went wrong!",Toast.LENGTH_SHORT).show();

                            };







                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                            lottie.setVisibility(View.GONE);

                        }
                    });


                    RequestQueue requestQueue = Volley.newRequestQueue(simoffer2.this);
                    requestQueue.add(stringRequest);


                }
            });

            viewHolder.edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {





                    simoffer.MYID=idx;
                    simoffer.PACKAGENAME=packagenamex;
                    simoffer.PRICE=pricex;
                    simoffer.VALIDITY=validityx;
                    simoffer.RECHARGE=rechargex;

                    startActivity(new Intent(simoffer2.this,simoffer.class));



                }
            });

            return convertView;
        }
    }

    private static class ViewHolder {
        TextView packagename, price, validity, recharge,type;
        TextView sim;
        CardView cardView;
        ImageView delete;
        CardView edit;
    }


    private void myClass(){


        arrayList = new ArrayList<>();

        lottie.setVisibility(VISIBLE);

        String url = "https://wikipediabangla.com/apps/addoffer3.php?sim=" + SIM;

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        lottie.setVisibility(View.GONE);

                        for (int x = 0; x < response.length(); x++) {
                            try {
                                JSONObject jsonObject = response.getJSONObject(x);
                                String packagename = jsonObject.getString("packagename");
                                String id = jsonObject.getString("id");
                                String sim = jsonObject.getString("sim");
                                String recharge = jsonObject.getString("recharge");
                                String price = jsonObject.getString("price");
                                String validity = jsonObject.getString("validity");
                                String catagory = jsonObject.getString("catagory");


                                HashMap<String, String> hashMap = new HashMap<>();
                                hashMap.put("packagename", packagename);
                                hashMap.put("id", id);
                                hashMap.put("recharge", recharge);
                                hashMap.put("price", price);
                                hashMap.put("validity", validity);
                                hashMap.put("sim", sim);
                                hashMap.put("catagory", catagory);
                                arrayList.add(hashMap);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        // Notify the adapter that the data set has changed
                        myadapter.notifyDataSetChanged();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Handle error response
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(simoffer2.this);
        requestQueue.add(jsonArrayRequest);



    }
}
