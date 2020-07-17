package qiaodan.yu.tomatodemo.ui
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.gms.ads.MobileAds
import kotlinx.android.synthetic.main.activity_main.*
import qiaodan.yu.tomatodemo.R
import qiaodan.yu.tomatodemo.ui.adapter.HomeListAdapter

/**
 * 本人的项目学习 包括一些比较大型的sdk接入方式总结
 * 保证demo简洁 主界面只有一个网格recyclerview
 * 本项目采用尽量采用最原生的android开发
 */
class MainActivity : AppCompatActivity() {
    //主界面列表数据 只有一个String
     lateinit var mData: ArrayList<String>
     lateinit var mAdapter: HomeListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()
        initData()
    }

    private fun initView() {
        mData= ArrayList()
        mAdapter=HomeListAdapter(mData)
        val layoutManager = GridLayoutManager(this, 3)
        home_list.layoutManager = layoutManager
        home_list.adapter=mAdapter
    }

    private fun initData(){
        mData.add("Firebase接入\n&\n广告")
        mData.add("穿山甲广告接入")
        mData.add("Drawable\n学习")
        mData.add("WebView\nDemo")
        mData.add("腾讯TBS接入")
        mData.add("Glide用法")
        mAdapter.notifyDataSetChanged()
    }

}
