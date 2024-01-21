package com.example.shappinglistcleanarch1.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.shappinglistcleanarch1.domain.ShopItem
import com.example.shappinglistcleanarch1.domain.ShopListRepository

object ShopListRepoImp : ShopListRepository {

    private val shopListLD = MutableLiveData<List<ShopItem>>()
    private val shopList = mutableListOf<ShopItem>()
    private var autoIncrementId = 0

    init {
        for (i in 0..1000) {
            val alphabet = "A B C D E F G H I J K L M N O P Q R S T U V W X Y Z "
            val item = ShopItem("Name $i \n$alphabet \n$alphabet \n$alphabet ", i, true)
            addShoppingItem(item)
        }

    }

    override fun addShoppingItem(shopItem: ShopItem) {
        if (shopItem.id == ShopItem.UNDEFINED_ID) {
            shopItem.id = autoIncrementId++
        }

        shopList.add(shopItem)
        updateShopListLD()
    }

    override fun deleteShopItem(shopItem: ShopItem) {
        shopList.remove(shopItem)
        updateShopListLD()
    }

    override fun editShopItem(shopItem: ShopItem) {
        val oldShopItem = getShopItemById(shopItem.id)
        shopList.remove(oldShopItem)
        addShoppingItem(shopItem)
    }

    override fun getShopItemById(shopItemId: Int): ShopItem {
        return shopList.find {
            it.id == shopItemId
        } ?: throw RuntimeException("Item with id: $shopItemId not found")
    }

    override fun getShoppingItemList(): LiveData<List<ShopItem>> {
        return shopListLD
    }

    private fun updateShopListLD() {
        shopListLD.postValue(shopList.toList())
    }
}