package com.sunnyday.framework;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.URL;
import java.util.Enumeration;

/**
 * Created by sunnyDay on 2021/01/19  15:04
 */
public class NetWorkUtils {

    public static final String TAG = "NetWorkUtils";

    /**
     * 判断当前网络是否可用（网络连接&可上网，网络才是可用）
     *
     * @param context 上下文
     * @return boolean值，网络是否可用。true网络可用，false 网路不可用。
     */
    public static boolean isNetworkAvailable(Context context) {

        ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = null;
        if (connectivity != null) info = connectivity.getActiveNetworkInfo();

        return info != null && info.isConnected() && info.isAvailable();
    }

    /**
     * 获取内网ip（wifi下的ip、手机数据下的ip）
     *
     * @param context 上下文
     * @return 获取到，返回ip字符串（例如192.168.1.1）。获取不到返回""
     */
    public static String getIPAddress(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo info = connectivity.getActiveNetworkInfo();
            if (info != null && info.isConnected() && info.isAvailable()) { // 网络可用
                try {
                    Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces(); // 获得所有类型对应的NetworkInterface结果集（wifi的、mobile的等等）
                    while (networkInterfaces != null && networkInterfaces.hasMoreElements()) {
                        NetworkInterface networkInterface = networkInterfaces.nextElement();
                        Enumeration<InetAddress> inetAddresses = networkInterface.getInetAddresses(); // 获取InetAddress结果集

                        while (inetAddresses != null && inetAddresses.hasMoreElements()) {
                            InetAddress inetAddress = inetAddresses.nextElement();
                            if (!inetAddress.isLoopbackAddress() && inetAddress instanceof Inet4Address) {
                                return inetAddress.getHostAddress();
                            }
                        }
                    }

                } catch (SocketException e) {
                    e.printStackTrace();
                    return "";
                }
            }
        }
        return "";
    }

    /**
     * 获取安卓客户端手机上网方式
     *
     * @param context 上下文
     * @return 手机上网返回类型。其值可为"wifi"、"mobile"、"ethernet"
     *
     * ps：mobile情况下还可细分2g,3g,4g，5g等。可参考：https://blog.csdn.net/qq_16240393/article/details/72628187
     */
    public static String getNetWorkType(Context context) {
        String type = ""; // 获取不到 默认传"" 空字符串吗

        ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo info = connectivity.getActiveNetworkInfo();
            if (info != null && info.isConnected() && info.isAvailable()) {
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
                        break;
                }
            } else {
                type = "unavailable";
            }
        } // else 获取不到：type = ""

        return type;
    }


    /**
     * 获取外网Ip。（Wifi主机的ip，手机数据基站的ip）目前客户端无法直接获得，可以通过请求服务器接口来获取。
     *
     * 1、不建议使用，这个受服务器限制。网上免费服务器不一定稳定。这里提供一种方案。开发者可以使用收费的服务器接口。
     * 2、一般情况下服务器如果需要上传这个字段，那么服务器直接解析请求信息即可。
     *
     * @param context 上下文
     * @return 因特网上的真正的ip。外网ip。获取不到时返回 ""
     */
    @Deprecated
    public static String getRealIp(Context context) {

        BufferedReader buff;
        HttpURLConnection urlConnection;
        try {
            URL url = new URL("http://pv.sohu.com/cityjson?ie=utf-8");
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setReadTimeout(5000);//读取超时
            urlConnection.setConnectTimeout(5000);//连接超时
            urlConnection.setDoInput(true);
            urlConnection.setUseCaches(false);

            int responseCode = urlConnection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                InputStream is = urlConnection.getInputStream();
                buff = new BufferedReader(new InputStreamReader(is, "UTF-8"));//注意编码，会出现乱码
                StringBuilder builder = new StringBuilder();
                String line;
                while ((line = buff.readLine()) != null) {
                    builder.append(line);
                }
                buff.close();
                urlConnection.disconnect();
                Log.i(TAG, "getRealIp: json:" + builder);
                int startIndex = builder.indexOf("{");
                int endIndex = builder.indexOf("}");
                String json = builder.substring(startIndex, endIndex + 1);//[startIndex,endIndex)
                JSONObject jo = new JSONObject(json);
                String ip = jo.getString("cip");
                return "".equals(ip) ? "" : ip;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
        return "";
    }


}
