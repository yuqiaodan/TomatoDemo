package qiaodan.yu.tomatodemo.app

import android.content.Context
import android.net.ConnectivityManager
import android.net.wifi.WifiConfiguration
import android.net.wifi.WifiManager

object WifiUtils {
    val wifiManager: WifiManager = MyApplication.getContext().applicationContext.getSystemService(
        Context.WIFI_SERVICE
    ) as WifiManager

    val connectivityManager: ConnectivityManager =
        MyApplication.getContext().applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    fun startScan() {
        if (!wifiManager.isWifiEnabled) wifiManager.isWifiEnabled = true
        wifiManager.startScan()
    }

    fun getScanResult() = wifiManager.scanResults

    fun connect(ssid: String, pwd: String): Boolean {
        //在扫描到的wifi中找ssid也就是wifi名相同的
        val scanResult = wifiManager.scanResults.singleOrNull { it.SSID == ssid }
        if (scanResult == null) {
            return false
        } else {
            //如果找到了wifi了，从配置表中搜索该wifi的配置config，也就是以前有没有连接过
            var success = false
            //注意configuredNetworks中的ssid，系统源码中加上了双引号，这里比对的时候要去掉
            val config =
                wifiManager.configuredNetworks.singleOrNull { it.SSID.replace("\"", "") == ssid }
            if (config != null) {
                //如果找到了，那么直接连接，不要调用wifiManager.addNetwork  这个方法会更改config的！

                success = wifiManager.enableNetwork(config.networkId, true)
            } else {
                // 没找到的话，就创建一个新的配置，然后正常的addNetWork、enableNetwork即可
                val padWifiNetwork =
                    createWifiConfig(scanResult.SSID, pwd, getCipherType(scanResult.capabilities))
                val netId = wifiManager.addNetwork(padWifiNetwork)
                success = wifiManager.enableNetwork(netId, true)
            }
            //LogUtils.d("连接${scanResult.SSID}...$success")
            return success
        }
    }


    fun isConnected(ssid: String): Boolean {
        return if (wifiManager.isWifiEnabled) {
            wifiManager.connectionInfo.ssid.replace("\"", "") == ssid
        } else {
            false
        }
    }

    private fun createWifiConfig(
        ssid: String,
        password: String,
        type: WifiCapabilityA
    ): WifiConfiguration {
        //初始化WifiConfiguration
        val config = WifiConfiguration()
        config.allowedAuthAlgorithms.clear()
        config.allowedGroupCiphers.clear()
        config.allowedKeyManagement.clear()
        config.allowedPairwiseCiphers.clear()
        config.allowedProtocols.clear()

        //指定对应的SSID
        config.SSID = "\"" + ssid + "\""

        //如果之前有类似的配置
        val tempConfig = wifiManager.configuredNetworks.singleOrNull { it.SSID == "\"$ssid\"" }
        if (tempConfig != null) {
            //则清除旧有配置  不是自己创建的network 这里其实是删不掉的
            wifiManager.removeNetwork(tempConfig.networkId)
            wifiManager.saveConfiguration()
        }

        //不需要密码的场景
        if (type == WifiCapabilityA.WIFI_CIPHER_NO_PASS) {
            config.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE)
            //以WEP加密的场景
        } else if (type == WifiCapabilityA.WIFI_CIPHER_WEP) {
            config.hiddenSSID = true
            config.wepKeys[0] = "\"" + password + "\""
            config.allowedAuthAlgorithms.set(WifiConfiguration.AuthAlgorithm.OPEN)
            config.allowedAuthAlgorithms.set(WifiConfiguration.AuthAlgorithm.SHARED)
            config.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE)
            config.wepTxKeyIndex = 0
            //以WPA加密的场景，自己测试时，发现热点以WPA2建立时，同样可以用这种配置连接
        } else if (type == WifiCapabilityA.WIFI_CIPHER_WPA) {
            config.preSharedKey = "\"" + password + "\""
            config.hiddenSSID = true
            config.allowedAuthAlgorithms.set(WifiConfiguration.AuthAlgorithm.OPEN)
            config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.TKIP)
            config.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.WPA_PSK)
            config.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.TKIP)
            config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.CCMP)
            config.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.CCMP)
            config.status = WifiConfiguration.Status.ENABLED
        }

        return config
    }

    fun getCipherType(capabilities: String): WifiCapabilityA {
        return if (capabilities.contains("WEB")) {
            WifiCapabilityA.WIFI_CIPHER_WEP
        } else if (capabilities.contains("PSK")) {
            WifiCapabilityA.WIFI_CIPHER_WPA
        } else if (capabilities.contains("WPS")) {
            WifiCapabilityA.WIFI_CIPHER_NO_PASS
        } else {
            WifiCapabilityA.WIFI_CIPHER_NO_PASS
        }
    }

    fun getDhcpInfo(): String {
        val dhcpInfo = wifiManager.dhcpInfo
        return intIP2StringIP(dhcpInfo.serverAddress)

    }

    fun intIP2StringIP(ip: Int): String {
        return (ip and 0xFF).toString() + "." +
                (ip shr 8 and 0xFF) + "." +
                (ip shr 16 and 0xFF) + "." +
                (ip shr 24 and 0xFF)
    }
}

enum class WifiCapabilityA {
    WIFI_CIPHER_WEP, WIFI_CIPHER_WPA, WIFI_CIPHER_NO_PASS
}
