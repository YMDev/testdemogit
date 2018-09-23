package mobile.a3tech.com.a3tech.capturephoto.util;

import java.io.File;

import android.os.Environment;

public final class FroyoAlbumDirFactory extends AlbumStorageDirFactory {
    public File getAlbumStorageDir(String albumName) {
        return new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), albumName);
    }
}
