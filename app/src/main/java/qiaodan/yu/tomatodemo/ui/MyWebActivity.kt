package qiaodan.yu.tomatodemo.ui

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.webkit.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_my_web.*
import qiaodan.yu.tomatodemo.R


class MyWebActivity : AppCompatActivity() {

    private var webUrl=""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_web)
        dealIntent()
        initView()
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun initView() {
        //设置
        webView.settings.javaScriptEnabled = true
        webView.webViewClient = getWebViewClient()
        webView.webChromeClient=getWebChromeClient()
        webView.loadUrl(webUrl)
        back_btn.setOnClickListener {
            if(webView.canGoBack()){
                webView.goBack()
            }else{
                onBackPressed()
            }
        }
    }

    private fun dealIntent(){
       val url=intent.getStringExtra("url")
        if(!TextUtils.isEmpty(url)){
            webUrl=url
        }else{
            webUrl="https://www.baidu.com/"//海外改为谷歌
        }
    }

    private fun getWebChromeClient():WebChromeClient{
        return object :WebChromeClient(){
            override fun onProgressChanged(view: WebView?, newProgress: Int) {
                super.onProgressChanged(view, newProgress)
                if(newProgress!=100){
                    web_load_progress.visibility=View.VISIBLE
                    web_load_progress.progress=newProgress
                }else{
                    web_load_progress.visibility=View.GONE
                }
            }
            override fun onReceivedTitle(view: WebView?, title: String?) {
                super.onReceivedTitle(view, title)
                web_title.text=title
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



    override fun onDestroy() {
        super.onDestroy()

        webView.destroy()
    }

    companion object {
        fun actionStart(context: Context,url:String="") {
            val intent: Intent = Intent(context, MyWebActivity::class.java)
            intent.putExtra("url",url)
            context.startActivity(intent)
        }
    }
    private var firstTime: Long = 0
    override fun onBackPressed() {
        val secondTime = System.currentTimeMillis()
        if (secondTime - firstTime > 2000) {
            Toast.makeText(this, "再次点击退出界面", Toast.LENGTH_SHORT).show()
            firstTime = secondTime
        } else {
            finish()
        }
    }

}