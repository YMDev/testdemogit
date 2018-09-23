package mobile.a3tech.com.a3tech.adapter;

import java.util.List;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import mobile.a3tech.com.a3tech.sliding.PagerItemEvaluation;

public class EvaluationViewPagerAdapter extends FragmentPagerAdapter {

	private List<PagerItemEvaluation> mTabs;
	public EvaluationViewPagerAdapter(FragmentManager fragmentManager, List<PagerItemEvaluation> mTabs) {
		super(fragmentManager);
		this.mTabs = mTabs;
	}

    @Override
    public Fragment getItem(int i) {
        return mTabs.get(i).createFragment();
    }

    @Override
    public int getCount() {
        return mTabs.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTabs.get(position).getTitle();
    }
}