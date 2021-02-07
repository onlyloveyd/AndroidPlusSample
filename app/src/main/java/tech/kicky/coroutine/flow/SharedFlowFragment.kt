package tech.kicky.coroutine.flow

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import tech.kicky.databinding.FragmentSharedFlowBinding

/**
 * Shared Flow Activity
 * author: yidong
 * 2021/1/16
 */
class SharedFlowFragment : Fragment() {

    private val viewModel by viewModels<SharedFlowViewModel>()

    private val mBinding: FragmentSharedFlowBinding by lazy {
        FragmentSharedFlowBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return mBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mBinding.btStart.setOnClickListener {
            startRefresh()
        }
        mBinding.btStop.setOnClickListener {
            endRefresh()
        }
    }

    override fun onPause() {
        super.onPause()
        viewModel.stopRefresh()
    }

    private fun startRefresh() {
        viewModel.startRefresh()
    }

    private fun endRefresh() {
        viewModel.stopRefresh()
    }
}