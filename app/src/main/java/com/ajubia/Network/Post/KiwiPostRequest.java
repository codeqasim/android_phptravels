package com.ajubia.Network.Post;

import com.ajubia.utality.Extra.Constant;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class KiwiPostRequest extends StringRequest {
    private Map<String,String> params;
    private static final String registerUrl= Constant.domain_name+"ota/kiwi/validate";
    public KiwiPostRequest( String ota_id,String from,String to,Response.Listener<String> listener,Response.ErrorListener errorListener) {
        super(Method.POST, registerUrl, listener, null);

        params=new HashMap<>();
        params.put("ota_id",ota_id);
        params.put("from",from);
        params.put("to",to);



    }
    @Override
    public Map<String, String> getParams() {
        return params;
    }

}
