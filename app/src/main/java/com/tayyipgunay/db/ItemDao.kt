package com.tayyipgunay.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.tayyipgunay.model.Item

@Dao
interface ItemDao {

    @Query("SELECT name,id FROM Item")
   suspend fun getNamesAndIds(): List<Item>


    @Query("SELECT * FROM Item WHERE id = :itemId")
    suspend fun getItemById(itemId: Int): Item?

    @Insert
    suspend fun insertItem(item: Item)

    @Delete
    suspend fun delete(item: Item)


}