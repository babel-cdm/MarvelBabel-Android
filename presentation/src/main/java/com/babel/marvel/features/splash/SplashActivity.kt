package com.babel.marvel.features.splash

import android.content.Intent
import android.os.Bundle
import android.os.Looper
import com.babel.marvel.R
import com.babel.marvel.base.BaseActivity
import com.babel.marvel.features.main.MainActivity
import com.babel.marvel.features.main.MainViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import org.koin.androidx.viewmodel.ext.android.viewModel

@FlowPreview
@ExperimentalCoroutinesApi
class SplashActivity : BaseActivity(R.layout.activity_splash) {

    override val viewModel: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        navMainActivity()
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
