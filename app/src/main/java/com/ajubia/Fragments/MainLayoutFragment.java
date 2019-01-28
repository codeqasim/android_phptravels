package com.ajubia.Fragments;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.ajubia.Activities.SplashActivity;
import com.ajubia.Models.MenuModel;
import com.ajubia.Network.Post.ModuleRequest;
import com.ajubia.R;
import com.ajubia.utality.Extra.Constant;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MainLayoutFragment extends Fragment {

    LinearLayout view;
    RequestQueue requestQueue;



    boolean first=false;

    public MainLayoutFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View inflated=inflater.inflate(R.layout.fragment_main_layout, container, false);

        ViewPager viewPager = (ViewPager)inflated. findViewById(R.id.viewpager);

        TabLayout tabLayout = (TabLayout)inflated. findViewById(R.id.tabs);
        final ScrollView scrollView=(ScrollView)inflated.findViewById(R.id.mainBar);
        final RelativeLayout relativeLayout=(RelativeLayout) inflated.findViewById(R.id.logo_layout);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                scrollView.post(new Runnable() {
                    @Override
                    public void run() {
                        if(first)
                       relativeLayout.setVisibility(View.GONE);
                        first=true;
                    }
                });
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });



        tabLayout.setupWithViewPager(viewPager);




        ViewPagerAdapter adapter = new ViewPagerAdapter(getActivity().getSupportFragmentManager());


        int size=SplashActivity.menuModels.size();

        if(size>5)
           size=5;

        Log.d("abcedf",SplashActivity.menuModels.size()+"");

        for(int i=0;i<size;i++)
        {
         if(SplashActivity.menuModels.get(i).getType().equals("hotels"))
                adapter.addFragment(new Default_Hotel(),getString(R.string.hotels));

         else if(SplashActivity.menuModels.get(i).getType().equals("flights"))
             adapter.addFragment(TravelPort_Home.newInstance("manual"),getString(R.string.filghts));

         else if(SplashActivity.menuModels.get(i).getType().equals("blog"))
             adapter.addFragment(new Default_Hotel(),getString(R.string.blog));

         else if(SplashActivity.menuModels.get(i).getType().equals("visa"))
             adapter.addFragment(new ivisa_home(),getString(R.string.ivisa));

         else if(SplashActivity.menuModels.get(i).getType().equals("packages"))
             adapter.addFragment(new Tour(),getString(R.string.packages));


        }

        viewPager.setAdapter(adapter);
        return inflated;
    }
    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }



}
