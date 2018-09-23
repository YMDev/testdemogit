package mobile.a3tech.com.a3tech.model;

public class NavigationItem {

	public String title;
	public int counter;
	public int icon;
	public boolean isHeader;	

	public NavigationItem(String title, int icon, boolean header,int counter) {
		this.title = title;
		this.icon = icon;
		this.isHeader = header;
		this.counter = counter;
	}
	
	public NavigationItem(String title, int icon, boolean header){
		this(title, icon, header, 0 );
	}
	
	public NavigationItem(String title, int icon) {
		this(title, icon, false);
	}
}
