package mobile.a3tech.com.a3tech.images;

import com.twotoasters.android.horizontalimagescroller.image.ImageToLoadDrawableResource;

public class ToasterToLoadDrawableResource extends ImageToLoadDrawableResource implements ToasterToLoad {
    private String _name;

    public ToasterToLoadDrawableResource(int drawableResourceId, String name) {
        super(drawableResourceId);
        this._name = name;
    }

    public String getName() {
        return this._name;
    }
}
