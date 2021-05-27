package tech.kicky.livedata

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * Progress ViewModel
 *
 * @author yidong
 * @date 2021/5/27
 */
class ProgressViewModel : ViewModel() {
    private val _progress = MutableLiveData<Int>()
    val progress: LiveData<Int> = _progress

    fun start() {
//        viewModelScope.launch(Dispatchers.IO) {
//            for (i in 0 until 100_000_000) {
//                _progress.postValue(i)
//            }
//        }
        viewModelScope.launch(Dispatchers.IO) {
            _progress.postValue(100)
            withContext(Dispatchers.Main) {
                _progress.value = 200
            }
        }
    }
}