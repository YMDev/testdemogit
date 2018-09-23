package mobile.a3tech.com.a3tech.view;

import android.content.Context;
import android.view.Display;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;

public class Helper {

	
	public static void getListViewSize(ListView myListView, Context context) {
		ListAdapter myListAdapter = myListView.getAdapter();
		if (myListAdapter == null) {
			// do nothing return null
			return;
		}
		// set listAdapter in loop for getting final size
		int totalHeight = 0;
		
		for (int size = 0; size < myListAdapter.getCount(); size++) {

			View listItem = myListAdapter.getView(size, null, myListView);
			if (listItem instanceof ViewGroup)
				listItem.setLayoutParams(new RelativeLayout.LayoutParams(
						RelativeLayout.LayoutParams.WRAP_CONTENT,
						RelativeLayout.LayoutParams.WRAP_CONTENT));
	

			WindowManager wm = (WindowManager) context
					.getSystemService(Context.WINDOW_SERVICE);
			Display display = wm.getDefaultDisplay();
			int screenWidth = display.getWidth(); // deprecated
			int height = display.getHeight(); // deprecated

			int listViewWidth = screenWidth - 10 - 10;
			int widthSpec = MeasureSpec.makeMeasureSpec(listViewWidth,
					MeasureSpec.AT_MOST);
			listItem.measure(widthSpec, 0);

			totalHeight += listItem.getMeasuredHeight();
		}
		// setting listview item in adapter
		ViewGroup.LayoutParams params = myListView.getLayoutParams();
		params.height = totalHeight
				+ (myListView.getDividerHeight() * (myListAdapter.getCount() - 1));
		myListView.setLayoutParams(params);
		myListView.requestLayout();
		// print height of adapter on log
		
	}
}
