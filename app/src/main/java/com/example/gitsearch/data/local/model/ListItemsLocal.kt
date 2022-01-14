package com.example.gitsearch.data.local.model

sealed class ListItemsLocal {

    data class ItemsLocal (val item : List<ItemLocalModel>) : ListItemsLocal()
}