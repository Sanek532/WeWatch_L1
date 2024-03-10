package com.example.wewatch_l1.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Movie::class], version = 1)
abstract class MyDatabase : RoomDatabase() {
    abstract fun getDao(): Dao
    companion object{
        fun getDb(context: Context): MyDatabase {
            return Room.databaseBuilder(
                context.applicationContext,
                MyDatabase::class.java,
                "movies.db"
            ).build()
        }
    }
}