package com.example.shappinglistcleanarch1.presentation

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.shappinglistcleanarch1.databinding.ActivityShopItemBinding
import com.example.shappinglistcleanarch1.domain.ShopItem

class ShopItemActivity : AppCompatActivity() {
    private lateinit var binding: ActivityShopItemBinding
    private lateinit var screenMode: String
    private var shopItemId: Int = ShopItem.UNDEFINED_ID

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShopItemBinding.inflate(layoutInflater)
        setContentView(binding.root)


    }

    companion object{

        fun newIntent(context: Context): Intent {
            return Intent(context, ShopItemActivity::class.java)
        }
    }
}