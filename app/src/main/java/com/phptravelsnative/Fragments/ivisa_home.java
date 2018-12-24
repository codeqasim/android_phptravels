package com.phptravelsnative.Fragments;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.countrypicker.CountryPicker;
import com.countrypicker.CountryPickerListener;
import com.phptravelsnative.Activities.DateActivity;
import com.phptravelsnative.Activities.SearchViewActivity;
import com.phptravelsnative.Activities.SearchingCarTourOffers;
import com.phptravelsnative.Activities.WebViewInvoice;
import com.phptravelsnative.Models.Auto_Model;
import com.phptravelsnative.Models.car_model;
import com.phptravelsnative.R;
import com.phptravelsnative.utality.Extra.ComplexPreferences;
import com.phptravelsnative.utality.Extra.DateSeter;

import java.util.ArrayList;
import java.util.Calendar;


public class ivisa_home extends Fragment {


    EditText car_to;
    EditText car_from;


    View inflated;

    String from_code="pakistan";
    String to_code="turkey";

    String from_data="pakistan";
    String to_data="turkey";

    SharedPreferences sharedPreferences;
    public  final String MyPREFERENCES = "MyPrefs" ;
    SharedPreferences.Editor editor;

    Button search;

    public ivisa_home() {



    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        inflated = inflater.inflate(R.layout.ivisa_layout, container, false);
        sharedPreferences = getContext().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();



        car_to = (EditText) inflated.findViewById(R.id.car_to);
        car_from = (EditText) inflated.findViewById(R.id.car_from);


        car_from.setText(sharedPreferences.getString(from_data,""));
        car_to.setText(sharedPreferences.getString(to_data,""));

        from_code =  sharedPreferences.getString(from_data,"").toLowerCase();
        to_code = sharedPreferences.getString(to_data,"").toLowerCase();




        car_from.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final CountryPicker picker = CountryPicker.newInstance("Select Country");
                picker.setListener(new CountryPickerListener() {

                    @Override
                    public void onSelectCountry(String name, String code) {
                        car_from.setText(name);
                        from_code = name.toLowerCase();
                        editor.putString(from_data,name);
                        editor.apply();
                        picker.dismiss();
                    }
                });

                picker.show(getChildFragmentManager(), "COUNTRY_PICKER");
            }
        });
          car_to.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final CountryPicker picker = CountryPicker.newInstance("Select Country");
                picker.setListener(new CountryPickerListener() {

                    @Override
                    public void onSelectCountry(String name, String code) {
                        car_to.setText(name);
                        to_code = name.toLowerCase();
                        editor.putString(to_data,name);
                        editor.apply();
                        picker.dismiss();
                    }
                });

                picker.show(getChildFragmentManager(), "COUNTRY_PICKER");
            }
        });





        search=(Button) inflated.findViewById(R.id.search_hotels);

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getContext(), WebViewInvoice.class);
                intent.putExtra("check_type","ivisa");
                intent.putExtra("from_code",from_code.toLowerCase());
                intent.putExtra("to_code",to_code.toLowerCase());
                startActivity(intent);

            }
        });


        return inflated;

    }

    public void OnClickHotelSelect()
    {

        Intent intent=new Intent(getContext(), DateActivity.class);
        startActivityForResult(intent,3);
        getActivity().overridePendingTransition(R.anim.enter, R.anim.exit);
    }
}
