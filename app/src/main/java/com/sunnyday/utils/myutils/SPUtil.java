
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * SharedPreferences 工具类
 */
public class SpUtils {

    private static final String FILE_NAME = "my_sp";
    @SuppressLint("StaticFieldLeak")
    private static Context mContext;
    private SharedPreferences mPreferences;
    private SharedPreferences.Editor mEditor;

    @SuppressLint("CommitPrefEdits")
    private SpUtils(String fileName) {
        if (null != mContext) {
            mPreferences = mContext.getSharedPreferences(fileName, Context.MODE_PRIVATE);
            mEditor = mPreferences.edit();
        } else {
            throw new IllegalArgumentException("context is null, you forgot init SpUtils in your Application !");
        }
    }

    private static class SingleTonHolder {
        @SuppressLint("StaticFieldLeak")
        private static SpUtils INSTANCE = new SpUtils(FILE_NAME);
    }

    /**
     * 获得SpUtils的实例对象
     */
    public static SpUtils getInstance() {
        return SingleTonHolder.INSTANCE;
    }

    /**
     * 初始化SpUtils，在application中初始化下即可。
     */
    public static void init(Context context) {
        mContext = context;
    }

    public void writeInt(String key, int value) {
        mEditor.putInt(key, value);
        mEditor.apply();
    }

    public int readInt(String key) {
        return mPreferences.getInt(key, 0);
    }

    public void writeLong(String key, Long value) {
        mEditor.putLong(key, value);
        mEditor.apply();
    }

    public long readLong(String key) {
        return mPreferences.getLong(key, 0L);
    }

    public void writeString(String key, String value) {
        mEditor.putString(key, value);
        mEditor.apply();
    }

    public String readString(String key) {
        return mPreferences.getString(key, null);
    }

    public void writeBoolean(String key, boolean value) {
        mEditor.putBoolean(key, value);
        mEditor.apply();
    }

    public boolean readBoolean(String key) {
        return mPreferences.getBoolean(key, false);
    }

    /**
     * 清空磁盘上指定的key-value
     *
     * @param key key
     */
    public boolean removeTargetKey(String key) {
        if (mPreferences.contains(key)) {
            mEditor.remove(key);
            mEditor.apply();
            return true;
        } else {
            return false;
        }
    }

    /**
     * 清空磁盘上所有的key-value
     */
    public void removeAll() {
        mEditor.clear();
        mEditor.apply();
    }

}
