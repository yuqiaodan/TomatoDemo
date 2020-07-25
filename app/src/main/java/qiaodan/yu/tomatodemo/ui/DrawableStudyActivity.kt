package qiaodan.yu.tomatodemo.ui

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.animation.ValueAnimator.AnimatorUpdateListener
import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import android.graphics.drawable.LevelListDrawable
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_drawable_study.*
import qiaodan.yu.tomatodemo.R

@RequiresApi(Build.VERSION_CODES.LOLLIPOP)
class DrawableStudyActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_drawable_study)
        initView()
    }


    var drawableB:Drawable?=null
    private fun initView() {
        //获取到 button的绘制对象drawableB
        drawableB=draw_Btn.background
        draw_Btn.setOnClickListener {
            shoWLoading(5*1000)
        }
    }
    private fun shoWLoading(duration: Long) {
       val progressAnim= ObjectAnimator.ofInt(draw_progress,"progress",0,1000)
        progressAnim.duration = duration
        //动画监听
        progressAnim.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator?) {
                //动画结束
                super.onAnimationEnd(animation)
                Log.d("动画值", "动画结束")
                if(drawableB?.level==1){
                    drawableB?.level=2
                }else{drawableB?.level=1}
            }
        })
        //动画值变化监听
        progressAnim.addUpdateListener(AnimatorUpdateListener { animation ->
            val value=animation.animatedValue as Int
            anim_value.text= "${value/10.0}%"//实际值按百分比显示：(value/1000)*100 %
            Log.d("动画值", value.toString())
            if(value==250||value==500||value==750){
                if(drawableB?.level==1){
                    drawableB?.level=2
                }else{drawableB?.level=1}
            }

        })
        progressAnim.start()
    }



    companion object {
        fun actionStart(context: Context) {
            val intent: Intent = Intent(context, DrawableStudyActivity::class.java)
            context.startActivity(intent)
        }
    }
}