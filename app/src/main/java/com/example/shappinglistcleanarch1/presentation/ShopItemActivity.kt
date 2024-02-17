package com.example.shappinglistcleanarch1.presentation

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.shappinglistcleanarch1.R
import com.example.shappinglistcleanarch1.databinding.ActivityShopItemBinding
import com.example.shappinglistcleanarch1.domain.ShopItem

class ShopItemActivity : AppCompatActivity(), ShopItemFragment.OnEditingFinishedListener {
    private lateinit var binding: ActivityShopItemBinding
    private lateinit var screenMode: String
    private var shopItemId: Int = ShopItem.UNDEFINED_ID

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShopItemBinding.inflate(layoutInflater)
        setContentView(binding.root)
        parseIntent()
        if (savedInstanceState == null) {
            setCorrectScreenMode()
        }

    }

    private fun setCorrectScreenMode() {
        val fragment = when (screenMode) {
            ShopItemFragment.MODE_EDIT -> ShopItemFragment.newInstanceEdit(shopItemId)
            ShopItemFragment.MODE_ADD -> ShopItemFragment.newInstanceAdd()
            else -> throw RuntimeException("Unknown screen mode $screenMode")
        }
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainerInShopItemActivity, fragment)
            .commit()
    }



    private fun parseIntent() {

        if (!intent.hasExtra(EXTRA_SCREEN_MODE)) {
            throw RuntimeException("Param screen mode missing")
        }

        val mode = intent.getStringExtra(EXTRA_SCREEN_MODE)

        if ((mode == MODE_ADD) || (mode == MODE_EDIT)) {
            screenMode = mode
        } else {
            throw RuntimeException("Unknown screen mode: $mode")
        }

        if (screenMode == MODE_EDIT) {
            if (!intent.hasExtra(EXTRA_SHOP_ITEM_ID)) {
                throw RuntimeException("Parameter SHOP_ITEM_ID is missing")
            }
            shopItemId = intent.getIntExtra(EXTRA_SHOP_ITEM_ID, ShopItem.UNDEFINED_ID)
        }
    }

    companion object {
        private const val EXTRA_SCREEN_MODE = "SCREEN_MODE"
        private const val MODE_EDIT = "MODE_EDIT"
        private const val MODE_ADD = "MODE_ADD"
        private const val EXTRA_SHOP_ITEM_ID = "SHOP_ITEM_ID"

        fun newIntentAdd(context: Context): Intent {
            val intent = Intent(context, ShopItemActivity::class.java)
            intent.putExtra(EXTRA_SCREEN_MODE, MODE_ADD)
            return intent
        }

        fun newIntentEdit(context: Context, id: Int): Intent {
            val intent = Intent(context, ShopItemActivity::class.java)
            intent.putExtra(EXTRA_SCREEN_MODE, MODE_EDIT)
            intent.putExtra(EXTRA_SHOP_ITEM_ID, id)
            return intent
        }
    }

    override fun onEditingFinished() {
        Toast.makeText( this@ShopItemActivity,"Success in ShopItemActivity", Toast.LENGTH_SHORT).show()
    }
}