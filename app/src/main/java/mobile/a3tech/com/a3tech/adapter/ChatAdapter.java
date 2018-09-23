package mobile.a3tech.com.a3tech.adapter;

import java.util.ArrayList;
import java.util.List;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.DisplayImageOptions.Builder;
import com.nostra13.universalimageloader.core.ImageLoader;

import android.app.Activity;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import mobile.a3tech.com.a3tech.R;
import mobile.a3tech.com.a3tech.activity.DetailOffreActivity;
import mobile.a3tech.com.a3tech.activity.DetailServiceActivity;
import mobile.a3tech.com.a3tech.activity.DetailServiceViewPagerFragment;
import mobile.a3tech.com.a3tech.manager.AbusManager;
import mobile.a3tech.com.a3tech.model.Categorie;
import mobile.a3tech.com.a3tech.model.Message;
import mobile.a3tech.com.a3tech.service.DataLoadCallback;
import mobile.a3tech.com.a3tech.utils.Constant;
import mobile.a3tech.com.a3tech.view.CircleImageView;
import mobile.a3tech.com.a3tech.view.CustomProgressDialog;

public class ChatAdapter extends BaseAdapter {
	private Context context;
	private LayoutInflater inflater;

	private List<Message> messages;
	private String originator;
	public ImageLoader imageLoader;
	
	Spinner idAbus_spinnerTypeAbus ;
	List<Categorie> typeabuss ;
	CategorieAdapter typeAbusAdapter;
	String connectedUser ;
	String password ;
	Fragment fragment ;
	DisplayImageOptions options = new Builder().cacheInMemory().cacheOnDisc().build();

	public ChatAdapter(Context context, List<Message> messages,String originator,String connected,Fragment fragment,String password) {
		this.context = context;
		inflater = LayoutInflater.from(context);
		this.messages = messages;
		this.originator = originator;
		this.connectedUser = connected;
		this.fragment  = fragment;
		this.password = password;
		 imageLoader = ImageLoader.getInstance();
		
	}

	public void setMessages(List<Message> messages) {
		this.messages = messages;
		this.notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		if (messages != null) {
			if (messages.size() > messages.size())
				return messages.size() + 1;
			else
				return messages.size();
		}
		return 0;
	}

	@Override
	public Message getItem(int position) {
		if (position < messages.size())
			return messages.get(position);
		else
			return null;
	}

