package mobile.a3tech.com.a3tech.utils;

import android.content.Context;

import mobile.a3tech.com.a3tech.R;

public class Title {

	public static int[] iconNavigation = new int[] { 0,
			R.drawable.ic_action_settings,
			R.drawable.ic_action_computer, R.drawable.ic_action_important, 0,
			R.drawable.ic_action_echange,R.drawable.ic_action_don, 0,
			R.drawable.ic_action_annuaire };

	public static String getTitleItem(Context context, int position) {
		String[] titles = context.getResources().getStringArray(
				R.array.nav_menu_items);
		return titles[position];
	}

	
	
//    <item>MON COMPTE</item>
//    <item>Param�trage</item>
//    <item>Mes taches</item>
//    <item>Mes �valuations</item>
//    <item>GRAND PUBLIC</item>
//    <item>Echanges</item>
//    <item>ASSOCIATIONS</item>
//    <item>Dons</item>
//    <item>Annuaire</item>
	
}
