
/**
 * Created by sunnyday on 2021/01/16 17:42
 */
public class NetworkUtils {

    private static final String TAG = NetworkUtils.class.getSimpleName();

    /**
     * @function 网络是否可用
     * @param context 上下文
     */
    public static boolean isNetworkAvailable(Context context) {

            ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            if (connectivity != null) {
                NetworkInfo info = connectivity.getActiveNetworkInfo();
                if (info != null && info.isConnected() && info.isAvailable()) {
                    return info.getState() == NetworkInfo.State.CONNECTED;
                }
            }
        return false;
    }

    public static String getNetWorkType(Context context) {
        String type = ""; // 获取不到 默认传"" 空字符串吗

        ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo info = connectivity.getActiveNetworkInfo();
            if (info != null && info.isConnected() && info.isAvailable()) {
                if (info.getState() == NetworkInfo.State.CONNECTED) {
                    switch (info.getType()) {
                        case ConnectivityManager.TYPE_WIFI: // 使用wifi上网
                            type = "wifi";
                            break;
                        case ConnectivityManager.TYPE_MOBILE:// 手机的数据联网
                            type = "mobile";
                            break;
                        case ConnectivityManager.TYPE_ETHERNET: // 以太网就是使用网线
                            type = "ethernet";
                            break;
                        default:
                            type = "unknown";
                            break;
                    }
                }
            } else {
                type = "unavailable";
            }
        }
        return type;
    }

}
