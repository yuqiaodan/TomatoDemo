package qiaodan.yu.tomatodemo.ui

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import qiaodan.yu.tomatodemo.R

//
class DrawableStudy : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_drawable_study)
    }



    companion object {
        fun actionStart(context: Context) {
            val intent: Intent = Intent(context, DrawableStudy::class.java)
            context.startActivity(intent)
        }
    }
}