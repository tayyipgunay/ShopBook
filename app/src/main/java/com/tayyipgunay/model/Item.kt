package com.tayyipgunay.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "item")
class Item(
    @ColumnInfo(name = "name")
    var itemName:String,

    @ColumnInfo(name = "storename")
    var storename:String?,


    @ColumnInfo(name = "image")
    var itemImage:ByteArray?,

@ColumnInfo(name = "price")
var itemPrice:String?
)

{
    @PrimaryKey(autoGenerate = true)
    var id:Int=0


}