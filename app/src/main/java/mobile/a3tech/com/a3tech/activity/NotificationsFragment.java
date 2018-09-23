package mobile.a3tech.com.a3tech.activity;

import java.util.ArrayList;
import java.util.List;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import mobile.a3tech.com.a3tech.R;
import mobile.a3tech.com.a3tech.manager.UserManager;
import mobile.a3tech.com.a3tech.model.Categorie;
import mobile.a3tech.com.a3tech.model.FormeRemuneration;
import mobile.a3tech.com.a3tech.model.SortCategorie;
import mobile.a3tech.com.a3tech.model.User;
import mobile.a3tech.com.a3tech.service.DataLoadCallback;
import mobile.a3tech.com.a3tech.view.CustomProgressDialog;
import mobile.a3tech.com.a3tech.view.Helper;
import mobile.a3tech.com.a3tech.view.SeekArc;

public class NotificationsFragment extends Activity implements DataLoadCallback {
    AlertDialog alertDialog;
   
    String connectedUser = "";
    String password = "" ;
    int distance;
   
    ExpandableListView expListView;
    ListView expListViewCat;
    ListView expListViewSCat;
    LinearLayout idNotifCScat_linearLayoutOK;
    TextView idNotifCScat_textViewTitleCat;
    TextView idNotifCScat_textViewTitleCatSCat;
    TextView idNotifCScat_textViewTitleSCat;
    CheckBox idNotification_checkBoxAllServices;
    LinearLayout idNotification_linearLayoutEnregistrer;
    ListView idNotification_listServices;
    NotificationServiceAdapter listAdapter;
    List<Categorie> lstServices;
    private SeekArc mSeekArc;
    private TextView mSeekArcProgress;
    NotificationServiceAdapter notificationCatAdapter;
    NotificationServiceAdapter notificationSCatAdapter;
    ProgressDialog progressDialog = null;
    String serviceId;
    String serviceLibelle;
    String serviceType;
    SortCategorie sortCategorie;
    String srvId;
    User user;
    Switch idSettings_switchNotification ;
    LinearLayout idNotification_linearLayoutRetour ;
    LinearLayout idNotification_linearLayoutDistance ;
    LinearLayout idNotification_linearLayoutCategories ;

   
    private OnCheckedChangeListener checkedChangeListener =new OnCheckedChangeListener() {
      
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            for (Categorie categorie : lstServices) {
                categorie.setSelected(isChecked);
            }
            listAdapter.notifyDataSetChanged();
        }
    };

    
    private OnClickListener enregistrerListener =new  OnClickListener() {
      

        public void onClick(View v) {
            String srvc = getSelectedServices();
            progressDialog = CustomProgressDialog.createProgressDialog(NotificationsFragment.this, getString(R.string.txtMenu_dialogChargement));
            UserManager.getInstance().saveProfil(connectedUser, "", "", "", "", "", "", "", "", "", idSettings_switchNotification.isChecked()?"1":"0", String.valueOf(distance), srvc, null, null,null,password, NotificationsFragment.this);
        }
    };

    private class NotificationServiceAdapter extends BaseAdapter {
        private List<Categorie> categories;
        private LayoutInflater inflater;

    

        private class ViewHolder {
            CheckBox idNotifSrvc_checkBox;
            ImageView notif_srvc_row_arrow_picto;
            TextView notif_srvc_row_srvc_libelle;
            ImageView notif_srvc_row_srvc_picto;

            private ViewHolder() {
            }
        }

        public NotificationServiceAdapter(Context context, List<Categorie> cat, ListView listView) {
            inflater = LayoutInflater.from(context);
            categories = cat;
        }

        public int getCount() {
            if (categories == null) {
                return 0;
            }
            if (categories.size() > categories.size()) {
                return categories.size() + 1;
            }
            return categories.size();
        }

        public Categorie getItem(int position) {
            if (position < categories.size()) {
                return (Categorie) categories.get(position);
            }
            return null;
        }

        public long getItemId(int position) {
            if (position < categories.size()) {
                return Long.parseLong(((Categorie) categories.get(position)).getIdentifiant());
            }
            return 0;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null || convertView.getTag() == null) {
                convertView = inflater.inflate(R.layout.notification_service_row, null);
                holder = new ViewHolder();
                holder.notif_srvc_row_srvc_picto = (ImageView) convertView.findViewById(R.id.notif_srvc_row_srvc_picto);
                holder.notif_srvc_row_srvc_libelle = (TextView) convertView.findViewById(R.id.notif_srvc_row_srvc_libelle);
                holder.idNotifSrvc_checkBox = (CheckBox) convertView.findViewById(R.id.idNotifSrvc_checkBox);
                holder.notif_srvc_row_arrow_picto = (ImageView) convertView.findViewById(R.id.notif_srvc_row_arrow_picto);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            if (position < categories.size()) {
                final Categorie categorie = (Categorie) categories.get(position);
                try {
                    holder.notif_srvc_row_srvc_picto.setImageResource(Integer.parseInt(categorie.getLogo()));
                } catch (Exception e) {
                    holder.notif_srvc_row_srvc_picto.setVisibility(View.GONE);
                    holder.notif_srvc_row_arrow_picto.setVisibility(View.GONE);
                }
                try {
                    holder.notif_srvc_row_srvc_libelle.setText(Integer.parseInt(categorie.getLibelle()));
                } catch (Exception e2) {
                    holder.notif_srvc_row_srvc_libelle.setText(categorie.getLibelle());
                }
                if (categorie.isSelected()) {
                    holder.idNotifSrvc_checkBox.setChecked(true);
                } else {
                    holder.idNotifSrvc_checkBox.setChecked(false);
                }
               
                holder.idNotifSrvc_checkBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
					
					@Override
					public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
						 categorie.setSelected(isChecked);
						
					}
				});
            }
            return convertView;
        }
    }

	private OnCheckedChangeListener notifcheckedChangeListener = new OnCheckedChangeListener() {
		public void onCheckedChanged(CompoundButton buttonView,
				boolean isChecked) {
			if (isChecked) {
				 idNotification_linearLayoutDistance.setVisibility(View.VISIBLE);
		          idNotification_linearLayoutCategories.setVisibility(View.VISIBLE);
				
				return;
			}else{
				idNotification_checkBoxAllServices.setChecked(false);
				idNotification_linearLayoutDistance.setVisibility(View.GONE);
		        idNotification_linearLayoutCategories.setVisibility(View.GONE);
				distance = 0;
				mSeekArcProgress.setText(new StringBuilder(String.valueOf(String.valueOf(distance))).append(" KM").toString());
                mSeekArc.setProgress(distance);
				for (Categorie categorie : lstServices) {
		           categorie.setSelected(false);
		        }
		        listAdapter.notifyDataSetChanged();
				
			}
			
		}
	};

    protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(1);
		setContentView(R.layout.notifications_fragment);
		 mSeekArc = (SeekArc)findViewById(R.id.seekArc);
		 idNotification_linearLayoutRetour = (LinearLayout)findViewById(R.id.idNotification_linearLayoutRetour);
         mSeekArcProgress = (TextView)findViewById(R.id.seekArcProgress);
         idNotification_listServices = (ListView)findViewById(R.id.idNotification_listServices);
         idNotification_linearLayoutEnregistrer = (LinearLayout)findViewById(R.id.idNotification_linearLayoutEnregistrer);
         idNotification_linearLayoutEnregistrer.setOnClickListener(enregistrerListener);
         idSettings_switchNotification =(Switch)findViewById(R.id.idSettings_switchNotification);
          idNotification_linearLayoutDistance = (LinearLayout)findViewById(R.id.idNotification_linearLayoutDistance);
          idNotification_linearLayoutCategories = (LinearLayout)findViewById(R.id.idNotification_linearLayoutCategories);
          idSettings_switchNotification
			.setOnCheckedChangeListener(notifcheckedChangeListener);
         lstServices = getServices();
         listAdapter = new NotificationServiceAdapter(this, lstServices, idNotification_listServices);
         idNotification_checkBoxAllServices = (CheckBox)findViewById(R.id.idNotification_checkBoxAllServices);
         idNotification_checkBoxAllServices.setOnCheckedChangeListener(checkedChangeListener);
         idNotification_listServices.setAdapter(listAdapter);
         connectedUser = PreferenceManager.getDefaultSharedPreferences(this).getString("identifiant", "");
         password = PreferenceManager.getDefaultSharedPreferences(this).getString("password", "");
         idNotification_linearLayoutRetour.requestFocus();
         mSeekArc.setOnSeekArcChangeListener(new SeekArc.OnSeekArcChangeListener() {
				
				@Override
				public void onStopTrackingTouch(SeekArc seekArc) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void onStartTrackingTouch(SeekArc seekArc) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void onProgressChanged(SeekArc seekArc, int progress,
						boolean fromUser) {
					 mSeekArcProgress.setText(String.valueOf(progress) + " KM");
			            distance = progress;
					
				}
			});
	        
	        idNotification_linearLayoutRetour.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					finish();
				}
			});
	        Helper.getListViewSize(idNotification_listServices, this);
	        progressDialog = CustomProgressDialog.createProgressDialog(this, getString(R.string.txtMenu_dialogChargement));
	        UserManager.getInstance().getProfil(connectedUser,password, this);
       
    }

    

    public List<Categorie> getServices() {
        List<Categorie> categories = new ArrayList<Categorie>();
        for (FormeRemuneration type : FormeRemuneration.values()) {
            Categorie categorie = new Categorie();
            categorie.setIdentifiant(String.valueOf(type.getId()));
            categorie.setLibelle(String.valueOf(type.getLibelle()));
            categorie.setLogo(String.valueOf(type.getDrawable()));
            categorie.setSrvcType(String.valueOf(type.getType()));
            categorie.setSelected(false);
            categories.add(categorie);
        }
        return categories;
    }

    public void selection(String srvc) {
        if (srvc != null && !srvc.equals("")) {
            String[] ids = srvc.split(",");
            for (Categorie categorie : lstServices) {
                for (Object equals : ids) {
                    if (categorie.getIdentifiant().equals(equals)) {
                        categorie.setSelected(true);
                    }
                }
            }
        }
    }

    public String getSelectedServices() {
        String srvces = "";
        for (Categorie categorie : lstServices) {
            if (categorie.isSelected()) {
                srvces = new StringBuilder(String.valueOf(srvces)).append(categorie.getIdentifiant()).append(",").toString();
            }
        }
        return srvces.equals("") ? "20" : srvces.substring(0, srvces.length() - 1);
    }

    public void dataLoaded(Object data, int method, int typeOperation) {
        switch (method) {
            case 8 /*8*/:
                user = (User) data;
                if(user.getMode()!=null && user.getMode().equals("1")){
                	idSettings_switchNotification.setChecked(true);
                	idNotification_linearLayoutDistance.setVisibility(View.VISIBLE);
                    idNotification_linearLayoutCategories.setVisibility(View.VISIBLE);
                }else{
                	idSettings_switchNotification.setChecked(false);
                }
                if (!(user.getDistance() == null || user.getDistance().equals(""))) {
                    distance = Integer.parseInt(user.getDistance());
                    mSeekArcProgress.setText(new StringBuilder(String.valueOf(String.valueOf(distance))).append(" KM").toString());
                    mSeekArc.setProgress(distance);
                }
                selection(user.getSrvc());
                listAdapter.notifyDataSetChanged();
                progressDialog.dismiss();
                break;
            case 9 /*9*/:
                Toast.makeText(getApplicationContext(), R.string.txtNotification_txtMessageEnregistrerSucces, Toast.LENGTH_LONG).show();
                progressDialog.dismiss();
                break;
            default:
        }
    }

    public void dataLoadingError(int errorCode) {
    	Toast.makeText(getApplicationContext(),R.string.txtSplash_messageCheckConnexion,Toast.LENGTH_SHORT).show();
		try {
			progressDialog.dismiss();
		} catch (Exception e) {
			
		}
    }

}
