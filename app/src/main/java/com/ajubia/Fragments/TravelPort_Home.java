package com.ajubia.Fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.ParseException;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ajubia.Activities.Cabin_Class;
import com.ajubia.Activities.DateActivity;
import com.ajubia.Activities.ManualFlightActivity;
import com.ajubia.Activities.SearchViewActivity;
import com.ajubia.Activities.SearchingHotels;
import com.ajubia.Activities.SingleDay;
import com.ajubia.Activities.TravelPortActivity;
import com.ajubia.Models.Amenities_Model;
import com.ajubia.Models.Auto_Model;
import com.ajubia.Models.Hotel_data;
import com.ajubia.Models.OverView;
import com.ajubia.Models.TravelPort;
import com.ajubia.Network.Parser.DetailRequest;
import com.ajubia.R;
import com.ajubia.utality.Extra.ComplexPreferences;
import com.ajubia.utality.Extra.DateSeter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


public class TravelPort_Home extends Fragment {

    Hotel_data hotel_data;
    ComplexPreferences sharedPreferences;
    String MyPREFERENCES = "MyPrefs";
    TextView date_from, date_to;

    DateSeter checkDate;

    String date_from_api;
    String date_to_api;
    String checkTourType = "oneway";
    TravelPort cabin_data = new TravelPort();

    View inflated;
    Auto_Model searchHotel=new Auto_Model();
    Auto_Model to_travel=new Auto_Model();

    EditText hotel_name,orign;
    RelativeLayout cabin_home;
    TextView cabin_return;
    TextView return_text;
    ImageView oneway;
    private ProgressDialog dialog;
    String type = "";
    Button search;

    public static Fragment newInstance(String type) {
        TravelPort_Home fragment = new TravelPort_Home();


        Bundle args = new Bundle();
        args.putString("type", type);
        fragment.setArguments(args);
        return fragment;
    }

    public TravelPort_Home() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        inflated  =  inflater.inflate(R.layout.tp_home, container, false);

        hotel_data = new Hotel_data();
        sharedPreferences =new ComplexPreferences(getContext(),MyPREFERENCES, Context.MODE_PRIVATE);

        hotel_name= (EditText) inflated.findViewById(R.id.tour_auto_search);
        cabin_home= (RelativeLayout) inflated.findViewById(R.id.cabin_class);
        cabin_return= (TextView) inflated.findViewById(R.id.cabin_return);
        oneway = (ImageView)inflated.findViewById(R.id.oneway);
        orign= (EditText) inflated.findViewById(R.id.orign);
        return_text= (TextView) inflated.findViewById(R.id.return_text);
        type = getArguments().getString("type");
        cabin_data.setAdults("1");
        cabin_data.setChild("0");
        cabin_data.setInfants("0");
        cabin_data.setClass_type("economy");
        checkTourType = "oneway";



        searchHotel=sharedPreferences.getObject("TravelPort_from",Auto_Model.class,searchHotel);
        hotel_name.setText(searchHotel.getName());

        oneway.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                date_to.setText("Add Return");
                date_to.setTypeface(null, Typeface.BOLD);

