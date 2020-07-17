package qiaodan.yu.tomatodemo.ui

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_my_glide.*
import qiaodan.yu.tomatodemo.R
import java.time.Instant

class MyGlideActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_glide)
        initView()
    }


    private fun initView() {
        Glide.with(my_glide_img.context)
            .load("https://upload.jianshu.io/users/upload_avatars/19229798/14550654-c541-4a71-a201-c8b81fefcf4a.jpg?imageMogr2/auto-orient/strip|imageView2/1/w/240/h/240")
            .into(my_glide_img)

    }

    companion object{

        fun actionStart(context: Context){
            val intent= Intent(context,MyGlideActivity::class.java)
            context.startActivity(intent)
        }


    }
}