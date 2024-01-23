package com.example.shappinglistcleanarch1.presentation

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.shappinglistcleanarch1.databinding.ItemShopDisabledBinding
import com.example.shappinglistcleanarch1.databinding.ItemShopEnabledBinding
import com.example.shappinglistcleanarch1.domain.ShopItem

class ShopListAdapter : RecyclerView.Adapter<ShopListAdapter.ShopItemViewHolder>() {
    private var countBind = 1
    private var countCreate = 1
    var shopItemList = listOf<ShopItem>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    var onShopItemLongClickListener: ((ShopItem) -> Unit)? = null
    var onShopItemClickListener: ((ShopItem) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShopItemViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        val binding = when (viewType) {
            VIEW_TYPE_ENABLED -> ItemShopEnabledBinding.inflate(
                inflater,
                parent,
                false
            )

            VIEW_TYPE_DISABLED -> ItemShopDisabledBinding.inflate(
                inflater,
                parent,
                false
            )

            else -> throw RuntimeException("UNKNOWN type $viewType")
        }

        Log.d(TAG_CREATE, "onCreateViewHolder was called: $countCreate many times")
        countCreate++

        return ShopItemViewHolder(binding.root)
    }

    override fun getItemCount(): Int {
        return shopItemList.size
    }

    //    override fun onViewRecycled(holder: ShopItemViewHolder) {
//        holder.binding.tvName.text = ""
//        holder.binding.tvCounter.text = ""
//    }
    override fun getItemViewType(position: Int): Int {
        return if (shopItemList[position].enabled) {
            VIEW_TYPE_ENABLED
        } else {
            VIEW_TYPE_DISABLED
        }
    }

    override fun onBindViewHolder(holder: ShopItemViewHolder, position: Int) {
        val shopItem = shopItemList[position]

        holder.binding.tvName.text = shopItem.name
        holder.binding.tvCounter.text = shopItem.count.toString()

        holder.itemView.setOnLongClickListener {
            onShopItemLongClickListener?.invoke(shopItem)
            true
        }

        holder.itemView.setOnClickListener {
            onShopItemClickListener?.invoke(shopItem)
        }

        Log.d(TAG_BIND, "onBindViewHolder was called: $countBind many times")
        countBind++
    }

    class ShopItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding: ItemShopEnabledBinding = ItemShopEnabledBinding.bind(view)

    }

    companion object {
        private const val TAG_BIND = "BIND"
        private const val TAG_CREATE = "CREATE"

        const val VIEW_TYPE_ENABLED = 1
        const val VIEW_TYPE_DISABLED = 0

        const val MAX_POOL_SIZE = 25
    }

}