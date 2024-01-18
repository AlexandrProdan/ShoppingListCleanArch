package com.example.shappinglistcleanarch1.data

import com.example.shappinglistcleanarch1.domain.ShopItem
import com.example.shappinglistcleanarch1.domain.ShopListRepository

object ShopListRepoImp : ShopListRepository {

    private val shopList = mutableListOf<ShopItem>()
    private var autoIncrementId = 0

    init {
        for (i in 0..9){
            val item = ShopItem("Name $i",i, true )
            addShoppingItem(item)
        }

    }

    override fun addShoppingItem(shopItem: ShopItem) {
        if (shopItem.id == ShopItem.UNDEFINED_ID) {
            shopItem.id = autoIncrementId++
        }

        shopList.add(shopItem)
    }

    override fun deleteShopItem(shopItem: ShopItem) {
        shopList.remove(shopItem)
    }

    override fun editShopItem(shopItem: ShopItem) {
        val oldShopItem = getShopItemById(shopItem.id)
        shopList.remove(oldShopItem)
        shopList.add(shopItem)
    }

    override fun getShopItemById(shopItemId: Int): ShopItem {
        return shopList.find {
            it.id == shopItemId
        } ?: throw RuntimeException("Item with id: $shopItemId not found")
    }

    override fun getShoppingItemList(): List<ShopItem> {
        return shopList.toList()
    }
}