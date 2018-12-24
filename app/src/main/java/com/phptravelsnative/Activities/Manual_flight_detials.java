package com.phptravelsnative.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewStub;
import android.widget.Button;
import android.widget.ImageView;

import com.phptravelsnative.Adapters.Manual_Flight_Adapter;
import com.phptravelsnative.Adapters.Manual_Flight_Detials_Adapter;
import com.phptravelsnative.Models.OneWay;
import com.phptravelsnative.Models.TravelPort;
import com.phptravelsnative.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class Manual_flight_detials extends Drawer {


    RecyclerView recyclerView;
    ImageView header;
    TravelPort cabin_class;
    String id = "";
    Button btn_book;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewStub stub = (ViewStub) findViewById(R.id.layout_stub);
        stub.setLayoutResource(R.layout.activity_manual_flight_detials);
        View inflated = stub.inflate();

        recyclerView = (RecyclerView)inflated.findViewById(R.id.recyclerView);
        header = (ImageView)inflated.findViewById(R.id.header);
        btn_book = (Button)inflated.findViewById(R.id.btn_book);

        Intent intent = getIntent();
        id = intent.getStringExtra("id");
        ArrayList<OneWay> onew_way = intent.getParcelableArrayListExtra("detials");
        Bundle b = intent.getExtras();
        cabin_class = b.getParcelable("cabin_class");
        Picasso.with(this)
                .load(intent.getStringExtra("url"))
                .error(R.drawable.ic_no_image)
                .resize(120,60)
                .into(header,  new com.squareup.picasso.Callback() {
                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onError() {
                    }
                });
        Manual_Flight_Detials_Adapter adapter = new Manual_Flight_Detials_Adapter(Manual_flight_detials.this,onew_way,intent.getStringExtra("type"));
        btn_book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                book();
            }
        });
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(Manual_flight_detials.this));

    }
    private void book() {

        Bundle bundle=new Bundle();
        bundle.putParcelable("flight_object",cabin_class);
        if(sharedPreferences.getBoolean("Check_Login",true) && !sharedPreferences.getBoolean("coupons",false))
        {
            Intent intent=new Intent(Manual_flight_detials.this, WebViewInvoice.class);
            intent.putExtra("check_type","flight");
            intent.putExtra("Check_Guest",false);
            Bundle b = new Bundle();
            b.putParcelable("cabin_class",cabin_class);
            intent.putExtras(b);
            intent.putExtra("id",id);
            intent.putExtras(bundle);
            startActivity(intent);

        }else
        {
            Intent intent=new Intent(Manual_flight_detials.this, Invoice.class);
            intent.putExtra("check_type","flights");
            Bundle b = new Bundle();
            b.putParcelable("cabin_class",cabin_class);
            intent.putExtras(b);
            intent.putExtra("id",id);
            intent.putExtras(bundle);
            startActivity(intent);
        }
    }
}
