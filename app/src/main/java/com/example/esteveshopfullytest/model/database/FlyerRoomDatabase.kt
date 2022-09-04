package com.example.esteveshopfullytest.model.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.esteveshopfullytest.model.Flyer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(entities = arrayOf(Flyer::class), version = 1, exportSchema = false)
abstract class FlyerRoomDatabase : RoomDatabase() {

    abstract fun flyerDao(): FlyerDAO


    private class FlyerDatabaseCallback(
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {

        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                scope.launch {
                    /*
                    // Delete all content here.
                    flyerDAO.deleteAll()

                    // Add sample flyers.
                    var flyer = Flyer("id","retailer_id","title",false,"imgUrl")
                    flyerDAO.insertFlyer(flyer)*/

                }
            }
        }
    }


    companion object {
        @Volatile
        private var INSTANCE: FlyerRoomDatabase? = null

        fun getDatabase(context: Context,scope: CoroutineScope): FlyerRoomDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    FlyerRoomDatabase::class.java,
                    "flyer_database"
                )
                    .addCallback(FlyerDatabaseCallback(scope))
                    .build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }
}