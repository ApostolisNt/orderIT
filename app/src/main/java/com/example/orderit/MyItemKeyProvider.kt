package com.example.orderit

import androidx.recyclerview.selection.ItemKeyProvider

class MyItemKeyProvider(private val adapter: CategoryAdapter) : ItemKeyProvider<String>(SCOPE_CACHED)
{
    override fun getKey(position: Int): String? =
//            adapter.currentList[position].nttUid
            adapter.getItem(position).categoryID
    override fun getPosition(key: String): Int {
        val itemCount = adapter.itemCount
        for (i in 0 until itemCount) {
            val item = adapter.getItem(i)
            if (item.categoryID == key) return i
        }
        return -1
    }
}