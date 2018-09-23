package mobile.a3tech.com.a3tech.capturephoto.util;

import java.io.File;

abstract class AlbumStorageDirFactory {
    public abstract File getAlbumStorageDir(String str);

    AlbumStorageDirFactory() {
    }
}
