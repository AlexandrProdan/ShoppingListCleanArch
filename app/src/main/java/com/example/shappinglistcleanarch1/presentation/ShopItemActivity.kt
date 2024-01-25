package com.example.shappinglistcleanarch1.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.shappinglistcleanarch1.R
import com.example.shappinglistcleanarch1.databinding.ActivityMainBinding
import com.example.shappinglistcleanarch1.databinding.ActivityShopItemBinding

class ShopItemActivity : AppCompatActivity() {
    private lateinit var binding: ActivityShopItemBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShopItemBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }
}