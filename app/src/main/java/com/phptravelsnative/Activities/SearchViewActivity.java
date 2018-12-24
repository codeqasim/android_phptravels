package com.phptravelsnative.Activities;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.phptravelsnative.Adapters.AutoSuggestedAdapter;
import com.phptravelsnative.Models.Auto_Model;
import com.phptravelsnative.R;
import com.phptravelsnative.utality.Extra.ComplexPreferences;
import com.phptravelsnative.utality.Extra.Constant;
import com.phptravelsnative.utality.Views.SingleTonRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class SearchViewActivity extends AppCompatActivity {

    EditText searchName;
    Destinations adatpter;
    ImageView clear_btn;
    Intent intent;
    String MyPREFERENCES = "MyPrefs";
    Auto_Model selectedValue=new Auto_Model();
    AutoSuggestedAdapter adapter=null;

    private String COUNTRY_URL =
            Constant.domain_name+"hotels/suggestions?appKey="+Constant.key+"&query=";
    ComplexPreferences complexPreferences;
     ArrayList<Auto_Model> auto_models=new ArrayList<>();
    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_view);

        searchName=(EditText) findViewById(R.id.auto_search);
        listView=(ListView)findViewById(R.id.listview);
         complexPreferences=new ComplexPreferences(this,MyPREFERENCES,Context.MODE_PRIVATE);

        clear_btn=(ImageView)findViewById(R.id.clearButton);

        clear_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchName.setText("");
            }
        });

        intent=getIntent();
        String ModuleType=intent.getStringExtra("ModuleTyple");
        if(ModuleType.equals("Bus"))
        {
            BusSearch();
        }
        else if(ModuleType.equals("DefaultHotel")){

            selectedValue=complexPreferences.getObject("Default_Hotel_Last_Search",Auto_Model.class,selectedValue);
            searchName.setText(selectedValue.getName());
            DefaultHotel(selectedValue.getName());

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    complexPreferences.putObject("Default_Hotel_Last_Search",auto_models.get(position));

                    complexPreferences.commit();
                    finish();
                    overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);

                }
            });
            searchName.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if(s.length()>=2) {

                        DefaultHotel(s.toString());
                    }
                    if(searchName.getText().length()>0)
                        clear_btn.setVisibility(View.VISIBLE);
                    else
                        clear_btn.setVisibility(View.GONE);
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
        }else if(ModuleType.equals("ExpediaHotel")){

            selectedValue=complexPreferences.getObject("Expedia_Hotel_Last_Search",Auto_Model.class,selectedValue);
            searchName.setText(selectedValue.getName());
            ExpediaSearch(selectedValue.getName());

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    complexPreferences.putObject("Expedia_Hotel_Last_Search",auto_models.get(position));

                    complexPreferences.commit();
                    finish();
                    overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);

                }
            });
            searchName.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if(s.length()>=2) {

                        ExpediaSearch(s.toString());
                    }
                    if(searchName.getText().length()>0)
                        clear_btn.setVisibility(View.VISIBLE);
                    else
                        clear_btn.setVisibility(View.GONE);
                }
                @Override
                public void afterTextChanged(Editable s) {

                }
            });
        }else if(ModuleType.equals("TravelPort_from")){

            selectedValue=complexPreferences.getObject("TravelPort_from",Auto_Model.class,selectedValue);
            searchName.setText(selectedValue.getName());
            TravelPortSearch(selectedValue.getName());

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    complexPreferences.putObject("TravelPort_from",auto_models.get(position));

                    complexPreferences.commit();
                    finish();
                    overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);

                }
            });
            searchName.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if(s.length()>=2) {

                        TravelPortSearch(s.toString());
                    }
                    if(searchName.getText().length()>0)
                        clear_btn.setVisibility(View.VISIBLE);
                    else
                        clear_btn.setVisibility(View.GONE);
                }
                @Override
                public void afterTextChanged(Editable s) {

                }
            });
        }else if(ModuleType.equals("TravelPort_to")){

            selectedValue=complexPreferences.getObject("TravelPort_to",Auto_Model.class,selectedValue);
            searchName.setText(selectedValue.getName());
            TravelPortSearch(selectedValue.getName());

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    complexPreferences.putObject("TravelPort_to",auto_models.get(position));

                    complexPreferences.commit();
                    finish();
                    overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);

                }
            });
            searchName.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if(s.length()>=2) {

                        TravelPortSearch(s.toString());
                    }
                    if(searchName.getText().length()>0)
                        clear_btn.setVisibility(View.VISIBLE);
                    else
                        clear_btn.setVisibility(View.GONE);
                }
                @Override
                public void afterTextChanged(Editable s) {

                }
            });
        }else if(ModuleType.equals("Tour")){

            selectedValue=complexPreferences.getObject("TourObject",Auto_Model.class,selectedValue);
            searchName.setText(selectedValue.getName());
            Tours(selectedValue.getName());

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    complexPreferences.putObject("TourObject",auto_models.get(position));

                    complexPreferences.commit();
                    finish();
                    overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);

                }
            });
            searchName.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if(s.length()>=2) {

                        Tours(s.toString());
                    }
                    if(searchName.getText().length()>0)
                        clear_btn.setVisibility(View.VISIBLE);
                    else
                        clear_btn.setVisibility(View.GONE);
                }
                @Override
                public void afterTextChanged(Editable s) {

                }
            });
        }else if(ModuleType.equals("CarTo")){

            CarsTo();

        }else if(ModuleType.equals("CarFrom")){

            CarsFrom();
        }
        searchName.setSelection(searchName.getText().length());

        if(searchName.getText().length()>0)
            clear_btn.setVisibility(View.VISIBLE);
        else
            clear_btn.setVisibility(View.GONE);


    }

    public  void Tours(String term)
    {
        COUNTRY_URL =
                Constant.domain_name+"tours/suggestions?appKey="+Constant.key+"&query=";
        StringRequest stringRequest = new StringRequest(Request.Method.GET,COUNTRY_URL+term+"&lang="+Constant.default_lang, new Response.Listener() {
            @Override
            public void onResponse(Object response) {

                JSONObject parentObject = null;
                auto_models.clear();
                try {
                    parentObject = new JSONObject(response.toString());
                    JSONArray jsonArray= parentObject.getJSONArray("response");
                    Auto_Model auto_model;
                    for (int i = 0; i <jsonArray.length(); i++) {
                        JSONObject jo = jsonArray.getJSONObject(i);
                        if(!jo.getString("text").equals("")) {
                            auto_model = new Auto_Model();
                            auto_model.setType(jo.getString("module"));
                            auto_model.setName(jo.getString("text"));
                            auto_model.setId(jo.getInt("id"));
                            if(jo.getString("module").equals("tour"))
                            {
                                auto_model.setImage_id(R.drawable.ic_tour_icon);
                            }else
                            {
                                auto_model.setImage_id(R.drawable.location_search);

                            }
                            auto_models.add(auto_model);
                        }
                    }
                }
                catch(JSONException e){

                    Log.d("abcwwwwd",e.getMessage());
                }
                if(adapter==null) {
                    adapter = new AutoSuggestedAdapter(getApplicationContext(), android.R.layout.simple_dropdown_item_1line, auto_models);
                    listView.setAdapter(adapter);
                }else
                {
                    adapter.notifyDataSetChanged();
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
                Log.e("HamzaError" + "-", logText, error);
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(50000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(stringRequest);
    }
    public void BusSearch()
    {
        searchName.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                if(adatpter!=null)
                    adatpter.getFilter().filter(s.toString());

                if(searchName.getText().length()>0)
                    clear_btn.setVisibility(View.VISIBLE);
                else
                    clear_btn.setVisibility(View.GONE);

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        final int check=intent.getIntExtra("checkSearchType",-1);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if(check==0)
                    complexPreferences.putObject("busObjectFrom",adatpter.getItem(position));
                else if(check==1)
                    complexPreferences.putObject("busObjectTo",adatpter.getItem(position));

                complexPreferences.commit();
                finish();
                overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
            }
        });
        Response.Listener response_listener = new Response.Listener<String>() {

            Auto_Model auto_model;

            @Override
            public void onResponse(String response) {
                JSONObject parentObject = null;
                try {
                    parentObject = new JSONObject(response);
                    JSONObject mainObject=parentObject.getJSONObject("response");
                    JSONArray ciities=mainObject.getJSONArray("cities");

                    for(int i=0;i<ciities.length();i++)
                    {
                        JSONObject jsonObject=ciities.getJSONObject(i);
                        auto_model=new Auto_Model();
                        auto_model.setName(jsonObject.getString("name"));
                        auto_model.setId(jsonObject.getInt("id"));
                        auto_model.setImage_id(R.drawable.location_search);
                        auto_models.add(auto_model);
                    }
                    adatpter=   new Destinations(SearchViewActivity.this,auto_models);
                    listView.setAdapter(adatpter);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        };

        StringRequest  stringRequest = new StringRequest(Request.Method.GET, Constant.domain_name+"bus/cities?appKey="+Constant.key
                , response_listener, null);

        RequestQueue requestQueue = SingleTonRequest.getmInctance(this).getRequestQueue();
        requestQueue.add(stringRequest);
    }

    public  void DefaultHotel(String term)
    {

        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                COUNTRY_URL+term+"&lang="+Constant.default_lang
                ,
                new Response.Listener() {
                    @Override
                    public void onResponse(Object response) {

                        JSONObject parentObject = null;
                        auto_models.clear();
                        try {
                            parentObject = new JSONObject(response.toString());
                            JSONArray jsonArray= parentObject.getJSONArray("response");
                            Auto_Model auto_model;
                            for (int i = 0; i <jsonArray.length(); i++) {
                                JSONObject jo = jsonArray.getJSONObject(i);
                                if(!jo.getString("text").equals("")) {
                                    auto_model = new Auto_Model();
                                    auto_model.setType(jo.getString("module"));
                                    auto_model.setName(jo.getString("text"));

                                    if(jo.getString("module").equals("hotel"))
                                    {
                                        auto_model.setImage_id(R.drawable.hotel_search);
                                    }else
                                    {
                                        auto_model.setImage_id(R.drawable.location_search);
                                    }
                                    auto_model.setId(jo.getInt("id"));
                                    auto_models.add(auto_model);
                                }
                            }
                        }
                        catch(JSONException e){

                            Log.d("abcwwwwd",e.getMessage());
                        }
                        if(adapter==null) {
                            adapter = new AutoSuggestedAdapter(getApplicationContext(), android.R.layout.simple_dropdown_item_1line, auto_models);
                            listView.setAdapter(adapter);
                        }else
                        {
                            adapter.notifyDataSetChanged();
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
                Log.e("HamzaError" + "-", logText, error);
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(50000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(stringRequest);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
    }

    public  void ExpediaSearch(String term)
    {
        Log.d("CheckValue","http://yasen.hotellook.com/autocomplete?lang=en-US&limit=10&term="+term);
        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                "http://yasen.hotellook.com/autocomplete?lang=en-US&limit=10&term="+term                ,
                new Response.Listener() {
                    @Override
                    public void onResponse(Object response) {

                        JSONObject parentObject = null;
                        auto_models.clear();
                        try {
                            parentObject = new JSONObject(response.toString());
                            JSONArray jsonArray= parentObject.getJSONArray("cities");
                            Auto_Model auto_model;
                            for (int i = 0; i <jsonArray.length(); i++) {
                                JSONObject jo = jsonArray.getJSONObject(i);
                                auto_model=new Auto_Model();
                                auto_model.setName(jo.getString("fullname"));
                                int a = getResources().getIdentifier("flag_"+jo.getString("countryCode").toLowerCase(), "drawable", getApplicationContext().getPackageName());
                                auto_model.setImage_id(a);
                                auto_models.add(auto_model);
                            }
                        }
                        catch(JSONException e){

                            Log.d("abcwwwwd",e.getMessage());
                        }
                        if(adapter==null) {
                            adapter = new AutoSuggestedAdapter(getApplicationContext(), android.R.layout.simple_dropdown_item_1line, auto_models);
                            listView.setAdapter(adapter);
                        }else
                        {
                            adapter.notifyDataSetChanged();

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
                Log.e("HamzaError" + "-", logText, error);
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(50000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(stringRequest);
    }

    public  void TravelPortSearch(String term)
    {
        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                Constant.domain_name+"suggession/airports?appKey="+Constant.key+"&q="+term,
                new Response.Listener() {
                    @Override
                    public void onResponse(Object response) {

                        JSONObject parentObject = null;
                        auto_models.clear();
                        try {
                            parentObject = new JSONObject(response.toString());
                            JSONArray jsonArray= parentObject.getJSONArray("response");
                            Auto_Model auto_model;
                            for (int i = 0; i <jsonArray.length(); i++) {
                                JSONObject jo = jsonArray.getJSONObject(i);
                                auto_model=new Auto_Model();
                                auto_model.setName(jo.getString("text"));
                                auto_model.setType(jo.getString("id"));
                                int a = getResources().getIdentifier("flag_"+jo.getString("countryCode").toLowerCase(), "drawable", getApplicationContext().getPackageName());
                                auto_model.setImage_id(a);
                                auto_models.add(auto_model);
                            }
                        }
                        catch(JSONException e){

                            Log.d("abcwwwwd",e.getMessage());
                        }
                        if(adapter==null) {
                            adapter = new AutoSuggestedAdapter(getApplicationContext(), android.R.layout.simple_dropdown_item_1line, auto_models);
                            listView.setAdapter(adapter);
                        }else
                        {
                            adapter.notifyDataSetChanged();

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
                Log.e("HamzaError" + "-", logText, error);
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(50000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(stringRequest);
    }

    private void CarsTo()
    {


        selectedValue=complexPreferences.getObject("carObjectTo",Auto_Model.class,selectedValue);

        searchName.setText(selectedValue.getName());

        searchName.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                if(adatpter!=null)
                    adatpter.getFilter().filter(s.toString());

                if(searchName.getText().length()>0)
                    clear_btn.setVisibility(View.VISIBLE);
                else
                    clear_btn.setVisibility(View.GONE);

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Toast.makeText(getApplicationContext(),"kfdfdkfd",Toast.LENGTH_LONG).show();
                    complexPreferences.putObject("carObjectTo",adatpter.getItem(position));
                     complexPreferences.commit();
                finish();
                overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
            }
        });

        StringRequest stringRequest = new StringRequest(Request.Method.GET, Constant.domain_name+"cars/locations?appKey="+Constant.key+"&lang="+Constant.default_lang, new Response.Listener() {
            @Override
            public void onResponse(Object response) {


                JSONObject parentObject = null;

                try {
                    parentObject = new JSONObject(response.toString());
                    JSONObject error_object=parentObject.getJSONObject("error");

                    if(error_object.getBoolean("status"))
                    {
                        Toast.makeText(getApplicationContext(),error_object.getString("msg"),Toast.LENGTH_LONG).show();

                    }
                    else
                    {
                        JSONObject paretnObject1 = parentObject.getJSONObject("response");
                        JSONArray parentArray = paretnObject1.getJSONArray("dropoffLocations");

                        Auto_Model auto_model;
                        for(int i=0;i<parentArray.length();i++)
                        {
                            auto_model=new Auto_Model();
                            JSONObject jsonObject=parentArray.getJSONObject(i);
                            auto_model.setName(jsonObject.getString("name"));
                            auto_model.setId(jsonObject.getInt("id"));
                            auto_model.setImage_id(R.drawable.location_search);
                            auto_models.add(auto_model);
                        }
                    }
                    adatpter = new Destinations(getApplicationContext(),auto_models);
                    listView.setAdapter(adatpter);

                }
                catch(JSONException e){
                    Log.d("abcwwwwd",e.getMessage());
                }
            }
        }, null);

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(50000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(stringRequest);
    }
    private void CarsFrom()
    {

        selectedValue=complexPreferences.getObject("carObjectFrom",Auto_Model.class,selectedValue);
        searchName.setText(selectedValue.getName());
        searchName.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                if(adatpter!=null)
                    adatpter.getFilter().filter(s.toString());

                if(searchName.getText().length()>0)
                    clear_btn.setVisibility(View.VISIBLE);
                else
                    clear_btn.setVisibility(View.GONE);

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                complexPreferences.putObject("carObjectFrom",adatpter.getItem(position));
                complexPreferences.commit();
                finish();
                overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
            }
        });
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Constant.domain_name+"cars/locations?appKey="+Constant.key+"&lang="+Constant.default_lang, new Response.Listener() {
            @Override
            public void onResponse(Object response) {


                JSONObject parentObject = null;

                try {
                    parentObject = new JSONObject(response.toString());
                    JSONObject error_object=parentObject.getJSONObject("error");

                    if(error_object.getBoolean("status"))
                    {
                        Toast.makeText(getApplicationContext(),error_object.getString("msg"),Toast.LENGTH_LONG).show();

                    }
                    else
                    {
                        JSONObject paretnObject1 = parentObject.getJSONObject("response");
                        JSONArray to = paretnObject1.getJSONArray("pickupLocations");

                        Auto_Model auto_model;
                        for(int i=0;i<to.length();i++)
                        {
                            auto_model=new Auto_Model();
                            JSONObject jsonObject=to.getJSONObject(i);
                            auto_model.setName(jsonObject.getString("name"));
                            auto_model.setId(jsonObject.getInt("id"));
                            auto_model.setImage_id(R.drawable.location_search);
                            auto_models.add(auto_model);
                        }
                    }
                    adatpter = new Destinations(SearchViewActivity.this,auto_models);
                    listView.setAdapter(adatpter);
                }
                catch(JSONException e){

                    Log.d("abcwwwwd",e.getMessage());
                }

            }
        }, null);

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(50000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(stringRequest);
    }

    public class Destinations extends BaseAdapter implements Filterable {

        private ArrayList<Auto_Model> originalData = null;
        private ArrayList<Auto_Model>filteredData = null;
        private LayoutInflater mInflater;
        private ItemFilter mFilter = new ItemFilter();

        public Destinations(Context context, ArrayList<Auto_Model> data) {
            this.filteredData = data ;
            this.originalData = data ;
            mInflater = LayoutInflater.from(context);
        }

        public int getCount() {
            return filteredData.size();
        }

        public Auto_Model getItem(int position) {
            return filteredData.get(position);
        }

        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            MyViewHolder mViewHolder;

            if (convertView == null) {
                convertView = mInflater.inflate(R.layout.searching, parent, false);
                mViewHolder = new MyViewHolder(convertView);
                convertView.setTag(mViewHolder);
            } else {
                mViewHolder = (MyViewHolder) convertView.getTag();
            }

            final Auto_Model currentListData = getItem(position);

            mViewHolder.tvTitle.setText(currentListData.getName());
            mViewHolder.ivIcon.setImageResource(currentListData.getImage_id());

            return convertView;
        }

        private class MyViewHolder {
            TextView tvTitle;
            ImageView ivIcon;

            public MyViewHolder(View item) {
                tvTitle = (TextView) item.findViewById(R.id.city_name);
                ivIcon = (ImageView) item.findViewById(R.id.iv_icon);

            }
        }

        public Filter getFilter() {
            return mFilter;
        }

        private class ItemFilter extends Filter {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {

                String filterString = constraint.toString().toLowerCase();

                FilterResults results = new FilterResults();

                final ArrayList<Auto_Model> list = originalData;

                int count = list.size();
                final ArrayList<Auto_Model> nlist = new ArrayList<Auto_Model>(count);

                String filterableString ;

                for (int i = 0; i < count; i++) {
                    filterableString = list.get(i).getName();
                    if (filterableString.toLowerCase().contains(filterString)) {
                        nlist.add(list.get(i));
                    }
                }
                results.values = nlist;
                results.count = nlist.size();

                return results;
            }
            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                filteredData = (ArrayList<Auto_Model>) results.values;
                notifyDataSetChanged();
            }
        }
    }
}

