package com.tamim.smartbholaadmin;

import static android.content.Context.MODE_PRIVATE;
import static android.view.View.VISIBLE;

import android.annotation.SuppressLint;
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

public class blooddonor extends Fragment {

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
        View myView =inflater.inflate(R.layout.fragment_blooddonor, container, false);

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

        String url = "https://wikipediabangla.com/apps/blooddonor2.php?name=";

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
                                String adress = jsonObject.getString("adress");
                                String mobile = jsonObject.getString("mobile");
                                String images = jsonObject.getString("images");
                                String bloodgroup = jsonObject.getString("bloodgroup");
                                String lastdonate = jsonObject.getString("lastdonate");
                                String id = jsonObject.getString("id");
                                String count = jsonObject.getString("count");
                                String birth = jsonObject.getString("birth");
                                String gender = jsonObject.getString("gender");
                                String upazila = jsonObject.getString("upazila");
                                String unions = jsonObject.getString("unions");


                                HashMap<String, String> hashMap = new HashMap<>();
                                hashMap.put("name", name);
                                hashMap.put("mobile", mobile);
                                hashMap.put("adress", adress);
                                hashMap.put("images", images);
                                hashMap.put("group", bloodgroup);
                                hashMap.put("lastd", lastdonate);
                                hashMap.put("id", id);
                                hashMap.put("count", count);
                                hashMap.put("birth", birth);
                                hashMap.put("gender", gender);
                                hashMap.put("unions", unions);
                                hashMap.put("upazila", upazila);

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


            TextView name1, adress1,mobile1,group1,lastd1,count1,birth1,gender1,upazila1,unions1,group2,id1;
            ImageView imageView1;

            LinearLayout mainlay;






            public MyViewHolder(@NonNull View itemView) {
                super(itemView);




                name1 = itemView.findViewById(R.id.name);
                adress1 = itemView.findViewById(R.id.adress);
                group1 = itemView.findViewById(R.id.group);
                lastd1 = itemView.findViewById(R.id.lastdonate);
                imageView1 = itemView.findViewById(R.id.imageView1);
                mainlay = itemView.findViewById(R.id.mainlay);
                id1 = itemView.findViewById(R.id.id);




            }
        }



        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            LayoutInflater layoutInflater = getLayoutInflater();
            View myView = layoutInflater.inflate(R.layout.blooddonor, parent, false);








            return new MyViewHolder(myView);
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {




            HashMap<String,String> hashMap = arrayList.get(position);
            String namex = hashMap.get("name");
            String adressx = hashMap.get("adress");
            String numberx = hashMap.get("mobile");
            String images = hashMap.get("images");
            String groupx = hashMap.get("group");
            String lastdx = hashMap.get("lastd");
            String id = hashMap.get("id");
            String countx = hashMap.get("count");
            String birthx = hashMap.get("birth");
            String genderx = hashMap.get("gender");
            String upazilax = hashMap.get("upazila");
            String unionsx = hashMap.get("unions");



            holder.name1.setText(namex);
            holder.adress1.setText(unionsx+", "+upazilax);
            holder.lastd1.setText("শেষ রক্তদানঃ  "+lastdx);





            holder.group1.setText(groupx);

            holder.id1.setText("ID: "+id);

            holder.group1.setText(groupx);

            String urlx = "https://wikipediabangla.com/apps/Images/"+images;


            Picasso.get()
                    .load(urlx)
                    .placeholder(R.drawable.bloodprofile)
                    .error(R.drawable.bloodprofile)
                    .into(holder.imageView1);




            Dialog dialog;
            dialog = new Dialog(requireContext());
            dialog.setContentView(R.layout.bloodlay);
            dialog.getWindow().setBackgroundDrawable(getResources().getDrawable(R.drawable.alertbackground));
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            dialog.setCancelable(true);

            TextView name, group, count, birth, lastd, adress;
            ImageView myimage,cross;


            name = dialog.findViewById(R.id.name);
            group = dialog.findViewById(R.id.group);
            count = dialog.findViewById(R.id.count);
            birth = dialog.findViewById(R.id.birth);
            lastd = dialog.findViewById(R.id.lastdonate);
            adress = dialog.findViewById(R.id.adress);
            myimage = dialog.findViewById(R.id.imageView1);
            cross = dialog.findViewById(R.id.cross);



            name.setText(namex);
            group.setText("ব্লাড গ্রুপঃ  "+groupx);
            count.setText("মোট রক্তদানঃ  "+countx);
            birth.setText("জন্ম তারিখঃ  "+birthx);
            lastd.setText("শেষ রক্তদানঃ  "+lastdx);
            adress.setText("ঠিকানাঃ "+adressx+", "+unionsx+", "+upazilax);


            Picasso.get()
                    .load(urlx)
                    .placeholder(R.drawable.imageicon)
                    .error(R.drawable.imageicon)
                    .into(myimage);




            holder.mainlay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    dialog.show();



                }
            });

            cross.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.cancel();
                }
            });






        }

        @Override
        public int getItemCount() {
            return arrayList.size();
        }




    }

}
