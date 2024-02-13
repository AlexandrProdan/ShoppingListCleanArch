package com.example.shappinglistcleanarch1.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.shappinglistcleanarch1.databinding.ShopItemFragmentLayoutBinding
import com.example.shappinglistcleanarch1.domain.ShopItem

class ShopItemFragment() : Fragment() {

    private lateinit var binding: ShopItemFragmentLayoutBinding
    private lateinit var viewModel: ShopItemViewModel
    private lateinit var screenMode: String
    private var shopItemId: Int = ShopItem.UNDEFINED_ID


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ShopItemViewModel()

        val args = requireArguments()
        shopItemId = args.getInt(SHOP_ITEM_ID)
        screenMode = args.getString(SCREEN_MODE).toString()
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
        when(screenMode){
            MODE_EDIT-> launchInEditMode()
            MODE_ADD-> launchInAddMode()
        }
    }

    private fun launchInAddMode(){
        binding.btnSave.setOnClickListener(){
            val name = binding.editTextName.text.toString()
            val count = binding.editTextCount.text.toString()
            viewModel.addShopItem(name, count)
        }
    }

    private fun launchInEditMode(){
        setValuesFromMain()
        binding.btnSave.setOnClickListener(){
            val newName = binding.editTextName.toString()
            val newCount = binding.editTextCount.toString()
            viewModel.editShopItem(newName, newCount)
        }

    }

    private fun setValuesFromMain(){
        viewModel.getShopItemById(shopItemId)

        viewModel.shopItemLD.observe(viewLifecycleOwner){
            binding.editTextName.setText(it.name)
            binding.editTextCount.setText(it.count.toString())
        }
    }



    companion object{
        const val SCREEN_MODE = "SCREEN_MODE"
        const val MODE_EDIT = "MODE_EDIT"
        const val MODE_ADD = "MODE_ADD"
        const val SHOP_ITEM_ID = "SHOP_ITEM_ID"

        fun newInstanceAdd(): Fragment {
            return ShopItemFragment().apply {
                arguments = Bundle().apply {
                    putString(SCREEN_MODE, MODE_ADD)
                }
            }
        }

        fun newInstanceEdit(shopItemId: Int): Fragment {
            return ShopItemFragment().apply {
                arguments = Bundle().apply {
                    putString(SCREEN_MODE, MODE_EDIT)
                    putInt(SHOP_ITEM_ID, shopItemId)
                }
            }
        }

    }
}