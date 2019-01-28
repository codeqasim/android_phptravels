package com.ajubia.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewStub;
import android.widget.TextView;

import com.ajubia.Adapters.SlidingImage_Adapter;
import com.ajubia.Fragments.DemoListViewFragment;
import com.ajubia.Fragments.DemoRecyclerViewFragment;
import com.ajubia.Fragments.Tour_Overview;
import com.ajubia.Models.Amenities_Model;
import com.ajubia.Models.DetailModel;
import com.ajubia.Models.Model_Tour;
import com.ajubia.Models.OverView;
import com.ajubia.Models.review_model;
import com.ajubia.R;
import com.ajubia.utality.lib.Parallex.CirclePageIndicator;
import com.ajubia.utality.lib.Parallex.ParallaxFragmentPagerAdapter;
import com.ajubia.utality.lib.Parallex.ParallaxViewPagerBaseActivity;
import com.ajubia.utality.slidingTab.SlidingTabLayout;

import java.util.ArrayList;


public class TourDetail extends ParallaxViewPagerBaseActivity {


    int id;
    private ProgressDialog dialog;
    private ViewPager mPager;
    private SlidingTabLayout mNavigBar;
    ArrayList<DetailModel> arrayList=new ArrayList<>();
    ArrayList<review_model> review_list=new ArrayList<>();
    ArrayList<Amenities_Model> amenities_list=new ArrayList<>();
    ArrayList<Amenities_Model> amenities_list2=new ArrayList<>();
    OverView overView=new OverView();
    Model_Tour tourM;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        ViewStub stub = (ViewStub) findViewById(R.id.layout_stub);
        stub.setLayoutResource(R.layout.activity_detail);
        View inflated = stub.inflate();


        TextView textView=(TextView) findViewById(R.id.NaviText);



        initValues();

        Intent intent=getIntent();
        amenities_list=intent.getParcelableArrayListExtra("am");
        amenities_list2=intent.getParcelableArrayListExtra("am2");
        arrayList=intent.getParcelableArrayListExtra("arrayList");
        review_list=intent.getParcelableArrayListExtra("review");
        overView=intent.getParcelableExtra("ov");
        tourM=intent.getParcelableExtra("tou");

        textView.setText(overView.getTitle());
        textView.setPaddingRelative(0,0,0,0);


        mPager = (ViewPager) inflated.findViewById(R.id.pager);
        mHeader =inflated. findViewById(R.id.header);
        mViewPager = (ViewPager) inflated.findViewById(R.id.view_pager);
        mNavigBar = (SlidingTabLayout) inflated.findViewById(R.id.navig_tab);


        if (savedInstanceState != null) {
            mPager.setTranslationY(savedInstanceState.getFloat(IMAGE_TRANSLATION_Y));
            mHeader.setTranslationY(savedInstanceState.getFloat(HEADER_TRANSLATION_Y));
        }

        mPager.setAdapter(new SlidingImage_Adapter(TourDetail.this, arrayList,true));


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
        mNumFragments = 4;

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
            mAdapter = new ViewPagerAdapter(getSupportFragmentManager(), mNumFragments,review_list,amenities_list,overView,amenities_list2,tourM);
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

        ArrayList<review_model>review_models;
        ArrayList<Amenities_Model> amenities_list;
        ArrayList<Amenities_Model> am2;
        OverView overView;
        Model_Tour tour;
        public ViewPagerAdapter(FragmentManager fm, int numFragments, ArrayList<review_model> rv, ArrayList<Amenities_Model> amenities_list, OverView overView, ArrayList<Amenities_Model> am2, Model_Tour tourM) {
            super(fm, numFragments);
            review_models=rv;
            this.amenities_list=amenities_list;
            this.overView=overView;
            this.am2=am2;
            this.tour=tourM;

        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment;
            switch (position) {
                case 0:
                    fragment = Tour_Overview.newInstance(0,overView,tour);
                    break;

                case 1:
                    fragment = DemoListViewFragment.newInstance(1,amenities_list);
                    break;

                case 2:
                    fragment = DemoListViewFragment.newInstance(2,am2);

                    break;
                case 3:
                    fragment = DemoRecyclerViewFragment.newInstance(3,review_models);
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
                    return getString(R.string.inclusion);
                case 2:
                    return getString(R.string.Exclusion);

                case 3:
                    return getString(R.string.reviews);
                default:
                    throw new IllegalArgumentException("wrong position for the fragment in vehicle page");
            }
        }
    }
}

