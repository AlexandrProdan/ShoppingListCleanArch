package com.example.shappinglistcleanarch1.domain

import androidx.lifecycle.LiveData

interface ShopListRepository {

    fun addShoppingItem(shopItem: ShopItem)

    fun deleteShopItem(shopItem: ShopItem)

    fun editShopItem(shopItem: ShopItem)

    fun getShopItemById(shopItemId: Int): ShopItem

    fun getShoppingItemList(): LiveData<List<ShopItem>>
}