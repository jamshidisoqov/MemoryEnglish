package io.jamshid.memeng.domain.use_cases

import android.util.Log
import io.jamshid.memeng.data.local.dao.EnglishDao
import io.jamshid.memeng.data.local.entites.Word
import javax.inject.Inject

class GameUseCase @Inject constructor(private val englishDao: EnglishDao) {

    suspend operator fun invoke(): List<Word> = englishDao.getAllWord()

}