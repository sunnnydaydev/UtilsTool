
/**
 * Created by sunnyDay on 2021/3/18 15:13
 
 * 网络状态是否可用检测：
 * 1、支持一次调用：checkNetWork
 * 2、支持实时检测：registerNetworkCallback
 * 3、兼容安卓10：
 *              a、NetworkInfo的获取。安卓10 废弃了NetworkInfo对象。取而代之的是使用NetworkCapabilities
 *
 *              b、网络是否可用、网络连接类型（手机 wifi 因特网）都可通过NetworkCapabilities 来进行判断。
 *
 *         connectivityManager.getActiveNetworkInfo();//获取当前使用 NetworkInfo，安卓10已经弃用
 *         connectivityManager.getAllNetworkInfo();//获得所有的NetworkInfo，已经废弃。
 *         connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);//根据指定的类型获取指定类型的NetworkInfo 已经废弃(数据、wifi、因特网（网线）类型)
 *         connectivityManager.getNetworkInfo(network); //根据netWork对象获取相应的NetworkInfo。已废弃
 *
 *
 * 更多信息可参考官方文档：
 * https://developer.android.com/training/monitoring-device-state/connectivity-status-type?hl=zh-cn#DetermineConnection
 *
 * https://developer.android.com/training/basics/network-ops/managing?hl=zh-cn
 */


public class NetWorkActivity extends AppCompatActivity {
    private static final String TAG = NetWorkActivity.class.getSimpleName();
    private boolean isNetworkAvailable;
    private ConnectivityManager connectivityManager;
    private ConnectivityManager.NetworkCallback networkCallback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_net_work);

        connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);

        checkNetWork();
        registerNetworkCallback();
    }

    /**
     * check netWork ：
     *
     *    is available
     *    connect net type
     */
    private void checkNetWork() {
        Network network = connectivityManager.getActiveNetwork();
        if (network == null) return;

        NetworkCapabilities capabilities = connectivityManager.getNetworkCapabilities(network);
        isNetworkAvailable = capabilities != null && capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED);

        Log.i(TAG, "checkNetWork: " + isNetworkAvailable);
        Log.i(TAG, "NetWorkType wifi ?: " + capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR));


    }

    /**
     *  netWork change listener
     * */
    private void registerNetworkCallback() {
        networkCallback = new ConnectivityManager.NetworkCallback() {

            @Override
            public void onLost(@NonNull Network network) {
                super.onLost(network);
                isNetworkAvailable = false;
                Log.i(TAG, "onLost: " + false);
            }

            @Override
            public void onCapabilitiesChanged(@NonNull Network network, @NonNull NetworkCapabilities networkCapabilities) {
                super.onCapabilitiesChanged(network, networkCapabilities);
                if (!isNetworkAvailable) {
                    if (networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED)) {
                        isNetworkAvailable = true;
                        Log.i(TAG, "onCapabilitiesChanged: " + true);
                    }
                }
            }
        };
        connectivityManager.registerDefaultNetworkCallback(networkCallback);
    }

    @Override
    protected void onDestroy() {
        Log.i(TAG, "onDestroy");
        if (isFinishing() && connectivityManager != null && networkCallback != null) {
            connectivityManager.unregisterNetworkCallback(networkCallback);
        }
        super.onDestroy();
    }
}