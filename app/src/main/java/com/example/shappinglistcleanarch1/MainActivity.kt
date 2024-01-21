package com.example.shappinglistcleanarch1

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.shappinglistcleanarch1.databinding.ActivityMainBinding
import com.example.shappinglistcleanarch1.presentation.MainViewModel
import com.example.shappinglistcleanarch1.presentation.ShopListAdapter

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel
    private lateinit var adapter: ShopListAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupRecyclerView()
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        viewModel.shopListLD.observe(this) {
            adapter.shopItemList = it
        }

    }

    companion object {
        private const val TAG = "TEST"

    }

    private fun setupRecyclerView() {
        val recycleViewShopList = binding.rvShopList
        val adapter = ShopListAdapter()
        recycleViewShopList.adapter = adapter
    }
}