package qiaodan.yu.tomatodemo.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bytedance.sdk.openadsdk.AdSlot
import com.bytedance.sdk.openadsdk.TTAdConstant
import com.bytedance.sdk.openadsdk.TTAdDislike.DislikeInteractionCallback
import com.bytedance.sdk.openadsdk.TTAdNative.NativeAdListener
import com.bytedance.sdk.openadsdk.TTAdSdk
import com.bytedance.sdk.openadsdk.TTNativeAd
import kotlinx.android.synthetic.main.activity_t_t_ad_demo.*
import kotlinx.android.synthetic.main.native_ad.*
import qiaodan.yu.tomatodemo.R
import java.util.*


class TTAdDemoActivity : AppCompatActivity(){


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_t_t_ad_demo)
        loadTTAd()
    }


    fun loadTTAd() {
        //一定要在初始化后才能调用，否则为空
        var mTTAdNative =
            TTAdSdk.getAdManager().createAdNative(baseContext) //baseContext建议为activity

        //step4:创建广告请求参数AdSlot,注意其中的setNativeAdtype方法，具体参数含义参考文档
        val adSlot = AdSlot.Builder()
            .setCodeId("901121423")
            .setSupportDeepLink(true)
            .setImageAcceptedSize(600, 257)
            .setNativeAdType(AdSlot.TYPE_BANNER) //请求原生广告时候，请务必调用该方法，设置参数为TYPE_BANNER或TYPE_INTERACTION_AD
            .setAdCount(1)
            .build()

        //step5:请求广告，对请求回调的广告作渲染处理
        mTTAdNative.loadNativeAd(adSlot, object : NativeAdListener {
            override fun onError(code: Int, message: String) {
                Toast.makeText(this@TTAdDemoActivity, "load error : $code, $message",Toast.LENGTH_SHORT).show()
            }
            override fun onNativeAdLoad(ads: List<TTNativeAd>) {
                if (ads[0] == null) {
                    return
                }
                val bannerView: View = LayoutInflater.from(this@TTAdDemoActivity)
                    .inflate(R.layout.native_ad, mBannerContainer, false)
                    ?: return
                mBannerContainer.removeAllViews()
                mBannerContainer.addView(bannerView)
            }
        })

    }



    companion object {
        fun actionStart(context: Context) {
            val intent: Intent = Intent(context, TTAdDemoActivity::class.java)
            context.startActivity(intent)
        }
    }


}