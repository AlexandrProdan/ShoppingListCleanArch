package com.example.shappinglistcleanarch1

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.shappinglistcleanarch1.presentation.MainViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: MainViewModel
    private var count = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        viewModel.shopListLD.observe(this) {
            Log.d(TAG, it.toString())
            if (count == 0) {
                val item = it[0]
                viewModel.changeEnableState(item)
                count++
            }
        }

    }

    companion object {
        private const val TAG = "TEST"

    }
}