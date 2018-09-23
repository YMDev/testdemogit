package mobile.a3tech.com.a3tech.sliding;


import android.support.v4.app.Fragment;

import mobile.a3tech.com.a3tech.activity.ServicesARendreFragment;
import mobile.a3tech.com.a3tech.activity.ServicesInteressantsFragment;
import mobile.a3tech.com.a3tech.activity.ServicesRendusFragment;

public class PagerItem {
    private Fragment[] listFragments;
    private final int mDividerColor;
    private final int mIndicatorColor;
    private final CharSequence mTitle;
    private final int position;

    public PagerItem(int position, CharSequence title, int indicatorColor, int dividerColor) {
        this.mTitle = title;
        this.position = position;
        this.mIndicatorColor = indicatorColor;
        this.mDividerColor = dividerColor;
        this.listFragments = new Fragment[]{ServicesRendusFragment.newInstance(), ServicesARendreFragment.newInstance(), ServicesInteressantsFragment.newInstance()};
    }

    public Fragment createFragment() {
        return this.listFragments[this.position];
    }

    public CharSequence getTitle() {
        return this.mTitle;
    }

    public int getIndicatorColor() {
        return this.mIndicatorColor;
    }

    public int getDividerColor() {
        return this.mDividerColor;
    }
}
