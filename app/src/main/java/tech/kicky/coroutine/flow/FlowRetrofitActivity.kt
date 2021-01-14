package tech.kicky.coroutine.flow

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import tech.kicky.coroutine.databinding.ActivityFlowRetrofitBinding

/**
 * Flow Retrofit
 * author: yidong
 * 2021/1/14
 */
class FlowRetrofitActivity : AppCompatActivity(), SwipeRefreshLayout.OnRefreshListener {

    private val viewModel by viewModels<FlowViewModel>()

    private val mBinding: ActivityFlowRetrofitBinding by lazy {
        ActivityFlowRetrofitBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(mBinding.root)
        mBinding.refresh.setOnRefreshListener(this)

        val adapter = ArticleAdapter(this, {}, {})
        mBinding.list.adapter = adapter
        viewModel.articles.observe(this, {
            adapter.setData(it)
        })
        viewModel.getArticles("list")

        viewModel.loading.observe(this, {
            Log.d("Flow", "FlowRetrofit isLoading = $it")
        })
    }

    override fun onRefresh() {

    }
}