package com.babel.marvel.features.splash

import android.content.Intent
import android.os.Bundle
import android.os.Looper
import com.babel.marvel.base.BaseActivity
import com.babel.marvel.databinding.ActivitySplashBinding
import com.babel.marvel.features.main.MainActivity
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview

@FlowPreview
@ExperimentalCoroutinesApi
class SplashActivity : BaseActivity() {

    lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        navMainActivity()
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    private fun navMainActivity() {
        android.os.Handler(Looper.getMainLooper()).postDelayed(
            {
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            },
            TIME_OUT
        )
    }

    override fun displayLoading(isLoading: Boolean) {
        // Not necessary
    }

    companion object {
        const val TIME_OUT = 3000L
    }
}
