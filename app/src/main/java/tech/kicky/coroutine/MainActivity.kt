package tech.kicky.coroutine

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.*
import tech.kicky.coroutine.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater, null, false)
    }

    private val viewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
//        singleIO()
//        processIO()
//        cancelCoroutine()
//        lifecycleCoroutine()
//        viewModel.banners.observe(this, {
//            val content: List<String> = it.map { banner ->
//                banner.title
//            }
//            binding.text.text = content.toTypedArray().contentToString()
//        })
//        viewModel.hotKeys.observe(this, {
//            val content: List<String> = it.map { key ->
//                key.name
//            }
//            binding.text.text = content.toTypedArray().contentToString()
//        })
//        viewModel.viewModelSequenceRequest()
        viewModel.viewModelAsync()
    }

    /**
     * 启动一个协程
     */
    private fun singleIO() {
        GlobalScope.launch(Dispatchers.IO) {
            delay(2000)
            Log.d("Coroutine Sample", "singleIO in ${Thread.currentThread().name}")
        }
    }

    /**
     * 切换线程
     */
    private fun processIO() {
        // 主线程内启动一个协程
        GlobalScope.launch(Dispatchers.Main) {
            // 切换到IO线程
            withContext(Dispatchers.IO) {
                delay(1000)
                Log.d("Coroutine Sample", "processIO in ${Thread.currentThread().name}")
            }
            // 切回主线程
            Log.d("Coroutine Sample", "processUI in ${Thread.currentThread().name}")
        }
    }

    /**
     * 取消协程操作
     */
    private fun cancelCoroutine() {
        val job = GlobalScope.launch(Dispatchers.IO) {
            for (i in 0..10000) {
                delay(1)
                Log.d("Coroutine Sample", "count = $i")
            }
        }
        Thread.sleep(30)
        job.cancel()
        Log.d("Coroutine Sample", "Coroutine Cancel")
    }

    private fun lifecycleCoroutine() {
        // 主线程内启动一个协程
        lifecycleScope.launch {
            // 切换到IO线程
            withContext(Dispatchers.IO) {
                delay(1000)
                Log.d("Coroutine Sample", "processIO in ${Thread.currentThread().name}")
            }
            // 切回主线程
            Log.d("Coroutine Sample", "processUI in ${Thread.currentThread().name}")
        }
    }
}