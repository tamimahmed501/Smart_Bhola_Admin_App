package com.tamim.smartbholaadmin;

import static android.content.Context.MODE_PRIVATE;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;

import com.airbnb.lottie.LottieAnimationView;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


public class adjustbalance extends Fragment {

    AutoCompleteTextView catagory;
    TextInputEditText uid, amount, note;

    LottieAnimationView lottie;


    public static String CAT = "";
    String[] CATAGORY = {"Commission", "Adjustment"};

    ArrayAdapter<String> arrayAdapter;
    CardView submit, back;
    ImageView backk;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View myView = inflater.inflate(R.layout.fragment_adjustbalance, container, false);


        catagory = myView.findViewById(R.id.catagory);
        uid = myView.findViewById(R.id.uid);
        amount = myView.findViewById(R.id.amount);
        note = myView.findViewById(R.id.note);
        lottie = myView.findViewById(R.id.lottie);
        submit = myView.findViewById(R.id.submit);


        arrayAdapter = new ArrayAdapter<>(getContext(), R.layout.list_item, CATAGORY);

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




                    SharedPreferences sharedPreferencesx = requireActivity().getSharedPreferences(getString(R.string.app_name), MODE_PRIVATE);


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

                            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
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




                            String url1 = "https://wikipediabangla.com/apps/firebase/fcm3.php?body="+"Your balance is adjusted. Amount is tk "+amount1+"&title="+"Hello User"+"&id="+uid1;
                            StringRequest stringRequest = new StringRequest(0, url1, new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {


                                }
                            }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {

                                }
                            });

                            RequestQueue requestQueue = Volley.newRequestQueue(getContext());
                            requestQueue.add(stringRequest);











                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    });

                    RequestQueue requestQueue = Volley.newRequestQueue(getContext());
                    requestQueue.add(stringRequest);









                } else {

                    uid.setError("Empty");

                }







            }
        });




        return myView;
    }
}