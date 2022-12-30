package com.cjmobileapps.stateflagandroid.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.cjmobileapps.stateflagandroid.R
import com.cjmobileapps.stateflagandroid.databinding.ActivityStateFlagBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StateFlagActivity : AppCompatActivity() {

    lateinit var binding: ActivityStateFlagBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_state_flag)

        binding = ActivityStateFlagBinding.inflate(layoutInflater)
    }
}
