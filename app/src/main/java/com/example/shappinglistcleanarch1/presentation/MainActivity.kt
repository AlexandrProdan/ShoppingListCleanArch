package com.example.shappinglistcleanarch1.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.shappinglistcleanarch1.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel
    private lateinit var shopListAdapter: ShopListAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        viewModel.shopListLD.observe(this) {
            shopListAdapter.submitList(it)
        }
        setupRecyclerView()

        binding.fabAddItem.setOnClickListener{
            val intent = ShopItemActivity.newIntentAddMode(this)
            startActivity(intent)
        }

    }



    private fun setupRecyclerView() {
        val rvShopList = binding.rvShopList
        shopListAdapter = ShopListAdapter()

        with(rvShopList) {
            adapter = shopListAdapter
            recycledViewPool.setMaxRecycledViews(
                ShopListAdapter.VIEW_TYPE_DISABLED,
                ShopListAdapter.MAX_POOL_SIZE
            )
            recycledViewPool.setMaxRecycledViews(
                ShopListAdapter.VIEW_TYPE_ENABLED,
                ShopListAdapter.MAX_POOL_SIZE
            )
        }

        shopListAdapter.onShopItemLongClickListener = { viewModel.changeEnableState(it) }
        shopListAdapter.onShopItemClickListener = {
            val id  = it.id
            val intent = ShopItemActivity.newIntentEditMode(this, id)
            startActivity(intent)
        }
        setupSwipeListener(rvShopList)
    }

    private fun setupSwipeListener(recyclerView: RecyclerView){
        val callback = object: ItemTouchHelper.SimpleCallback(
            0,
            ItemTouchHelper.RIGHT or ItemTouchHelper.LEFT
        ){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
               val item = shopListAdapter.currentList[viewHolder.adapterPosition]
                viewModel.deleteShopItem(item)
            }
        }
        val itemTouchHelper = ItemTouchHelper(callback)
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }

}