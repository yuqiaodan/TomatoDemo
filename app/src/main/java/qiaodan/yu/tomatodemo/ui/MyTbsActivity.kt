package qiaodan.yu.tomatodemo.ui

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import com.tencent.smtt.sdk.WebChromeClient
import com.tencent.smtt.sdk.WebView
import com.tencent.smtt.sdk.WebViewClient

import androidx.appcompat.app.AppCompatActivity

import kotlinx.android.synthetic.main.activity_my_tbs.*

import qiaodan.yu.tomatodemo.R

/**
 *
 * TBS用法和原本的WebView几乎一致
 * 效果目前来看还没有区别
 * 后续再考虑是否将项目中的WebView替换为TBS
 * **/
class MyTbsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_tbs)
        initTBS()
    }

    private fun initTBS() {
        myWebView.settings.javaScriptEnabled=true
        myWebView.webChromeClient=getWebChromeClient()
        myWebView.webViewClient=getWebViewClient()
        myWebView.loadUrl("http://weixin.qq.com/r/vz9tdcjEOZVGrYwW92qt")
    }
    private fun getWebChromeClient(): WebChromeClient {
        return object : WebChromeClient(){
            override fun onProgressChanged(view: WebView?, newProgress: Int) {
                super.onProgressChanged(view, newProgress)
 /*               if(newProgress!=100){
                    web_load_progress.visibility= View.VISIBLE
                    web_load_progress.progress=newProgress
                }else{
                    web_load_progress.visibility= View.GONE
                }*/
            }
            override fun onReceivedTitle(view: WebView?, title: String?) {
                super.onReceivedTitle(view, title)
                //web_title.text=title
            }
        }
    }




    private fun getWebViewClient(): WebViewClient {
        return object : WebViewClient() {
            override fun shouldOverrideUrlLoading(wv: WebView, url: String): Boolean {
                try {
                    if (!url.startsWith("http")
                    ) {
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                        startActivity(intent)
                        return true
                    }
                } catch (e: Exception) { //防止crash (如果手机上没有安装处理某个scheme开头的url的APP, 会导致crash)
                    return true //没有安装该app时，返回true，表示拦截自定义链接，但不跳转，避免弹出上面的错误页面
                }
                //处理http和https开头的url
                wv.loadUrl(url)
                return true
            }
        }
    }

    companion object {
        fun actionStart(context: Context, url: String = "") {
            val intent: Intent = Intent(context, MyTbsActivity::class.java)
            intent.putExtra("url", url)
            context.startActivity(intent)
        }
    }
}