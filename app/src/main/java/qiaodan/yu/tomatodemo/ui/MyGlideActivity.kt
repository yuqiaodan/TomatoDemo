package qiaodan.yu.tomatodemo.ui

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.text.style.RelativeSizeSpan
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_my_glide.*
import qiaodan.yu.tomatodemo.R


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
        val content =
            "Wifi名称: testbird123\n" +
                    "Wifi密码: 88888888\n" +
                    "加密方式: WPA2\n"

        val sp1 = formatString("Wifi名称: testbird123\n")
        val sp2 = formatString("Wifi密码: 88888888\n")
        val sp3 = formatString("加密方式: WPA2\n")


        val sp4 = SpannableStringBuilder(sp1).append(sp2).append(sp3)

        my_spannable_text.text = formatString("Wifi名称:  testbird123")
    }

    private fun formatString(string: String): SpannableStringBuilder {
        val stringBuilder = SpannableStringBuilder()
        for (str in string.split("\n")) {
            val spannableString = SpannableString(str+"\n")
            spannableString.setSpan(
                RelativeSizeSpan(0.9f),
                0,
                spannableString.indexOf(":") + 1,
                Spanned.SPAN_INCLUSIVE_INCLUSIVE
            )
            spannableString.setSpan(
                ForegroundColorSpan(this.resources.getColor(R.color.result_color_write)),
                0,
                spannableString.indexOf(":") + 1,
                Spanned.SPAN_INCLUSIVE_INCLUSIVE
            )
            stringBuilder.append(spannableString)
        }
        return stringBuilder
    }


    companion object {
        fun actionStart(context: Context) {
            val intent = Intent(context, MyGlideActivity::class.java)
            context.startActivity(intent)
        }
    }
}