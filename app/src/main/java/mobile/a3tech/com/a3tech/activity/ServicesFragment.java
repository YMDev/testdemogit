package mobile.a3tech.com.a3tech.activity;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import mobile.a3tech.com.a3tech.R;
import mobile.a3tech.com.a3tech.adapter.NatureEchangeAdapter;
import mobile.a3tech.com.a3tech.adapter.NotificationServiceAdapter;
import mobile.a3tech.com.a3tech.adapter.ServicesAdapter;
import mobile.a3tech.com.a3tech.manager.MissionManager;
import mobile.a3tech.com.a3tech.model.Categorie;
import mobile.a3tech.com.a3tech.model.FormeRemuneration;
import mobile.a3tech.com.a3tech.model.Mission;
import mobile.a3tech.com.a3tech.service.DataLoadCallback;
import mobile.a3tech.com.a3tech.utils.Constant;
import mobile.a3tech.com.a3tech.view.CustomListView;
import mobile.a3tech.com.a3tech.view.Helper;
import mobile.a3tech.com.a3tech.view.SeekArc;
import mobile.a3tech.com.a3tech.view.Toaster;

@SuppressLint({ "HandlerLeak" })
public class ServicesFragment extends Fragment implements DataLoadCallback {

	AlertDialog alertDialog;
	String connectedUser = "";
	String password ="" ;
	int distance = -1;
	CheckBox idSimpleSearch_checkBoxAllServices;
	EditText idSimpleSearch_editTextRecherchePar;
	LinearLayout idSimpleSearch_linearLayoutAnnuler;
	LinearLayout idSimpleSearch_linearLayoutGlobalRayon;
	LinearLayout idSimpleSearch_linearLayoutValider;
	ListView idSimpleSearch_listServices;
	Switch idSimpleSearch_switcherActivationDistance;
	String keyWord = "";
	String premium ="" ;
	String lang = "";
	int limit = 10;
	NotificationServiceAdapter listAdapter;
	List<Categorie> lstServices;
	private ServicesAdapter mAdapter;
	private List<Mission> mList = new ArrayList<Mission>();
	private CustomListView mListView;
	private SeekArc mSeekArc;
	private TextView mSeekArcProgress;
	private boolean searchCheck;
	String services = "";
	int start = 0;
	String checkphone ="" ;
	SearchView searchView ;
	MenuItem menuItem  ;
	
	ImageButton idDetailSrvc_Row_imageButtonAdd ;
	int typeTransaction;
	
	ImageButton idListService_imageButtonDefaut ;
	ImageButton idListService_imageButtonDate ;
	ImageButton idListService_imageButtonDistance ;
	Spinner idListService_spinnerType ;
	
	int order = 0 ;
	int type = -1 ;
	
	NatureEchangeAdapter typeTrocAdapter;
	List<Categorie> typeTrocs;

	private OnClickListener rechercherListener = new OnClickListener() {
		public void onClick(View v) {
			order = 0;
			setButtonImage();
			keyWord = idSimpleSearch_editTextRecherchePar.getText().toString();
			premium = "" ;
			services = getSelectedServices();
			start = 0;
			alertDialog.dismiss();
			mAdapter.clear();
			mAdapter.notifyDataSetChanged();
			mListView.charger();
		}
	};

	private OnClickListener annulerListener = new OnClickListener() {
		public void onClick(View v) {
			alertDialog.dismiss();
		}
	};

	private CompoundButton.OnCheckedChangeListener checkedAllChangeListener = new CompoundButton.OnCheckedChangeListener() {
		public void onCheckedChanged(CompoundButton buttonView,
				boolean isChecked) {
			for (Categorie categorie : lstServices) {
				categorie.setSelected(isChecked);
			}
			listAdapter.notifyDataSetChanged();

		}
	};

