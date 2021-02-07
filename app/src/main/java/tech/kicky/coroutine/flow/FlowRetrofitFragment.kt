package tech.kicky.coroutine.flow

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.*
import tech.kicky.databinding.FragmentFlowRetrofitBinding

/**
 * Flow Retrofit
 * author: yidong
 * 2021/1/14
 */
@InternalCoroutinesApi
@FlowPreview
@ExperimentalCoroutinesApi
class FlowRetrofitFragment : Fragment(), SwipeRefreshLayout.OnRefreshListener {

    private val viewModel by viewModels<FlowViewModel>()

    private val mBinding: FragmentFlowRetrofitBinding by lazy {
        FragmentFlowRetrofitBinding.inflate(layoutInflater)
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


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return mBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mBinding.refresh.setOnRefreshListener(this)
        context?.let {
            val adapter = ArticleAdapter(it, {}, {})
            mBinding.list.adapter = adapter
            viewModel.articles.observe(viewLifecycleOwner, { articles ->
                adapter.setData(articles)
            })
        }

        viewModel.loading.observe(viewLifecycleOwner, {
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
        mBinding.btIncrease.setOnClickListener {
            incCounter()
        }
        mBinding.btDecrease.setOnClickListener {
            decCounter()
        }
    }

    override fun onRefresh() {

    }

    private fun incCounter() {
        viewModel.incrementCount()
    }

    private fun decCounter() {
        viewModel.decrementCount()
    }
}