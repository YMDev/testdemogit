package mobile.a3tech.com.a3tech.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import mobile.a3tech.com.a3tech.R;
import mobile.a3tech.com.a3tech.fragment.A3techMissionsHomeFragment;
import mobile.a3tech.com.a3tech.fragment.A3techProfilInformationFragment;
import mobile.a3tech.com.a3tech.fragment.A3techReviewsFragment;
import mobile.a3tech.com.a3tech.model.A3techUser;

public class A3techMissionsListeAdapter extends FragmentPagerAdapter {
    final int PAGE_COUNT = 2;
    private String tabTitles[] = new String[]{"Profile", "Avis"};
    private Context context;
    private A3techUser user;
    private Boolean editMode = Boolean.FALSE;
    public static final String MISSIONS_EN_COURS = "MISSIONS_EN_COURS ";
    public static final String MISSIONS_HISTORIQUE = "MISSIONS_HISTORIQUE";
    public A3techMissionsListeAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
        tabTitles[0] = context.getResources().getString(R.string.mission_en_cours_header_tab);
        tabTitles[1] = context.getResources().getString(R.string.mission_historique_header_tab);
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
                fragmntToShow = A3techMissionsHomeFragment.newInstance(MISSIONS_EN_COURS);
                break;
            case 1:
                fragmntToShow = A3techMissionsHomeFragment.newInstance(MISSIONS_HISTORIQUE);
                break;
            default:
                fragmntToShow = A3techMissionsHomeFragment.newInstance(MISSIONS_HISTORIQUE);
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
