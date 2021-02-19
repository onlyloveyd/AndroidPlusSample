package tech.kicky.inlinefunc

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import tech.kicky.databinding.FragmentInlineFunctionBinding


class InlineFuncFragment : Fragment() {

    private val mBinding by lazy {
        FragmentInlineFunctionBinding.inflate(layoutInflater)
    }

    private val viewModel by viewModels<InlineFuncViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel.text.observe(viewLifecycleOwner) {
            mBinding.text.text = it ?: ""
        }
        return mBinding.root
    }

    override fun onStart() {
        super.onStart()
        viewModel.fastPost()
    }

}