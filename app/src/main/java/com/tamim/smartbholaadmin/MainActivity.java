package com.tamim.smartbholaadmin;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;

import me.ibrahimsn.lib.OnItemSelectedListener;
import me.ibrahimsn.lib.SmoothBottomBar;

public class MainActivity extends AppCompatActivity {

    private static final int MY_REQUEST_CODE = 1 ;

    FrameLayout frameLayout;
    SmoothBottomBar smoothBottomBar;



    LinearLayout bottomlay;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);




        askNotificationPermission();


        onInitToken();



        smoothBottomBar = findViewById(R.id.smoothbottomber);
        frameLayout = findViewById(R.id.frame);





        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.frame, new home());
        fragmentTransaction.commit();

        smoothBottomBar.setOnItemSelectedListener(new OnItemSelectedListener() {

            @Override
            public boolean onItemSelect(int i) {
                if (i==0) {
                    FragmentManager fm = getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fm.beginTransaction();
                    fragmentTransaction.replace(R.id.frame, new home());
                    fragmentTransaction.commit();

                } else if (i==1) {
                    FragmentManager fmx = getSupportFragmentManager();
                    FragmentTransaction fragmentTransactionx = fmx.beginTransaction();
                    fragmentTransactionx.replace(R.id.frame, new appointment());
                    fragmentTransactionx.commit();
                } else if (i==2) {
                    FragmentManager fmy = getSupportFragmentManager();
                    FragmentTransaction fragmentTransactiony = fmy.beginTransaction();
                    fragmentTransactiony.replace(R.id.frame, new internet()); // Assuming there is a dashboard fragment class
                    fragmentTransactiony.commit();
                } else if (i==3) {
                    FragmentManager fmy = getSupportFragmentManager();
                    FragmentTransaction fragmentTransactiony = fmy.beginTransaction();
                    fragmentTransactiony.replace(R.id.frame, new notification()); // Assuming there is a dashboard fragment class
                    fragmentTransactiony.commit();
                }else if (i==4) {
                    FragmentManager fmy = getSupportFragmentManager();
                    FragmentTransaction fragmentTransactiony = fmy.beginTransaction();
                    fragmentTransactiony.replace(R.id.frame, new user()); // Assuming there is a dashboard fragment class
                    fragmentTransactiony.commit();
                }
                return true;
            }
        });








    }


    @SuppressLint("MissingSuperCall")
    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Exit")
                .setMessage("এপ থেকে বের হতে চান?")
                .setPositiveButton("হ্যা", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(Intent.ACTION_MAIN);
                        intent.addCategory(Intent.CATEGORY_HOME);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish();
                        System.exit(0);
                    }
                })
                .setNegativeButton("না", null)
                .show();

    }

    // Declare the launcher at the top of your Activity/Fragment:
    // Declare the launcher at the top of your Activity/Fragment:
    private final ActivityResultLauncher<String> requestPermissionLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
                if (isGranted) {
                    // FCM SDK (and your app) can post notifications.
                } else {
                    // TODO: Inform user that that your app will not show notifications.
                }
            });



    private void askNotificationPermission() {
        // This is only necessary for API level >= 33 (TIRAMISU)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.POST_NOTIFICATIONS) ==
                    PackageManager.PERMISSION_GRANTED) {
                // FCM SDK (and your app) can post notifications.
            } else if (shouldShowRequestPermissionRationale(android.Manifest.permission.POST_NOTIFICATIONS)) {




                new AlertDialog.Builder(MainActivity.this)
                        .setTitle("Request Permission")
                        .setMessage("You must need to give use send notification permission")
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                requestPermissionLauncher.launch(android.Manifest.permission.POST_NOTIFICATIONS);

                            }
                        })
                        .setNegativeButton("No Thanks", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .create()
                        .show();






            } else {
                // Directly ask for the permission
                requestPermissionLauncher.launch(android.Manifest.permission.POST_NOTIFICATIONS);
            }
        }
    }
    private void onInitToken(){

        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            Log.w("firebasetoken", "Fetching FCM registration token failed", task.getException());
                            return;
                        }

                        // Get new FCM registration token
                        String token = task.getResult();
                        Log.d("firebasetoken", token);


                        SharedPreferences sharedPreferences = getSharedPreferences(getString(R.string.app_name), MODE_PRIVATE);

                        String id = sharedPreferences.getString("uid", "");


                        String url5 = "https://wikipediabangla.com/apps/firebase/atoken.php?token="+token+"&id="+id;

                        StringRequest stringRequest = new StringRequest(Request.Method.GET, url5, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {




                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {


                            }
                        });


                        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
                        requestQueue.add(stringRequest);





                    }
                });


    }

}

