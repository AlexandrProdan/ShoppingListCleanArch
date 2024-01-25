package com.example.shappinglistcleanarch1.presentation

import androidx.lifecycle.ViewModel
import com.example.shappinglistcleanarch1.data.ShopListRepoImp
import com.example.shappinglistcleanarch1.domain.AddShoppingItemUseCase
import com.example.shappinglistcleanarch1.domain.DeleteShopItemUseCase
import com.example.shappinglistcleanarch1.domain.EditShopItemUseCase
import com.example.shappinglistcleanarch1.domain.GetShopItemByIdUseCase
import com.example.shappinglistcleanarch1.domain.GetShopItemsListUseCase
import com.example.shappinglistcleanarch1.domain.ShopItem
import java.lang.Exception

class ShopItemViewModel: ViewModel() {
    private val repo = ShopListRepoImp


    private val getShopItemByIdUseCase = GetShopItemByIdUseCase(repo)
    private val addShopListUseCase = AddShoppingItemUseCase(repo)
    private val editShopItemUseCase = EditShopItemUseCase(repo)

    fun getShopItemById(shopItemId: Int){
        getShopItemByIdUseCase.getShopItemById(shopItemId)
    }

    fun addShopItem(inputName:String?, inputCount:String?){
        val name = parseName(inputName)
        val count = parseCount(inputCount)
        val fieldsValid = validateInput(name, count)

        if (fieldsValid) {
            val shopItem = ShopItem(name, count, true)
            addShopListUseCase.addShoppingItem(shopItem)
        }
    }

    fun editShopItem(shopItem: ShopItem){
        editShopItemUseCase.editShopItem(shopItem)
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
            // TODO: show error input name
            result =  false
        }

        if (count <= 0){
            //TODO: show error input count
            result = false
        }

        return result
    }
}