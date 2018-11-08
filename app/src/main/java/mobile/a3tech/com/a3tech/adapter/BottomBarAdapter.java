package mobile.a3tech.com.a3tech.adapter;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import mobile.a3tech.com.a3tech.fragment.A3techDisplayTechInMapFragment;

/**
 * BottomNav
 * Created by Suleiman19 on 6/12/17.
 * Copyright (c) 2017. Suleiman Ali Shakir. All rights reserved.
 */

public class BottomBarAdapter extends SmartFragmentStatePagerAdapter {
    private final List<Fragment> fragments = new ArrayList<>();

    public BottomBarAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
    }

    public void addFragments(Fragment fragment) {
        fragments.add(fragment);
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }
    @Override
    public void setPrimaryItem(ViewGroup container, int position, Object object) {
        super.setPrimaryItem(container, position, object);
//        if(position == 1 && getItem(position) instanceof A3techDisplayTechInMapFragment){
//            ((A3techDisplayTechInMapFragment)getItem(position)).refreshMap();
//        }
    }
}