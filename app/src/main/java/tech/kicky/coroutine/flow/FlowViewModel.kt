package tech.kicky.coroutine.flow

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import tech.kicky.coroutine.Article
import tech.kicky.common.Retrofitance
import tech.kicky.coroutine.bus.Event
import tech.kicky.coroutine.bus.LocalEventBus
import tech.kicky.coroutine.db.AppDatabase
import tech.kicky.coroutine.db.User

/**
 * Flow ViewModel
 * author: yidong
 * 2021/1/13
 */
@FlowPreview
@ExperimentalCoroutinesApi
class FlowViewModel(app: Application) : AndroidViewModel(app) {
    val users: LiveData<List<User>>

    private val _articles = MutableLiveData<List<Article>>()
    val articles: LiveData<List<Article>> = _articles

    private val _toastMsg = MutableLiveData<String>()
    val toastMsg: LiveData<String> = _toastMsg

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    private val _countState = MutableStateFlow(0)

    val countState = _countState.asStateFlow()

    fun incrementCount() {
        _countState.value++
    }

    fun decrementCount() {
        _countState.value--
    }


    init {
        users = AppDatabase.getInstance(getApplication()).userDao().getAll()
            .distinctUntilChanged()
            .catch { ex ->
                ex.printStackTrace()
                _toastMsg.setValue(ex.message)
            }.asLiveData(Dispatchers.IO)
    }

    fun getArticles(key: String) {
        viewModelScope.launch {
            flow {
                Log.d("Flow", "Emit on ${Thread.currentThread().name}")
                val result = Retrofitance.wanAndroidApi.searchArticles(0, key)
                emit(result.data.datas)
            }.flowOn(Dispatchers.IO)
                .onStart {
                    _loading.value = true
                    Log.d("Flow", "onStart on ${Thread.currentThread().name}")
                }.onCompletion {
                    _loading.value = false
                    Log.d("Flow", "onComplete on ${Thread.currentThread().name}")
                }.catch { ex ->
                    ex.printStackTrace()
                    _toastMsg.setValue(ex.message)
                }.collect {
                    Log.d("Flow", "Collect on ${Thread.currentThread().name}")
                    _articles.setValue(it)
                }
        }
    }

    fun addUser(uid: String, firstName: String, lastName: String) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                flow {
                    emit(uid.toInt())
                }.catch {
                    _toastMsg.postValue("UID 格式错误")
                }.collect {
                    val result = AppDatabase.getInstance(getApplication()).userDao()
                        .addOne(User(it, firstName, lastName))
                    if (result > 0) {
                        _toastMsg.postValue("Insert Successfully")
                    } else {
                        _toastMsg.postValue("Insert Failed")
                    }
                }
            }
        }
    }

    fun startCount() {
        viewModelScope.launch {
            while (true) {
                LocalEventBus.postEvent(Event(System.currentTimeMillis()))
            }
        }
    }
}