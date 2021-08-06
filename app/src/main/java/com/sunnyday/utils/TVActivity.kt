
class TVActivity : AppCompatActivity() {

    companion object{
        const val tag = "TVActivity"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_t_v)
        Log.i("TVActivity", "onCreate")

        //keytool -printcert -jarfile xxx.apk
        // SHA256: 12:BF:C3:6F:85:80:E7:ED:94:BC:34:43:4D:26:B3:58:AF:7A:14:AC:17:61:E3:5C:38:D0:41:CF:99:38:95:9F
        // ps:如上可获取SHA256值，但是这个值要手动处理下：
        //(1)去除冒号
        //(2)字符串中字母都变为小写。
        Log.i("TVActivity", "checkIsTargetApp:${AppUtils.checkIsTargetApp(this,"com.example.albumdemo","")}")

    }


}