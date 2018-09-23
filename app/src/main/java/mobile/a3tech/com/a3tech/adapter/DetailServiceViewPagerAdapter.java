package mobile.a3tech.com.a3tech.adapter;

import java.lang.ref.WeakReference;
import java.util.Hashtable;
import java.util.List;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import mobile.a3tech.com.a3tech.sliding.PagerServiceOffreItem;

public class DetailServiceViewPagerAdapter extends
		FragmentStatePagerAdapter {

	protected Hashtable<Integer, WeakReference<Fragment>> fragmentReferences = new Hashtable<Integer, WeakReference<Fragment>>();
	private List<PagerServiceOffreItem> mTabs;

	public DetailServiceViewPagerAdapter(
			FragmentManager fragmentManager,
			List<PagerServiceOffreItem> pagerServiceOffreItems) {
		super(fragmentManager);
		this.mTabs = pagerServiceOffreItems;
	}

	@Override
	public int getCount() {
		return 2;
	}

	public Fragment getFragment(int position) {
		WeakReference<Fragment> weakReference = (WeakReference<Fragment>) this.fragmentReferences
				.get(Integer.valueOf(position));
		if (weakReference == null) {
			return null;
		}
		return (Fragment) weakReference.get();
	}

	@Override
	public Fragment getItem(int position) {
		Fragment fragment = ((PagerServiceOffreItem) this.mTabs.get(position))
				.createFragment();
		this.fragmentReferences.put(Integer.valueOf(position),
				new WeakReference<Fragment>(fragment));
		return fragment;
	}

	@Override
	public int getItemPosition(Object paramObject) {
		return -2;
	}

	@Override
	public CharSequence getPageTitle(int position) {
		return ((PagerServiceOffreItem) this.mTabs.get(position)).getTitle();
	}

}
