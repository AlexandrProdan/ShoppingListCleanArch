package com.example.shappinglistcleanarch1.domain

class AddShoppingItemUseCase(private val shopListRepository: ShopListRepository) {
    fun addShoppingItem(shopItem: ShopItem){
        shopListRepository.addShoppingItem(shopItem)
    }
}