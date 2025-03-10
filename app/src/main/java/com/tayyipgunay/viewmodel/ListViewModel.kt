package com.tayyipgunay.viewmodel

import android.app.Application
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import com.tayyipgunay.db.ItemDatabase
import com.tayyipgunay.model.Item
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ListViewModel( application: Application): AndroidViewModel(application) {
   /* var itemList= mutableStateOf<List<Item>>(listOf())
     var selectedItem= mutableStateOf<Item?>(null)*/

    val itemList = mutableStateOf<List<Item>>(listOf())
    val selectedItem = mutableStateOf<Item>(Item("e","e", byteArrayOf(),"ss")
    )

    /*init {
    getItemList()
}*/



    val db = Room.databaseBuilder(application
        , ItemDatabase::class.java, "item_database")
        .build()

    val itemDao = db.itemDao()


    fun getItemList() {
        viewModelScope.launch(Dispatchers.IO) {
           val itemList=itemDao.getNamesAndIds()
            itemList.let {
                this@ListViewModel.itemList.value=it
            }


            withContext(Dispatchers.Main) {
               // println(itemList.get(1).itemName)
            }


        }
    }
        fun getItemById(itemId: Int): Item? {
            viewModelScope.launch(Dispatchers.IO) {
                val item = itemDao.getItemById(itemId)

                withContext(Dispatchers.Main) {
                    item?.let {
                        selectedItem.value = it
                    }
                }
            }
            return null
        }
        fun insertItem(item: Item) {
            viewModelScope.launch(Dispatchers.IO) {
                itemDao.insertItem(item)
            }


    }
    fun deleteItem(item: Item) {
        viewModelScope.launch(Dispatchers.IO) {
            itemDao.delete(item)
        }
    }





}