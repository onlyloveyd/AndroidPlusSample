package tech.kicky.coroutine

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.*
import tech.kicky.databinding.FragmentBasicCoroutineBinding

class BasicCoroutineFragment : Fragment() {

    private val binding: FragmentBasicCoroutineBinding by lazy {
        FragmentBasicCoroutineBinding.inflate(layoutInflater)
    }

    private val viewModel by viewModels<MainViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return binding.root
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

//        singleIO()
//        processIO()
//        cancelCoroutine()
//        lifecycleCoroutine()
        viewModel.banners.observe(this, {
            val content: List<String> = it.map { banner ->
                banner.title
            }
            binding.text.text = content.toTypedArray().contentToString()
        })
        viewModel.toastMsg.observe(this, {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
        })
//        viewModel.hotKeys.observe(this, {
//            val content: List<String> = it.map { key ->
//                key.name
//            }
//            binding.text.text = content.toTypedArray().contentToString()
//        })
//        viewModel.viewModelSequenceRequest()
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