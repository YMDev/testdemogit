package mobile.a3tech.com.a3tech.sliding;


import android.support.v4.app.Fragment;

import mobile.a3tech.com.a3tech.activity.EvaluationClientFragment;
import mobile.a3tech.com.a3tech.activity.EvaluationPrestataireFragment;

public class PagerItemEvaluation {
	
	private final CharSequence mTitle;
    private final int mIndicatorColor;
    private final int mDividerColor;
    private final int position;
        
    private Fragment[] listFragments;
    public PagerItemEvaluation(int position, CharSequence title, int indicatorColor, int dividerColor) {
        this.mTitle = title;
        this.position = position;
        this.mIndicatorColor = indicatorColor;
        this.mDividerColor = dividerColor;
        
        listFragments = new Fragment[] { EvaluationPrestataireFragment.newInstance(), EvaluationClientFragment.newInstance()};
    }

    public Fragment createFragment() {
		return listFragments[position];
    }

    public CharSequence getTitle() {
        return mTitle;
    }

    public int getIndicatorColor() {
        return mIndicatorColor;
    }

    public int getDividerColor() {
        return mDividerColor;
    }
}
