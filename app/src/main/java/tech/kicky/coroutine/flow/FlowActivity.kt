package tech.kicky.coroutine.flow

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import tech.kicky.coroutine.databinding.ActivityFlowBinding

/**
Flow Practice
author: yidong
2021/1/10
 */
class FlowActivity : AppCompatActivity() {

    private val viewModel by viewModels<FlowViewModel>()

    private val mBinding: ActivityFlowBinding by lazy {
        ActivityFlowBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(mBinding.root)
//        GlobalScope.launch(Dispatchers.Main) {
//            count().flowOn(Dispatchers.Unconfined)
//                .map {
//                    Log.d("Coroutine", "map on ${Thread.currentThread().name}")
//                    if (it > 15) {
//                        throw NumberFormatException()
//                    }
//                    "I am $it"
//                }.flowOn(Dispatchers.IO)
//                .catch { ex ->
//                    Log.d("Coroutine", "catch on ${Thread.currentThread().name}")
//                    emit("error")
//                }.collect {
//                    Log.d("Coroutine", "collect on ${Thread.currentThread().name}")
//                    mBinding.text.text = it
//                }
//        }

        viewModel.content.observe(this, {
            mBinding.text.text = it
        })

        viewModel.toastMsg.observe(this, {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        })
        viewModel.getUsers()
    }

    private fun count(): Flow<Int> = flow {
        var x = 0
        while (true) {
            if (x > 20) {
                break
            }
            delay(500)
            Log.d("Coroutine", "emit on ${Thread.currentThread().name}")
            emit(x)
            x = x.plus(1)
        }
    }

    suspend fun getBanners() {

    }
}