                return_text.setText("");
                checkTourType = "oneway";
                oneway.setVisibility(View.GONE);
            }
        });


        to_travel=sharedPreferences.getObject("TravelPort_to",Auto_Model.class,searchHotel);
        orign.setText(to_travel.getName());

        cabin_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(getContext(),Cabin_Class.class);
                startActivityForResult(intent,3);
                getActivity().overridePendingTransition(R.anim.enter, R.anim.exit);

            }
        });


        date_from=(TextView)inflated.findViewById(R.id.date_from);
        date_to=(TextView)inflated.findViewById(R.id.date_to);

        search= (Button) inflated.findViewById(R.id.search_hotels);

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickButton();
            }
        });

        date_from.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(getContext(),SingleDay.class);
                intent.putExtra("check","travel_port");
                startActivityForResult(intent,2);
                getActivity().overridePendingTransition(R.anim.enter, R.anim.exit);
            }
        });
        date_to.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OnClickHotelSelect();
            }
        });
        date_to.setTypeface(null, Typeface.BOLD);

          checkDate=new DateSeter(getContext());

        hotel_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(),SearchViewActivity.class);
                intent.putExtra("ModuleTyple","TravelPort_from");
                startActivityForResult(intent,1);
                getActivity().overridePendingTransition(R.anim.enter, R.anim.exit);
            }
        });

        orign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(),SearchViewActivity.class);
                intent.putExtra("ModuleTyple","TravelPort_to");
                startActivityForResult(intent,4);
                getActivity().overridePendingTransition(R.anim.enter, R.anim.exit);

            }
        });

        date_from.setText(checkDate.getCurrentDay()+"/"+checkDate.getCurrentMonthN()+"/"+checkDate.getCurrentYear());

        date_from_api=checkDate.getCurrentDay()+"/"+checkDate.getCurrentMonthN()+"/"+checkDate.getCurrentYear();


        checkDate.incrementBy();

        date_to_api=checkDate.getCurrentDay()+"/"+checkDate.getCurrentMonthN()+"/"+checkDate.getCurrentYear();



        return inflated;
    }

    public void onClickButton() {

        if(type.equals("manual"))
        {
//            cabin_data.setFrom_id(searchHotel.getType());
//            cabin_data.setTo_id(to_travel.getType());
            cabin_data.setFrom_date(date_from.getText().toString());
            cabin_data.setTo_date(date_to.getText().toString());
            cabin_data.setTour_type(checkTourType);
            cabin_data.setDestination(searchHotel.getCode());
            cabin_data.setOrigin(to_travel.getCode());

            Intent intent = new Intent(getContext(),ManualFlightActivity.class);
            Bundle b = new Bundle();
            b.putParcelable("cabin_date",cabin_data);
            intent.putExtras(b);
            startActivity(intent);
        }else{
            cabin_data.setFrom_id(searchHotel.getType());
            cabin_data.setTo_id(to_travel.getType());
            cabin_data.setFrom_date(date_from_api);
            cabin_data.setTo_date(date_to_api);
            cabin_data.setTour_type(checkTourType);

            Intent intent = new Intent(getContext(),TravelPortActivity.class);
            Bundle b = new Bundle();
            b.putParcelable("cabin_date",cabin_data);
            intent.putExtras(b);
            startActivity(intent);
        }


    }





    public void OnClickHotelSelect()
    {
        if (!checkTourType.equals("oneway")){

            Intent intent=new Intent(getContext(),SingleDay.class);
            intent.putExtra("check","travel_port");
            startActivityForResult(intent,0);
            getActivity().overridePendingTransition(R.anim.enter, R.anim.exit);
        }else {

            Date date = new Date();
            checkTourType = "round";
            Calendar calendar = Calendar.getInstance();
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            try {
                return_text.setText("Return");
                date = dateFormat.parse(date_from_api);
                calendar.setTime(date);
                calendar.add(Calendar.DATE,1);
                oneway.setVisibility(View.VISIBLE);
                date_to_api=checkDate.getCurrentDay()+"/"+checkDate.getCurrentMonthN()+"/"+checkDate.getCurrentYear();
                date_to.setText(calendar.get(Calendar.DAY_OF_MONTH)+"/"+calendar.get(Calendar.MONTH)+"/"+calendar.get(Calendar.YEAR));
                date_to.setTypeface(null, Typeface.NORMAL);

            } catch (java.text.ParseException e) {
                e.printStackTrace();
            }


        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);



        if(requestCode==0) {
            if(data!=null) {
                date_to_api = data.getStringExtra("date_to_api");
                date_to.setText(data.getStringExtra("date_to"));
                date_to.setTypeface(null, Typeface.NORMAL);

            }
        }else if(requestCode==1)
        {
            searchHotel=sharedPreferences.getObject("TravelPort_from",Auto_Model.class,searchHotel);
            hotel_name.setText(searchHotel.getName());
        }else if(requestCode==4)
        {
            to_travel=sharedPreferences.getObject("TravelPort_to",Auto_Model.class,searchHotel);
            orign.setText(to_travel.getName());
        }else if(requestCode==2 && (resultCode == 2))
        {
            date_from.setText(data.getStringExtra("date_to"));
            date_from_api = data.getStringExtra("date_to_api");

        }else if((requestCode==3) && (resultCode == 3))
        {
            cabin_data = data.getExtras().getParcelable("cabinData");
            cabin_return.setText(cabin_data.getAdults()+" Adults "+cabin_data.getChild()+" Childs "+cabin_data.getInfants()+" Infants "
            +cabin_data.getClass_type());
        }
    }


}
