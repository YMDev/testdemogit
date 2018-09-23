package mobile.a3tech.com.a3tech.images;

import android.os.Parcel;
import android.os.Parcelable;

public class Image implements Parcelable {

	protected Image(Parcel in) {
		duration = in.readString();
		identifiant = in.readString();
		img = in.readString();
		type = in.readString();
	}

	public static final Creator<Image> CREATOR = new Creator<Image>() {
		@Override
		public Image createFromParcel(Parcel in) {
			return new Image(in);
		}

		@Override
		public Image[] newArray(int size) {
			return new Image[size];
		}
	};

	public String getDuration() {
		return duration;
	}
	public void setDuration(String duration) {
		this.duration = duration;
	}
	public String getIdentifiant() {
		return identifiant;
	}
	public void setIdentifiant(String identifiant) {
		this.identifiant = identifiant;
	}
	public String getImg() {
		return img;
	}
	public void setImg(String img) {
		this.img = img;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	private String duration;
	private String identifiant;
	private String img;
	private String type;
	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		// TODO Auto-generated method stub

		dest.writeString(duration);
		dest.writeString(identifiant);
		dest.writeString(img);
		dest.writeString(type);
	}

}
