package mobile.a3tech.com.a3tech.adapter;

import java.lang.ref.WeakReference;
import java.util.Hashtable;
import java.util.List;



import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import mobile.a3tech.com.a3tech.sliding.PagerAssociationItem;

public class DetailAssociationPagerAdapter extends FragmentStatePagerAdapter {

	protected Hashtable<Integer, WeakReference<Fragment>> fragmentReferences = new Hashtable<Integer, WeakReference<Fragment>>();
	private List<PagerAssociationItem> mTabs;

	public DetailAssociationPagerAdapter(FragmentManager fragmentManager,
			List<PagerAssociationItem> pagerAssociationItems) {
		super(fragmentManager);
		this.mTabs = pagerAssociationItems;
	}

	@Override
	public int getCount() {
		return 2;
	}

	public Fragment getFragment(int paramInt) {
		WeakReference<Fragment> localWeakReference = (WeakReference<Fragment>) this.fragmentReferences
				.get(Integer.valueOf(paramInt));
		if (localWeakReference == null) {
			return null;
		}
		return (Fragment) localWeakReference.get();
	}

	@Override
	public Fragment getItem(int paramInt) {
		Fragment fragment = ((PagerAssociationItem) this.mTabs.get(paramInt))
				.createFragment();
		this.fragmentReferences.put(Integer.valueOf(paramInt),
				new WeakReference<Fragment>(fragment));
		return fragment;
	}

	@Override
	public int getItemPosition(Object paramObject) {
		return -2;
	}

	@Override
	public CharSequence getPageTitle(int position) {
		return ((PagerAssociationItem) this.mTabs.get(position)).getTitle();
	}

}
