package com.ajubia.Network.Post;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.ajubia.utality.Extra.Constant;

import java.util.HashMap;
import java.util.Map;

public class PostHotelRequest extends StringRequest {
    private Map<String,String> params;
    private static final String registerUrl= Constant.domain_name+"ota/hotels/search";
    public PostHotelRequest( String city, String ota_id, String checkin,String checkout, Response.Listener<String> listener, Response.ErrorListener errorListener) {
        super(Method.POST, registerUrl, listener, null);

        params=new HashMap<>();
        params.put("city",city);
        params.put("ota_id",ota_id);
        params.put("checkin",checkin);
        params.put("checkout",checkout);

    }
    @Override
    public Map<String, String> getParams() {
        return params;
    }

}