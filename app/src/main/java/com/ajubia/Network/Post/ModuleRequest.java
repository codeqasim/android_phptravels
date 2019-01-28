package com.ajubia.Network.Post;

import android.util.Log;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.JsonObject;
import com.ajubia.utality.Extra.Constant;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ModuleRequest extends StringRequest {
    private Map<String,String> params;
    private static final String registerUrl= Constant.domain_name+"global/getSettings";
    public ModuleRequest( String token, String domainName, String domainType, Response.Listener<String> listener, Response.ErrorListener errorListener) {
        super(Method.POST, registerUrl, listener, null);

        params=new HashMap<>();
        params.put("token",token);
        params.put("ota_domain",domainName);
        params.put("domain_type",domainType);

    }
    @Override
    public Map<String, String> getParams() {
        return params;
    }

}
