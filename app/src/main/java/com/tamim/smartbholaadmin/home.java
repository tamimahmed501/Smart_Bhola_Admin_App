package com.tamim.smartbholaadmin;

import static android.content.Context.MODE_PRIVATE;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
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

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONException;


public class home extends Fragment {


    CardView union,upozila,postoffice,emergency,police,sim,hospital,diagnostic, addbalance, balanceWithdraw;

    CardView blooddonor,foundation,ambulance,lawyer,freedoctor,simoffer,reporter,member,curier,biddut, bus, notify;

    DrawerLayout drawerLayout;
    NavigationView navigationView;

    ImageView drawer;

    TextView name;


    TextView balance, todaysell, todayrecharge, totalrecarge, totalsell, totaluser, addedbalance, commission;



    ActionBarDrawerToggle actionBarDrawerToggle;



    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View myView = inflater.inflate(R.layout.fragment_home, container, false);


        union=myView.findViewById(R.id.union);
        upozila=myView.findViewById(R.id.upozila);
        postoffice=myView.findViewById(R.id.postoffice);
        emergency=myView.findViewById(R.id.emergency);
        police=myView.findViewById(R.id.police);
        sim=myView.findViewById(R.id.sim);
        hospital=myView.findViewById(R.id.hospital);
        diagnostic=myView.findViewById(R.id.diagnostic);
        blooddonor=myView.findViewById(R.id.blooddoner);
        foundation=myView.findViewById(R.id.foundation);
        ambulance=myView.findViewById(R.id.ambulance);
        lawyer=myView.findViewById(R.id.lawyer);
        freedoctor=myView.findViewById(R.id.freedoctor);
        simoffer=myView.findViewById(R.id.simoffer);
        member=myView.findViewById(R.id.member);
        name=myView.findViewById(R.id.name);
        balanceWithdraw=myView.findViewById(R.id.balanceWithdraw);

        reporter=myView.findViewById(R.id.reporter);
        curier=myView.findViewById(R.id.curier);
        biddut=myView.findViewById(R.id.biddut);
        bus=myView.findViewById(R.id.bus);
        addbalance=myView.findViewById(R.id.addbalance);
        addedbalance=myView.findViewById(R.id.addedbalance);
        commission=myView.findViewById(R.id.commission);

        balance = myView.findViewById(R.id.balance);
        todayrecharge = myView.findViewById(R.id.todayrecharge);
        todaysell = myView.findViewById(R.id.todaysell);
        totalrecarge = myView.findViewById(R.id.totalrecharge);
        totalsell = myView.findViewById(R.id.totalsell);
        totaluser = myView.findViewById(R.id.totaluser);
        notify = myView.findViewById(R.id.notify);

        drawer = myView.findViewById(R.id.drawer);
        navigationView = myView.findViewById(R.id.nevigation);
        drawerLayout = myView.findViewById(R.id.drawerLayout);

