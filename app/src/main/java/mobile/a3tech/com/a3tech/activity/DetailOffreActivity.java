package mobile.a3tech.com.a3tech.activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.twotoasters.android.horizontalimagescroller.image.ImageToLoad;
import com.twotoasters.android.horizontalimagescroller.widget.HorizontalImageScroller;
import com.twotoasters.android.horizontalimagescroller.widget.HorizontalImageScrollerAdapter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import mobile.a3tech.com.a3tech.R;
import mobile.a3tech.com.a3tech.adapter.ChatAdapter;
import mobile.a3tech.com.a3tech.images.ToasterToLoadDrawableResource;
import mobile.a3tech.com.a3tech.images.ToasterToLoadUrl;
import mobile.a3tech.com.a3tech.manager.MessagesManager;
import mobile.a3tech.com.a3tech.manager.OffresManager;
import mobile.a3tech.com.a3tech.model.Message;
import mobile.a3tech.com.a3tech.model.Offre;
import mobile.a3tech.com.a3tech.service.DataLoadCallback;
import mobile.a3tech.com.a3tech.utils.Constant;
import mobile.a3tech.com.a3tech.view.CircleImageView;
import mobile.a3tech.com.a3tech.view.CustomProgressDialog;
import mobile.a3tech.com.a3tech.view.Helper;


