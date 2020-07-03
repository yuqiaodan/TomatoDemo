package qiaodan.yu.tomatodemo.app

import android.app.Application
import androidx.multidex.MultiDex
import com.bytedance.sdk.openadsdk.TTAdConfig
import com.bytedance.sdk.openadsdk.TTAdConstant
import com.bytedance.sdk.openadsdk.TTAdSdk
class MyApplication :Application(){
    override fun onCreate() {
        super.onCreate()
        MultiDex.install(this)

        //强烈建议在应用对应的Application#onCreate()方法中调用，避免出现content为null的异常
        TTAdSdk.init(
            this,
            TTAdConfig.Builder()
                .appId("5001121")
                .useTextureView(false) //使用TextureView控件播放视频,默认为SurfaceView,当有SurfaceView冲突的场景，可以使用TextureView
                .appName("APP测试媒体")
                .titleBarTheme(TTAdConstant.TITLE_BAR_THEME_DARK)
                .allowShowNotify(true) //是否允许sdk展示通知栏提示
                .allowShowPageWhenScreenLock(true) //是否在锁屏场景支持展示广告落地页
                .debug(true) //测试阶段打开，可以通过日志排查问题，上线时去除该调用
                .directDownloadNetworkType(
                    TTAdConstant.NETWORK_STATE_WIFI,
                    TTAdConstant.NETWORK_STATE_3G
                ) //允许直接下载的网络状态集合
                .supportMultiProcess(false) //是否支持多进程，true支持
                //.httpStack(new MyOkStack3())//自定义网络库，demo中给出了okhttp3版本的样例，其余请自行开发或者咨询工作人员。
                .build()
        )

    }

}