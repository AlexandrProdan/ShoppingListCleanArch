package com.example.shappinglistcleanarch1.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.shappinglistcleanarch1.databinding.ItemShopEnabledBinding
import com.example.shappinglistcleanarch1.domain.ShopItem

class ShopListAdapter : RecyclerView.Adapter<ShopListAdapter.ShopItemViewHolder>() {

    var shopItemList = listOf<ShopItem>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShopItemViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemShopEnabledBinding.inflate(
            inflater,
            parent,
            false
        )
        return ShopItemViewHolder(binding.root)
    }

    override fun getItemCount(): Int {
        return shopItemList.size
    }

    override fun onBindViewHolder(holder: ShopItemViewHolder, position: Int) {
        val shopItem = shopItemList[position]

        holder.binding.tvName.text = shopItem.name
        holder.binding.tvCounter.text = shopItem.count.toString()

        holder.itemView.setOnLongClickListener {
            true
        }
    }

    class ShopItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding: ItemShopEnabledBinding = ItemShopEnabledBinding.bind(view)

    }


}