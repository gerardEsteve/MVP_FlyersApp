package com.example.esteveshopfullytest.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "flyer_table")
data class Flyer(
    @PrimaryKey val id: String,
    @ColumnInfo(name = "retailerId")val retailer_id: String,
    @ColumnInfo(name = "title")val title: String,
    @ColumnInfo(name = "read")var read: Boolean,
    @ColumnInfo(name = "imgUrl")val imgURL: String) {

    constructor(flyerModel: FlyerModel,read: Boolean, imgURL: String ) : this(flyerModel.id,flyerModel.retailer_id,flyerModel.title,read,imgURL) {

    }

}