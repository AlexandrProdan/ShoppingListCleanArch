package com.example.shappinglistcleanarch1.domain

data class ShopItem(
    val name: String,
    val count: Int,
    val enabled: Boolean,
    var id: Int = UNDEFINED_ID
){
    companion object{
        public const val UNDEFINED_ID = -1
    }
}
