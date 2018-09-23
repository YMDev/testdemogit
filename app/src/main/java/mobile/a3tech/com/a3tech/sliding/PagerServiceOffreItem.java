package mobile.a3tech.com.a3tech.sliding;


import android.support.v4.app.Fragment;

import mobile.a3tech.com.a3tech.activity.DetailServiceActivity;
import mobile.a3tech.com.a3tech.activity.ListOffresFragment;

public class PagerServiceOffreItem {

	private Fragment[] listFragments;
	private final int mDividerColor;
	private final int mIndicatorColor;
	private final CharSequence mTitle;
	private final int position;

	public PagerServiceOffreItem(int position, CharSequence title,
			int indicatorColor, int dividerColor) {
		this.mTitle = title;
		this.position = position;
		this.mIndicatorColor = indicatorColor;
		this.mDividerColor = dividerColor;
		this.listFragments = new Fragment[] {
				DetailServiceActivity.newInstance(),
				ListOffresFragment.newInstance() };
	}

	public Fragment createFragment() {
		return this.listFragments[this.position];
	}

	public int getDividerColor() {
		return this.mDividerColor;
	}

	public int getIndicatorColor() {
		return this.mIndicatorColor;
	}

	public CharSequence getTitle() {
		return this.mTitle;
	}

}
