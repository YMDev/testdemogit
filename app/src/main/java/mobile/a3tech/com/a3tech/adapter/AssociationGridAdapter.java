package mobile.a3tech.com.a3tech.adapter;

import java.util.List;


import android.content.Context;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import mobile.a3tech.com.a3tech.R;
import mobile.a3tech.com.a3tech.model.Association;
import mobile.a3tech.com.a3tech.utils.Constant;

public class AssociationGridAdapter extends BaseAdapter {
	public ImageLoader imageLoader;
	private Context mContext;
	private Fragment mFragment;
	private LayoutInflater mLayoutInflater;
	private List<Association> mStoreBeans;
	DisplayImageOptions options = new DisplayImageOptions.Builder()
			.cacheInMemory().cacheOnDisc().build();

	@Override
	public int getCount() {
		return this.mStoreBeans.size();
	}

	public List<Association> getAssociations() {
		return this.mStoreBeans;
	}

	@Override
	public Object getItem(int paramInt) {
		return this.mStoreBeans.get(paramInt);
	}

	@Override
	public long getItemId(int paramInt) {
		return 0;
	}

	private class ViewHolder {
		ImageView idAssociationGrid_image;
		TextView idAssociationGrid_text;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = this.mLayoutInflater.inflate(
					R.layout.association_item_grid, null);
			holder.idAssociationGrid_image = ((ImageView) convertView
					.findViewById(R.id.idAssociationGrid_image));
			holder.idAssociationGrid_text = ((TextView) convertView
					.findViewById(R.id.idassociationGrid_text));
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
			holder.idAssociationGrid_image.setImageBitmap(null);

		}
		String societe = ((Association) this.mStoreBeans.get(position))
				.getSociete();
		holder.idAssociationGrid_text.setText(Html.fromHtml(societe));
		imageLoader.displayImage(
				Constant.CHECK_EDU_LOGOS_URL
						+ ((Association) this.mStoreBeans.get(position))
								.getIdentifiant() + ".jpg",
				holder.idAssociationGrid_image, this.options);
		return convertView;
	}

	public AssociationGridAdapter(List<Association> grids,
			Fragment paramFragment) {
		this.mFragment = paramFragment;
		this.mStoreBeans = grids;
		this.mLayoutInflater = ((LayoutInflater) paramFragment.getActivity()
				.getSystemService("layout_inflater"));
		this.imageLoader = ImageLoader.getInstance();
	}

}
