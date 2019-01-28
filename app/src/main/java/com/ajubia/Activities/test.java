package com.ajubia.Activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.ajubia.Network.Post.ModuleRequest;
import com.ajubia.R;
import com.ajubia.utality.Extra.Constant;
import com.ajubia.utality.Views.SingleTonRequest;

import org.json.JSONException;
import org.json.JSONObject;

public class test extends AppCompatActivity {

    @Override
    protected void onCreate( @Nullable Bundle savedInstanceState ) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test);



    }
}
