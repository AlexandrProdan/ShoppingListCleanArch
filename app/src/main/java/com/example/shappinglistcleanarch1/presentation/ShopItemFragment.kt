package com.example.shappinglistcleanarch1.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.shappinglistcleanarch1.R
import com.example.shappinglistcleanarch1.databinding.ActivityShopItemBinding
import com.example.shappinglistcleanarch1.databinding.FragmentShopItemBinding
import com.example.shappinglistcleanarch1.domain.ShopItem

class ShopItemFragment(
    private val screenMode: String = MODE_UNKNOWN,
    private val shopItemId: Int = ShopItem.UNDEFINED_ID
) : Fragment() {

    private lateinit var viewModel: ShopItemViewModel
    private lateinit var binding: FragmentShopItemBinding


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentShopItemBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel = ViewModelProvider(this)[ShopItemViewModel::class.java]
        parseParams()
        setCorrectScreenMode()
        observeViewModel()
        addInputChangeListeners()
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

    private fun observeViewModel() {
        viewModel.errorInputCount.observe(viewLifecycleOwner) {
            val errorMessage = if (it) {
                getString(R.string.error_input_count)
            } else {
                null
            }
            binding.tilCount.error = errorMessage
        }

        viewModel.errorInputName.observe(viewLifecycleOwner) {
            val errorMessage = if (it) {
                getString(R.string.error_input_name)
            } else {
                null
            }
            binding.tilName.error = errorMessage
        }

        viewModel.shouldCloseScreen.observe(viewLifecycleOwner) {
            activity?.onBackPressedDispatcher?.onBackPressed()
        }
    }

    private fun setCorrectScreenMode() {
        when (screenMode) {
            MODE_EDIT -> launchEditMode()
            MODE_ADD -> launchAddMode()
        }
    }

    private fun launchEditMode() {

        setTextFromMainActivity()
        binding.btnSave.setOnClickListener {
            val inputName = binding.editTextName.text?.toString()
            val inputCount = binding.editTextCount.text?.toString()
            viewModel.editShopItem(inputName, inputCount)
        }
    }

    private fun launchAddMode() {
        binding.btnSave.setOnClickListener {
            val inputName = binding.editTextName.text?.toString()
            val inputCount = binding.editTextCount.text?.toString()
            viewModel.addShopItem(inputName, inputCount)
        }
    }

    private fun setTextFromMainActivity() {
        viewModel.getShopItemById(shopItemId)
        viewModel.shopItemLD.observe(viewLifecycleOwner) {
            binding.editTextName.setText(it.name)
            binding.editTextCount.setText(it.count.toString())
        }
    }

    private fun parseParams() {

        if (screenMode != MODE_ADD && screenMode != MODE_EDIT) {
            throw RuntimeException("Param screen mode missing")
        }

        if (screenMode == MODE_EDIT && shopItemId == ShopItem.UNDEFINED_ID) {
            throw RuntimeException("Param shop item id missing")
        }
    }


    companion object {
        private const val EXTRA_SCREEN_MODE = "extra_mode"
        private const val EXTRA_SHOP_ITEM_ID = "extra_id"
        private const val MODE_EDIT = "mode_edit"
        private const val MODE_ADD = "mode_add"
        private const val MODE_UNKNOWN = ""

        fun newInstanceAddItemFragment(): ShopItemFragment{
            return ShopItemFragment(MODE_ADD)
        }

        fun newInstanceEditItemFragment(shopItemId: Int): ShopItemFragment{
            return ShopItemFragment(MODE_EDIT, shopItemId)
        }

        fun newIntentAddMode(context: Context): Intent {
            val intent = Intent(context, ShopItemActivity::class.java)
            intent.putExtra(EXTRA_SCREEN_MODE, MODE_ADD)
            return intent
        }

        fun newIntentEditMode(context: Context, id: Int): Intent {
            val intent = Intent(context, ShopItemActivity::class.java)
            intent.putExtra(EXTRA_SCREEN_MODE, MODE_EDIT)
            intent.putExtra(EXTRA_SHOP_ITEM_ID, id)
            return intent
        }
    }
}
