package tech.kicky.coroutine.bus

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

/**
 * SharedFlow 本地总线
 * author: yidong
 * 2021/1/16
 */
object LocalEventBus {
    private val localEvents = MutableSharedFlow<Event>()
    val events = localEvents.asSharedFlow()

    suspend fun postEvent(event: Event) {
        localEvents.emit(event)
    }
}