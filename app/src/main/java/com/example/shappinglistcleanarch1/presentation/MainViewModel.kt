package com.example.shappinglistcleanarch1.presentation

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

    val shopListLD = getShopListUseCase.getShoppingItemList()



    fun deleteShopItem(shopItem: ShopItem) {
        deleteShopItemUseCase.deleteShopItem(shopItem)
    }

    fun changeEnableState(shopItem: ShopItem){
        val newItem = shopItem.copy(enabled = !shopItem.enabled)
        editShopItemUseCase.editShopItem(newItem)
    }
}