package mobile.a3tech.com.a3tech.adapter;

import java.util.List;


import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import mobile.a3tech.com.a3tech.R;
import mobile.a3tech.com.a3tech.model.Categorie;

public class CatureEchangeAdapter extends ArrayAdapter<String> {
	private TextView text;
	private Context context;

	LayoutInflater layoutInflater;
	List<Categorie> list;

	public CatureEchangeAdapter(Context context, List<Categorie> list) {
		super(context, R.layout.row_spinner);
		this.context = context;
		layoutInflater = LayoutInflater.from(context);
		this.list = list;
	}

	@Override
	public int getCount() {

		return list.size();
	}

	
	@Override
	public long getItemId(int arg0) {

		return arg0;
	}
	@Override
	public String getItem(int position) {
		
		return ((Categorie) list.get(position)).getLibelle();
	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		return getCustomView(position, convertView, parent);
	}

	@Override
	public View getDropDownView(int position, View convertView, ViewGroup parent) {
		return getCustomView(position, convertView, parent);

	};

	public View getCustomView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = layoutInflater.inflate(R.layout.row_spinner, parent,
					false);
		}
		text = (TextView) convertView.findViewById(android.R.id.text1);
		if (position == 0) {
			text.setText(getItem(0));
			text.setHint(getItem(0)); // "Hint to be displayed"
			text.setTextColor(context.getResources().getColor(R.color.Gray));
		}else{
			text.setText(Html.fromHtml(getItem(position)));
			text.setTextColor(context.getResources().getColor(R.color.black));
		}

		return convertView;
	}

}
