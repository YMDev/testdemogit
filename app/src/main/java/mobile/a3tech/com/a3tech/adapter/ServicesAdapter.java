package mobile.a3tech.com.a3tech.adapter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;



import org.codehaus.jackson.util.MinimalPrettyPrinter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.text.Html;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import bakerj.backgrounddarkpopupwindow.BackgroundDarkPopupWindow;
import mobile.a3tech.com.a3tech.R;
import mobile.a3tech.com.a3tech.activity.DetailServiceViewPagerFragment;
import mobile.a3tech.com.a3tech.manager.FormeRemunerationManager;
import mobile.a3tech.com.a3tech.model.FormeRemuneration;
import mobile.a3tech.com.a3tech.model.Mission;
import mobile.a3tech.com.a3tech.utils.Constant;
import mobile.a3tech.com.a3tech.utils.DateManager;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.DisplayImageOptions.Builder;
import com.nostra13.universalimageloader.core.ImageLoader;

public class ServicesAdapter extends BaseAdapter {
	private List<Mission> missions;
	public static List<Mission> mapMissions;
	private Context context;
	SimpleDateFormat dateFormat;
	public ImageLoader imageLoader;
	private LayoutInflater inflater;
	DisplayImageOptions options = new DisplayImageOptions.Builder()
			.cacheInMemory().cacheOnDisc()
			.showStubImage(R.drawable.logo_portrait)
			.showImageForEmptyUri(R.drawable.logo_portrait)
			.showImageOnFail(R.drawable.logo_portrait).resetViewBeforeLoading()
			.build();
	SimpleDateFormat sdf;
	private BackgroundDarkPopupWindow mPopupWindow;
	private boolean isMe;

	private class ViewHolder {
		TextView mission_row_description;

		TextView mission_row_nb_times;
		TextView mission_row_posted_by_date;
		TextView mission_row_posted_by_nb_km;
		TextView mission_row_posted_by_pseudo;
		TextView mission_row_posted_forme;
		ImageView mission_row_profil_picto;
		ImageView mission_row_service_picto;
		TextView mission_row_status;
		TextView sousCategorieText;
		TextView mission_row_nb_vus;
		LinearLayout srvc;
		FrameLayout mission_row_sponsorise;

	}
	
	private static String arabicToenglish(String number) 
	{
	  char[] chars = new char[number.length()];
	  for(int i=0;i<number.length();i++) {
	    char ch = number.charAt(i);
	    if (ch >= 0x0660 && ch <= 0x0669)
	      ch -= 0x0660 - '0';
	    else if (ch >= 0x06f0 && ch <= 0x06F9)
	      ch -= 0x06f0 - '0';
	    chars[i] = ch;
	  }
	  return new String(chars);
	}

	public ServicesAdapter(Context context, List<Mission> miss, boolean is) {
		this.sdf = new SimpleDateFormat("dd/MM/yyyy",Locale.ENGLISH);
		this.dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		this.options = new Builder().cacheInMemory().cacheOnDisc().build();
		this.isMe = is;
		this.context = context;
		this.inflater = LayoutInflater.from(context);
		missions = miss;
		if (isMe == false)
			mapMissions = missions;
		this.imageLoader = ImageLoader.getInstance();
	}

	public void setMissions(List<Mission> miss) {
		missions = miss;
		if (isMe)
			mapMissions = missions;
		notifyDataSetChanged();
	}

	public List<Mission> getMissions() {
		return missions;
	}

	public int getCount() {
		if (missions != null) {
			return missions.size();
		}
		return 0;
	}

	public Mission getItem(int position) {
		if (position < missions.size()) {
			return (Mission) missions.get(position);
		}
		return null;
	}

