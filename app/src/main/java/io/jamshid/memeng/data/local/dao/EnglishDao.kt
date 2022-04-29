package io.jamshid.memeng.data.local.dao

import androidx.room.*
import io.jamshid.memeng.data.local.entites.Word

@Dao
interface EnglishDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWord(word: Word)

    @Delete
    suspend fun deleteWord(word: Word)

    @Transaction
    @Query("SELECT*FROM word")
    suspend fun getAllWord(): List<Word>

    @Update(entity = Word::class)
    suspend fun updateWord(word: Word)

    @Transaction
    @Query("SELECT*FROM word WHERE engName like '%' ||:query||'%'")
    suspend fun updateList(query:String):List<Word>
}