	private CompoundButton.OnCheckedChangeListener checkedChangeListener = new CompoundButton.OnCheckedChangeListener() {
		public void onCheckedChanged(CompoundButton buttonView,
				boolean isChecked) {
			if (isChecked) {
				idSimpleSearch_linearLayoutGlobalRayon
						.setVisibility(View.VISIBLE);
				distance = 0;
				return;
			}
			idSimpleSearch_linearLayoutGlobalRayon.setVisibility(View.GONE);
			mSeekArc.setProgress(0);
			mSeekArcProgress.setText(getActivity().getString(
					R.string.txtSeekArcProgress));
			distance = -1;
		}
	};

	
	private SearchView.OnCloseListener onCloseListener = new SearchView.OnCloseListener() {
		@Override
		public boolean onClose() {
		
			return false;
		}
	};
	
	
	private SearchView.OnQueryTextListener OnQuerySearchView = new SearchView.OnQueryTextListener() {
		public boolean onQueryTextChange(String paramAnonymousString) {
			return false;
		}

		public boolean onQueryTextSubmit(String text) {
			order = 0;
			setButtonImage();
			keyWord = text;
			premium = "" ;
			services = "";
			start = 0;
			distance = -1;
			mAdapter.clear();
			mAdapter.notifyDataSetChanged();
			mListView.charger();
			return false;
		}
	};

