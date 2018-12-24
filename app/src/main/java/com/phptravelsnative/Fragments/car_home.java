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

import com.phptravelsnative.Activities.DateActivity;
import com.phptravelsnative.Activities.SearchViewActivity;
import com.phptravelsnative.Activities.SearchingCarTourOffers;
import com.phptravelsnative.Models.Auto_Model;
import com.phptravelsnative.Models.car_model;
import com.phptravelsnative.R;
import com.phptravelsnative.utality.Extra.ComplexPreferences;
import com.phptravelsnative.utality.Extra.DateSeter;

import java.util.ArrayList;
import java.util.Calendar;


public class car_home extends Fragment {


    EditText car_to;
    EditText car_from;

    TextView time_from,time_to;

    ComplexPreferences sharedPreferences;
    String MyPREFERENCES = "MyPrefs";
    SharedPreferences.Editor editor;

    DatePickerDialog date2;
    Calendar calendar = Calendar.getInstance();

    View inflated;

    Auto_Model car_form_m=new Auto_Model();
    Auto_Model car_to_m=new Auto_Model();

    String date_from_api;
    String date_to_api;

    Button search;
    TextView date_from,date_to;
    DateSeter checkDate;
    View keyBoard;

    ArrayList<Auto_Model> location_from=new ArrayList<>();
    ArrayList<Auto_Model> location_to=new ArrayList<>();
    private int day_to_int;

    public car_home() {
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        inflated = inflater.inflate(R.layout.car_layout, container, false);
        sharedPreferences =new ComplexPreferences(getContext(),MyPREFERENCES, Context.MODE_PRIVATE);
        checkDate = new DateSeter(getContext());

        car_to = (EditText) inflated.findViewById(R.id.car_to);
        car_from = (EditText) inflated.findViewById(R.id.car_from);

        car_form_m=sharedPreferences.getObject("carObjectFrom",Auto_Model.class,car_form_m);
        car_to_m=sharedPreferences.getObject("carObjectTo",Auto_Model.class,car_to_m);

        car_from.setText(car_form_m.getName());
        car_to.setText(car_to_m.getName());



        car_from.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(),SearchViewActivity.class);
                intent.putExtra("ModuleTyple","CarFrom");
                startActivityForResult(intent,0);
                getActivity().overridePendingTransition(R.anim.enter, R.anim.exit);
            }
        });
          car_to.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(),SearchViewActivity.class);
                intent.putExtra("ModuleTyple","CarTo");
                startActivityForResult(intent,1);
                getActivity().overridePendingTransition(R.anim.enter, R.anim.exit);
            }
        });



        date_from=(TextView)inflated.findViewById(R.id.date_from);
        date_to=(TextView)inflated.findViewById(R.id.date_to);


        search=(Button) inflated.findViewById(R.id.search_hotels);

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickButton();
            }
        });



        date_from.setText(checkDate.getCurrentDay()+"/"+checkDate.getCurrentMonthN()+"/"+checkDate.getCurrentYear());

        date_from_api=checkDate.getCurrentMonthN()+"/"+checkDate.getCurrentDay()+"/"+checkDate.getCurrentYear();

        checkDate.incrementBy();

        date_to_api=checkDate.getCurrentMonthN()+"/"+checkDate.getCurrentDay()+"/"+checkDate.getCurrentYear();

        date_to.setText(checkDate.getCurrentDay()+"/"+checkDate.getCurrentMonthN()+"/"+checkDate.getCurrentYear());




        time_from=(TextView)inflated.findViewById(R.id.time_from);
        time_to=(TextView)inflated.findViewById(R.id.time_to);
        time_from.setText(checkDate.getCurrentTime());
        time_to.setText(checkDate.getCurrentTime());


        date_from.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OnClickHotelSelect();
            }
        });
        date_to.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OnClickHotelSelect();
            }
        });
        time_from.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onTimeSeter(v);
            }
        });
        time_to.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onTimeSeter(v);
            }
        });


        return inflated;

    }


    private void showTimePicker(int a) {

        booking_car.TimePickerFragment Time = new booking_car.TimePickerFragment();



        Calendar calender = Calendar.getInstance();
        Bundle args = new Bundle();

        args.putInt("mint", calender.get(Calendar.MINUTE));
        args.putInt("hour", calender.get(Calendar.HOUR_OF_DAY));
        args.putInt("a", a);
        Time.setArguments(args);


        if (a == 1) {
            Time.setCallBack(Time_from_listener);
        } else {
            Time.setCallBack(Time_to_listener);
        }
        Time.show(getFragmentManager(), "Date Picker");


    }
    public void OnClickHotelSelect()
    {

        Intent intent=new Intent(getContext(), DateActivity.class);
        startActivityForResult(intent,3);
        getActivity().overridePendingTransition(R.anim.enter, R.anim.exit);
    }

    public void onTimeSeter(View v)
    {
        if(v.getId()== R.id.time_from)
            showTimePicker(0);
        else
            showTimePicker(1);
    }

    public void onClickButton()
    {
        Intent intent=new Intent(getContext(),SearchingCarTourOffers.class);
        car_model c=new car_model();
        c.setPickupDate(date_from_api);
        c.setDropOfDate(date_to_api);
        c.setDropOfTime(time_to.getText().toString());
        c.setPickupTime(time_from.getText().toString());
        Bundle bundle=new Bundle();
        bundle.putParcelable("carInfo",c);
        bundle.putString("type","cars");
        intent.putExtras(bundle);
        if(!car_to.getText().toString().equals("") || !car_from.getText().toString().equals(""))
        {
            if(car_form_m.getId()==0|| car_to_m.getId()==0)
            {
                Toast.makeText(getContext(),"Nothing Found",Toast.LENGTH_LONG).show();
            }else {
                c.setPickupId(car_form_m.getId());
                c.setDropOfId(car_to_m.getId());
                startActivity(intent);
            }
        }
        else
        {
            c.setPickupId(0);
            c.setDropOfId(0);
            bundle.putParcelable("carInfo",c);
            bundle.putString("type","cars");
            startActivity(intent);
        }
    }
    TimePickerDialog.OnTimeSetListener Time_from_listener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

            time_from.setText(hourOfDay+":"+minute);
            showTimePicker(0);
        }
    };
    TimePickerDialog.OnTimeSetListener Time_to_listener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            time_to.setText(hourOfDay + ":"+minute);
        }
    };
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==0)
        {
            car_form_m=sharedPreferences.getObject("carObjectFrom",Auto_Model.class,car_form_m);
            car_from.setText(car_form_m.getName());
        }else if(requestCode==1)
        {
            car_to_m=sharedPreferences.getObject("carObjectTo",Auto_Model.class,car_to_m);
            car_to.setText(car_to_m.getName());
        }else if(requestCode==3)
        {
            if(data!=null) {
                date_from.setText(data.getStringExtra("date_from"));
                date_from_api = data.getStringExtra("date_from_api");
                date_to_api = data.getStringExtra("date_to_api");
                date_to.setText(data.getStringExtra("date_to"));
            }
        }
    }
}
