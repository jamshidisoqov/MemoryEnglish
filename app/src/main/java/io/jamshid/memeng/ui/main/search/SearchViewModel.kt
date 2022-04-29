package io.jamshid.memeng.ui.main.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.jamshid.memeng.data.local.dao.EnglishDao
import io.jamshid.memeng.data.local.entites.Word
import io.jamshid.memeng.domain.use_cases.SearchUseCase
import io.jamshid.memeng.utils.base.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val englishDao: EnglishDao,
    private val searchUseCase: SearchUseCase
) : BaseViewModel() {

    private var _allWords: MutableStateFlow<List<Word>> = MutableStateFlow(emptyList())
    val allWords: StateFlow<List<Word>> get() = _allWords

    fun getAllWords() {
        viewModelScope.launch {
            _allWords.value = englishDao.getAllWord()
        }
    }


    fun deleteWord(word: Word) {
        viewModelScope.launch {
            englishDao.deleteWord(word)
            getAllWords()
        }
    }

    fun adapterChange(newString: String) {
        viewModelScope.launch {
            _allWords.value = searchUseCase.changeList(newString)
        }
    }

}