package com.example.shappinglistcleanarch1.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.shappinglistcleanarch1.data.ShopListRepoImp
import com.example.shappinglistcleanarch1.domain.DeleteShopItemUseCase
import com.example.shappinglistcleanarch1.domain.EditShopItemUseCase
import com.example.shappinglistcleanarch1.domain.GetShopItemsListUseCase
import com.example.shappinglistcleanarch1.domain.ShopItem

class MainViewModel : ViewModel() {
    private val repo = ShopListRepoImp

    private val getShopListUseCase = GetShopItemsListUseCase(repo)
    private val deleteShopItemUseCase = DeleteShopItemUseCase(repo)
    private val editShopItemUseCase = EditShopItemUseCase(repo)

    val shopListLD = MutableLiveData<List<ShopItem>>()

    fun getShopList() {
        val list = getShopListUseCase.getShoppingItemList()
        shopListLD.postValue(list)
    }

    fun deleteShopIte(shopItem: ShopItem) {
        deleteShopItemUseCase.deleteShopItem(shopItem)
        getShopList()
    }

    fun editShopItem(shopItem: ShopItem){
        val newItem = shopItem.copy(enabled = !shopItem.enabled)
        editShopItemUseCase.editShopItem(newItem)
        getShopList()
    }
}