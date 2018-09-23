package mobile.a3tech.com.a3tech.model;

import java.util.ArrayList;
import java.util.List;

import android.os.Parcel;
import android.os.Parcelable;

public class ReponseOffre  implements Parcelable{
	
	private String result ;
	private List<Offre> offres = new ArrayList<Offre>();
	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		// TODO Auto-generated method stub
		
	}
	
	public List<Offre> getOffres() {
		return offres;
	}
	public void setOffres(List<Offre> offres) {
		this.offres = offres;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}

}
