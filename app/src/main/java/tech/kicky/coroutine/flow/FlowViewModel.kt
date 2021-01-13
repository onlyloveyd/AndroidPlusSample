package tech.kicky.coroutine.flow

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import tech.kicky.coroutine.Retrofitance
import tech.kicky.coroutine.db.AppDatabase

/**
 * Flow ViewModel
 * author: yidong
 * 2021/1/13
 */
class FlowViewModel(app: Application) : AndroidViewModel(app) {

    val content = MutableLiveData<String>()
    val toastMsg = MutableLiveData<String>()

    fun getArticles(key: String) {
        viewModelScope.launch {
            flow {
                val result = Retrofitance.wanAndroidApi.searchArticles(0, key)
                emit(result.data.datas)
            }.map {
                var titles = ""
                for (item in it) {
                    titles += item.title + "\n"
                }
                titles
            }.flowOn(Dispatchers.IO)
                .catch { ex ->
                    ex.printStackTrace()
                    toastMsg.setValue(ex.message)
                }
                .collect {
                    content.setValue(it)
                }
        }
    }

    fun getUsers() {
        viewModelScope.launch {
            AppDatabase.getInstance(getApplication()).userDao().getAll()
                .map {
                    var titles = ""
                    for (item in it) {
                        titles += item.firstName + "\n"
                    }
                    titles
                }.flowOn(Dispatchers.IO)
                .catch { ex ->
                    ex.printStackTrace()
                    toastMsg.setValue(ex.message)
                }
                .collect {
                    content.setValue(it)
                }
        }
    }
}