package io.jamshid.memeng.ui.main.words.add

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.jamshid.memeng.data.local.dao.EnglishDao
import io.jamshid.memeng.data.local.entites.Word
import io.jamshid.memeng.utils.base.BaseViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddWordViewModel @Inject constructor(private val englishDao: EnglishDao) : BaseViewModel() {

    fun addWords(word: Word) {
        viewModelScope.launch {
            englishDao.insertWord(word)
        }
    }

    fun updateWord(word: Word){
        viewModelScope.launch {
            englishDao.updateWord(word)
        }
    }

}