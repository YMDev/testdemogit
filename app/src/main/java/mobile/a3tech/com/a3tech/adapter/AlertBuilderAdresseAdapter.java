package mobile.a3tech.com.a3tech.adapter;

import java.util.List;

import android.content.Context;
import android.graphics.Typeface;
import android.location.Address;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import mobile.a3tech.com.a3tech.R;

public class AlertBuilderAdresseAdapter extends BaseAdapter{

	private Context context;
	private LayoutInflater inflater;

	private List<Address> addresses;
	
	
	
	//Date d = sdf.parse(s);

	public AlertBuilderAdresseAdapter(Context context, List<Address> addresses) {
		this.context = context;
		inflater = LayoutInflater.from(context);
		this.addresses  = addresses;
		
	}

	public void setAddresses(List<Address> addresses) {
		this.addresses = addresses;
		this.notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		if (addresses != null) {
			if (addresses.size() > addresses.size())
				return addresses.size() + 1;
			else
				return addresses.size();
		}
		return 0;
	}

	@Override
	public Address getItem(int position) {
		if (position < addresses.size())
			return addresses.get(position);
		else
			return null;
	}

	@Override
	public long getItemId(int position) {
		if (position < addresses.size())
			return position;
		else
			return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder;
		Address address = addresses.get(position);
		
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.item,null);
			viewHolder = new ViewHolder();
			viewHolder.img = (ImageView) convertView.findViewById(R.id.categoryImage);
			viewHolder.categorieName = (TextView) convertView.findViewById(R.id.categoryName);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		viewHolder.categorieName.setTypeface(null, Typeface.BOLD);
		String localite ="-";
			if(address.getLocality()!=null && !address.getLocality().equals("null"))
				localite= localite+address.getLocality()+"-";
		viewHolder.categorieName.setText(Html.fromHtml(address.getFeatureName()+localite+address.getCountryName()));
		return convertView;
		
	
	}

	
	class ViewHolder {
		ImageView img;
		TextView categorieName;
	}




}
