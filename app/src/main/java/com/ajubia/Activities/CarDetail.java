package com.ajubia.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewStub;
import android.widget.TextView;

import com.ajubia.Adapters.SlidingImage_Adapter;
import com.ajubia.Fragments.Map;
import com.ajubia.Fragments.Overview_car;
import com.ajubia.Fragments.booking_car;
import com.ajubia.Models.Auto_Model;
import com.ajubia.Models.DetailModel;
import com.ajubia.Models.OverView;
import com.ajubia.Models.car_model;
import com.ajubia.R;
import com.ajubia.utality.lib.Parallex.CirclePageIndicator;
import com.ajubia.utality.lib.Parallex.ParallaxFragmentPagerAdapter;
import com.ajubia.utality.lib.Parallex.ParallaxViewPagerBaseActivity;
import com.ajubia.utality.slidingTab.SlidingTabLayout;

import java.util.ArrayList;


public class CarDetail extends ParallaxViewPagerBaseActivity {


    int id;
    private static ViewPager mPager;
    private SlidingTabLayout mNavigBar;
    ArrayList<DetailModel> arrayList=new ArrayList<>();
    ArrayList<Auto_Model> pickup=new ArrayList<>();
    ArrayList<Auto_Model> dropOff=new ArrayList<>();
    OverView overView=new OverView();
    car_model car=new car_model();




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ViewStub stub = (ViewStub) findViewById(R.id.layout_stub);
        stub.setLayoutResource(R.layout.activity_detail);
        View inflated = stub.inflate();



        TextView textView=(TextView) findViewById(R.id.NaviText);



        initValues();

        Intent intent=getIntent();
        arrayList=intent.getParcelableArrayListExtra("arrayList");
        pickup=intent.getParcelableArrayListExtra("pickup");
        dropOff=intent.getParcelableArrayListExtra("DropOff");
        overView=intent.getParcelableExtra("ov");
        car=intent.getParcelableExtra("car");
        textView.setText(overView.getTitle());
        textView.setSelected(true);



        mPager = (ViewPager) inflated.findViewById(R.id.pager);
        mHeader =inflated. findViewById(R.id.header);
        mViewPager = (ViewPager) inflated.findViewById(R.id.view_pager);
        mNavigBar = (SlidingTabLayout) inflated.findViewById(R.id.navig_tab);


        if (savedInstanceState != null) {
            mPager.setTranslationY(savedInstanceState.getFloat(IMAGE_TRANSLATION_Y));
            mHeader.setTranslationY(savedInstanceState.getFloat(HEADER_TRANSLATION_Y));
        }

        mPager.setAdapter(new SlidingImage_Adapter(CarDetail.this, arrayList,true));


        setupAdapter();

        CirclePageIndicator indicator =(CirclePageIndicator)
                findViewById(R.id.indicator);


        indicator.setViewPager(mPager);


    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void initValues() {

        int tabHeight = getResources().getDimensionPixelSize(R.dimen.tab_height);
        mMinHeaderHeight = getResources().getDimensionPixelSize(R.dimen.min_header_height);
        mHeaderHeight = getResources().getDimensionPixelSize(R.dimen.header_height);
        mMinHeaderTranslation = -mMinHeaderHeight + tabHeight;
        mNumFragments = 3;

    }

    @Override
    protected void scrollHeader(int scrollY) {

        float translationY = Math.max(-scrollY, mMinHeaderTranslation);
        mHeader.setTranslationY(translationY);
        mPager.setTranslationY(-translationY / 3);
    }

    @Override
    protected void setupAdapter() {

        if (mAdapter == null) {
            mAdapter = new ViewPagerAdapter(getSupportFragmentManager(), mNumFragments,overView,pickup,dropOff,car);
        }

        mViewPager.setAdapter(mAdapter);
        mViewPager.setOffscreenPageLimit(mNumFragments);
        mNavigBar.setOnPageChangeListener(getViewPagerChangeListener());
        mNavigBar.setViewPager(mViewPager);

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putFloat(IMAGE_TRANSLATION_Y, mPager.getTranslationY());
        outState.putFloat(HEADER_TRANSLATION_Y, mHeader.getTranslationY());
        super.onSaveInstanceState(outState);
    }



    private  class ViewPagerAdapter extends ParallaxFragmentPagerAdapter {


        OverView overView;
        ArrayList<Auto_Model> pickup=new ArrayList<>();
        ArrayList<Auto_Model> dropOff=new ArrayList<>();
        car_model car;
        public ViewPagerAdapter(FragmentManager fm, int numFragments, OverView overView, ArrayList<Auto_Model> pickup, ArrayList<Auto_Model> dropOff, car_model car) {
            super(fm, numFragments);
            this.overView=overView;
            this.pickup=pickup;
            this.dropOff=dropOff;
            this.car=car;
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment;
            switch (position) {
                case 0:
                    fragment = Overview_car.newInstance(0,overView);
                    break;
                case 1:
                    fragment = booking_car.newInstance(1,pickup,dropOff,car);
                    break;
                case 2:
                    fragment = Map.newInstance(2,overView);
                    break;

                default:
                    throw new IllegalArgumentException("Wrong page given " + position);
            }
            return fragment;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return getString(R.string.overview);
                case 1:
                    return getString(R.string.booking);
                case 2:
                    return getString(R.string.map);
                default:
                    throw new IllegalArgumentException("wrong position for the fragment in vehicle page");
            }
        }
    }
}

