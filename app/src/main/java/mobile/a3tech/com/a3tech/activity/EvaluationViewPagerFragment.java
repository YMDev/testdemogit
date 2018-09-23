package mobile.a3tech.com.a3tech.activity;

import java.util.ArrayList;
import java.util.List;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import mobile.a3tech.com.a3tech.R;
import mobile.a3tech.com.a3tech.adapter.EvaluationViewPagerAdapter;
import mobile.a3tech.com.a3tech.sliding.PagerItemEvaluation;
import mobile.a3tech.com.a3tech.sliding.SlidingTabLayout;
import mobile.a3tech.com.a3tech.utils.UtileColor;

public class EvaluationViewPagerFragment extends Fragment {
	private List<PagerItemEvaluation> mTabs = new ArrayList<PagerItemEvaluation>();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mTabs.add(new PagerItemEvaluation(0, getString(R.string.tab_two_prestataire_title), getResources()
				.getColor(UtileColor.colors[0]), Color.GRAY));
		mTabs.add(new PagerItemEvaluation(1, getString(R.string.tab_one_client_title), getResources()
				.getColor(UtileColor.colors[2]), Color.GRAY));
		
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.messervice_viewpager_fragment,
				container, false);
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		ViewPager mViewPager = (ViewPager) view.findViewById(R.id.mPager);
		mViewPager.setAdapter(new EvaluationViewPagerAdapter(
				getChildFragmentManager(), mTabs));

		SlidingTabLayout mSlidingTabLayout = (SlidingTabLayout) view
				.findViewById(R.id.mTabs);
		mSlidingTabLayout.setViewPager(mViewPager);

		mSlidingTabLayout
				.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {

					@Override
					public int getIndicatorColor(int position) {
						return mTabs.get(position).getIndicatorColor();
					}

					@Override
					public int getDividerColor(int position) {
						return mTabs.get(position).getDividerColor();
					}
				});
	}
}