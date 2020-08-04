package qiaodan.yu.tomatodemo.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import qiaodan.yu.tomatodemo.R


class QRCodeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_q_r_code)

         //IntentIntegrator(this).initiateScan(); // `this` is the current Activity


    }


    companion object{
        fun actionStart(context: Context) {
            val intent = Intent(context, QRCodeActivity::class.java)
            context.startActivity(intent)
        }


    }
}


