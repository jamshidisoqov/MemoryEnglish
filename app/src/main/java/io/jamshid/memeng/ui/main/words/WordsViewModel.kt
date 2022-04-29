package io.jamshid.memeng.ui.main.words

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.jamshid.memeng.data.local.dao.EnglishDao
import io.jamshid.memeng.data.local.entites.Word
import io.jamshid.memeng.utils.base.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WordsViewModel @Inject constructor(private val englishDao: EnglishDao) : BaseViewModel() {

    private var _allWords: MutableStateFlow<List<Word>> = MutableStateFlow(emptyList())
    val allWords: StateFlow<List<Word>> get() = _allWords


    fun deleteWord(word: Word) {
        viewModelScope.launch {
            englishDao.deleteWord(word)
            getAllWords()
        }
    }

    fun getAllWords(){
        viewModelScope.launch {
            _allWords.value = englishDao.getAllWord()
        }
    }


}