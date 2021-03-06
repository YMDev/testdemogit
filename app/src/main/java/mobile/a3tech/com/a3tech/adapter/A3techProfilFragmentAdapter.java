package mobile.a3tech.com.a3tech.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import mobile.a3tech.com.a3tech.fragment.A3techProfilInformationFragment;
import mobile.a3tech.com.a3tech.fragment.A3techReviewsFragment;
import mobile.a3tech.com.a3tech.model.A3techUser;
import mobile.a3tech.com.a3tech.test.DummyFragment;

public class A3techProfilFragmentAdapter extends FragmentPagerAdapter {
    final int PAGE_COUNT = 2;
    private String tabTitles[] = new String[]{"Profile", "Avis"};
    private Context context;
    private A3techUser user;
    private Boolean editMode = Boolean.FALSE;
    private Boolean modeMyAccount = Boolean.FALSE;

    public A3techProfilFragmentAdapter(FragmentManager fm, Context context, A3techUser userV) {
        super(fm);
        this.context = context;
        this.user = userV;
    }

    public void setModeMyAccount(Boolean modeMyAccount) {
        this.modeMyAccount = modeMyAccount;
    }

    public Boolean getModeMyAccount() {
        return modeMyAccount;
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragmntToShow = null;
        switch (position) {
            case 0:
                fragmntToShow = A3techProfilInformationFragment.newInstance(user, modeMyAccount);
                break;
            case 1:
                fragmntToShow = A3techReviewsFragment.newInstance(user);
                break;
            default:
                fragmntToShow = new A3techProfilInformationFragment();
                break;
        }
        return fragmntToShow;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        return tabTitles[position];
    }



    public Boolean getEditMode() {
        return editMode;
    }

    public void setEditMode(Boolean editMode) {
        this.editMode = editMode;
    }
}
