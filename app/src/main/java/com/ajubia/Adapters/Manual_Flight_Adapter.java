package com.ajubia.Adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ajubia.Activities.Manual_flight_detials;
import com.ajubia.Models.RouteModel;
import com.ajubia.Models.TravelPort;
import com.ajubia.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class Manual_Flight_Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;


    private ArrayList<RouteModel> data;

    private LayoutInflater inflater;
    TravelPort cabin_class;


    private int previousPosition = 0;

    public Manual_Flight_Adapter( Context context,ArrayList<RouteModel> data,TravelPort cabin_class) {

        this.context = context;
        this.data = data;
        this.cabin_class = cabin_class;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getItemViewType(int position) {

        if (cabin_class.getTour_type().equals("oneway"))
        {
            return  0;
        }else{
            return  1;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int position) {

        if (position == 1){
            View view = inflater.inflate(R.layout.manual_double_child_flight, parent, false);
            MyViewHolder2 holder = new MyViewHolder2(view);
            return holder;

        }else{
            View view = inflater.inflate(R.layout.child_manualoneway_flight, parent, false);
            MyViewHolder holder = new MyViewHolder(view);
            return holder;

        }

    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {

        if(cabin_class.getTour_type().equals("oneway"))
        {
            MyViewHolder myViewHolder = (MyViewHolder)holder;
            final RouteModel oneWayModel = data.get(position);

            myViewHolder.company_name.setText(oneWayModel.getAirLineName());
            myViewHolder.departure_from.setText(oneWayModel.getCode());
            myViewHolder.departure_timefrom.setText(oneWayModel.getDate());
            myViewHolder.departure_timeto.setText(oneWayModel.getTo_date());

            for(int i=0;i<data.size();i++){
                myViewHolder.departure_to.setText(data.get(i).getTo_code());
            }

            myViewHolder.stops.setText(data.size()-1+ " Stops");
            myViewHolder.total_time.setText("");
            myViewHolder.price.setText("");
            myViewHolder.flight_number.setText(oneWayModel.getFlight_no());

            Picasso.with(context)
                    .load(oneWayModel.getImg()+".PNG")
                    .error(R.drawable.ic_no_image)
                    .resize(120,60)
                    .into(myViewHolder.imageView,  new com.squareup.picasso.Callback() {
                        @Override
                        public void onSuccess() {

                        }

                        @Override
                        public void onError() {
                        }
                    });
            myViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Bundle b = new Bundle();
                    Intent intent = new Intent(context, Manual_flight_detials.class);
//                    intent.putExtra("type","oneway");
//                    intent.putParcelableArrayListExtra("detials",flightModel.getModels_array());
//                    b.putParcelable("cabin_class",cabin_class);
//                    intent.putExtras(b);
//                    intent.putExtra("id",flightModel.getId());
//                    intent.putExtra("url",flightModel.getModels_array().get(0).getImg());
                    context.startActivity(intent);
                }
            });

        }else{

            MyViewHolder2 myViewHolder = (MyViewHolder2)holder;
            final RouteModel flightModel = data.get(position);

//            myViewHolder.company_name.setText(flightModel.getModels_array().get(0).getName());
//            myViewHolder.departure_from.setText(flightModel.getModels_array().get(0).getCode());
//            myViewHolder.departure_to.setText(flightModel.getModels_array().get(flightModel.getModels_array().size()).getTo_code());
//            myViewHolder.departure_timefrom.setText(flightModel.getModels_array().get(0).getTime());
//            myViewHolder.departure_timeto.setText(flightModel.getModels_array().get(flightModel.getModels_array().size()-1).getTo_time());
//            myViewHolder.stops.setText((flightModel.getModels_array().size()-1)+" Stops");
//            myViewHolder.total_time.setText("Time : "+flightModel.getTotal_time());
//            myViewHolder.price.setText(flightModel.getCurrSymbol()+" "+flightModel.getPrice()+" "+flightModel.getCurrCode());
//            myViewHolder.flight_number.setText(flightModel.getModels_array().get(0).getFlight_no());

//            myViewHolder.b_departure_from.setText(flightModel.getReturn_array().get(0).getCode());
//            myViewHolder.b_departure_to.setText(flightModel.getModels_array().get(flightModel.getReturn_array().size()-1).getTo_code());
//            myViewHolder.b_departure_timefrom.setText(flightModel.getReturn_array().get(0).getTime());
//            myViewHolder.b_departure_timeto.setText(flightModel.getReturn_array().get(flightModel.getReturn_array().size()-1).getTo_time());
//            myViewHolder.b_stops.setText((flightModel.getReturn_array().size()-1)+" Stops");
//            myViewHolder.b_total_time.setText("Time : "+flightModel.getTotal_time());

            myViewHolder.b_departure_from.setText("");
            myViewHolder.b_departure_to.setText("");
            myViewHolder.b_departure_timefrom.setText("");
            myViewHolder.b_departure_timeto.setText("");
            myViewHolder.b_stops.setText("");
            myViewHolder.b_total_time.setText("Time : ");

            Picasso.with(context)
                    .load(R.drawable.ic_no_image)
                    .error(R.drawable.ic_no_image)
                    .resize(120,60)
                    .into(myViewHolder.imageView,  new com.squareup.picasso.Callback() {
                        @Override
                        public void onSuccess() {

                        }

                        @Override
                        public void onError() {
                        }
                    });

            myViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Bundle b = new Bundle();
                    Intent intent = new Intent(context, Manual_flight_detials.class);
//                    intent.putExtra("type","oneway");
//                    flightModel.getModels_array().addAll(flightModel.getReturn_array());
//                    intent.putParcelableArrayListExtra("detials",flightModel.getModels_array());
//                    b.putParcelable("cabin_class",cabin_class);
//                    intent.putExtras(b);
//                    intent.putExtra("id",flightModel.getId());
//                    intent.putExtra("url",flightModel.getModels_array().get(0).getImg());
                    context.startActivity(intent);
                }
            });

        }


    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class MyViewHolder2 extends RecyclerView.ViewHolder{

        TextView departure_timefrom;
        TextView departure_timeto;
        TextView total_time;
        TextView departure_from;
        TextView stops;
        TextView departure_to;
        TextView flight_number;

        TextView b_departure_timefrom;
        TextView b_departure_timeto;
        TextView b_total_time;
        TextView b_departure_from;
        TextView b_stops;
        TextView b_departure_to;
        TextView b_flight_number;
        TextView company_name;
        TextView price;
        ImageView imageView;
        public MyViewHolder2(View itemView) {
            super(itemView);

            departure_timefrom = (TextView) itemView.findViewById(R.id.departure_timefrom);
            departure_timeto = (TextView) itemView.findViewById(R.id.departure_timeto);
            total_time = (TextView) itemView.findViewById(R.id.total_time);
            departure_from = (TextView) itemView.findViewById(R.id.departure_from);
            stops = (TextView) itemView.findViewById(R.id.stops);
            departure_to = (TextView) itemView.findViewById(R.id.departure_to);
            flight_number = (TextView) itemView.findViewById(R.id.flight_number);
            company_name = (TextView) itemView.findViewById(R.id.company_name);

            b_departure_timefrom = (TextView) itemView.findViewById(R.id.b_departure_timefrom);
            b_departure_timeto = (TextView) itemView.findViewById(R.id.b_departure_timeto);
            b_total_time = (TextView) itemView.findViewById(R.id.b_total_time);
            b_departure_from = (TextView) itemView.findViewById(R.id.b_departure_from);
            b_stops = (TextView) itemView.findViewById(R.id.b_stops);
            b_departure_to = (TextView) itemView.findViewById(R.id.b_departure_to);
            b_flight_number = (TextView) itemView.findViewById(R.id.b_flight_number);

            price = (TextView) itemView.findViewById(R.id.price);
            imageView = (ImageView) itemView.findViewById(R.id.main_id);

        }
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        TextView departure_timefrom;
        TextView departure_timeto;
        TextView total_time;
        TextView departure_from;
        TextView stops;
        TextView departure_to;
        TextView flight_number;
        TextView company_name;
        TextView price;
        ImageView imageView;


        public MyViewHolder(View itemView) {
            super(itemView);

            departure_timefrom = (TextView) itemView.findViewById(R.id.departure_timefrom);
            departure_timeto = (TextView) itemView.findViewById(R.id.departure_timeto);
            total_time = (TextView) itemView.findViewById(R.id.total_time);
            departure_from = (TextView) itemView.findViewById(R.id.departure_from);
            stops = (TextView) itemView.findViewById(R.id.stops);
            departure_to = (TextView) itemView.findViewById(R.id.departure_to);
            flight_number = (TextView) itemView.findViewById(R.id.flight_number);
            company_name = (TextView) itemView.findViewById(R.id.company_name);
            price = (TextView) itemView.findViewById(R.id.price);
            imageView = (ImageView) itemView.findViewById(R.id.main_id);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }
    }
}
