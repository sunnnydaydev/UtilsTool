
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.text.TextUtils;
import android.util.Log;
import java.security.MessageDigest;
import static android.os.Binder.getCallingUid;

/**
 * Created by sunnyday on 2021/8/6 10:56
 *
 *
 *test:https://github.com/sunnnydaydev/UtilsTool/blob/master/app/src/main/java/com/sunnyday/utils/TVActivity.kt
 */
public class AppUtils {

    private static final String TAG = AppUtils.class.getSimpleName();

    /**
     * @param context  application’s context
     * @param knownPkg knownPkg to check
     * @return true represent target app or not false.
     * @function check whether is target app
     */
    public static boolean checkIsTargetApp(Context context, String knownPkg, String knownSHA256) throws IllegalAccessException {
        if (context == null) throw new IllegalAccessException("context can not be empty.");
        String callingPackage = getCallingPackage(context);
        Log.i(TAG, "callPackage = " + callingPackage);

        if (!TextUtils.isEmpty(callingPackage)) {
            if (callingPackage.equals(knownPkg) &&
                    getSignature(context, callingPackage).equals(knownSHA256)) {
                return true;
            } else {
                Log.i(TAG, "Please check the package name and signature information.");
                return false;
            }
        } else {
            Log.i(TAG, "call knownPkg is null.");
            return false;
        }
    }


    /**
     * get calling package by calling uid.
     */
    private static String getCallingPackage(Context context) {
        PackageManager packageManager = context.getPackageManager();
        if (null == packageManager) {
            Log.i(TAG, "PackageManager is null.");
            return null;
        }

        //import static android.os.Binder.getCallingUid;
        // return calling app's uid or not return it's own uid.
        int callUid = getCallingUid();
        Log.i(TAG,"callUid:"+callUid);
        return packageManager.getNameForUid(callUid);
    }

    /**
     * generate app's sha256 Signature by pkg
     */
    private static String getSignature(Context context, String targetPkg) {
        byte[] signByte = null;
        PackageManager manager = context.getPackageManager();
        boolean isEmpty = targetPkg.isEmpty();
        if (isEmpty || manager == null) {
            Log.e(TAG, "getSignature " + "package or manager is null");
            return "";
        } else {
            try {
                PackageInfo packageInfo = manager.getPackageInfo(targetPkg, PackageManager.GET_SIGNATURES);
                Signature[] signatures = packageInfo.signatures;
                Signature sign = signatures[0];
                signByte = sign.toByteArray();
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
        }
        return bytesToHexString(generateSHA256(signByte));
    }

    /**
     * generate sha256 given by byte array.
     */
    private static byte[] generateSHA256(byte[] data) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            messageDigest.update(data);
            return messageDigest.digest();
        } catch (Exception e) {
            Log.e(TAG,"generateSHA256:"+e.getMessage());
        }
        return null;
    }

    /**
     * generate the string which length is 64
     *
     * ps: String consists of hexadecimal digits
     *
     * eg：12cfc00fb180e5ad94bc16834d26b350af6a14ac1761e35d38d041cf98389566
     *
     * */
    private static String bytesToHexString(byte[] bytes) {
        if (bytes == null) {
            return "";
        }
        StringBuilder buff = new StringBuilder();
        for (byte aByte : bytes) {
            if ((aByte & 0xff) < 16) {
                buff.append('0');
            }
            buff.append(Integer.toHexString(aByte & 0xff));
        }
        Log.e(TAG,"bytesToHexString :"+buff.toString());
        return buff.toString();
    }
}
