package com.ikbalghozali.githubuserapi.ui


import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ikbalghozali.githubuserapi.databinding.ActivitySplashScreenBinding
import com.ikbalghozali.githubuserapi.ui.main.MainActivity

@SuppressLint("CustomSplashScreen")
class SplashScreenActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashScreenBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate((layoutInflater))
        setContentView(binding.root)

        binding.splashImgLogo.alpha= 0f
        binding.splashImgLogo.animate().setDuration(2000L).alpha(1f).withEndAction {
            val i = Intent(this, MainActivity::class.java)
            startActivity(i)
            overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out)
            finish()
        }

    }




}