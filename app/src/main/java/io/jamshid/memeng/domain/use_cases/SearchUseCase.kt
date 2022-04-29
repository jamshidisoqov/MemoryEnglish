package io.jamshid.memeng.domain.use_cases

import io.jamshid.memeng.data.local.dao.EnglishDao
import io.jamshid.memeng.data.local.entites.Word
import javax.inject.Inject

class SearchUseCase @Inject constructor(private val englishDao: EnglishDao) {
    suspend fun changeList(query:String):List<Word>{
        return englishDao.updateList(query)
    }
}