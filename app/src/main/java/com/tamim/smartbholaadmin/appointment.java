package com.tamim.smartbholaadmin;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.google.android.material.tabs.TabLayout;


public class appointment extends Fragment {


    TabLayout tabLayout;
    FrameLayout frameLayout;
    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View myView = inflater.inflate(R.layout.fragment_appointment, container, false);

        tabLayout = myView.findViewById(R.id.tabLayout);
        frameLayout = myView.findViewById(R.id.framelayout);






        FragmentManager fm = requireActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.framelayout, new tutionmaster());
        fragmentTransaction.commit();



        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                if (position == 0) {


                    FragmentManager fm = requireActivity().getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fm.beginTransaction();
                    fragmentTransaction.replace(R.id.framelayout, new tutionmaster());
                    fragmentTransaction.commit();




                } else if (position == 1) {

                    FragmentManager fm = requireActivity().getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fm.beginTransaction();
                    fragmentTransaction.replace(R.id.framelayout, new approvedtution());
                    fragmentTransaction.commit();




                }else if (position == 2) {



                    FragmentManager fm = requireActivity().getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fm.beginTransaction();
                    fragmentTransaction.replace(R.id.framelayout, new tutionpost2());
                    fragmentTransaction.commit();




                }if (position == 3) {



                    FragmentManager fm = requireActivity().getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fm.beginTransaction();
                    fragmentTransaction.replace(R.id.framelayout, new tutionpost());
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








        return myView;
    }


}