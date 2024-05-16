package com.tamim.smartbholaadmin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.google.android.material.tabs.TabLayout;

public class bus extends AppCompatActivity {

    TabLayout tabLayout;
    FrameLayout frameLayout;

    ImageView back;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus);


        tabLayout = findViewById(R.id.tabLayout);
        back = findViewById(R.id.backbutton);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                busadd.ID="0";

                onBackPressed();
                finishAndRemoveTask();
                finish();


            }
        });


        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.framelayout, new busadd());
        fragmentTransaction.commit();



        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                if (position == 0) {


                    FragmentManager fm = getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fm.beginTransaction();
                    fragmentTransaction.replace(R.id.framelayout, new busadd());
                    fragmentTransaction.commit();




                } else if (position == 1) {



                    busadd.ID="0";

                    FragmentManager fm = getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fm.beginTransaction();
                    fragmentTransaction.replace(R.id.framelayout, new busedit());
                    fragmentTransaction.commit();




                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                // Do nothing when a tab is unselected
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                // Handle tab reselection if needed
            }
        });




        // Check for the flag in the Intent
        if (getIntent().getBooleanExtra("NAVIGATE_TO_ADDBUS", false)) {
            fragmentTransaction = fm.beginTransaction();
            fragmentTransaction.replace(R.id.framelayout, new busadd()); // Replace with the actual Offer fragment class
            fragmentTransaction.commit();

        }







    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        busadd.ID="0";
        finishAndRemoveTask();
        finish();
    }
}