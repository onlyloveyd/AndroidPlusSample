package tech.kicky.livedata

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import tech.kicky.databinding.FragmentListBinding
import tech.kicky.databinding.FragmentLiveDataBinding

/**
 * Description
 *
 * @author yidong
 * @date 2021/5/27
 */
class LiveDataFragment : Fragment() {
    private val mBinding by lazy {
        FragmentLiveDataBinding.inflate(layoutInflater)
    }

    private val viewModel: ProgressViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return mBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mBinding.start.setOnClickListener {
            viewModel.start()
        }
        viewModel.progress.observe(viewLifecycleOwner) {
            mBinding.progress.text = it.toString()
        }
    }
}