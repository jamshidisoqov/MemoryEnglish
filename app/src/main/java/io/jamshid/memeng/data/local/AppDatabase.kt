package io.jamshid.memeng.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import io.jamshid.memeng.data.local.dao.EnglishDao
import io.jamshid.memeng.data.local.entites.Word

@Database(entities = [Word::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase(){
    abstract fun englishDao():EnglishDao
}