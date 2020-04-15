
import android.os.Build;

/**
 * 判断当前安卓手机支持的安卓系统版本
 */
public class SupportUtils {

    /**
     * 是否支持安卓Q（安卓10.0，sdk 版本29）
     */
    public boolean isSupportQ() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q;
    }

    /**
     * 是否支持安卓P（安卓9.0，sdk版本28）
     */
    public boolean isSupportP() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.P;
    }

    /**
     * 是否支持安卓O（安卓8.0，sdk版本26）
     * ps：安卓8.1为 O_MR1。sdk版本27
     */
    public boolean isSupportO() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.O;
    }

    /**
     * 是否支持安卓N（安卓7.0，sdk版本24）
     * ps：安卓7.1为 N_MR1。sdk版本25
     */
    public boolean isSupportN() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.N;
    }

    /**
     * 是否支持安卓M（安卓6.0，sdk版本23）
     */
    public boolean isSupportM() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M;
    }

}
