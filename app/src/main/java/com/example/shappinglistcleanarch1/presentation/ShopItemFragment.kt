package com.example.shappinglistcleanarch1.presentation

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.shappinglistcleanarch1.R
import com.example.shappinglistcleanarch1.databinding.ShopItemFragmentLayoutBinding
import com.example.shappinglistcleanarch1.domain.ShopItem

class ShopItemFragment() : Fragment() {

    private lateinit var binding: ShopItemFragmentLayoutBinding
    private lateinit var viewModel: ShopItemViewModel
    private lateinit var screenMode: String
    private lateinit var onEditingFinishedListener: OnEditingFinishedListener
    private var shopItemId: Int = ShopItem.UNDEFINED_ID


    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnEditingFinishedListener){
            onEditingFinishedListener = (context as OnEditingFinishedListener)
        }else{
            throw RuntimeException("Activity must implement OnEditingFinishedListener")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ShopItemViewModel()
        setArguments()
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
        if (savedInstanceState == null){
            setCorrectScreenMode()
        }
        observeViewModel()
        addInputChangeListeners()
    }

    private fun setArguments() {
        val args = requireArguments()
        shopItemId = args.getInt(SHOP_ITEM_ID)
        screenMode = args.getString(SCREEN_MODE).toString()
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
            val newName = binding.editTextName.text.toString()
            val newCount = binding.editTextCount.text.toString()
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

    private fun observeViewModel(){
        viewModel.errorInputCount.observe(viewLifecycleOwner){
            val errorMessage = if (it){
                getString(R.string.error_input_count)
            }else{
                null
            }
            binding.tilCount.error = errorMessage
        }

        viewModel.inputNameError.observe(viewLifecycleOwner){
            val errorMessage = if (it){
                getString(R.string.error_input_name)
            }else{
                null
            }
            binding.tilName.error = errorMessage
        }

        viewModel.shouldCloseScreen.observe(viewLifecycleOwner){
            onEditingFinishedListener.onEditingFinished()
        }
    }

    private fun addInputChangeListeners() {
        binding.editTextName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                viewModel.resetErrorInputName()
            }

            override fun afterTextChanged(p0: Editable?) {}

        })

        binding.editTextCount.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                viewModel.resetErrorInputCount()
            }

            override fun afterTextChanged(p0: Editable?) {}

        })
    }

    interface OnEditingFinishedListener{
        fun onEditingFinished()
    }

    companion object{
        const val SCREEN_MODE = "SCREEN_MODE"
        const val MODE_EDIT = "MODE_EDIT"
        const val MODE_ADD = "MODE_ADD"
        const val SHOP_ITEM_ID = "SHOP_ITEM_ID"

        fun newInstanceAdd(): ShopItemFragment {
            return ShopItemFragment().apply {
                arguments = Bundle().apply {
                    putString(SCREEN_MODE, MODE_ADD)
                }
            }
        }

        fun newInstanceEdit(shopItemId: Int): ShopItemFragment {
            return ShopItemFragment().apply {
                arguments = Bundle().apply {
                    putString(SCREEN_MODE, MODE_EDIT)
                    putInt(SHOP_ITEM_ID, shopItemId)
                }
            }
        }

    }
}