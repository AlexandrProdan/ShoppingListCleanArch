package com.example.shappinglistcleanarch1.presentation

import androidx.recyclerview.widget.DiffUtil
import com.example.shappinglistcleanarch1.domain.ShopItem

class ShopItemDiffCallback() : DiffUtil.ItemCallback<ShopItem>() {
    override fun areItemsTheSame(oldItem: ShopItem, newItem: ShopItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: ShopItem, newItem: ShopItem): Boolean {
        return oldItem == newItem
    }
}