package tech.kicky.coroutine

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import tech.kicky.databinding.FragmentCoroutineSamplesBinding

/**
 * Coroutine Samples Fragment
 * author: yidong
 * 2021/2/6
 */
class CoroutineSamplesFragment : Fragment() {
    private val mBinding by lazy {
        FragmentCoroutineSamplesBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return mBinding.root
    }
}