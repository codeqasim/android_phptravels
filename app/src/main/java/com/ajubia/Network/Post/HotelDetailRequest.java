package com.ajubia.Network.Post;

import com.ajubia.Models.HotelModel;
import com.ajubia.Models.OtaModel;
import com.ajubia.utality.Extra.Constant;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

import static com.ajubia.utality.Extra.Constant.otaModel;

public class HotelDetailRequest extends StringRequest {
    private Map<String,String> params;
    private static final String registerUrl= Constant.domain_name+"ota/hotels/detail";
    public HotelDetailRequest( String ota_id,String hotel_slug,Response.Listener<String> listener,Response.ErrorListener errorListener) {
        super(Method.POST, registerUrl, listener, null);

        params=new HashMap<>();
        params.put("ota_id",ota_id);
        params.put("hotel_slug",hotel_slug);


    }
    @Override
    public Map<String, String> getParams() {
        return params;
    }

}
