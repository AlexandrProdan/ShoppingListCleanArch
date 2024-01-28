package com.example.shappinglistcleanarch1.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.shappinglistcleanarch1.domain.ShopItem
import com.example.shappinglistcleanarch1.domain.ShopListRepository
import kotlin.random.Random

object ShopListRepoImp : ShopListRepository {

    private val shopListLD = MutableLiveData<List<ShopItem>>()
    private val shopList = sortedSetOf<ShopItem>({o1, o2 -> o1.id.compareTo(o2.id)})
    private var autoIncrementId = 0

    init {
        for (i in 1..10) {

            val item = ShopItem("Name $i ", i, Random.nextBoolean())
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