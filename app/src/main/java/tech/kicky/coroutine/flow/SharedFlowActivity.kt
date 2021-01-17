package tech.kicky.coroutine.flow

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import tech.kicky.coroutine.databinding.ActivitySharedFlowBinding

/**
 * Shared Flow Activity
 * author: yidong
 * 2021/1/16
 */
class SharedFlowActivity : AppCompatActivity() {

    private val viewModel by viewModels<SharedFlowViewModel>()

    private val mBinding: ActivitySharedFlowBinding by lazy {
        ActivitySharedFlowBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(mBinding.root)
    }

    fun startRefresh(view: View) {
        viewModel.startRefresh()
    }

    fun endRefresh(view: View) {
        viewModel.stopRefresh()
    }
}