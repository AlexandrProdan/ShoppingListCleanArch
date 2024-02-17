package com.example.shappinglistcleanarch1.presentation

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.shappinglistcleanarch1.R
import com.example.shappinglistcleanarch1.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), ShopItemFragment.OnEditingFinishedListener {
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
        setUpOnFABClickListener()
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
            if (isOnePaneMode()){
                intent = ShopItemActivity.newIntentEdit(this@MainActivity, it.id)
                startActivity(intent)
            }else{
                val fragment = ShopItemFragment.newInstanceEdit(it.id)

                launchFragment(fragment)
            }

        }
        setupSwipeListener(rvShopList)
    }

    private fun setUpOnFABClickListener(){
        binding.fab.setOnClickListener(){
            if (isOnePaneMode()){
                intent = ShopItemActivity.newIntentAdd(this)
                startActivity(intent)
            }else{
                val fragment = ShopItemFragment.newInstanceAdd()
                launchFragment(fragment)
            }
        }
    }

    private fun launchFragment(fragment: Fragment) {
        supportFragmentManager.popBackStack()
        supportFragmentManager.beginTransaction()
            .replace(
                R.id.fragmentContainerInMainActivity,
                fragment
            )//will cause issues with navigation
            .addToBackStack(null)
            .commit()
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

    private fun isOnePaneMode():Boolean{
        return binding.fragmentContainerInMainActivity == null
    }

    companion object {
        private const val TAG = "Main Activity"
    }

    override fun onEditingFinished() {
        Toast.makeText( this@MainActivity,"Success Success in MainActivity", Toast.LENGTH_SHORT).show()
    }
}