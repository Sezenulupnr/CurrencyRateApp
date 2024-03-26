package com.example.trader_project.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.trader_project.common.viewBinding
import com.example.trader_project.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val binding by viewBinding (ActivityMainBinding::inflate)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(binding.root)
    }
}