        actionBarDrawerToggle = new ActionBarDrawerToggle(
                requireActivity(),
                drawerLayout,
                R.string.open,
                R.string.close
        );

        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        drawer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                    drawerLayout.closeDrawer(GravityCompat.START);
                } else {
                    drawerLayout.openDrawer(GravityCompat.START);
                }
            }
        });






        String url3 = "https://wikipediabangla.com/apps/offer/rechargeinfo.php";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET, url3, null,
                response -> {
                    try {
                        String allbalance = response.getString("allbalance");
                        String todaysell1 = response.getString("todaysell");
                        String todayrecharge1 = response.getString("todayrecharge");
                        String allsell1 = response.getString("allsell");
                        String allrecharge1 = response.getString("allrecharge");
                        String totaluser1 = response.getString("totaluser");
                        String tab = response.getString("totaladdedbalance");
                        String tc = response.getString("totalcommission");


                        addedbalance.setText(tab+ "  ৳");
                        commission.setText(tc+ "  ৳");
                        balance.setText(allbalance+ "  ৳");
                        todaysell.setText(todaysell1+ "  টি");
                        totalsell.setText(allsell1+"  টি");
                        totalrecarge.setText(allrecharge1+"  ৳");
                        totaluser.setText(totaluser1+"  জন");

                        if (todayrecharge1.contains("null")){

                            todayrecharge.setText("0  ৳");


                        } else {

                            todayrecharge.setText(todayrecharge1+"  ৳");

                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> {
                    // Handle error
                }
        );

        RequestQueue requestQueue2 = Volley.newRequestQueue(getContext());
        requestQueue2.add(jsonObjectRequest);




        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

               if (item.getItemId()==R.id.ad1){

                    startActivity(new Intent(getContext(), adverstisment.class));

                } else if (item.getItemId()==R.id.ad2){

                    Toast.makeText(getContext(),"This function will be added in Next Update",Toast.LENGTH_SHORT);

                } else if (item.getItemId()==R.id.ad3){

                    Toast.makeText(getContext(),"This function will be added in Next Update",Toast.LENGTH_SHORT);


                } else if (item.getItemId()==R.id.comment){

                    Toast.makeText(getContext(),"This function will be added in Next Update",Toast.LENGTH_SHORT);


                } else if (item.getItemId()==R.id.recommend){

                    Toast.makeText(getContext(),"This function will be added in Next Update",Toast.LENGTH_SHORT);


                } else if (item.getItemId()==R.id.logout){


                   AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                   builder.setTitle("Permission Required").setMessage("Do you want to Log out?").setCancelable(false).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                       @Override
                       public void onClick(DialogInterface dialog, int id) {

                           SharedPreferences sharedPreferencesx = requireActivity().getSharedPreferences(getString(R.string.app_name), MODE_PRIVATE);
                           SharedPreferences.Editor editorx = sharedPreferencesx.edit();
                           editorx.clear();
                           editorx.apply();



                           Toast.makeText(getContext(), "Log-out Successfull.", Toast.LENGTH_SHORT).show();
                           startActivity(new Intent(getContext(), splash.class));
                       }
                   }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                       @Override
                       public void onClick(DialogInterface dialog, int id) {
                       }
                   });
                   AlertDialog alert = builder.create();
                   alert.show();

                }






                return false;
            }
        });






        SharedPreferences sharedPreferencesx = requireActivity().getSharedPreferences(getString(R.string.app_name), MODE_PRIVATE);


        String namex = sharedPreferencesx.getString("name", "");

        name.setText(namex);




        addbalance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), com.tamim.smartbholaadmin.addbalance.class));
            }
        });

        balanceWithdraw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), com.tamim.smartbholaadmin.withDraw.class));
            }
        });


        biddut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), com.tamim.smartbholaadmin.pollibiddut.class));
            }
        });




        notify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), com.tamim.smartbholaadmin.notifyadmin.class));
            }
        });



        curier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), com.tamim.smartbholaadmin.curier.class));
            }
        });



        reporter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                startActivity(new Intent(getContext(), com.tamim.smartbholaadmin.reporter.class));
            }
        });





        member.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                startActivity(new Intent(getContext(), com.tamim.smartbholaadmin.member.class));
            }
        });

        simoffer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                startActivity(new Intent(getContext(), com.tamim.smartbholaadmin.simoffer.class));
            }
        });






        freedoctor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                startActivity(new Intent(getContext(), com.tamim.smartbholaadmin.freedoctor.class));
            }
        });

        ambulance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                startActivity(new Intent(getContext(), com.tamim.smartbholaadmin.ambulance.class));
            }
        });


        lawyer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                startActivity(new Intent(getContext(), com.tamim.smartbholaadmin.lawer.class));
            }
        });


        foundation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                startActivity(new Intent(getContext(), com.tamim.smartbholaadmin.bloodfoundation.class));
            }
        });

        blooddonor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                startActivity(new Intent(getContext(), com.tamim.smartbholaadmin.blooddoner.class));
            }
        });

        hospital.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                startActivity(new Intent(getContext(), com.tamim.smartbholaadmin.hospital.class));
            }
        });


        diagnostic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                startActivity(new Intent(getContext(), com.tamim.smartbholaadmin.diagnostic.class));
            }
        });




        sim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                com.tamim.smartbholaadmin.schoolP.SCHOOL="1";

                startActivity(new Intent(getContext(), com.tamim.smartbholaadmin.simdetails.class));
            }
        });





        police.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                com.tamim.smartbholaadmin.schoolP.SCHOOL="1";

                startActivity(new Intent(getContext(), com.tamim.smartbholaadmin.police.class));
            }
        });





        union.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                com.tamim.smartbholaadmin.upmember.SECTION="1";

                startActivity(new Intent(getContext(), com.tamim.smartbholaadmin.upmember.class));
            }
        });


        upozila.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                startActivity(new Intent(getContext(), com.tamim.smartbholaadmin.upozila.class));
            }
        });

        postoffice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                startActivity(new Intent(getContext(), com.tamim.smartbholaadmin.postoffice.class));
            }
        });

        emergency.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                startActivity(new Intent(getContext(), com.tamim.smartbholaadmin.emergency.class));
            }
        });

        bus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                startActivity(new Intent(getContext(), com.tamim.smartbholaadmin.bus.class));
            }
        });

        return myView;



    }
}