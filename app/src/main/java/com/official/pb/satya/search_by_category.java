package com.official.pb.satya;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.google.android.material.tabs.TabLayout;
import com.official.pb.satya.Genre.Blouse;
import com.official.pb.satya.Genre.Kurta;
import com.official.pb.satya.Genre.Patiala;
import com.official.pb.satya.Genre.Plazo;
import com.official.pb.satya.Genre.Tab_layout_adapter;
import com.official.pb.satya.Genre.Western;

public class search_by_category extends Fragment {

    ViewPager viewPager;

    private TabLayout tabLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v= inflater.inflate(R.layout.fragment_search_by_category, container, false);
        tabLayout = v.findViewById(R.id.tabLayout);
        viewPager = v.findViewById(R.id.tab_bar_view_pager);
        tabLayout.setupWithViewPager(viewPager);
        setupViewPager(viewPager);




        return v;
    }

    private void setupViewPager(ViewPager viewPager) {
        Tab_layout_adapter tab_layout_adapter = new Tab_layout_adapter(getChildFragmentManager());
        tab_layout_adapter.addFragment(new Plazo(), "Plazo");
        tab_layout_adapter.addFragment(new Western(), "Western");
        tab_layout_adapter.addFragment(new Patiala(), "Patiala");
        tab_layout_adapter.addFragment(new Kurta(), "Kurta");
        tab_layout_adapter.addFragment(new Blouse(), "Blouse");
        viewPager.setAdapter(tab_layout_adapter);
    }


}
