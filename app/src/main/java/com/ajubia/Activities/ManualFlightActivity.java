package com.ajubia.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewStub;
import android.widget.Toast;

import com.ajubia.Adapters.Manual_Flight_Adapter;
import com.ajubia.Models.RouteModel;
import com.ajubia.Network.Post.KiwiPostRequest;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.ajubia.Adapters.Tp_Listing_adapter;
import com.ajubia.Models.TravelPort;
import com.ajubia.Models.TravelPortModel;
import com.ajubia.R;
import com.ajubia.utality.Extra.Constant;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class ManualFlightActivity extends Drawer {


    Tp_Listing_adapter adapter;
    ArrayList<TravelPortModel> tpm = new ArrayList<>();
    TravelPort cabin_class = new TravelPort();
    RecyclerView recyclerView;
    String url = "";
    DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    StringRequest stringRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ViewStub stub = (ViewStub) findViewById(R.id.layout_stub);
        stub.setLayoutResource(R.layout.fragment_recycler_view);
        View inflated = stub.inflate();

        Intent intent = getIntent();
        cabin_class = intent.getExtras().getParcelable("cabin_date");
        dialog = new ProgressDialog(this,
                R.style.AppTheme_Dark_Dialog);
        recyclerView = inflated.findViewById(R.id.recyclerView);
        dialog.setIndeterminate(true);
        dialog.setCancelable(false);
        dialog.setMessage(getString(R.string.loading));
        ExpediaSearch();
    }

    public void ExpediaSearch() {

        dialog.show();

        if(!cabin_class.getTour_type().equals("oneway"))
        {
            cabin_class.setTour_type("return");
        }

//            url = Constant.domain_name+"flights/search?appKey="+Constant.key+"&from="+cabin_class.getFrom_id()+"&to="+cabin_class.getTo_id()+
//                    "&departure_date="+cabin_class.getFrom_date()+"&arrival_date="+cabin_class.getTo_date()+"&type="+cabin_class.getTour_type()+
//                    "&cabinclass="+cabin_class.getClass_type()+"&adults="+cabin_class.getAdults()+"&childs="+cabin_class.getChild()
//                    +"&infants="+cabin_class.getInfants();
        if(cabin_class.getTo_date().equals("Add Return")){
            cabin_class.setTo_date(cabin_class.getFrom_date());
        }

        url = Constant.domain_name+"ota/kiwi/searching?&flyFrom="+cabin_class.getDestination()+"&to="+cabin_class.getOrigin()+
                "&dateFrom="+cabin_class.getFrom_date()+"&dateTo="+cabin_class.getTo_date()+"&adults="+cabin_class.getAdults()+"&children="+cabin_class.getChild()
                +"&infants="+cabin_class.getInfants()+"&curr="+"us"+"&ota_id="+Constant.otaModel.getOta_id()+
                "&partner="+"phptravels";
        Response.Listener listener=new Response.Listener() {
            @Override
            public void onResponse( Object response ) {
                Log.d("responce", response.toString());
                try {
                    JSONObject parentObject=new JSONObject(response.toString());
                    boolean data=parentObject.getBoolean("data");
                    if(data){
                        stringRequest=new StringRequest(Request.Method.GET,url,new Response.Listener<String>() {
                            @Override
                            public void onResponse( String response ) {
                                Log.d("responce", response);
                                ArrayList<RouteModel> array_main = new ArrayList<>();
                                try {
                                    JSONObject parentObject = new JSONObject(response);
                                    JSONObject dataObject = parentObject.getJSONObject("data");
                                    JSONArray dataArray = dataObject.getJSONArray("data");

                                    ArrayList<String> route=new ArrayList<>();
                                    for(int i=0;i<dataArray.length();i++){

                                    }


                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                                Manual_Flight_Adapter adapter = new Manual_Flight_Adapter(ManualFlightActivity.this, array_main,cabin_class);
                                recyclerView.setAdapter(adapter);
                                recyclerView.setLayoutManager(new LinearLayoutManager(ManualFlightActivity.this));
                                dialog.dismiss();
                            }
                        },new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse( VolleyError error ) {
                                Log.d("responce", error.getMessage());

                            }
                        });
                    }else {
                        Toast.makeText(ManualFlightActivity.this,"Something Wrong",Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                stringRequest.setRetryPolicy(new DefaultRetryPolicy(150000,
                        DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                requestQueue.add(stringRequest);
            }
        };

        Response.ErrorListener errorListener=new Response.ErrorListener() {
            @Override
            public void onErrorResponse( VolleyError error ) {
                Log.d("responce", error.getMessage());

            }
        };


        KiwiPostRequest kiwiPostRequest=new KiwiPostRequest(Constant.otaModel.getOta_id(),cabin_class.getDestination(),cabin_class.getOrigin(),listener,errorListener);

//        StringRequest stringRequest = new StringRequest(Request.Method.GET,
//                url,
//                new Response.Listener() {
//                    @Override
//                    public void onResponse(Object response) {
//
//                        ArrayList<ManualFlightModel> array_main = new ArrayList<>();
//
//                        JSONObject parentObject = null;
//                        try {
//                            parentObject = new JSONObject(response.toString());
//                            if (!parentObject.getJSONObject("error").getBoolean("status")) {
//
//                                JSONArray main_array = parentObject.getJSONArray("response");
//                                for(int i=0;i<main_array.length();i++)
//                                {
//                                    JSONObject oneWay = main_array.getJSONObject(i).getJSONObject("oneway");
//                                    JSONObject  return_ = main_array.getJSONObject(i).getJSONObject("return");
//
//                                    ManualFlightModel mainObject = new ManualFlightModel();
//                                    mainObject.setDes(oneWay.getString("desc_flight"));
//                                    mainObject.setTotal_time(oneWay.getString("total_hours"));
//                                    mainObject.setId(oneWay.getString("id"));
//                                    mainObject.setPrice(oneWay.getString("price"));
//                                    mainObject.setCurrSymbol(oneWay.getString("currency"));
//                                    mainObject.setCurrCode(oneWay.getString("symbol"));
//                                    mainObject.setAero_name(oneWay.getString("name"));
//
//                                    for(int j =0;j<oneWay.getJSONArray("mainArray").length();j++)
//                                    {
//                                        JSONObject jsonInnerObject = oneWay.getJSONArray("mainArray").getJSONObject(j);
//                                        OneWay innerObject = new OneWay();
//                                        innerObject.setCode(jsonInnerObject.getString("from_code"));
//                                        innerObject.setDate(jsonInnerObject.getString("from_date"));
//                                        innerObject.setName(jsonInnerObject.getString("from_label"));
//                                        innerObject.setFlight_no(jsonInnerObject.getString("flight_no"));
//                                        innerObject.setTime(jsonInnerObject.getString("from_time"));
//                                        innerObject.setImg(jsonInnerObject.getString("aero_img"));
//                                        innerObject.setTo_code(jsonInnerObject.getString("to_code"));
//                                        innerObject.setTo_date(jsonInnerObject.getString("to_date"));
//                                        innerObject.setTo_time(jsonInnerObject.getString("to_time"));
//                                        innerObject.setTo_name(jsonInnerObject.getString("to_label"));
//                                        mainObject.getModels_array().add(innerObject);
//                                    }
//                                    if(!cabin_class.getTour_type().equals("oneway")){
//
//                                        for(int j =0;j<return_.getJSONArray("mainArray").length();j++)
//                                        {
//                                            JSONObject jsonInnerObject = return_.getJSONArray("mainArray").getJSONObject(j);
//                                            OneWay innerObject = new OneWay();
//                                            innerObject.setCode(jsonInnerObject.getString("from_code"));
//                                            innerObject.setDate(jsonInnerObject.getString("from_date"));
//                                            innerObject.setName(jsonInnerObject.getString("from_label"));
//                                            innerObject.setFlight_no(jsonInnerObject.getString("flight_no"));
//                                            innerObject.setTime(jsonInnerObject.getString("from_time"));
//                                            innerObject.setImg(jsonInnerObject.getString("aero_img"));
//                                            innerObject.setTo_code(jsonInnerObject.getString("to_code"));
//                                            innerObject.setTo_date(jsonInnerObject.getString("to_date"));
//                                            innerObject.setTo_time(jsonInnerObject.getString("to_time"));
//                                            innerObject.setTo_name(jsonInnerObject.getString("to_label"));
//
//                                            mainObject.getReturn_array().add(innerObject);
//                                        }
//
//                                    }
//                                    array_main.add(mainObject);
//                                }
//
//                                Manual_Flight_Adapter adapter = new Manual_Flight_Adapter(ManualFlightActivity.this, array_main,cabin_class);
//                                recyclerView.setAdapter(adapter);
//                                recyclerView.setLayoutManager(new LinearLayoutManager(ManualFlightActivity.this));
//
//
//
//
//                            } else {
//
//                                Toast.makeText(getBaseContext(), parentObject.getJSONObject("error").getString("msg"), Toast.LENGTH_LONG).show();
//                                finish();
//
//                            }
//                            dialog.dismiss();
//
//
//                        } catch (JSONException e) {
//
//                            Log.d("abcwwwwd", e.getMessage());
//                            Toast.makeText(getBaseContext(), e.getMessage(), Toast.LENGTH_LONG).show();
//                            dialog.dismiss();
//                            finish();
//                        }
//                    }
//
//                }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                if (error == null)
//                    return;
//                String logText;
//                if (error.networkResponse == null) {
//                    logText = error.getMessage();
//                } else {
//                    logText = error.getMessage() + ", status "
//                            + error.networkResponse.statusCode
//                            + " - " + error.networkResponse.toString();
//                }
//                Toast.makeText(getBaseContext(),logText,Toast.LENGTH_LONG).show();
//                finish();
//
//
//            }
//        });

        RequestQueue requestQueuePost = Volley.newRequestQueue(getApplicationContext());
        kiwiPostRequest.setRetryPolicy(new DefaultRetryPolicy(150000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueuePost.add(kiwiPostRequest);


    }


}
