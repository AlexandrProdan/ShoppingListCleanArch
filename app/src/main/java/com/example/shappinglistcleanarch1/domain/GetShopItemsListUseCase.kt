package com.example.shappinglistcleanarch1.domain

class GetShopItemsListUseCase(private val shopListRepository: ShopListRepository) {
    fun getShoppingItemList(): List<ShopItem>{
       return shopListRepository.getShoppingItemList()
    }
}