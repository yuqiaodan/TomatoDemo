package qiaodan.yu.tomatodemo.ui.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import qiaodan.yu.tomatodemo.ui.AdDemoActivity
import qiaodan.yu.tomatodemo.R
import qiaodan.yu.tomatodemo.ui.DrawableStudyActivity
import qiaodan.yu.tomatodemo.ui.MyWebActivity
import qiaodan.yu.tomatodemo.ui.TTAdDemoActivity

/**
 * kotlin中最标准的adapter写法 《第一行代码》P184
 * */
class HomeListAdapter(private val items: List<String>) :
    RecyclerView.Adapter<HomeListAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_home_list, parent, false)
        val holder = ViewHolder(itemView)
        holder.itemView.setOnClickListener {
            /**想要在这里根据位置跳转到指定界面就必须为每个位置指定一个activity
             * 一般recyclerview用于商品列表跳转到统一的商品详情，只是传递数据不同，但界面相同
             * 但是当我想跳转到不同界面时 就需要为每个位置作判断
             * */
            Log.d("点击监听","点击位置：${holder.adapterPosition} 位置内容 ${items[holder.adapterPosition]}")

            when(holder.adapterPosition){
                0->{
                    AdDemoActivity.actionStart(parent.context)
                }
                1->{
                    TTAdDemoActivity.actionStart(parent.context)
                }
                2->{
                    DrawableStudyActivity.actionStart(parent.context)
                }
                3->{
                    MyWebActivity.actionStart(parent.context,"https://www.jianshu.com/u/cbe941dea753")
                }
            }

        }
        return holder
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemName.text = items[position]
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var itemName: TextView = itemView.findViewById(R.id.home_item_name)
    }
}