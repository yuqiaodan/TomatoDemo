package qiaodan.yu.tomatodemo.ui

import android.Manifest
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.wifi.WifiManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_wifi_config.*
import qiaodan.yu.tomatodemo.R
import qiaodan.yu.tomatodemo.app.MyApplication
import qiaodan.yu.tomatodemo.app.WifiTools
import qiaodan.yu.tomatodemo.app.WifiUtil
import qiaodan.yu.tomatodemo.app.WifiUtils

class WifiConfigActivity : AppCompatActivity() {
    val ssid = "ufoprod-1_5G"//网络名称
    val security = "WPA"//加密类型
    val password = "testbird123"//密码

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wifi_config)
        connect_wifi_btn.setOnClickListener {
            //位置权限！
            WifiTools.instance.connectWifi("ufoprod-2_5G","testbird123")

        }
        checkAndRequestPermission()
    }
    fun checkAndRequestPermission() {
        //请求权限
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.WRITE_SETTINGS
            ) == PackageManager.PERMISSION_GRANTED
        ) {

        } else { //否则去请求相机权限
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.WRITE_SETTINGS),
                100
            )
        }
    }


    companion object {
        fun actionStart(context: Context) {
            val intent: Intent = Intent(context, WifiConfigActivity::class.java)
            context.startActivity(intent)
        }
    }

}