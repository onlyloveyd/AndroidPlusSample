package tech.kicky

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import tech.kicky.common.addDivider
import tech.kicky.databinding.FragmentHomeBinding

/**
 * Entry Fragment
 *
 * @author yidong
 * @date 2/7/21
 */
class HomeFragment : Fragment() {

    private val pairs = listOf(
        "协程" to { directToTarget(HomeFragmentDirections.homeToCoroutineSample()) },
        "分区存储" to { directToTarget(HomeFragmentDirections.homeToScopedStorage()) }
    )
    private lateinit var mAdapter: EntryAdapter

    private val mBinding: FragmentHomeBinding by lazy {
        FragmentHomeBinding.inflate(layoutInflater)
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
            mAdapter = EntryAdapter(it)
            mAdapter.setData(pairs)
            mBinding.menuList.adapter = mAdapter
            mBinding.menuList.addDivider(it)
        }
    }

    private fun directToTarget(direction: NavDirections) {
        findNavController().navigate(direction)
    }
}