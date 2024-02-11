package com.example.shappinglistcleanarch1.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.shappinglistcleanarch1.databinding.ShopItemFragmentLayoutBinding

class ShopItemFragment() : Fragment() {

    private lateinit var binding: ShopItemFragmentLayoutBinding
    private lateinit var viewModel: ShopItemViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ShopItemViewModel()
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ShopItemFragmentLayoutBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setCorrectScreenMode()
    }

    private fun setCorrectScreenMode() {
        TODO()
    }


    companion object{
        private const val SCREEN_MODE = "SCREEN_MODE"
        private const val MODE_EDIT = "MODE_EDIT"
        private const val MODE_ADD = "MODE_ADD"
        private const val SHOP_ITEM_ID = "SHOP_ITEM_ID"

        fun newInstanceAdd(): Fragment {
            return ShopItemFragment().apply {
                arguments = Bundle().apply {
                    putString(SCREEN_MODE, MODE_ADD)
                }
            }
        }

        fun newInstanceAdd(shopItemId: Int): Fragment {
            return ShopItemFragment().apply {
                arguments = Bundle().apply {
                    putString(SCREEN_MODE, MODE_EDIT)
                    putInt(SHOP_ITEM_ID, shopItemId)
                }
            }
        }

    }
}