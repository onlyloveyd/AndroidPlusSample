package tech.kicky.coroutine.flow

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.*
import tech.kicky.coroutine.databinding.ActivityFlowRetrofitBinding

/**
 * Flow Retrofit
 * author: yidong
 * 2021/1/14
 */
@InternalCoroutinesApi
@FlowPreview
@ExperimentalCoroutinesApi
class FlowRetrofitActivity : AppCompatActivity(), SwipeRefreshLayout.OnRefreshListener {

    private val viewModel by viewModels<FlowViewModel>()

    private val mBinding: ActivityFlowRetrofitBinding by lazy {
        ActivityFlowRetrofitBinding.inflate(layoutInflater)
    }

    private fun TextView.textWatcherFlow(): Flow<String> = callbackFlow<String> {
        val textWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                Log.d("Flow", "afterTextChanged s =  ${s.toString()}")
                offer(s.toString())
            }
        }
        addTextChangedListener(textWatcher)
        awaitClose { removeTextChangedListener(textWatcher) }
    }.buffer(Channel.CONFLATED)
        .debounce(300L)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(mBinding.root)
        mBinding.refresh.setOnRefreshListener(this)

        val adapter = ArticleAdapter(this, {}, {})
        mBinding.list.adapter = adapter
        viewModel.articles.observe(this, {
            adapter.setData(it)
        })
        viewModel.loading.observe(this, {
            Log.d("Flow", "FlowRetrofit isLoading = $it")
        })
        lifecycleScope.launchWhenStarted {
            mBinding.etSearch.textWatcherFlow().collect {
                viewModel.getArticles(it)
            }
        }
        lifecycleScope.launchWhenStarted {
            viewModel.countState.collect { value ->
                mBinding.tvCount.text = "$value"
            }
        }
    }

    override fun onRefresh() {

    }

    fun incrementCounter(view: View) {
        viewModel.incrementCount()
    }

    fun decrementCounter(view: View) {
        viewModel.decrementCount()
    }
}