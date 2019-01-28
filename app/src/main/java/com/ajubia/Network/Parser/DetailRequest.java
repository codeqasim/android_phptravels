package com.ajubia.Network.Parser;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.ajubia.Network.Post.HotelDetailRequest;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.ajubia.Activities.HotelDetail;
import com.ajubia.Models.Amenities_Model;
import com.ajubia.Models.DetailModel;
import com.ajubia.Models.HotelModel;
import com.ajubia.Models.Hotel_data;
import com.ajubia.Models.OverView;
import com.ajubia.Models.review_model;
import com.ajubia.Models.rooms_model;
import com.ajubia.R;
import com.ajubia.utality.Extra.Constant;

import java.util.ArrayList;

import static com.ajubia.utality.Extra.Constant.otaModel;

/**
 * Created by apple on 23/10/2016.
 */

public class DetailRequest implements NetworkRequest {


    Context context;
    OverView overView;

   ArrayList<review_model> review_list;
   ArrayList<HotelModel> related_list;
   ArrayList<Amenities_Model> amenities_list;
   ArrayList<rooms_model> room_list;
   ArrayList<DetailModel> arrayList;
   static  ProgressDialog dialog;
    Hotel_data hotel_data;
    int id;
    String hotel_slug;


    public DetailRequest(Context c, String hotel_slug, Hotel_data hotel) {
        context=c;
       this. hotel_data=hotel;
       this. hotel_slug=hotel_slug;
        dialog = new ProgressDialog(c,
                R.style.AppTheme_Dark_Dialog);
        dialog.setIndeterminate(true);
        dialog.setCancelable(true);
        dialog.setMessage(c.getString(R.string.loading));

        overView=new OverView();
        review_list=new ArrayList<>();
        amenities_list=new ArrayList<>();
        room_list=new ArrayList<>();

        arrayList=new ArrayList<>();
        this.id=id;

    }


    @Override
    public  void checkResult()
    {
        Response.Listener listener=new Response.Listener() {
            @Override
            public void onResponse( Object response ) {
                Log.d("DetailRequest", response.toString());
                Default_Detail_Parser default_detail_parser=new Default_Detail_Parser(context,hotel_data);
                default_detail_parser.execute(response);
            }
        };

        Response.ErrorListener errorListener=new Response.ErrorListener() {
            @Override
            public void onErrorResponse( VolleyError error ) {
                Log.d("DetailRequest", error.getMessage());
            }
        };

        HotelDetailRequest hotelDetailRequest=new HotelDetailRequest(otaModel.getOta_id(),hotel_slug,listener, errorListener);

//        StringRequest stringRequest = new StringRequest(Request.Method.GET,
//                Constant.domain_name+"hotels/hoteldetails?appKey="+Constant.key+"&id="+id+"&checkin="+hotel_data.getFrom()+"&checkout="+hotel_data.getTo()+"&lang="+Constant.default_lang
//                        +"&child="+hotel_data.getChild()+"&adults="+hotel_data.getAdult()                ,
//                new Response.Listener() {
//            @Override
//            public void onResponse(Object response) {
//
//                Default_Detail_Parser default_detail_parser=new Default_Detail_Parser(context,hotel_data);
//                default_detail_parser.execute(response);
//
//
//
//            }
//        }, new Response.ErrorListener() {
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
//                Log.e("HamzaError" + "-", logText, error);
//                dialog.dismiss();
//            }
//        });

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        hotelDetailRequest.setRetryPolicy(new DefaultRetryPolicy(50000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(hotelDetailRequest);
        dialog.show();
    }
    public void callmethod() {

        Intent mIntent=new Intent(context,HotelDetail.class);

        Bundle mBundle = new Bundle();
        mBundle.putParcelableArrayList("arrayList", arrayList);
                mBundle.putParcelableArrayList("review", review_list);
                mBundle.putParcelableArrayList("room", room_list);
                mBundle.putParcelableArrayList("am", amenities_list);
                mBundle.putParcelableArrayList("related", related_list);
                mBundle.putParcelable("ov", overView);
                mBundle.putParcelable("hotel", hotel_data);

        mIntent.putExtras(mBundle);
        dialog.dismiss();
        context.startActivity(mIntent);
    }

}
