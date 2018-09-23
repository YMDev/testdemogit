package mobile.a3tech.com.a3tech.activity;

import java.util.ArrayList;
import java.util.List;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import mobile.a3tech.com.a3tech.R;
import mobile.a3tech.com.a3tech.adapter.MesServicesViewPagerAdapter;
import mobile.a3tech.com.a3tech.sliding.PagerItem;
import mobile.a3tech.com.a3tech.sliding.SlidingTabLayout;
import mobile.a3tech.com.a3tech.utils.UtileColor;

public class MesServiceViewPagerFragment extends Fragment {
	SlidingTabLayout mSlidingTabLayout;
	private List<PagerItem> mTabs = new ArrayList<PagerItem>();
	ViewPager mViewPager;
	MesServicesViewPagerAdapter mesServicesViewPagerAdapter;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.mTabs.add(new PagerItem(0, getString(R.string.tab_one),
				getResources().getColor(UtileColor.colors[0]), Color.GRAY));
		this.mTabs.add(new PagerItem(1, getString(R.string.tab_two),
				getResources().getColor(UtileColor.colors[1]), Color.GRAY));
		this.mTabs.add(new PagerItem(2, getString(R.string.tab_three),
				getResources().getColor(UtileColor.colors[2]), Color.GRAY));
	}

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.messervice_viewpager_fragment,
				container, false);
	}

	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == 101) {
			mesServicesViewPagerAdapter
					.getFragment(mViewPager.getCurrentItem()).onActivityResult(
							requestCode, resultCode, data);
		}
	}

	public void onViewCreated(View view, Bundle savedInstanceState) {
		mViewPager = (ViewPager) view.findViewById(R.id.mPager);
		mesServicesViewPagerAdapter = new MesServicesViewPagerAdapter(
				getChildFragmentManager(), this.mTabs);
		mViewPager.setAdapter(this.mesServicesViewPagerAdapter);
		mSlidingTabLayout = (SlidingTabLayout) view.findViewById(R.id.mTabs);
		mSlidingTabLayout.setViewPager(this.mViewPager);

		mSlidingTabLayout.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {

			@Override
			public int getIndicatorColor(int position) {
				return ((PagerItem) MesServiceViewPagerFragment.this.mTabs
						.get(position)).getIndicatorColor();
			}

			@Override
			public int getDividerColor(int position) {
				return ((PagerItem) MesServiceViewPagerFragment.this.mTabs
						.get(position)).getDividerColor();
			}
		});

	}
}
