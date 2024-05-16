package com.tamim.smartbholaadmin;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.appcompat.widget.SearchView;
import android.util.Log;
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
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;
import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;

public class busedit extends Fragment {
    ListView listView;
    LottieAnimationView lottie;
    ArrayList<HashMap<String, String>> originalList = new ArrayList<>();
    Myadapter myadapter = new Myadapter(originalList);

    SearchView start;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View myView = inflater.inflate(R.layout.fragment_busedit, container, false);

        start = myView.findViewById(R.id.start);
        listView = myView.findViewById(R.id.listview);
        lottie = myView.findViewById(R.id.lottie);

        listView.setAdapter(myadapter);

        start.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                fileList(newText);
                return true;
            }
        });

        school();

        return myView;
    }



    private void fileList(String newText) {
        ArrayList<HashMap<String, String>> originalList = new ArrayList<>();
        for (HashMap<String, String> detailsItem : myadapter.myArraylist) {
            if (detailsItem.get("route1").toLowerCase().contains(newText.toLowerCase()) ||
                    detailsItem.get("route2").toLowerCase().contains(newText.toLowerCase())) {
                originalList.add(detailsItem);
            }
        }
        if (originalList.isEmpty()) {
            Toast.makeText(getContext(), "No Data Found", Toast.LENGTH_SHORT).show();
        } else {
            myadapter.setFilteredList(originalList);
        }
    }


    private void school() {
        originalList.clear();
        lottie.setVisibility(View.VISIBLE);

        String mainUrl = "https://wikipediabangla.com/apps/bus2.php";

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                com.android.volley.Request.Method.GET, mainUrl, null,
                response -> {
                    lottie.setVisibility(View.GONE);

                    for (int x = 0; x < response.length(); x++) {
                        try {
                            JSONObject jsonObject = response.getJSONObject(x);
                            String route1 = jsonObject.getString("route1");
                            String route2 = jsonObject.getString("route2");
                            String local = jsonObject.getString("local");
                            String student = jsonObject.getString("student");
                            String distance = jsonObject.getString("distance");
                            String way = jsonObject.getString("way");
                            String id = jsonObject.getString("id");

                            HashMap<String, String> hashMap = new HashMap<>();
                            hashMap.put("route1", route1);
                            hashMap.put("route2", route2);
                            hashMap.put("local", local);
                            hashMap.put("distance", distance);
                            hashMap.put("student", student);
                            hashMap.put("way", way);
                            hashMap.put("id", id);
                            originalList.add(hashMap);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    myadapter.notifyDataSetChanged();
                },
                error -> {
                    lottie.setVisibility(View.GONE);
                    Toast.makeText(getContext(), "Error Response: " + error.toString(), Toast.LENGTH_SHORT).show();
                });

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(jsonArrayRequest);
    }

    private class Myadapter extends BaseAdapter {
        ArrayList<HashMap<String, String>> myArraylist = new ArrayList<>();

        public Myadapter(ArrayList<HashMap<String, String>> myArraylist) {
            this.myArraylist = myArraylist;
        }

        public void setFilteredList(ArrayList<HashMap<String, String>> filteredList) {
            this.myArraylist = filteredList;
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return myArraylist.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @SuppressLint("ViewHolder")
        @Override
        public View getView(int position, View viewx, ViewGroup parent) {
            if (viewx == null) {
                LayoutInflater layoutInflater = (LayoutInflater) getLayoutInflater().getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                viewx = layoutInflater.inflate(R.layout.bus, parent, false);
            }


            TextView local, student, distance, route1, route2, way;
            TextView delete, edit;

            route1 = viewx.findViewById(R.id.route1);
            route2 = viewx.findViewById(R.id.route2);
            local = viewx.findViewById(R.id.local);
            student = viewx.findViewById(R.id.student);
            distance = viewx.findViewById(R.id.distance);
            way = viewx.findViewById(R.id.way);
            delete = viewx.findViewById(R.id.delete1);
            edit = viewx.findViewById(R.id.edit);


            HashMap<String, String> hashMap = myArraylist.get(position);
            String route1x = hashMap.get("route1");
            String route2x = hashMap.get("route2");
            String localx = hashMap.get("local");
            String studentx = hashMap.get("student");
            String distancex = hashMap.get("distance");
            String ways = hashMap.get("way");
            String id = hashMap.get("id");


            route1.setText(route1x);
            route2.setText(route2x);
            local.setText("লোকাল ভাড়াঃ   " + localx + "  ৳");
            student.setText("স্টুডেন্ট ভাড়াঃ   " + studentx + "  ৳");
            distance.setText("দুরত্বঃ   " + distancex + "  কিঃমিঃ");
            way.setText("Road Type:   " + ways);



            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    lottie.setVisibility(VISIBLE);



                    String url = "https://wikipediabangla.com/apps/delete.php?id="+id+"&table=bus";


                    StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            lottie.setVisibility(View.GONE);
                            school();
                            if (response.contains("deleted")){


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


            edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    busadd.DISTANCE=distancex;
                    busadd.WAY=ways;
                    busadd.ROUTE1=route1x;
                    busadd.ROUTE2=route2x;
                    busadd.ID="1";
                    busadd.UID=id;



                    // Start MainActivity with an Intent
                    Intent intent = new Intent(getContext(), bus.class);

                    // Add a flag to indicate navigation to Offer fragment
                    intent.putExtra("NAVIGATE_TO_ADDBUS", true);

                    startActivity(intent);



                }
            });



            return viewx;
        }


    }
}
