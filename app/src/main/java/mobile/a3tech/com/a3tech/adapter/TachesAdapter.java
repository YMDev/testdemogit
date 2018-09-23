package mobile.a3tech.com.a3tech.adapter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import mobile.a3tech.com.a3tech.R;
import mobile.a3tech.com.a3tech.model.Mission;
import mobile.a3tech.com.a3tech.utils.Constant;

public class TachesAdapter extends BaseAdapter {
	private Context context;
	private LayoutInflater inflater;
	private List<Mission> missions;
	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	public TachesAdapter(Context context, List<Mission> missions) {
		this.context  = context;
		this.inflater = LayoutInflater.from(context);
		this.missions = missions;
	}

	public void setMissions(List<Mission> missions) {
		this.missions = missions;
		this.notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		if (missions != null) {
			if (missions.size() > missions.size())
				return missions.size() + 1;
			else
				return missions.size();
		}
		return 0;
	}

	@Override
	public Mission getItem(int position) {
		if (position < missions.size())
			return missions.get(position);
		else
			return null;
	}

	@Override
	public long getItemId(int position) {
		if (position < missions.size())
			return Long.parseLong(missions.get(position).getIdentifiant());
		else
			return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		ViewHolder holder;
		if ((convertView == null) || (convertView.getTag() == null)) {
			convertView = inflater.inflate(R.layout.mission_row, null);

			holder = new ViewHolder();
			holder.categorieView = (ImageView) convertView
					.findViewById(R.id.mission_row_category_picto);
			holder.sousCategorieText = (TextView) convertView
					.findViewById(R.id.mission_row_subcategory);
			holder.posteParText = (TextView) convertView
					.findViewById(R.id.mission_row_posted_by);
			holder.prixText = (TextView) convertView
					.findViewById(R.id.mission_row_prix);
			holder.mission_row_status = (TextView) convertView
					.findViewById(R.id.mission_row_status);
			holder.mission_row_km = (TextView) convertView
					.findViewById(R.id.mission_row_km);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		if (position < missions.size()) {
			Mission mission = missions.get(position);

//			holder.sousCategorieText.setText(Html.fromHtml(mission
//					.getDescription()));

			String dd = "";
			try {
				Date datem = dateFormat.parse(mission.getDateCreation());
				dd = sdf.format(datem);

			} catch (ParseException e) {
				e.printStackTrace();
			}

			holder.posteParText.setText("Post� par " + " "
					+"Pseudo" + " " + " Le " + " " + dd);
//			holder.prixText.setText("" + mission.getRemuneration() + " DH");
//			if (mission.getDistance() != null
//					&& !mission.getDistance().equals("")) {
//				holder.mission_row_km.setText("" + mission.getDistance()
//						+ " KM");
//				holder.mission_row_km.setVisibility(View.VISIBLE);
//			} else {
//				holder.mission_row_km.setVisibility(View.GONE);
//			}
			int statut = 0;
//			if (mission.getStatut() != null && !mission.getStatut().equals(""))
//				statut = Integer.parseInt(mission.getStatut());

			switch (statut) {
			case Constant.STATUT_MISSION_ACCEPTE:
				holder.mission_row_status.setText(" " + "Accept�e");
				holder.mission_row_status.setTextColor(context.getResources()
						.getColor(R.color.DarkGray));
				break;
			case Constant.STATUT_MISSION_CLOTURE:
				holder.mission_row_status.setText(" " + "Clotur�e");
				holder.mission_row_status.setTextColor(context.getResources()
						.getColor(R.color.DarkGray));
				break;
			case Constant.STATUT_MISSION_EXPIRE:
				holder.mission_row_status.setText(" " + "Expir�e");
				holder.mission_row_status.setTextColor(context.getResources()
						.getColor(R.color.DarkGray));
				break;
			case Constant.STATUT_MISSION_SUPPRIME:
				holder.mission_row_status.setText(" " + "Supprim�e");
				holder.mission_row_status.setTextColor(context.getResources()
						.getColor(R.color.DarkGray));
				break;
			default:
				holder.mission_row_status.setText(" " + "En recherche");
				holder.mission_row_status.setTextColor(context.getResources()
						.getColor(R.color.DarkGray));
				break;
			}

		}

		return convertView;

	}

	private class ViewHolder {
		ImageView categorieView;
		TextView sousCategorieText;
		TextView posteParText;
		TextView prixText;
		TextView mission_row_km;
		TextView mission_row_status;
	}

	public void addToListResultats(List<Mission> missions) {
			this.missions.addAll(missions);
			this.notifyDataSetChanged();
	}
	public void clear() {
		this.missions.clear();
	}

}