	private SeekArc.OnSeekArcChangeListener arcChangeListener = new SeekArc.OnSeekArcChangeListener() {
		public void onProgressChanged(SeekArc seekArc, int progress,
				boolean fromUser) {
			mSeekArcProgress.setText(String.valueOf(progress) + " KM");
			distance = progress;
		}

		public void onStartTrackingTouch(SeekArc paramAnonymousSeekArc) {
		}

		public void onStopTrackingTouch(SeekArc paramAnonymousSeekArc) {
		}
	};

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.services_fragment, container,
				false);
		rootView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT));
		return rootView;
	}

	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		setHasOptionsMenu(true);
		setMenuVisibility(true);
		SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(getActivity());
		this.connectedUser = prefs.getString("identifiant", "");
		this.password = prefs.getString("password", "");
		this.checkphone = prefs.getString("checkphone", "");
		this.lang = prefs.getString("ApplicationLanguage",
				Constant.LANGUAGE_FRENSH);
		Bundle extras = getArguments();
		typeTransaction = Integer.parseInt(extras.getString(Constant.TYPE_TRANSACTION));
		initView();
	}

	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		super.onCreateOptionsMenu(menu, inflater);
		inflater.inflate(R.menu.menu, menu);
	    searchView = (SearchView) MenuItemCompat.getActionView(menu
				.findItem(R.id.menu_search));
		 menuItem = menu.findItem(R.id.menu_search);
		
		int currentapiVersion = android.os.Build.VERSION.SDK_INT;
	    if (currentapiVersion >= android.os.Build.VERSION_CODES.ICE_CREAM_SANDWICH)
	    {
	        menuItem.setOnActionExpandListener(new MenuItem.OnActionExpandListener() {
	            @Override
	            public boolean onMenuItemActionCollapse(MenuItem item)
	            {
	            	refresh();
	                return true; 
	            }

	            @Override
	            public boolean onMenuItemActionExpand(MenuItem item)
	            {
	            	
	                return true;
	            }
	        });
	    } else {
	       
	    	searchView.setOnCloseListener(onCloseListener);
	    }

	    

		searchView.setQueryHint(getString(R.string.search));
		((EditText) searchView.findViewById(R.id.search_src_text))
				.setHintTextColor(getResources().getColor(R.color.white));
		searchView.setOnQueryTextListener(OnQuerySearchView);
		
		
		menu.findItem(R.id.menu_add).setVisible(true);
		menu.findItem(R.id.menu_list).setVisible(true);
		menu.findItem(R.id.menu_refresh).setVisible(true);
		menu.findItem(R.id.menu_ssearch).setVisible(true);
		menu.findItem(R.id.menu_search).setVisible(true);
		
		menu.findItem(R.id.menu_more).setVisible(true);
		
		this.searchCheck = false;
	}

	private void replaceFragment(Fragment f) {
        android.support.v4.app.FragmentTransaction transaction =
                                getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.content_frame, f);
        transaction.addToBackStack(null);
        transaction.commit();
      }
	
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menu_add:
			if(connectedUser.equals("")){
				showConnect();
			}else{
				SharedPreferences prefs = PreferenceManager
						.getDefaultSharedPreferences(getActivity());
				this.checkphone = prefs.getString("checkphone", "");
				if(checkphone==null || checkphone.equals("")){
					Toaster.makeLongToast(getActivity().getApplicationContext(),
							getString(R.string.txtLogin_messageConfirmationMobile),
							8000);

					
					
					Intent mainIntent = new Intent(getActivity(),
							ProfilFragment.class);
					Bundle bundle = new Bundle();
					bundle.putString("src","phone");
					mainIntent.putExtras(bundle);
					startActivity(mainIntent);
					//startActivity(new Intent(getActivity(), ProfilFragment.class));
				}else{
					Intent mainIntent = new Intent(getActivity(),
							PostageActivityStep1.class);
					Bundle bundle = new Bundle();
					bundle.putString(Constant.TYPE_TRANSACTION,
							String.valueOf(typeTransaction));
					mainIntent.putExtras(bundle);
					startActivity(mainIntent);
				}
			}
			break;
		case R.id.menu_search:
			this.searchCheck = true;
			break;
		case R.id.menu_map:
			ServicesMapFragment s = new ServicesMapFragment();
			Bundle bndl = new Bundle();
			bndl.putString(Constant.TYPE_TRANSACTION,
					String.valueOf(typeTransaction));
			s.setArguments(bndl);
			replaceFragment(s);
			//startActivity(new Intent(getActivity(), ServicesMap.class));
			break;
		case R.id.menu_ssearch:
			showSimpleSearchDialog();
			break;
		case R.id.menu_refresh:
			searchView.setIconified(true);
			searchView.onActionViewCollapsed();
			menuItem.collapseActionView();
			refresh();
			break;
		
		}
		return true;
	}

	private void premium() {
		this.keyWord = "";
		this.services = "";
		premium = "1";
		this.start = 0;
		this.distance = -1;
		this.mAdapter.clear();
		this.mAdapter.notifyDataSetChanged();
		this.mListView.charger();
	}
	private void refresh() {
		order = 0;
		type = -1 ;
		idListService_spinnerType.setSelection(0);
		setButtonImage();
		this.keyWord = "";
		this.services = "";
		premium = "";
		this.start = 0;
		this.distance = -1;
		this.mAdapter.clear();
		this.mAdapter.notifyDataSetChanged();
		this.mListView.charger();
	}

	private void showSimpleSearchDialog() {
		Handler handler = new Handler();
		handler.post(new Runnable() {
			@Override
			public void run() {
				Builder builder = new Builder(getActivity());
				View content = getActivity().getLayoutInflater().inflate(
						R.layout.simple_search_dialog, null);
				idSimpleSearch_editTextRecherchePar = (EditText) content
						.findViewById(R.id.idSimpleSearch_editTextRecherchePar);
				idSimpleSearch_listServices = (ListView) content
						.findViewById(R.id.idSimpleSearch_listServices);
				idSimpleSearch_switcherActivationDistance = (Switch) content
						.findViewById(R.id.idSimpleSearch_switcherActivationDistance);
				idSimpleSearch_linearLayoutGlobalRayon = (LinearLayout) content
						.findViewById(R.id.idSimpleSearch_linearLayoutGlobalRayon);
				idSimpleSearch_linearLayoutAnnuler = (LinearLayout) content
						.findViewById(R.id.idSimpleSearch_linearLayoutAnnuler);
				idSimpleSearch_linearLayoutValider = (LinearLayout) content
						.findViewById(R.id.idSimpleSearch_linearLayoutValider);
				mSeekArc = (SeekArc) content.findViewById(R.id.seekArc);
				mSeekArcProgress = (TextView) content
						.findViewById(R.id.seekArcProgress);
				mSeekArc.setOnSeekArcChangeListener(arcChangeListener);
				idSimpleSearch_linearLayoutAnnuler
						.setOnClickListener(annulerListener);
				idSimpleSearch_linearLayoutValider
						.setOnClickListener(rechercherListener);
				idSimpleSearch_switcherActivationDistance
						.setOnCheckedChangeListener(checkedChangeListener);
				idSimpleSearch_switcherActivationDistance.setChecked(false);
				lstServices = getServices();
				listAdapter = new NotificationServiceAdapter(getActivity(),
						lstServices, idSimpleSearch_listServices);
				idSimpleSearch_checkBoxAllServices = (CheckBox) content
						.findViewById(R.id.idSimpleSearch_checkBoxAllServices);
				idSimpleSearch_checkBoxAllServices
						.setOnCheckedChangeListener(checkedAllChangeListener);
				idSimpleSearch_listServices.setAdapter(listAdapter);
				Helper.getListViewSize(
						ServicesFragment.this.idSimpleSearch_listServices,
						getActivity());
				builder.setView(content);
				alertDialog = builder.create();
				alertDialog.show();
			}
		});
	}

	public String getSelectedServices() {
		String srvces = "";
		for (Categorie categorie : this.lstServices) {
			if (categorie.isSelected()) {
				srvces = new StringBuilder(String.valueOf(srvces))
						.append(categorie.getIdentifiant()).append(",")
						.toString();
			}
		}
		return srvces.equals("") ? "" : srvces
				.substring(0, srvces.length() - 1);
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
	private OnItemSelectedListener typeTrocListener = new OnItemSelectedListener() {
		@Override
		public void onItemSelected(AdapterView<?> arg0, View arg1,
				int position, long arg3) {
			switch (position) {
			case 0:
				type = -1 ;
				break;
			case 1:
				type = 0 ;
				break;
			case 2:
				type = 1;
				break;

			}
			start = 0;				
			mAdapter.clear();
			mAdapter.notifyDataSetChanged();
			mListView.charger();
		}

		@Override
		public void onNothingSelected(AdapterView<?> arg0) {

		}
	};
	private void initView() {
		mAdapter = new ServicesAdapter(getActivity(), mList,false);
		mListView = (CustomListView) getView().findViewById(R.id.mListView);
		idListService_imageButtonDefaut = (ImageButton) getView().findViewById(R.id.idListService_imageButtonDefaut);
		idListService_imageButtonDate= (ImageButton) getView().findViewById(R.id.idListService_imageButtonDate);
		idListService_imageButtonDistance= (ImageButton) getView().findViewById(R.id.idListService_imageButtonDistance);
		idListService_spinnerType= (Spinner) getView().findViewById(R.id.idListService_spinnerType);
		
		idListService_imageButtonDefaut.setOnClickListener(orderResultListener);
		idListService_imageButtonDate.setOnClickListener(orderResultListener);
		idListService_imageButtonDistance.setOnClickListener(orderResultListener);
		setButtonImage();
		
		typeTrocs = getTypeEchange();
		typeTrocAdapter = new NatureEchangeAdapter(getActivity(),typeTrocs);
		idListService_spinnerType.setAdapter(typeTrocAdapter);
		idListService_spinnerType.setOnItemSelectedListener(typeTrocListener);
		idListService_spinnerType.setSelection(0);
		
		idDetailSrvc_Row_imageButtonAdd =(ImageButton)getView().findViewById(R.id.idDetailSrvc_Row_imageButtonAdd);
		idDetailSrvc_Row_imageButtonAdd.bringToFront();
		idDetailSrvc_Row_imageButtonAdd.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(connectedUser.equals("")){
					showConnect();
				}else{
					SharedPreferences prefs = PreferenceManager
							.getDefaultSharedPreferences(getActivity());
					checkphone = prefs.getString("checkphone", "");
					if(checkphone==null || checkphone.equals("")){
//						Toast.makeText(getActivity().getApplicationContext(),
//								R.string.txtLogin_messageConfirmationMobile,
//								Toast.LENGTH_LONG).show();
						Toaster.makeLongToast(getActivity().getApplicationContext(),
								getString(R.string.txtLogin_messageConfirmationMobile),
								8000);
						
						
						Intent mainIntent = new Intent(getActivity(),
								ProfilFragment.class);
						Bundle bundle = new Bundle();
						bundle.putString("src","phone");
						mainIntent.putExtras(bundle);
						startActivity(mainIntent);
						
						//startActivity(new Intent(getActivity(), ProfilFragment.class));
					}else{
						Intent mainIntent = new Intent(getActivity(),
								PostageActivityStep1.class);
						Bundle bundle = new Bundle();
						bundle.putString(Constant.TYPE_TRANSACTION,
								String.valueOf(typeTransaction));
						mainIntent.putExtras(bundle);
						startActivity(mainIntent);
					}
				}
				
			}
		});
		mListView.setAdapter(this.mAdapter);
		mListView.setOnRefreshListener(new CustomListView.OnRefreshListener() {
			@Override
			public void onRefresh() {
				start = 0;
				keyWord = "";
				order = 0 ;
				services = "";
				distance = -1;
				type = -1 ;
				premium = "" ;
				idListService_spinnerType.setSelection(0);
				MissionManager.getInstance().filtreMission(lang, connectedUser,
						"", "-1", "", Constant.REFRESH_DATA_FINISH,
						String.valueOf(start), String.valueOf(limit),
						connectedUser,String.valueOf(typeTransaction),premium,password,order,type, ServicesFragment.this);
			}
		});
		mListView.setOnLoadListener(new CustomListView.OnLoadMoreListener() {
			@Override
			public void onLoadMore() {
				MissionManager.getInstance().filtreMission(lang, connectedUser,
						keyWord, String.valueOf(distance), services,
						Constant.LOAD_DATA_FINISH, String.valueOf(start),
						String.valueOf(limit), connectedUser,String.valueOf(typeTransaction),premium,password,order,type,
						ServicesFragment.this);

			}
		});
		mListView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> adapterView, View arg1,
					int position, long arg3) {
				if(connectedUser.equals("")){
					showConnect();
				}else{
					
						Mission mission = (Mission) mAdapter.getMissions().get(
								position - 1);
						Intent mainIntent = new Intent(getActivity(),
								DetailServiceViewPagerFragment.class);
						Bundle bundle = new Bundle();
						bundle.putString(Constant.KEY_EXTRA_DETAIL_MISSION_STATUT,
								mission.getStatut());
						bundle.putString(Constant.KEY_EXTRA_DETAIL_MISSION_USER_ID,
								mission.getOriginator());
						bundle.putString(Constant.KEY_EXTRA_DETAIL_MISSION_ID,
								mission.getIdentifiant());
						mainIntent.putExtras(bundle);
						getActivity().startActivityForResult(mainIntent, 101);
					
				}
			}
		});
		this.mListView.charger();
	}

	 private void showConnect(){
   	  Builder builder = new Builder(ServicesFragment.this.getActivity());
         View content = getActivity().getLayoutInflater().inflate(R.layout.message_dialog, null);
         LinearLayout idMessageDialog_linearLayoutAnnuler = (LinearLayout) content.findViewById(R.id.idMessageDialog_linearLayoutAnnuler);
         LinearLayout idMessageDialog_linearLayoutValider = (LinearLayout) content.findViewById(R.id.idMessageDialog_linearLayoutValider);
         ((TextView) content.findViewById(R.id.idMessageDialog_textView)).setText(R.string.txtDetailSrvc_textAlertTitleConnectApp);
         idMessageDialog_linearLayoutValider.setOnClickListener(alertValiderConnetctListener);
         idMessageDialog_linearLayoutAnnuler.setOnClickListener(alertAnnulerListener);
         builder.setTitle(getString(R.string.txtDetailSrvc_textAlertTitleAppConnectApp));
         builder.setView(content);
         alertDialog = builder.create();
         alertDialog.show();
   }
	 
	 private void setButtonImage(){
		 idListService_imageButtonDefaut.setImageResource(order==0 ? R.drawable.order_b:R.drawable.order_c);
		 idListService_imageButtonDate.setImageResource(order==1 ? R.drawable.temps_b:R.drawable.temps_c);
		 idListService_imageButtonDistance.setImageResource(order==2 ? R.drawable.distance_b:R.drawable.distance_c);
		
		
		 
	 }
	
	 public List<Categorie> getTypeEchange() {
			List<Categorie> categories = new ArrayList<Categorie>();
			Categorie hint = new Categorie();
			hint.setLibelle(getString(R.string.txtSevices_tous_les_echanges));
			Categorie service = new Categorie();
			service.setIdentifiant(String.valueOf(Constant.TYPE_TROC_SERVICE));
			service.setLibelle(getString(R.string.txtSevices_tous_les_services));
			Categorie objet = new Categorie();
			objet.setIdentifiant(String.valueOf(Constant.TYPE_TROC_OBJET));
			objet.setLibelle(getString(R.string.txtSevices_tous_les_objets));
			categories.add(hint);
			categories.add(service);
			categories.add(objet);
			return categories;

		}
	 private OnClickListener orderResultListener = new OnClickListener() {
	        public void onClick(View v) {
	            switch (v.getId()) {
				case R.id.idListService_imageButtonDefaut:
					order = 0 ;
					break;
				case R.id.idListService_imageButtonDate:
					order = 1 ;			
					break;
				case R.id.idListService_imageButtonDistance:
					order = 2;
					break;
				
				}
	            setButtonImage();	        
				start = 0;				
				mAdapter.clear();
				mAdapter.notifyDataSetChanged();
				mListView.charger();
	            
	            
	            
	        }
	    };
	    
	    
	    private OnClickListener alertValiderConnetctListener = new OnClickListener() {
	        public void onClick(View v) {
	            alertDialog.dismiss();
	            Intent mainIntent = new Intent(ServicesFragment.this.getActivity(), LoginActivity.class);

				startActivity(mainIntent);
				getActivity().finish();
	        }
	    };
	    
	    private OnClickListener alertAnnulerListener = new OnClickListener() {
	        public void onClick(View v) {
	            alertDialog.dismiss();
	        }
	    };
	    
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == 101 && resultCode == -1) {
			String result = data.getStringExtra(Constant.RESULT_ACTION_CODE);
			if (result.equals(Constant.RESULT_ACTION_CODE_DELETE_MISSION)
					|| result
							.equals(Constant.RESULT_ACTION_CODE_CREATE_MESSAGE)
					|| result.equals(Constant.RESULT_ACTION_CODE_CREATE_OFFRE)
					|| result.equals(Constant.RESULT_ACTION_CODE_DELETE_OFFRE)) {
				refresh();
			}
		}
	}

	public void dataLoaded(Object data, int method, int typeOperation) {
		List<Mission> miss = (List<Mission>) data;
		if (typeOperation == Constant.LOAD_DATA_FINISH) {
			for (int i = miss.size()-1;i>=0;i--) {
				if(this.mAdapter.getMissions().contains(miss.get(i)))
					miss.remove(i);
					
			}
			this.mAdapter.addToListResultats(miss);
			this.mListView.onLoadMoreComplete();
		} else {
			this.mAdapter.clear();
			this.mAdapter.addToListResultats(miss);
			this.mListView.onRefreshComplete();
		}
		this.start += miss.size();
	}

	public void dataLoadingError(int errorCode) {
	}

	public void dataLoadingError(String error) {
	}
}
