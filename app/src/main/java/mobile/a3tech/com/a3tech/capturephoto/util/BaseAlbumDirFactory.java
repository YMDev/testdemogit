package mobile.a3tech.com.a3tech.capturephoto.util;

import java.io.File;

import android.os.Environment;

public final class BaseAlbumDirFactory extends AlbumStorageDirFactory {
    private static final String CAMERA_DIR = "/dcim/";

    public File getAlbumStorageDir(String albumName) {
        return new File(Environment.getExternalStorageDirectory() + CAMERA_DIR + albumName);
    }
}
