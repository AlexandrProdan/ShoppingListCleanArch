package com.example.shappinglistcleanarch1.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.shappinglistcleanarch1.data.ShopListRepoImp
import com.example.shappinglistcleanarch1.domain.AddShoppingItemUseCase
import com.example.shappinglistcleanarch1.domain.EditShopItemUseCase
import com.example.shappinglistcleanarch1.domain.GetShopItemByIdUseCase
import com.example.shappinglistcleanarch1.domain.ShopItem
import java.lang.Exception

class ShopItemViewModel: ViewModel() {
    private val repo = ShopListRepoImp


    private val getShopItemByIdUseCase = GetShopItemByIdUseCase(repo)
    private val addShopListUseCase = AddShoppingItemUseCase(repo)
    private val editShopItemUseCase = EditShopItemUseCase(repo)

    private val _errorInputName = MutableLiveData<Boolean>()
    val inputNameError : LiveData<Boolean>
        get() = _errorInputName

    private val _errorInputCount = MutableLiveData<Boolean>()
    val errorInputCount : LiveData<Boolean>
        get() = _errorInputCount

    private val _shopItemMLD = MutableLiveData<ShopItem>()
    val shopItemLD: LiveData<ShopItem>
        get() = _shopItemMLD

    private val _shouldCloseScreen = MutableLiveData<Unit>()
    val shouldCloseScreen: LiveData<Unit>
        get() = _shouldCloseScreen




    fun getShopItemById(shopItemId: Int){
        val item = getShopItemByIdUseCase.getShopItemById(shopItemId)
        _shopItemMLD.value = item
    }

    fun addShopItem(inputName:String?, inputCount:String?){
        val name = parseName(inputName)
        val count = parseCount(inputCount)
        val fieldsValid = validateInput(name, count)

        if (fieldsValid) {
            val shopItem = ShopItem(name, count, true)
            addShopListUseCase.addShoppingItem(shopItem)
            finishWork()
        }
    }

    fun editShopItem(inputName:String?, inputCount:String?){
        val name = parseName(inputName)
        val count = parseCount(inputCount)
        val fieldsValid = validateInput(name, count)

        if (fieldsValid) {
            _shopItemMLD.value?.let {

                val editedShopItem = it.copy(name = name, count = count)
                editShopItemUseCase.editShopItem(editedShopItem)
                finishWork()
            }

            finishWork()
        }
    }

    private fun parseName(inputName:String?): String{
        return inputName?.trim() ?:""
    }

    private fun parseCount(inputCount: String?): Int{
        return  try {
            inputCount?.toString()?.trim()?.toInt() ?: 0
        }catch (e: Exception){
            0
        }
    }

    private fun validateInput(name: String, count: Int): Boolean{
        var result = true

        if (name.isBlank()){
            _errorInputName.value = true
            result =  false
        }

        if (count <= 0){
            _errorInputCount.value = true
            result = false
        }

        return result
    }

    fun resetErrorInputName(){
        _errorInputName.value = false
    }

    fun resetErrorInputCount(){
        _errorInputCount.value = false
    }

    private fun finishWork(){
        _shouldCloseScreen.value = Unit
    }
}