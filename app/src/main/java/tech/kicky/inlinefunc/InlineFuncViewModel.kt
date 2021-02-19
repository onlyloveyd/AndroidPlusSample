package tech.kicky.inlinefunc

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 *
 * Inline Func ViewModel
 * @author yidong
 * @date 2/19/21
 */
class InlineFuncViewModel : ViewModel() {
    private val _text = MutableLiveData<String>()
    val text = _text

    fun fastPost() {
        viewModelScope.launch(Dispatchers.IO) {
            for (i in 0..1000000) {
//                delay(1000)
                _text.postValue(i.toString())
            }
        }

    }
}