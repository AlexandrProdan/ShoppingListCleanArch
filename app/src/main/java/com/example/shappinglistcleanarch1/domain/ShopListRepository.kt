package com.example.shappinglistcleanarch1.domain

interface ShopListRepository {

    fun addShoppingItem(shopItem: ShopItem)

    fun deleteShopItem(shopItem: ShopItem)

    fun editShopItem(shopItem: ShopItem)

    fun getShopItemById(shopItemId: Int): ShopItem

    fun getShoppingItemList(): List<ShopItem>
}