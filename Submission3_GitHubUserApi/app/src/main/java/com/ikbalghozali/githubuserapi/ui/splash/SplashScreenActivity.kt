package com.ikbalghozali.githubuserapi.ui.splash

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.ikbalghozali.githubuserapi.R
import com.ikbalghozali.githubuserapi.databinding.ActivitySplashScreenBinding
import com.ikbalghozali.githubuserapi.ui.main.MainActivity
import com.ikbalghozali.githubuserapi.ui.setting.SettingPreferences
import com.ikbalghozali.githubuserapi.ui.setting.SettingViewModel
import com.ikbalghozali.githubuserapi.ui.setting.ViewModelFactory


@SuppressLint("CustomSplashScreen")
class SplashScreenActivity : AppCompatActivity() {
    companion object{
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
    }
    private lateinit var binding: ActivitySplashScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate((layoutInflater))
        setContentView(binding.root)
        supportActionBar?.hide()

        binding.splashImgLogo.alpha= 0f
        binding.splashImgLogo.animate().setDuration(2000L).alpha(1f).withEndAction {
            val i = Intent(this, MainActivity::class.java)
            startActivity(i)
            overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out)
            finish()
        }

        val pref = SettingPreferences.getInstance(dataStore)
        val mainViewModel = ViewModelProvider(this, ViewModelFactory(pref))[SettingViewModel::class.java]

        mainViewModel.getThemeSettings().observe(
            this
        ) { isDarkModeActive: Boolean ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                binding.splashImgLogo.setImageResource(R.drawable.logo_dark)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                binding.splashImgLogo.setImageResource(R.drawable.logo_light)
            }
        }
    }
}