package com.example.shappinglistcleanarch1.domain

class GetShopItemByIdUseCase(private val shopListRepository: ShopListRepository) {
    fun getShopItemById(shopItemId: Int): ShopItem{
        return shopListRepository.getShopItemById(shopItemId)
    }
}