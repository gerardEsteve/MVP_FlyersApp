package com.example.esteveshopfullytest.model.database

import androidx.room.*
import com.example.esteveshopfullytest.model.Flyer

@Dao
interface  FlyerDAO {

    @Query("SELECT * FROM flyer_table")
    fun getAllFlyers(): List<Flyer>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAllFlyers(flyers: List<Flyer>)

    @Delete
    suspend fun deleteFlyer(flyer: Flyer)

    @Query("DELETE FROM flyer_table")
    suspend fun deleteAll()

    @Insert
    suspend fun insertFlyer(flyer: Flyer)
}