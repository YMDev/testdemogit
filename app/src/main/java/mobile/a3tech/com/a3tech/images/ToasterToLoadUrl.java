package mobile.a3tech.com.a3tech.images;

import com.twotoasters.android.horizontalimagescroller.image.ImageToLoadUrl;

public class ToasterToLoadUrl extends ImageToLoadUrl implements ToasterToLoad {
    private String _name;

    public ToasterToLoadUrl(String url, String name) {
        super(url);
        this._name = name;
        this._canCacheFile = true;
    }

    public String getName() {
        return this._name;
    }
}