	@Override
	public long getItemId(int position) {
		if (position < messages.size())
			return messages.get(position).getIdentifiant();
		else
			return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		Message message = null;
		final int pos = position;
		if (position < messages.size()) {
			 message = messages.get(position);
		}
		String imgId = "";
		int width = context.getResources().getDisplayMetrics().widthPixels;
		ViewHolder holder;
		if ((convertView == null) || (convertView.getTag() == null)) {
			if(message.getType().equals("0") || message.getType().equals("1") ){
				if(message.getUserId().equals(originator))
					convertView = inflater.inflate(R.layout.chat_row_layout_left, null);
				else
					convertView = inflater.inflate(R.layout.chat_row_layout_right, null);
			}else{
				if(message.getUserId().equals(originator))
					convertView = inflater.inflate(R.layout.chat_row_layout_event_left, null);
				else
					convertView = inflater.inflate(R.layout.chat_row_layout_event_right, null);
				if(message.getType().equals("4") ||message.getType().equals("5") ||message.getType().equals("6") ||message.getType().equals("8")||message.getType().equals("3"))
					convertView = inflater.inflate(R.layout.chat_row_layout_event_left, null);
			}
			holder = new ViewHolder();
			holder.profilView = (CircleImageView) convertView.findViewById(R.id.idChat_pseudo);
			holder.heureView = (TextView)convertView.findViewById(R.id.idChat_heure);
			holder.textView = (TextView)convertView.findViewById(R.id.idChat_message);
			holder.idChat_relativeLayoutDate =(RelativeLayout)convertView.findViewById(R.id.idChat_relativeLayoutDate);
			holder.idChat_viewDate = (TextView)convertView.findViewById(R.id.idChat_viewDate);
			holder.idChat_signaler = (ImageButton)convertView.findViewById(R.id.idChat_signaler);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		if (position < messages.size()) {
			
			
			final int idMessage = message.getIdentifiant();
			
			
			if(message.getDateCreation().equals("")){
				holder.idChat_relativeLayoutDate.setVisibility(View.GONE);
			}else{
				holder.idChat_relativeLayoutDate.setVisibility(View.VISIBLE);
				holder.idChat_viewDate.setText(message.getDateCreation());
			}
			holder.textView.setMaxWidth(width-350);
			holder.heureView.setText(message.getHeure());
			String text= "";
			if(message.getType().equals("4") || message.getType().equals("6") || message.getType().equals("5") || message.getType().equals("8")|| message.getType().equals("3")){
				text=message.getPseudoOriginator();
				imgId = message.getUserIdOriginator();
			}else{
				text=message.getPseudo();
				imgId = message.getUserId();
			}
			SpannableString spanString = new SpannableString(text);
			spanString.setSpan(new UnderlineSpan(), 0, spanString.length(), 0);
			if(message.getType().equals("0") || message.getType().equals("1")){
				holder.textView.setText(Html.fromHtml("<u>"+text+"</u><br>"+message.getTexte()));
						
			}else{
				String msg = "";
				int type = Integer.parseInt(message.getType());
				switch (type) {
				case Constant.MESSAGE_PROPOSER_OFFRE:
					msg = context.getString(R.string.txtDetailMission_evenementAproposeUneOffreDe);//+" "+message.getPrix(); 
					break;
                case Constant.MESSAGE_ANNULER_OFFRE:
                	msg = context.getString(R.string.txtDetailMission_evenementAAnnulerUneOffreDee)+" "+message.getPseudo();
					break;
                case Constant.MESSAGE_ANNULER_SON_OFFRE:
                	msg = context.getString(R.string.txtDetailMission_evenementASupprimeSonOffre);
					break;	
					
                case Constant.MESSAGE_ACCEPTER_OFFRE:
                	msg = context.getString(R.string.txtDetailMission_evenementAAccepteUneOffreDe)+" "+message.getPseudo();
					break;
                case Constant.MESSAGE_REJETER_OFFRE:
                	msg = context.getString(R.string.txtDetailMission_evenementARejeteUneOffreDe)+" "+message.getPseudo();
					break;
                case Constant.MESSAGE_CHAT_PRIVE:
                	msg = context.getString(R.string.txtDetailMission_evenementEstEnChatPriveAvec)+" "+message.getPseudo();
					break;
                case Constant.MESSAGE_SUPPRIMER_SON_OFFRE:
                	msg = context.getString(R.string.txtDetailMission_evenementASupprimeSonOffre);
					break;

				default:
					msg = context.getString(R.string.txtDetailMission_evenementASupprimeUneOffreDe)+" "+message.getPseudo();;
					break;
				}
				holder.textView.setText(Html.fromHtml("<u>"+text+"</u><br>"+msg));
				
			}
			 //imageLoader.DisplayImageResized(Constant.CHECK_EDU_PICTURES_URL+imgId+".jpg", holder.profilView,R.drawable.creercompte_btnaddphoto,R.drawable.creercompte_btnaddphoto);
			 imageLoader.displayImage(new StringBuilder(Constant.CHECK_EDU_PICTURES_URL).append(imgId).append(".jpg").toString(), holder.profilView, options);
			 if(message.getAbus()==0){
				 holder.idChat_signaler.setImageResource(R.drawable.button_signaler_abbus);
				 holder.idChat_signaler.setClickable(true);
			 }else{
				 holder.idChat_signaler.setImageResource(R.drawable.button_signaler_abbus_n);
				 holder.idChat_signaler.setClickable(false);
			 }
			 holder.idChat_signaler.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					 android.app.AlertDialog.Builder builder = new  android.app.AlertDialog.Builder((Activity)context);
		                View content = ((Activity)context).getLayoutInflater().inflate(R.layout.abus_dialog, null);
		                 final Spinner idAbus_spinnerTypeAbus = (Spinner) content.findViewById(R.id.idAbus_spinnerTypeAbus);
		                
		                typeabuss = getTypeAbus();
		        		typeAbusAdapter = new CategorieAdapter(context.getApplicationContext(), typeabuss);
		        		idAbus_spinnerTypeAbus.setAdapter(typeAbusAdapter);
		        		idAbus_spinnerTypeAbus.setSelection(0);
		        		
		                LinearLayout idMessageDialog_linearLayoutAnnuler = (LinearLayout) content.findViewById(R.id.idMessageDialog_linearLayoutAnnuler);
		                LinearLayout idMessageDialog_linearLayoutValider = (LinearLayout) content.findViewById(R.id.idMessageDialog_linearLayoutValider);
		            
		                idMessageDialog_linearLayoutValider.setOnClickListener(new OnClickListener() {
							
							@Override
							public void onClick(View v) {
								if (idAbus_spinnerTypeAbus.getSelectedItemPosition() == 0) {
					    			Toast.makeText(context.getApplicationContext(),
					    					R.string.txtAbus_msgChoixTypeAbus, Toast.LENGTH_SHORT).show();
					    			((TextView) idAbus_spinnerTypeAbus.getSelectedView())
					    					.setError(context.getString(R.string.txtAbus_msgChoixTypeAbus));
					    			
					    		}else{
					    			messages.get(pos).setAbus(1);
					    			ChatAdapter.this.notifyDataSetChanged();
					    			
						          
						            if(context instanceof DetailServiceViewPagerFragment){
							            DetailServiceActivity.progressDialog = CustomProgressDialog.createProgressDialog(context, context.getString(R.string.txtMenu_dialogChargement));
							            AbusManager.getInstance().createAbus("",idMessage,idAbus_spinnerTypeAbus.getSelectedItemPosition(), connectedUser,password, (DataLoadCallback)fragment);
							            DetailServiceActivity.alertDialog.dismiss();
						            }else{
						            	 DetailOffreActivity.progressDialog = CustomProgressDialog.createProgressDialog(context, context.getString(R.string.txtMenu_dialogChargement));
								         AbusManager.getInstance().createAbus("",idMessage,idAbus_spinnerTypeAbus.getSelectedItemPosition(), connectedUser,password, (DataLoadCallback)context);
								         DetailOffreActivity.alertDialog.dismiss();
						            }
						    		}
								
							}
						});
		                idMessageDialog_linearLayoutAnnuler.setOnClickListener(new OnClickListener() {
							
							@Override
							public void onClick(View v) {
								if(context instanceof DetailServiceViewPagerFragment)
									DetailServiceActivity.alertDialog.dismiss();
								else
									DetailOffreActivity.alertDialog.dismiss();
								
							}
						});
		                builder.setTitle(context.getString(R.string.txtDetailSrvc_textAlertTitleSignalerAbus));
		                builder.setView(content);
		                if(context instanceof DetailServiceViewPagerFragment){
			                DetailServiceActivity.alertDialog = builder.create();
			                DetailServiceActivity.alertDialog.show();
		                }else{
		                	DetailOffreActivity.alertDialog = builder.create();
		                	DetailOffreActivity.alertDialog.show();
		                }
					
				}
			});
			
		}
		

		return convertView;

	}
	
  
	
	
	
	public List<Categorie> getTypeAbus() {

		List<Categorie> categories = new ArrayList<Categorie>();
		Categorie hint = new Categorie();
		hint.setLibelle(context.getString(R.string.txtAbus_choisirAbus));

		Categorie service = new Categorie();
		service.setIdentifiant("0");
		service.setLibelle(context.getString(R.string.txtAbus_choixAbusContrefacon));
		
		Categorie objet = new Categorie();
		objet.setIdentifiant("1");
		objet.setLibelle(context.getString(R.string.txtAbus_choixAbusContreViolation));
		
		categories.add(hint);
		categories.add(service);
		categories.add(objet);
		
		return categories;

	}

	
	
	/**
	 * 
	 */
	private class ViewHolder {
		CircleImageView profilView;
		TextView textView;
		TextView heureView;
		RelativeLayout idChat_relativeLayoutDate;
		TextView idChat_viewDate;
		ImageButton idChat_signaler ;
		
	}
	

	
}
