package com.phptravelsnative.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.phptravelsnative.Models.ManualFlightModel;
import com.phptravelsnative.Models.OneWay;
import com.phptravelsnative.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class Manual_Flight_Detials_Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;


    private ArrayList<OneWay> data;

    private LayoutInflater inflater;



    private int previousPosition = 0;

    public Manual_Flight_Detials_Adapter(Context context, ArrayList<OneWay> data, String type) {

        this.context = context;
        this.data = data;
        inflater = LayoutInflater.from(context);
    }



    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int position) {

            View view = inflater.inflate(R.layout.manual_flight_detial_child, parent, false);
            MyViewHolder holder = new MyViewHolder(view);
            return holder;

    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {


            MyViewHolder myViewHolder = (MyViewHolder)holder;
            OneWay flightModel = data.get(position);
            myViewHolder.a_date.setText(flightModel.getTo_date());
            myViewHolder.d_date.setText(flightModel.getDate());
            myViewHolder.a_time.setText(flightModel.getTo_time());
            myViewHolder.d_time.setText(flightModel.getTime());
            myViewHolder.l_from.setText(flightModel.getName());
            myViewHolder.l_to.setText(flightModel.getTo_name());

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        TextView l_from;
        TextView l_to;
        TextView d_time;
        TextView a_time;
        TextView d_date;
        TextView a_date;


        public MyViewHolder(View itemView) {
            super(itemView);

            l_from = (TextView) itemView.findViewById(R.id.l_from);
            l_to = (TextView) itemView.findViewById(R.id.l_to);
            d_time = (TextView) itemView.findViewById(R.id.d_time);
            a_time = (TextView) itemView.findViewById(R.id.a_time);
            d_date = (TextView) itemView.findViewById(R.id.d_date);
            a_date = (TextView) itemView.findViewById(R.id.a_date);
        }
    }
}
