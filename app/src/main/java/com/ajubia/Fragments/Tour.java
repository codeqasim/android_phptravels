package com.ajubia.Fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.ajubia.Activities.SearchViewActivity;
import com.ajubia.Activities.SearchingCarTourOffers;
import com.ajubia.Activities.SingleDay;
import com.ajubia.Models.Auto_Model;
import com.ajubia.Models.Model_Tour;
import com.ajubia.Network.Parser.TourRequest;
import com.ajubia.R;
import com.ajubia.utality.Extra.ComplexPreferences;
import com.ajubia.utality.Extra.Constant;
import com.ajubia.utality.Extra.DateSeter;
import com.ajubia.utality.Views.LightSpinner;
import com.ajubia.utality.Views.TourSpinner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class Tour extends Fragment {

    AutoCompleteTextView auto_tour;
    ArrayList<Auto_Model> auto_array_list=new ArrayList<>();
    ArrayList<Auto_Model> tour_type=new ArrayList<>();

    String tour_type_arr[];
    String type_id[];


    Auto_Model SearchName=new Auto_Model();
    ComplexPreferences sharedPreferences;
    String MyPREFERENCES = "MyPrefs";
    DateSeter checkDate;
    TextView date_to;
    String date_to_api;
    TourSpinner tourSpinner;
    ImageView tour_icon;
    LightSpinner adult;
    ProgressBar pb;
    Button search;
    View inflated;
    View date;


    private String item_url =
            Constant.domain_name+"tours/tourtypes?appKey="+Constant.key+"&lang="+Constant.default_lang;
    public Tour() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        inflated= inflater.inflate(R.layout.activity_tours, container, false);

        sharedPreferences =new ComplexPreferences(getContext(),MyPREFERENCES, Context.MODE_PRIVATE);
        checkDate = new DateSeter(getContext());
        auto_tour= (AutoCompleteTextView) inflated.findViewById(R.id.tour_auto_search);
         date=inflated.findViewById(R.id.date);

        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OnClickHotelSelect();
            }
        });

        SearchName=sharedPreferences.getObject("TourObject",Auto_Model.class,SearchName);

        auto_tour.setText(SearchName.getName());


        auto_tour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(),SearchViewActivity.class);
                intent.putExtra("ModuleTyple","Tour");
                startActivityForResult(intent,1);
                getActivity().overridePendingTransition(R.anim.enter, R.anim.exit);
            }
        });
        date_to=(TextView)inflated.findViewById(R.id.date_from);
        search=(Button) inflated.findViewById(R.id.search_hotels);
        tour_icon=(ImageView) inflated.findViewById(R.id.tour_icon);
        tour_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tourSpinner.performClick();
            }
        });

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickButton();
            }
        });

        adult=(LightSpinner) inflated.findViewById(R.id.adult);
        tourSpinner=(TourSpinner) inflated.findViewById(R.id.tour_type);
        adult.setText(String.format("%s 2", getString(R.string.adult)));
        adult.setTag("2");



        pb= (ProgressBar) inflated.findViewById(R.id.tour_type_progress);
        checkResultForTerm();

        date_to_api=checkDate.getCurrentMonthN()+"/"+checkDate.getCurrentDay()+"/"+checkDate.getCurrentYear();
        date_to.setText(checkDate.getCurrentDay()+"/"+checkDate.getCurrentMonthN()+"/"+checkDate.getCurrentYear());

        return inflated;
    }
    public void onClickButton()
    {

        Bundle bundle=new Bundle();
        Model_Tour t=new Model_Tour();
        Intent intent;

        if (auto_tour.getText().toString().equals("")) {

            t.setId(0);
            t.setType(tourSpinner.getTag().toString());
            t.setDate(date_to_api);
            t.setTitle(auto_tour.getText().toString());
            t.setGusest(adult.getText().charAt(adult.length()-1)+"");
            bundle.putParcelable("tours",t);
            intent=new Intent(getContext(),SearchingCarTourOffers.class);
            intent.putExtra("type","tour");
            intent.putExtras(bundle);

            startActivity(intent);

        }else
        {
            t.setId(SearchName.getId());
            t.setType(tourSpinner.getTag().toString());
            t.setDate(date_to_api);
            t.setTitle(auto_tour.getText().toString());
            t.setGusest(adult.getText().charAt(adult.length()-1)+"");
            bundle.putParcelable("tours",t);

            if(SearchName.getType().equals("tour"))
            {
                TourRequest tourRequest=new TourRequest(getContext(),SearchName.getId(),t);
                tourRequest.checkResult();

            }else if(SearchName.getType().equals("location")){
                intent=new Intent(getContext(),SearchingCarTourOffers.class);
                intent.putExtra("type","tour");
                intent.putExtras(bundle);
                startActivity(intent);
            }else
            {

                Toast.makeText(getContext(),"Nothing Found"+auto_tour.getText().toString(),Toast.LENGTH_LONG).show();
            }
        }

    }


    public  void checkResultForTerm()
    {

        StringRequest stringRequest = new StringRequest(Request.Method.GET,item_url, new Response.Listener() {
            @Override
            public void onResponse(Object response) {

                JSONObject parentObject = null;
                try {
                    parentObject = new JSONObject(response.toString());
                    JSONArray jsonArray= parentObject.getJSONArray("response");
                    type_id=new String[jsonArray.length()];
                    tour_type_arr=new String[jsonArray.length()];
                    for (int i = 0; i <jsonArray.length(); i++) {
                        JSONObject jo = jsonArray.getJSONObject(i);
                        tour_type_arr[i]=jo.getString("name");
                        type_id[i]=jo.getInt("id")+"";
                    }
                }
                catch(JSONException e){

                    Log.d("abcwwwwd",e.getMessage());
                }
                tourSpinner.setVisibility(View.VISIBLE);
                tourSpinner.listAdapter(tour_type_arr,type_id);
                pb.setVisibility(View.GONE);
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error == null)
                    return;
                String logText;
                if (error.networkResponse == null) {
                    logText = error.getMessage();
                } else {
                    logText = error.getMessage() + ", status "
                            + error.networkResponse.statusCode
                            + " - " + error.networkResponse.toString();
                }
                Log.e("HamzaError" + "-", logText, error);
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(50000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(stringRequest);
    }
    public void OnClickHotelSelect()
    {
        Intent intent=new Intent(getContext(),SingleDay.class);
        intent.putExtra("check","tour");
        startActivityForResult(intent,0);
        getActivity().overridePendingTransition(R.anim.enter, R.anim.exit);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==0) {
            if(data!=null) {
                date_to_api = data.getStringExtra("date_to_api");
                date_to.setText(data.getStringExtra("date_to"));
            }
        }else if(requestCode==1)
        {
            SearchName=sharedPreferences.getObject("TourObject",Auto_Model.class,SearchName);
            auto_tour.setText(SearchName.getName());
        }
    }

}
