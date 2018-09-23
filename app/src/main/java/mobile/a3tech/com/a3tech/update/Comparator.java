
package mobile.a3tech.com.a3tech.update;

import android.app.Activity;
import android.content.pm.PackageManager;

public class Comparator {
    
    public static boolean isVersionDownloadableNewer(Activity mActivity, String versionDownloadable) {
        String versionInstalled = null;
        try {
            versionInstalled = mActivity.getPackageManager().getPackageInfo(mActivity.getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException ignored) {
        }
        if (versionInstalled.equals(versionDownloadable)) { 
            return false;
        } else {
            return versionCompareNumerically(versionDownloadable, versionInstalled); 
        }
    }

    
    public static boolean versionCompareNumerically(String str1, String str2) {
        String[] vals1 = str1.split("\\.");
        String[] vals2 = str2.split("\\.");
        if( vals1[0].equals(vals2[0]))return false;
        else return true;
        
    }

}
