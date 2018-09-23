// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package mobile.a3tech.com.a3tech.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Indicateur implements Parcelable {

	public String getNbrAvis() {
		return nbrAvis;
	}
	public void setNbrAvis(String nbrAvis) {
		this.nbrAvis = nbrAvis;
	}
	public String getNbrImages() {
		return nbrImages;
	}
	public void setNbrImages(String nbrImages) {
		this.nbrImages = nbrImages;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	private String nbrAvis;
	private String nbrImages;
	private String note;
	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		// TODO Auto-generated method stub
		
	}

}