public class DetailOffreActivity extends FragmentActivity implements
		DataLoadCallback {
	String connectedUser;
	String password = "" ;
	String idOffre;
	Offre offre ;
	String tel ;
	String email ;
	
	EditText idDetailOffre_editMessageText;
	ImageButton idDetailOffre_buttonSendMessage;
	LinearLayout idDetailOffre_linearLayoutRetour;
	CircleImageView idDetailOffre_ci_picto;
	TextView idDetailOffre_tv_nom_prenom;
	RatingBar idDetailOffre_ratinBarEval;
	TextView idDetailOffre_tv_mobile_value;
	
	RelativeLayout idDetailOffre_relativeLayoutProfilEmetteur;
	RelativeLayout idDetailOffre_relativeLayoutProfilOffreur;
	CircleImageView idDetailOffre_ci_pictoOffreur;
	TextView idDetailOffre_tv_nom_prenomOffreur;
	RatingBar idDetailOffre_ratinBarEvalOffreur;
	TextView idDetailOffre_tv_mobile_libelleOffreur;
	TextView idDetailOffre_tv_mobile_valueOffreur;
	
	TextView idDetailOffre_tv_motivation_value;
	HorizontalImageScroller scroller_androids;
	ListView idDetailOffre_listeChatPrive;
	ImageLoader imageLoader;
	DisplayImageOptions options   = new DisplayImageOptions.Builder().cacheInMemory().cacheOnDisc().build();
	TextView idDetailOffre_textViewPiecesJointes ;
	View idDetailOffre_viewPiecesJointes ;
	LinearLayout idDetailOffre_linearLayoutPiecesJointes ;
	String urls ="";
	RelativeLayout idDetailOffre_ll_mobile_libelle ;
	
	RelativeLayout idDetailOffre_ll_mobile_libelleOffreur ;
	
	RelativeLayout idDetailOffre_relativeLayoutMessage ;
	SwipeRefreshLayout swipeRefreshLayout ;
	

	ArrayList<ImageToLoad> androidToasters = new ArrayList<ImageToLoad>();
	HorizontalImageScrollerAdapter adapter;
	
	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	public static SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy",Locale.ENGLISH);
    public static SimpleDateFormat formatterHM= new SimpleDateFormat("dd/MM/yyyy HH:mm");
    SimpleDateFormat sdfHeure  = new SimpleDateFormat("HH:mm");
    
    String dateChatPrive = "";
    List<Message> messagesPrives;
    ChatAdapter listAdapterPrive;
    String pseudoConnectedUser ;
    String userId ;
    public static ProgressDialog progressDialog = null;
    public static AlertDialog alertDialog;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.detail_offre_activity);

		SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(getApplicationContext());

		connectedUser = prefs.getString("identifiant", "");
		password = prefs.getString("password", "");
		pseudoConnectedUser = prefs.getString("pseudo", "");
		

		Bundle b = getIntent().getExtras();
		if (b != null) {
			idOffre = b.getString("ID_OFFRE");
			 userId = b.getString(Constant.KEY_EXTRA_DETAIL_MISSION_USER_ID);
		}

		initView();
		OffresManager.getInstance().detailOffre(idOffre,0,connectedUser,password, this);

	}

	private OnClickListener retourListener = new OnClickListener() {
		public void onClick(View v) {
			finish();
		}
	};
	
	private OnClickListener telCallListener = new OnClickListener() {
		public void onClick(View paramAnonymousView) {
			Intent mainIntent = new Intent("android.intent.action.CALL");
			mainIntent.setData(Uri.parse("tel:" + tel));
			startActivity(mainIntent);
		}
	};



	private void initView() {
		 imageLoader = ImageLoader.getInstance();
		idDetailOffre_relativeLayoutMessage = (RelativeLayout) findViewById(R.id.idDetailOffre_relativeLayoutMessage);
		idDetailOffre_editMessageText = (EditText) findViewById(R.id.idDetailOffre_editMessageText);
		idDetailOffre_buttonSendMessage = (ImageButton) findViewById(R.id.idDetailOffre_buttonSendMessage);
		idDetailOffre_linearLayoutRetour = (LinearLayout) findViewById(R.id.idDetailOffre_linearLayoutRetour);
		idDetailOffre_ci_picto = (CircleImageView) findViewById(R.id.idDetailOffre_ci_picto);
		idDetailOffre_tv_nom_prenom = (TextView) findViewById(R.id.idDetailOffre_tv_nom_prenom);
		idDetailOffre_ratinBarEval = (RatingBar) findViewById(R.id.idDetailOffre_ratinBarEval);
		idDetailOffre_tv_mobile_value = (TextView) findViewById(R.id.idDetailOffre_tv_mobile_value);
		
		idDetailOffre_relativeLayoutProfilEmetteur = (RelativeLayout) findViewById(R.id.idDetailOffre_relativeLayoutProfilEmetteur);
		idDetailOffre_relativeLayoutProfilOffreur = (RelativeLayout) findViewById(R.id.idDetailOffre_relativeLayoutProfilOffreur);
		idDetailOffre_ci_pictoOffreur = (CircleImageView) findViewById(R.id.idDetailOffre_ci_pictoOffreur);
		idDetailOffre_tv_nom_prenomOffreur = (TextView) findViewById(R.id.idDetailOffre_tv_nom_prenomOffreur);
		idDetailOffre_ratinBarEvalOffreur = (RatingBar) findViewById(R.id.idDetailOffre_ratinBarEvalOffreur);
		idDetailOffre_tv_mobile_libelleOffreur = (TextView) findViewById(R.id.idDetailOffre_tv_mobile_libelleOffreur);
		idDetailOffre_tv_mobile_valueOffreur = (TextView) findViewById(R.id.idDetailOffre_tv_mobile_valueOffreur);
		
		idDetailOffre_tv_motivation_value = (TextView) findViewById(R.id.idDetailOffre_tv_motivation_value);
		scroller_androids = (HorizontalImageScroller) findViewById(R.id.scroller_androids);
		idDetailOffre_listeChatPrive = (ListView) findViewById(R.id.idDetailOffre_listeChatPrive);
		
		idDetailOffre_textViewPiecesJointes  = (TextView) findViewById(R.id.idDetailOffre_textViewPiecesJointes);
		idDetailOffre_viewPiecesJointes = (View) findViewById(R.id.idDetailOffre_viewPiecesJointes);
		idDetailOffre_linearLayoutPiecesJointes = (LinearLayout) findViewById(R.id.idDetailOffre_linearLayoutPiecesJointes);
		_setupToasterScroller( onItemClickListener);
		 
		idDetailOffre_ll_mobile_libelle = (RelativeLayout) findViewById(R.id.idDetailOffre_ll_mobile_libelle);
		
		idDetailOffre_ll_mobile_libelleOffreur = (RelativeLayout) findViewById(R.id.idDetailOffre_ll_mobile_libelleOffreur);
		

		idDetailOffre_linearLayoutRetour.setOnClickListener(retourListener);
		idDetailOffre_buttonSendMessage.setOnClickListener(sendMessageListener);
		swipeRefreshLayout =   (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
		swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {  
		    @Override
		    public void onRefresh() {
		        // Refresh items
		        refreshItems();
		    }
		});

	
		
        messagesPrives = new ArrayList<Message>();
   	 	listAdapterPrive = new ChatAdapter(this, messagesPrives,userId,connectedUser,null,password );
   	 	idDetailOffre_listeChatPrive.setAdapter(listAdapterPrive);
   	 
		
		idDetailOffre_relativeLayoutMessage.requestFocus();
	}
	
	private void refreshItems() {  
		OffresManager.getInstance().detailOffre(idOffre,1,connectedUser,password, this);
		
	    
	}

	
	private void showMessage(String text, String publicPrive) {
        Message message = new Message();
        message.setTexte(text);
        message.setUserId(connectedUser);
        Date date2 = new Date();
        String dateCreation = formatter.format(date2);
        message.setHeure(sdfHeure.format(date2));
        message.setType(publicPrive);
        message.setPseudo(pseudoConnectedUser);
        message.setOriginator(userId);
        if (dateCreation.equals(dateChatPrive)) {
            message.setDateCreation("");
        } else {
            message.setDateCreation(dateCreation);
        }
        dateChatPrive = dateCreation;
        messagesPrives.add(message);
        listAdapterPrive.notifyDataSetChanged();
        
        Helper.getListViewSize(idDetailOffre_listeChatPrive, this);
           
      
    }
	
	
	
	
	
	
	
	
	public void showMessages(List<Message> messages) {
        for (Message message : messages) {
            String dateCreations = "";
            
            try {
                Date dateCreation = dateFormat.parse(message.getDateCreation());
                dateCreations = formatter.format(dateCreation);
                message.setHeure(sdfHeure.format(dateCreation));
            } catch (ParseException e) {
            }
            if (dateCreations.equals(dateChatPrive)) {
                message.setDateCreation("");
            } else {
                message.setDateCreation(dateCreations);
            }
            dateChatPrive = dateCreations;
            messagesPrives.add(message);
           
        }
     
        listAdapterPrive.notifyDataSetChanged();
        Helper.getListViewSize(idDetailOffre_listeChatPrive, this);
    }

	private OnItemClickListener onItemClickListener =new OnItemClickListener() {
        public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
            Intent i = new Intent(DetailOffreActivity.this, FullScreenViewActivity.class);
            i.putExtra("position", position);
            i.putExtra("urls", urls);
            startActivity(i);
        }
    };
    
	@Override
	public void dataLoaded(Object data, int method, int j) {
		 switch (method) {
         case Constant.KEY_OFFRE_MANAGER_DETAIL_OFFRE :
             offre = (Offre) data;
             switch (j) {
			 case 1:
				idDetailOffre_listeChatPrive.setAdapter(null);
				messagesPrives = new ArrayList<Message>();
				listAdapterPrive = new ChatAdapter(this, messagesPrives,userId,connectedUser,null,password );
				listAdapterPrive.setMessages(messagesPrives);
				idDetailOffre_listeChatPrive.setAdapter(listAdapterPrive);
				Helper.getListViewSize(idDetailOffre_listeChatPrive, this);
				dateChatPrive = "" ;
				showMessages(offre.getMessages());
				swipeRefreshLayout.setRefreshing(false);
				break;
			 default:
				 showDetailOffre();
				break;
			}
            
            
             break;
         case Constant.KEY_MESSAGE_MANAGER_CREATE_MESSAGE :
             if (((String) data).equals("OK")) {
                 showMessage(idDetailOffre_editMessageText.getText().toString(), String.valueOf(0));
                 idDetailOffre_editMessageText.setText("");
             } else {
                 Toast.makeText(getApplicationContext(), R.string.txtDetailSrvc_txtViewMissionChangee, 1).show();
                 Intent returnIntentMsg = new Intent();
                 returnIntentMsg.putExtra(Constant.RESULT_ACTION_CODE, Constant.RESULT_ACTION_CODE_CREATE_MESSAGE);
                 setResult(-1, returnIntentMsg);
                 finish();
             }
             progressDialog.dismiss();
             break;
         case Constant.KEY_ABUS_MANAGER_CREATE_ABUS :
             progressDialog.dismiss();
             Toast.makeText(getApplicationContext(), R.string.txtDetailSrvc_txtMessageResultAbus, 1).show();
             break;
	 }
	}
	
    public void showAttachments(List<String> jointes) {
        if (jointes == null || jointes.size() <= 0) {
        	 idDetailOffre_textViewPiecesJointes.setVisibility(View.GONE);
    		 idDetailOffre_viewPiecesJointes.setVisibility(View.GONE);
    		 idDetailOffre_linearLayoutPiecesJointes.setVisibility(View.GONE);
            
            return;
        }
        androidToasters.remove(0);
        for (String pieceJointe : jointes) {
            ImageToLoad load = new ToasterToLoadUrl(getGravatarUrl(pieceJointe), pieceJointe);
            urls += pieceJointe + ";";
            androidToasters.add(load);
        }
        urls = urls.substring(0, urls.length() - 1);
        adapter.notifyDataSetChanged();
    }
    
    private String getGravatarUrl(String url) {
    	return new StringBuilder(
				Constant.CHECK_EDU_IMAGES_URL).append(url)
				.toString();
       
    }

	
	private void _setupToasterScroller(OnItemClickListener onItemClickListener) {
        androidToasters.add(new ToasterToLoadDrawableResource(R.drawable.portrait, "Adit Shukla"));
        adapter = new HorizontalImageScrollerAdapter(this, androidToasters);
        adapter.setLoadingImageResourceId(R.drawable.generic_toaster);
        adapter.setImageSize((int) getResources().getDimension(R.dimen.image_size));
        adapter.setDefaultImageFailedToLoadResourceId(R.drawable.generic_toaster);
        scroller_androids.setAdapter(adapter);
        scroller_androids.setOnItemClickListener(onItemClickListener);
    }
		 
	 private void showDetailOffre(){
		  imageLoader.displayImage(new StringBuilder(Constant.CHECK_EDU_PICTURES_URL).append(offre.getMissionUser()).append(".jpg").toString(), idDetailOffre_ci_picto, options);
		  idDetailOffre_tv_nom_prenom.setText(offre.getPseudoMission());
		  idDetailOffre_ratinBarEval.setRating(Float.valueOf(offre.getNoteMission()));
		  String telEmetteur = offre.getTelephoneEmetteur();
		  
		  if(telEmetteur!=null && !telEmetteur.equals("")){
			  SpannableString content = new SpannableString(telEmetteur);
			  content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
			  idDetailOffre_tv_mobile_value.setText(content);
		  }

		  idDetailOffre_ll_mobile_libelle.setOnClickListener(telCallListener);
		  
		  idDetailOffre_ll_mobile_libelleOffreur.setOnClickListener(telCallListener);
		 
		  
		  imageLoader.displayImage(new StringBuilder(Constant.CHECK_EDU_PICTURES_URL).append(offre.getUserId()).append(".jpg").toString(), idDetailOffre_ci_pictoOffreur, options);
		  idDetailOffre_tv_nom_prenomOffreur.setText(offre.getPseudo());
		  idDetailOffre_ratinBarEvalOffreur.setRating(Integer.valueOf(offre.getNote()));
		  String tell = offre.getTelephone();
		 
		  if(tell!=null && !tell.equals("")){
			  SpannableString content = new SpannableString(tell);
			  content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
			  idDetailOffre_tv_mobile_valueOffreur.setText(content);
		  }

		  idDetailOffre_tv_motivation_value.setText(offre.getCommentaire());
		  List<String> bitmaps = new ArrayList<String>();
			if(offre.getUrl1()!=null && !offre.getUrl1().equals(""))
				bitmaps.add(offre.getUrl1());
			if(offre.getUrl2()!=null && !offre.getUrl2().equals(""))
				bitmaps.add(offre.getUrl2());
			if(offre.getUrl3()!=null && !offre.getUrl3().equals(""))
				bitmaps.add(offre.getUrl3());
			 showAttachments(bitmaps);
		  
			 if(connectedUser.equals(offre.getMissionUser())){
				 idDetailOffre_relativeLayoutProfilOffreur.setVisibility(View.VISIBLE);
				 tel = offre.getTelephone() ;
				 email =offre.getEmail();
			 }else{
				 email = offre.getEmailEmetteur();
				 tel = offre.getTelephoneEmetteur() ;
				 idDetailOffre_relativeLayoutProfilEmetteur.setVisibility(View.VISIBLE);
			 }
			 if(offre.getEtat()!=null && !offre.getEtat().equals("") && offre.getEtat().equals("1")){
				 idDetailOffre_ll_mobile_libelle.setVisibility(View.VISIBLE);
				 
				  idDetailOffre_ll_mobile_libelleOffreur.setVisibility(View.VISIBLE);
				 
			 }
			
			 showMessages(offre.getMessages());
	      
	 }
	 
	    private OnClickListener sendMessageListener = new OnClickListener() {
	        public void onClick(View v) {
	            if (idDetailOffre_editMessageText.getText().toString().length() == 0) {
	                Toast.makeText(getApplicationContext(), getString(R.string.txtDetailSrvc_textMessageVide), Toast.LENGTH_SHORT).show();
	                return;
	            }
	            progressDialog = CustomProgressDialog.createProgressDialog(DetailOffreActivity.this, getString(R.string.txtMenu_dialogChargement));
	            MessagesManager.getInstance().createMessage(offre.getMissionId(), connectedUser, idDetailOffre_editMessageText.getText().toString(), 1 ,offre.getIdentifiant(),password, DetailOffreActivity.this);
	        }
	    };

	@Override
	public void dataLoadingError(int errorCode) {
		Toast.makeText(getApplicationContext(),R.string.txtSplash_messageCheckConnexion,Toast.LENGTH_SHORT).show();
		try {
			progressDialog.dismiss();
		} catch (Exception e) {
			
		}
		

	}
}
