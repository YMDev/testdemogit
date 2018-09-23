package mobile.a3tech.com.a3tech.model;

import android.os.Parcel;
import android.os.Parcelable;

public class PieceJointe implements Parcelable {
    private String idMission;
    private String identifiant;
    private String url;

    public String getIdentifiant() {
        return this.identifiant;
    }

    public void setIdentifiant(String identifiant) {
        this.identifiant = identifiant;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getIdMission() {
        return this.idMission;
    }

    public void setIdMission(String idMission) {
        this.idMission = idMission;
    }

    public void writeToParcel(Parcel dest, int flags) {
    }

    public int describeContents() {
        return 0;
    }
}
