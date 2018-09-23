package mobile.a3tech.com.a3tech.adapter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import mobile.a3tech.com.a3tech.R;
import mobile.a3tech.com.a3tech.lazyimagelist.ImageLoader;
import mobile.a3tech.com.a3tech.manager.FormeRemunerationManager;
import mobile.a3tech.com.a3tech.model.Evaluation;
import mobile.a3tech.com.a3tech.utils.Constant;

public class EvaluationAdapter extends BaseAdapter {
	private Context context;
	private LayoutInflater inflater;

	private List<Evaluation> evaluations;

	
	
	public ImageLoader imageLoader;
	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	
	

	public EvaluationAdapter(Context context, List<Evaluation> evaluations) {
		this.context = context;
		inflater = LayoutInflater.from(context);
		
		this.evaluations = evaluations;
		this.imageLoader =  new ImageLoader(context.getApplicationContext());
	}

	public void setMissions(List<Evaluation> evaluations) {
		this.evaluations = evaluations;
		this.notifyDataSetChanged();

		
	}

	@Override
	public int getCount() {
		if (evaluations != null) {
			if (evaluations.size() > evaluations.size())
				return evaluations.size() + 1;
			else
				return evaluations.size();
		}
		return 0;
	}

	@Override
	public Evaluation getItem(int position) {
		if (position < evaluations.size())
			return evaluations.get(position);
		else
			return null;
	}

	@Override
	public long getItemId(int position) {
		if (position < evaluations.size())
			return Long.parseLong(evaluations.get(position).getIdentifiant());
		else
			return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		ViewHolder holder;
		if ((convertView == null) || (convertView.getTag() == null)) {
			convertView = inflater.inflate(R.layout.evaluation_row, null);

			holder = new ViewHolder();
			holder.idEvaluationRow_textViewDescription = (TextView) convertView.findViewById(R.id.idEvaluationRow_textViewDescription);
			holder.idEvaluationRow_textViewPseudo = (TextView)convertView.findViewById(R.id.idEvaluationRow_textViewPseudo);
			holder.idEvaluationRow_ratingBar = (RatingBar)convertView.findViewById(R.id.idEvaluationRow_ratingBar);
			holder.idEvaluationRow_imageViewProfil = (ImageView)convertView.findViewById(R.id.idEvaluationRow_imageViewProfil);
		  
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		if (position < evaluations.size()) {
			Evaluation evaluation = evaluations.get(position);
			String cat = evaluation.getCategorie();
			String scat = evaluation.getScategorie() ;
			int service = Integer.parseInt(evaluation.getService());
			
			holder.idEvaluationRow_textViewDescription.setText(Html.fromHtml(evaluation.getMissionDescription()));
			holder.idEvaluationRow_textViewDescription.setText(Html.fromHtml(FormeRemunerationManager.getInstance().getCatScat(service, cat, scat)));
			 String dd = "";
			 try {
				 Date datem = dateFormat.parse(evaluation.getDateCreation());
				 dd = sdf.format(datem);	
			 } catch (ParseException e) {
			 }
			holder.idEvaluationRow_textViewPseudo.setText(Html.fromHtml(evaluation.getPseudo())+context.getResources().getString(R.string.txtEvaluationRow_txtLe)+" "+dd);
			holder.idEvaluationRow_ratingBar.setRating(Float.parseFloat(evaluation.getNote()));
			imageLoader.DisplayImageResized(Constant.CHECK_EDU_PICTURES_URL+evaluation.getEvaluateurId()+".jpg", holder.idEvaluationRow_imageViewProfil,R.drawable.creercompte_btnaddphoto,R.drawable.creercompte_btnaddphoto);
			
		}
		

		return convertView;

	}

	
	/**
	 * 
	 */
	private class ViewHolder {
		TextView idEvaluationRow_textViewDescription ;
		TextView idEvaluationRow_textViewPseudo ;
		RatingBar idEvaluationRow_ratingBar ;
		ImageView idEvaluationRow_imageViewProfil;
		
		
	}
	
	public void addToListResultats(List<Evaluation> evaluations) {
		this.evaluations.addAll(evaluations);
		this.notifyDataSetChanged();
	}
	public void clear() {
		this.evaluations.clear();
	}
	
	/**
	 * 
	 */
	
	
	/**
	 * 
	 */
	

}
