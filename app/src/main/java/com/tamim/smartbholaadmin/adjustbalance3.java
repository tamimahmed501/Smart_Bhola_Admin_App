package com.tamim.smartbholaadmin;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.airbnb.lottie.LottieAnimationView;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.google.android.material.textfield.TextInputEditText;

import org.checkerframework.checker.guieffect.qual.UI;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class adjustbalance3 extends AppCompatActivity {

    AutoCompleteTextView catagory;
    TextInputEditText uid, amount, note;

    LottieAnimationView lottie;


    public static String CAT = "";
    String[] CATAGORY = {"Commission", "Adjustment"};

    ArrayAdapter<String> arrayAdapter;
    CardView submit, back;
    ImageView backk;

    public static String UID = "";


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adjustbalance3);

        catagory = findViewById(R.id.catagory);
        uid = findViewById(R.id.uid);
        amount = findViewById(R.id.amount);
        note = findViewById(R.id.note);
        lottie = findViewById(R.id.lottie);
        submit = findViewById(R.id.submit);
        back = findViewById(R.id.back);
        backk = findViewById(R.id.backbutton);

        uid.setText(UID);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onBackPressed();
                finishAndRemoveTask();
                finish();

            }
        });

        backk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onBackPressed();
                finishAndRemoveTask();
                finish();
                Animatoo.animateSwipeRight(adjustbalance3.this);

            }
        });

        arrayAdapter = new ArrayAdapter<>(adjustbalance3.this, R.layout.list_item, CATAGORY);

        catagory.setAdapter(arrayAdapter);






        catagory.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String item = parent.getItemAtPosition(position).toString();

                CAT=item;


            }
        });



        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String uid1 = uid.getText().toString();
                String amount1 = amount.getText().toString();
                String note1 = note.getText().toString();


                if (!uid1.isEmpty()){




                    lottie.setVisibility(View.VISIBLE);




                    SharedPreferences sharedPreferencesx = getSharedPreferences(getString(R.string.app_name), MODE_PRIVATE);


                    String namex = sharedPreferencesx.getString("name", "");



                    Calendar calendar2 = Calendar.getInstance();
                    Date date = calendar2.getTime();
                    SimpleDateFormat timeFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                    SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm a", Locale.getDefault());
                    String dateTimeString = dateFormat.format(date);
                    String timestring = timeFormat.format(date);
                    String time = dateTimeString +", "+ timestring;





                    String url1 = "https://wikipediabangla.com/apps/offer/adjustbalance.php?uid="+uid1+"&amount="+amount1+"&note="+note1+"&catagory="+CAT+"&admin="+namex+"&time="+time;
                    StringRequest stringRequest = new StringRequest(0, url1, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {


                            lottie.setVisibility(View.GONE);

                            AlertDialog.Builder builder = new AlertDialog.Builder(adjustbalance3.this);
                            builder.setTitle("Alert").setMessage(response).setCancelable(false).setPositiveButton("Close", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int id) {

                                }
                            });
                            AlertDialog alert = builder.create();
                            alert.show();


                            uid.getText().clear();
                            amount.getText().clear();
                            note.getText().clear();


                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    });

                    RequestQueue requestQueue = Volley.newRequestQueue(adjustbalance3.this);
                    requestQueue.add(stringRequest);









                } else {

                    uid.setError("Empty");

                }







            }
        });







    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAndRemoveTask();
        finish();
        Animatoo.animateSwipeRight(adjustbalance3.this);
    }
}