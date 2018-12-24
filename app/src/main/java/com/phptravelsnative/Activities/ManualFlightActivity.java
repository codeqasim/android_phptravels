package com.phptravelsnative.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewStub;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonObject;
import com.phptravelsnative.Adapters.Manual_Flight_Adapter;
import com.phptravelsnative.Adapters.Tp_DoubleListing_adapter;
import com.phptravelsnative.Adapters.Tp_Listing_adapter;
import com.phptravelsnative.Models.ManualFlightModel;
import com.phptravelsnative.Models.OneWay;
import com.phptravelsnative.Models.TravelPort;
import com.phptravelsnative.Models.TravelPortDetails;
import com.phptravelsnative.Models.TravelPortModel;
import com.phptravelsnative.R;
import com.phptravelsnative.utality.Extra.Constant;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

public class ManualFlightActivity extends Drawer {


    Tp_Listing_adapter adapter;
    ArrayList<TravelPortModel> tpm = new ArrayList<>();
    TravelPort cabin_class = new TravelPort();
    RecyclerView recyclerView;

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

        String url = "";
        dialog.show();

        if(!cabin_class.getTour_type().equals("oneway"))
        {
            cabin_class.setTour_type("return");
        }

            url = Constant.domain_name+"flights/search?appKey="+Constant.key+"&from="+cabin_class.getFrom_id()+"&to="+cabin_class.getTo_id()+
                    "&departure_date="+cabin_class.getFrom_date()+"&arrival_date="+cabin_class.getTo_date()+"&type="+cabin_class.getTour_type()+
                    "&cabinclass="+cabin_class.getClass_type()+"&adults="+cabin_class.getAdults()+"&childs="+cabin_class.getChild()
                    +"&infants="+cabin_class.getInfants();




        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                url,
                new Response.Listener() {
                    @Override
                    public void onResponse(Object response) {

                        ArrayList<ManualFlightModel> array_main = new ArrayList<>();

                        JSONObject parentObject = null;
                        try {
                            parentObject = new JSONObject(response.toString());
                            if (!parentObject.getJSONObject("error").getBoolean("status")) {

                                JSONArray main_array = parentObject.getJSONArray("response");
                                for(int i=0;i<main_array.length();i++)
                                {
                                    JSONObject oneWay = main_array.getJSONObject(i).getJSONObject("oneway");
                                    JSONObject  return_ = main_array.getJSONObject(i).getJSONObject("return");

                                    ManualFlightModel mainObject = new ManualFlightModel();
                                    mainObject.setDes(oneWay.getString("desc_flight"));
                                    mainObject.setTotal_time(oneWay.getString("total_hours"));
                                    mainObject.setId(oneWay.getString("id"));
                                    mainObject.setPrice(oneWay.getString("price"));
                                    mainObject.setCurrSymbol(oneWay.getString("currency"));
                                    mainObject.setCurrCode(oneWay.getString("symbol"));
                                    mainObject.setAero_name(oneWay.getString("name"));

                                    for(int j =0;j<oneWay.getJSONArray("mainArray").length();j++)
                                    {
                                        JSONObject jsonInnerObject = oneWay.getJSONArray("mainArray").getJSONObject(j);
                                        OneWay innerObject = new OneWay();
                                        innerObject.setCode(jsonInnerObject.getString("from_code"));
                                        innerObject.setDate(jsonInnerObject.getString("from_date"));
                                        innerObject.setName(jsonInnerObject.getString("from_label"));
                                        innerObject.setFlight_no(jsonInnerObject.getString("flight_no"));
                                        innerObject.setTime(jsonInnerObject.getString("from_time"));
                                        innerObject.setImg(jsonInnerObject.getString("aero_img"));
                                        innerObject.setTo_code(jsonInnerObject.getString("to_code"));
                                        innerObject.setTo_date(jsonInnerObject.getString("to_date"));
                                        innerObject.setTo_time(jsonInnerObject.getString("to_time"));
                                        innerObject.setTo_name(jsonInnerObject.getString("to_label"));
                                        mainObject.getModels_array().add(innerObject);
                                    }
                                    if(!cabin_class.getTour_type().equals("oneway")){

                                        for(int j =0;j<return_.getJSONArray("mainArray").length();j++)
                                        {
                                            JSONObject jsonInnerObject = return_.getJSONArray("mainArray").getJSONObject(j);
                                            OneWay innerObject = new OneWay();
                                            innerObject.setCode(jsonInnerObject.getString("from_code"));
                                            innerObject.setDate(jsonInnerObject.getString("from_date"));
                                            innerObject.setName(jsonInnerObject.getString("from_label"));
                                            innerObject.setFlight_no(jsonInnerObject.getString("flight_no"));
                                            innerObject.setTime(jsonInnerObject.getString("from_time"));
                                            innerObject.setImg(jsonInnerObject.getString("aero_img"));
                                            innerObject.setTo_code(jsonInnerObject.getString("to_code"));
                                            innerObject.setTo_date(jsonInnerObject.getString("to_date"));
                                            innerObject.setTo_time(jsonInnerObject.getString("to_time"));
                                            innerObject.setTo_name(jsonInnerObject.getString("to_label"));

                                            mainObject.getReturn_array().add(innerObject);
                                        }

                                    }
                                    array_main.add(mainObject);
                                }

                                Manual_Flight_Adapter adapter = new Manual_Flight_Adapter(ManualFlightActivity.this, array_main,cabin_class);
                                recyclerView.setAdapter(adapter);
                                recyclerView.setLayoutManager(new LinearLayoutManager(ManualFlightActivity.this));




                            } else {

                                Toast.makeText(getBaseContext(), parentObject.getJSONObject("error").getString("msg"), Toast.LENGTH_LONG).show();
                                finish();

                            }
                            dialog.dismiss();


                        } catch (JSONException e) {

                            Log.d("abcwwwwd", e.getMessage());
                            Toast.makeText(getBaseContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                            dialog.dismiss();
                            finish();
                        }
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
                Toast.makeText(getBaseContext(),logText,Toast.LENGTH_LONG).show();
                finish();


            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(150000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(stringRequest);
    }


}
