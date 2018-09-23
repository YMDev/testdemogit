package mobile.a3tech.com.a3tech.data;


import android.content.Context;

import mobile.a3tech.com.a3tech.R;
import mobile.a3tech.com.a3tech.adapter.NavigationAdapter;
import mobile.a3tech.com.a3tech.model.NavigationItem;
import mobile.a3tech.com.a3tech.utils.Title;

public class NavigationList {
	
	public static NavigationAdapter getNavigationAdapter(Context context){
		
		NavigationAdapter navigationAdapter = new NavigationAdapter(context);		
		String[] menuItems = context.getResources().getStringArray(R.array.nav_menu_items);
		for (int i = 0; i < menuItems.length; i++) {


			
			String title = menuItems[i];				
			if (i == 0 || i == 4 || i == 7){ 
				navigationAdapter.addHeader(title);			
			}else{
			
				NavigationItem itemNavigation;
				itemNavigation = new NavigationItem(title, Title.iconNavigation[i]);
//				switch (i) {
//				case 3:
//					itemNavigation = new NavigationItem(title, Title.iconNavigation[i], false, 1);														
//					break;
//				default:
//					itemNavigation = new NavigationItem(title, Title.iconNavigation[i]);									
//					break;
//				}		
				navigationAdapter.addItem(itemNavigation);
			}
		}		
		return navigationAdapter;			
	}	
}
