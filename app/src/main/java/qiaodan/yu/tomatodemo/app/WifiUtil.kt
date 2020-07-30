package qiaodan.yu.tomatodemo.app

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.*
import android.net.ConnectivityManager.NetworkCallback
import android.net.wifi.WifiManager
import android.net.wifi.WifiNetworkSpecifier
import android.net.wifi.WifiNetworkSuggestion
import android.os.Build
import android.os.PatternMatcher
import android.util.Log


class WifiUtil {
    companion object {
        val instance: WifiUtil by lazy(LazyThreadSafetyMode.SYNCHRONIZED) { WifiUtil() }
    }

    private val ssid = "ufoprod-1_5G"//网络名称
    private val security = "WPA"//加密类型
    private val password = "testbird123"//密码
    private val TAG = "wifi操作"//网络名称
    private val context = MyApplication.getContext()
    private val wifiManager = context.applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager

    fun connectWifi(){}


    //通过建议来
    fun connectBySug() {
        val suggestion = WifiNetworkSuggestion.Builder()
            .setSsid(ssid)
            .setWpa2Passphrase(password)
            .setIsAppInteractionRequired(true) // Optional (Needs location permission)
            .build()
        val suggestionsList = listOf(suggestion)

        //wifiManager.removeNetworkSuggestions(suggestionsList)
        val status = wifiManager.addNetworkSuggestions(suggestionsList)

        Log.d(TAG, status.toString())
        if (status != WifiManager.STATUS_NETWORK_SUGGESTIONS_SUCCESS) {

        }

// Optional (Wait for post connection broadcast to one of your suggestions)
        val intentFilter = IntentFilter(WifiManager.ACTION_WIFI_NETWORK_SUGGESTION_POST_CONNECTION);

        val broadcastReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context, intent: Intent) {
                if (!intent.action.equals(WifiManager.ACTION_WIFI_NETWORK_SUGGESTION_POST_CONNECTION)) {
                    return;
                }
                // do post connect processing here
            }
        };
        context.registerReceiver(broadcastReceiver, intentFilter);

    }

    //这只是针对设备对设备进行连接 通过这个办法连接wifi会显得有点奇怪
    fun connectByP2P() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val specifier = WifiNetworkSpecifier.Builder()
                .setSsid(ssid)
                .setWpa2Passphrase(password)
                .build()

            val request =
                NetworkRequest.Builder()
                    .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
                    .removeCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
                    .setNetworkSpecifier(specifier)
                    .build()

            val connectivityManager =
                context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val networkCallback = object : ConnectivityManager.NetworkCallback() {
                override fun onAvailable(network: Network?) {
                    // do success processing here..
                    Log.d(TAG, "连接成功")
                    //connectivityManager.bindProcessToNetwork(network)

                }

                override fun onUnavailable() {
                    // do failure processing here..
                    Log.d(TAG, "连接失败")
                }
            }
            connectivityManager.requestNetwork(request, networkCallback)
            //connectivityManager.unregisterNetworkCallback(networkCallback)
        }
    }

    fun test() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val specifier: NetworkSpecifier = WifiNetworkSpecifier.Builder()
                .setSsidPattern(PatternMatcher(ssid, PatternMatcher.PATTERN_PREFIX))
                .setWpa2Passphrase(password)
                .build()
            val request = NetworkRequest.Builder()
                .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
                .removeCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
                .setNetworkSpecifier(specifier)
                .build()
            val connectivityManager =
                context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val networkCallback: NetworkCallback = object : NetworkCallback() {
                override fun onAvailable(network: Network) {
                    Log.d(TAG, "连接成功")

                }

                override fun onUnavailable() {
                    Log.d(TAG, "连接失败")
                    // do failure processing here..
                }
            }
            connectivityManager.requestNetwork(request, networkCallback)
            // Release the request when done.
            connectivityManager.unregisterNetworkCallback(networkCallback);
        }
    }

    fun startScantWifi() {
        val context = MyApplication.getContext().applicationContext
        val wifiManager = context.getSystemService(Context.WIFI_SERVICE) as WifiManager
        val wifiScanReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context, intent: Intent) {
                val success = intent.getBooleanExtra(WifiManager.EXTRA_RESULTS_UPDATED, false)
                if (success) {

                    scanSuccess()
                } else {

                    scanFailure()
                }
            }
        }

        val intentFilter = IntentFilter()
        intentFilter.addAction(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION)
        context.registerReceiver(wifiScanReceiver, intentFilter)

        val success = wifiManager.startScan()
        if (!success) {
            // scan failure handling
            scanFailure()
        }
    }

    private fun scanSuccess() {
        val results = wifiManager.scanResults
        Log.d(TAG, "扫描成功 获取到wifi扫描结果")
    }

    private fun scanFailure() {
        // handle failure: new scan did NOT succeed
        // consider using old scan results: these are the OLD results!
        val results = wifiManager.scanResults
        Log.d(TAG, "扫描失败 获取到比较旧的扫描结果")
    }

}