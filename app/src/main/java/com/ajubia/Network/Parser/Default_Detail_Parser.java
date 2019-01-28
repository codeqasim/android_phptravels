package com.ajubia.Network.Parser;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import com.ajubia.Activities.HotelDetail;
import com.ajubia.Models.Amenities_Model;
import com.ajubia.Models.DetailModel;
import com.ajubia.Models.HotelModel;
import com.ajubia.Models.Hotel_data;
import com.ajubia.Models.OverView;
import com.ajubia.Models.review_model;
import com.ajubia.Models.rooms_model;
import com.ajubia.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class Default_Detail_Parser extends AsyncTask<Object,Void,String> {

    Context context;
    OverView overView;

    ArrayList<review_model> review_list;
    ArrayList<HotelModel> related_list;
    ArrayList<Amenities_Model> amenities_list;
    ArrayList<rooms_model> room_list;
    ArrayList<DetailModel> arrayList;
    ProgressDialog dialog;
    Hotel_data hotel_data;

    public Default_Detail_Parser(Context c, Hotel_data hotel)
    {
        context=c;
        this.hotel_data=hotel;
        dialog = new ProgressDialog(c,
                R.style.AppTheme_Dark_Dialog);
        dialog.setIndeterminate(true);
        dialog.setCancelable(true);
        dialog.setMessage(c.getString(R.string.loading));

        overView=new OverView();
        review_list=new ArrayList<>();
        amenities_list=new ArrayList<>();
        room_list=new ArrayList<>();
        related_list=new ArrayList<>();
        arrayList=new ArrayList<>();

    }
    @Override
    protected String doInBackground(Object... params) {



        JSONObject parentObject = null;
        try {
            parentObject = new JSONObject((String) params[0]);
            JSONObject dataObject=parentObject.getJSONObject("data");
            JSONArray amenities_array=dataObject.getJSONArray("amenities");
            JSONArray room_array=dataObject.getJSONArray("rooms");
            JSONArray image_array= dataObject.getJSONArray("images");


            overView.setPolicy("");
            overView.setLatitude(dataObject.getDouble("latitude"));
            overView.setLongitude(dataObject.getDouble("longitude"));
            overView.setDesc(dataObject.getString("description"));
            overView.setTitle(dataObject.getString("company_name"));
            overView.setId(dataObject.getInt("id"));


//            StringBuilder sbL=new StringBuilder();
//            StringBuilder sbR=new StringBuilder();
//            for(int i=0;i<payments_array.length();i++)
//            {
//                JSONObject payment_object=payments_array.getJSONObject(i);
//                if(i%2!=0)
//                    sbL.append(payment_object.getString("name")+"90");
//                else
//                    sbR.append(payment_object.getString("name")+"90");
//            }
            HotelModel hm = new HotelModel();
                hm.setName(dataObject.getString("company_name"));
                hm.setPrice("");
                hm.setStarsCount(Integer.parseInt(dataObject.getString("rating")));
                hm.setThumbnail(dataObject.getString("thumb"));
                hm.setRating(dataObject.getString("rating") );
                hm.setcurrCode("");

//                if(finalObject.getString("currSymbol").equals("null"))
//                    hm.setcurrSymbol("");
//                else
//                hm.setcurrSymbol(finalObject.getString("currSymbol"));

               // hm.setLocation(dataObject.getJSONObject("avgReviews").getString("overall"));
                hm.setid(dataObject.getInt("id"));
                related_list.add(hm);

            overView.setParmentsRights("");
           overView.setPaymentsLefts("");
            DetailModel dm;
            for(int i=0;i<image_array.length();i++)
            {
                dm=new DetailModel();

//                JSONObject child_ob=image_array.getJSONObject(i);
                dm.setSliderImages(image_array.get(i).toString());
                arrayList.add(dm);
            }
            rooms_model rm;
            for(int i=0;i<room_array.length();i++)
            {
                JSONObject room_object=room_array.getJSONObject(i);
                //if(room_object.getInt("maxQuantity")>0) {
                    rm = new rooms_model();
                    rm.setId(room_object.getString("id"));
                   // rm.setStay(room_object.getJSONObject("Info").getString("stay"));
                   // rm.setQuantity(room_object.getJSONObject("Info").getInt("quantity"));
                    rm.setTitle(room_object.getString("room_name"));
                   // rm.setCurrencyCode(room_object.getString("currCode"));
                    rm.setPrice(room_object.getString("price"));
                    rm.setImage(room_object.getString("images"));
                    room_list.add(rm);
                }
           // }
            review_model rv;
//            for(int i=0;i<review_array.length();i++)
//            {
                rv=new review_model();
               // JSONObject review_object=review_array.getJSONObject(i);
                rv.setRating("");
                rv.setReview_by("");
                rv.setReview_date("");
                rv.setReview_comment("");
                review_list.add(rv);
//            }
            Amenities_Model amenities_model;
            for(int i=0;i<amenities_array.length();i++)
            {
                amenities_model=new Amenities_Model();
                JSONObject amenities_object=amenities_array.getJSONObject(i);
                amenities_model.setIcon(String.valueOf(context.getResources().getDrawable(R.drawable.mainlogo)));
                amenities_model.setName(amenities_object.getString("title"));
                amenities_list.add(amenities_model);
            }

        }
        catch(JSONException e){

            Log.d("abcwwwwd",e.getMessage());
        }
        return "Olamba";
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
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
        DetailRequest.dialog.dismiss();

        context.startActivity(mIntent);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }
}
