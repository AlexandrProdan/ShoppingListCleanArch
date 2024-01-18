package com.example.shappinglistcleanarch1.domain

import androidx.lifecycle.LiveData

class GetShopItemsListUseCase(private val shopListRepository: ShopListRepository) {
    fun getShoppingItemList(): LiveData<List<ShopItem>>{
       return shopListRepository.getShoppingItemList()
    }
}