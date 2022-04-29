package io.jamshid.memeng.ui.main.game

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.jamshid.memeng.data.local.entites.Word
import io.jamshid.memeng.domain.use_cases.GameUseCase
import io.jamshid.memeng.utils.base.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GameViewModel @Inject constructor(private val gameUseCase: GameUseCase) : BaseViewModel() {


     var _questionList: MutableStateFlow<List<Word>> = MutableStateFlow(emptyList())
    val questionList: StateFlow<List<Word>> get() = _questionList

    fun getQuestions() {
        viewModelScope.launch {
            _questionList.value = gameUseCase.invoke()
            Log.d(TAG, "getQuestions: ${questionList.value}")
        }
    }
}