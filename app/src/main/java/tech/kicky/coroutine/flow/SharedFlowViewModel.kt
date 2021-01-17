package tech.kicky.coroutine.flow

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import tech.kicky.coroutine.bus.Event
import tech.kicky.coroutine.bus.LocalEventBus

/**
 * Shared Flow ViewModel
 * author: yidong
 * 2021/1/13
 */
class SharedFlowViewModel : ViewModel() {

    private var job: Job? = null

    fun startRefresh() {
        job = viewModelScope.launch(Dispatchers.IO) {
            while (true) {
                LocalEventBus.postEvent(Event(System.currentTimeMillis()))
            }
        }
    }

    fun stopRefresh() {
        job?.cancel()
    }
}