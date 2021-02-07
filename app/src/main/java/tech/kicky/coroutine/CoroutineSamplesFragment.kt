package tech.kicky.coroutine

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import tech.kicky.EntryAdapter
import tech.kicky.common.addDivider
import tech.kicky.databinding.FragmentCoroutineSamplesBinding
import tech.kicky.storage.ScopedStorageFragment

/**
 * Coroutine Samples Fragment
 * author: yidong
 * 2021/2/6
 */
class CoroutineSamplesFragment : Fragment() {

    private lateinit var mAdapter: EntryAdapter

    private val pairs = listOf(
        "Basic Coroutine" to CoroutineSamplesFragmentDirections.coroutineToBasic(),
        "Flow & Room" to CoroutineSamplesFragmentDirections.coroutineToFlow(),
        "Flow & Retrofit" to CoroutineSamplesFragmentDirections.coroutineToFlowRetrofit(),
        "Shared Flow" to CoroutineSamplesFragmentDirections.coroutineToSharedFlow()
    )

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

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        context?.let {
            mAdapter = EntryAdapter(it) { direction ->
                findNavController().navigate(direction)
            }
            mAdapter.setData(pairs)
            mBinding.sampleList.adapter = mAdapter
            mBinding.sampleList.addDivider(it)
        }
    }
}