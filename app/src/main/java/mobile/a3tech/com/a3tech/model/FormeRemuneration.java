package mobile.a3tech.com.a3tech.model;


import mobile.a3tech.com.a3tech.R;

public enum FormeRemuneration {

	ELECTRONIQUE(1, R.drawable.ic_cat_electronique,R.string.txtPostage_textViewElectronique,1),
	INFORMATIQUE(2,R.drawable.ic_cat_informatique,R.string.txtPostage_textViewInformatique,1),
	VETEMENTS_HOMME(3,R.drawable.ic_cat_vetementshomme,R.string.txtPostage_textViewVetementsHomme,1),
	VETEMENTS_FEMME(4,R.drawable.ic_cat_vetementsfemme,R.string.txtPostage_textViewVetementsFemme,1),
	ENFANT(5,R.drawable.ic_cat_enfant,R.string.txtPostage_textViewEnfant,1),
	BEBE(6,R.drawable.ic_cat_bebe,R.string.txtPostage_textViewBebe,1),
	BIEN_ETRE(7,R.drawable.ic_cat_bienetre,R.string.txtPostage_textViewBienEtre,1),
	MAISON(8,R.drawable.ic_cat_maison,R.string.txtPostage_textViewMaison,1),
	TRANSPORT(9,R.drawable.ic_cat_transport,R.string.txtPostage_textViewTransport,1),
	BRICOLAGE(10,R.drawable.ic_cat_bricolage,R.string.txtPostage_textViewBricolage,1),
	LIVRE(11,R.drawable.ic_cat_livre,R.string.txtPostage_textViewLivre,1),
	SPORT(12,R.drawable.ic_cat_sport,R.string.txtPostage_textViewSport,1),
	SORTIE(13,R.drawable.ic_cat_auto_sortie,R.string.txtPostage_textViewSortie,1),
	ART(14,R.drawable.ic_cat_arts,R.string.txtPostage_textViewArt,1),
	ALIMENTATION(15,R.drawable.ic_cat_alimentation,R.string.txtPostage_textViewAlimentation,1),
	ANIMAUX(16,R.drawable.ic_cat_animaux,R.string.txtPostage_textViewAnimaux,1),
	COUP_MAIN(17,R.drawable.ic_cat_coupmain,R.string.txtPostage_textViewCoupMain,1),
	AUTRE(18,R.drawable.ic_cat_autre,R.string.txtPostage_textViewAutre,1);

	private int drawable ;
	private int id ;
	private int libelle;
	private int type ;

	public int getType(){
		return type;
	}
	FormeRemuneration(int id ,int drawable,int libelle,int type) {
		this.id = id ;
		
		this.drawable = drawable ;
		this.libelle = libelle;
		this.type = type ;
	}
	
	public int getLibelle(){
		return libelle;
	}
	
	public int getId(){
		return id ;
	}

	
	
	public int getDrawable(){
		return drawable;
	}
	


	
	
}