	public long getItemId(int position) {
		if (position < missions.size()) {
			return Long.parseLong(((Mission) missions.get(position))
					.getIdentifiant());
		}
		return 0;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null || convertView.getTag() == null) {
			convertView = inflater.inflate(R.layout.service_row, null);
			holder = new ViewHolder();
			holder.srvc = (LinearLayout) convertView.findViewById(R.id.srvc);

			holder.mission_row_profil_picto = (ImageView) convertView
					.findViewById(R.id.mission_row_profil_picto);
			holder.sousCategorieText = (TextView) convertView
					.findViewById(R.id.mission_row_subcategory);
			holder.mission_row_status = (TextView) convertView
					.findViewById(R.id.mission_row_status);
			holder.mission_row_service_picto = (ImageView) convertView
					.findViewById(R.id.mission_row_service_picto);
			holder.mission_row_description = (TextView) convertView
					.findViewById(R.id.mission_row_description);
			holder.mission_row_posted_forme = (TextView) convertView
					.findViewById(R.id.mission_row_posted_forme);

			holder.mission_row_nb_times = (TextView) convertView
					.findViewById(R.id.mission_row_nb_times);
			holder.mission_row_posted_by_pseudo = (TextView) convertView
					.findViewById(R.id.mission_row_posted_by_pseudo);
			holder.mission_row_posted_by_date = (TextView) convertView
					.findViewById(R.id.mission_row_posted_by_date);
			holder.mission_row_posted_by_nb_km = (TextView) convertView
					.findViewById(R.id.mission_row_posted_by_nb_km);
			holder.mission_row_nb_vus = (TextView) convertView
					.findViewById(R.id.mission_row_nb_vus);
			holder.mission_row_sponsorise = (FrameLayout) convertView
					.findViewById(R.id.mission_row_sponsorise);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		if (position < missions.size()) {

			Date datem;
			final Mission mission = (Mission) missions.get(position);
			String cat = mission.getCatDescription();
			if (mission.getSponsorise() == 1)
				holder.mission_row_sponsorise.setVisibility(View.VISIBLE);
			else
				holder.mission_row_sponsorise.setVisibility(View.GONE);

			int service = Integer.parseInt(mission.getService());
			FormeRemuneration formeRemuneration = FormeRemunerationManager
					.getInstance().getFormeRemunerationSrvc(service);
			String categorie = context
					.getString(formeRemuneration.getLibelle());
			if (cat != null && !cat.equals(""))
				categorie = categorie + " > " + cat;
			holder.sousCategorieText.setText(Html.fromHtml(categorie));
			holder.mission_row_profil_picto
					.setImageResource(R.drawable.logo_portrait);
			imageLoader.displayImage(new StringBuilder(
					Constant.CHECK_EDU_IMAGES_URL).append(mission.getUrl1())
					.toString(), holder.mission_row_profil_picto, options);
			holder.mission_row_service_picto.setImageResource(formeRemuneration
					.getDrawable());

			holder.mission_row_profil_picto
					.setOnClickListener(new View.OnClickListener() {

						@Override
						public void onClick(View v) {

							showImage(v, mission);

						}
					});

			String forme = "";
			if (mission.getTypeTransaction().equals("0")) {
				if (Integer.parseInt(mission.getContre()) == Constant.CONTRE_JE_SAIS_PAS) {
					forme = context
							.getString(R.string.txt_detailsrvc_msg_jattendvotreproposition);
				} else {
					forme = (mission.getObjetrecherche() == null || mission
							.getObjetrecherche().equals("")) ? "" : mission
							.getObjetrecherche();
				}
			} else {
				forme = context.getString(R.string.txt_mission_row_khod_ajr);
			}
			holder.mission_row_posted_forme.setText(forme);

			String dd = "";
			try {
				datem = this.dateFormat.parse(mission.getDateCreation());
				dd = this.sdf.format(datem);
			} catch (ParseException e2) {
			}
			holder.mission_row_posted_by_pseudo.setText(mission.getPseudo());
			try {
				if (Double.parseDouble(mission.getDistance()) != -1.0d) {
					SpannableString spannableString = new SpannableString(
							mission.getDistance()
									+ MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR
									+ context
											.getString(R.string.txt_msg_mission_row_km_around_me));
					spannableString.setSpan(new UnderlineSpan(), 0,
							spannableString.length(), 0);
					holder.mission_row_posted_by_nb_km.setText(spannableString);
					holder.mission_row_posted_by_nb_km.setVisibility(View.VISIBLE);
				}
			} catch (Exception e3) {
			}
			holder.mission_row_posted_by_date.setText(", " + dd + " , ");
			// int nombreAleatoire =(int)(Math.random() * ((50) + 1));
			// int nombreAleatoire2 = (int)(Math.random() * ((50) + 1));
			// int nombreAleatoire3 = (int)(Math.random() * ((50) + 1));
			//
			// holder.mission_row_nb_messages
			// .setText(nombreAleatoire
			// + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR
			// + context
			// .getString(R.string.txt_msg_mission_row_nombre_messages));
			// holder.mission_row_nb_offres
			// .setText(nombreAleatoire2
			// + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR
			// + context
			// .getString(R.string.txt_msg_mission_row_nombre_offres));
			// holder.mission_row_nb_vus
			// .setText(nombreAleatoire3
			// + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR
			// + context
			// .getString(R.string.txt_msg_mission_row_nombre_vues));

			
			holder.mission_row_nb_vus
					.setText(mission.getNbrVues()
							+ MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR
							+ context
									.getString(R.string.txt_msg_mission_row_nombre_vues));

			try {
				datem = this.dateFormat.parse(mission.getDateFin());
				Date now = new Date();
				String echeanceMission = "";
				if (datem.after(now)) {
					Integer[] elapsed = DateManager.elapsedTime(now, datem);
					if (elapsed[0].intValue() != 0) {
						echeanceMission = new StringBuilder(
								String.valueOf(echeanceMission))
								.append(elapsed[0])
								.append(MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR)
								.append(this.context
										.getString(R.string.txtDetailSrvc_textJours))
								.append(MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR)
								.toString();
					} else if (elapsed[1].intValue() != 0) {
						echeanceMission = new StringBuilder(
								String.valueOf(echeanceMission))
								.append(elapsed[1])
								.append(MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR)
								.append(this.context
										.getString(R.string.txtDetailSrvc_textHeure))
								.append(MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR)
								.toString();
					} else if (elapsed[2].intValue() != 0) {
						echeanceMission = new StringBuilder(
								String.valueOf(echeanceMission))
								.append(elapsed[2])
								.append(MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR)
								.append(this.context
										.getString(R.string.txtDetailSrvc_textMinute))
								.append(MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR)
								.toString();
					} else {
						echeanceMission = new StringBuilder(
								String.valueOf(echeanceMission))
								.append("1")
								.append(this.context
										.getString(R.string.txtDetailSrvc_textMinute))
								.append(MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR)
								.toString();
					}
				} else {
					echeanceMission = "0";
				}
				holder.mission_row_nb_times.setText(echeanceMission);
			} catch (ParseException e4) {
			}
			int statut = 0;
			if (!(mission.getStatut() == null || mission.getStatut().equals(""))) {
				statut = Integer.parseInt(mission.getStatut());
			}

			holder.mission_row_description
					.setText(Html.fromHtml(((mission.getArticle() == null || mission
							.getArticle().equals("")) ? mission.getTitre()
							: mission.getArticle())));
			switch (statut) {
			case Constant.STATUT_MISSION_ACCEPTE /* 1 */:
				holder.mission_row_status
						.setText(new StringBuilder(
								MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR)
								.append(this.context
										.getString(R.string.txtDetailSrvc_txtStatutAccepte))
								.toString());
				holder.mission_row_status.setTextColor(this.context
						.getResources().getColor(R.color.menu));
				holder.srvc.setBackgroundResource(R.drawable.service_contour);
				break;
			case Constant.STATUT_MISSION_FINALE /* 6 */:
			case Constant.STATUT_MISSION_CLOTURE /* 2 */:
				holder.mission_row_status
						.setText(new StringBuilder(
								MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR)
								.append(this.context
										.getString(R.string.txtDetailSrvc_txtStatutCloture))
								.toString());
				holder.mission_row_status.setTextColor(this.context
						.getResources().getColor(R.color.notifMove));
				holder.srvc
						.setBackgroundResource(R.drawable.service_contour_gray);
				break;
			case Constant.STATUT_MISSION_EXPIRE /* 3 */:
				holder.mission_row_status
						.setText(new StringBuilder(
								MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR)
								.append(this.context
										.getString(R.string.txtDetailSrvc_txtStatutExpire))
								.toString());
				holder.mission_row_status.setTextColor(this.context
						.getResources().getColor(R.color.DarkGray));
				holder.srvc
						.setBackgroundResource(R.drawable.service_contour_gray);
				break;
			case Constant.STATUT_MISSION_SUPPRIME /* 5 */:
				holder.mission_row_status
						.setText(new StringBuilder(
								MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR)
								.append(this.context
										.getString(R.string.txtDetailSrvc_txtStatutSupprime))
								.toString());
				holder.mission_row_status.setTextColor(this.context
						.getResources().getColor(R.color.DarkGray));
				holder.srvc
						.setBackgroundResource(R.drawable.service_contour_gray);
				break;
			default:
				holder.mission_row_status
						.setText(new StringBuilder(
								MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR)
								.append(this.context
										.getString(R.string.txtDetailSrvc_txtStatutEnRecherche))
								.toString());
				holder.mission_row_status.setTextColor(this.context
						.getResources().getColor(R.color.viber));
				holder.srvc.setBackgroundResource(R.drawable.service_contour);
				break;
			}
		}
		return convertView;
	}

