package com.tayyipgunay.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.tayyipgunay.model.Item

    @Database(entities = [Item::class], version = 1)
    abstract class ItemDatabase : RoomDatabase() {
        abstract fun itemDao(): ItemDao
    }