	public String getRandom(int min, int max, boolean isInt) {
		int random = ((int) (Math.random() * ((double) ((max + 1) - min))))
				+ min;
		if (isInt) {
			return String.valueOf(random);
		}
		return String.valueOf(((float) random) / 10.0f);
	}

	public void showImage(View button, final Mission mission) {
		LayoutInflater layoutInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view = layoutInflater.inflate(R.layout.pop_image, null);
		mPopupWindow = new BackgroundDarkPopupWindow(view,
				WindowManager.LayoutParams.WRAP_CONTENT,
				WindowManager.LayoutParams.WRAP_CONTENT);
		ImageView popup_row_article_picto = (ImageView) view
				.findViewById(R.id.popup_row_article_picto);

		int popupWidth = 1200;
		int popupHeight = 800;

		Point p;
		mPopupWindow.setFocusable(true);
		mPopupWindow.setBackgroundDrawable(new BitmapDrawable());
		mPopupWindow.setAnimationStyle(android.R.style.Animation_Dialog);

		mPopupWindow.setDarkStyle(-1);
		mPopupWindow.setDarkColor(Color.parseColor("#a0000000"));
		mPopupWindow.resetDarkPosition();

		// mPopupWindow.setWidth(popupWidth);
		// mPopupWindow.setHeight(popupHeight);

		int OFFSET_X = 120;
		int OFFSET_Y = 120;

		int[] location = new int[2];

		// Get the x, y location and store it in the location[] array
		// location[0] = x, location[1] = y.
		button.getLocationOnScreen(location);

		// Initialize the Point with x, and y positions
		p = new Point();
		p.x = location[0];
		p.y = location[1];
		popup_row_article_picto.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				Intent mainIntent = new Intent(context,
						DetailServiceViewPagerFragment.class);
				Bundle bundle = new Bundle();
				bundle.putString(Constant.KEY_EXTRA_DETAIL_MISSION_USER_ID,
						mission.getOriginator());
				bundle.putString(Constant.KEY_EXTRA_DETAIL_MISSION_ID,
						mission.getIdentifiant());
				mainIntent.putExtras(bundle);
				((Activity) context).startActivityForResult(mainIntent, 101);

			}
		});
		imageLoader.displayImage(getGravatarUrl(mission.getUrl1()),
				popup_row_article_picto, options);

		mPopupWindow.darkFillScreen();
		// mPopupWindow.showAtLocation(idDetailMissionSrvc_circleImaleProfilPicto,
		// Gravity.NO_GRAVITY, p.x, p.y );
		mPopupWindow.showAtLocation(button, Gravity.NO_GRAVITY, p.x + OFFSET_X,
				p.y + OFFSET_Y);

	}

	private String getGravatarUrl(String url) {
		return new StringBuilder(Constant.CHECK_EDU_IMAGES_URL).append(url)
				.toString();

	}

	public void addToListResultats(List<Mission> miss) {
		missions.addAll(miss);
		if (isMe == false)
			mapMissions = missions;
		notifyDataSetChanged();
	}

	public void clear() {

		missions.clear();
		if (isMe == false)
			mapMissions.clear();
	}
}
