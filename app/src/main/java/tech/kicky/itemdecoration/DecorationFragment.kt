package tech.kicky.itemdecoration

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import tech.kicky.EntryAdapter
import tech.kicky.common.addDivider
import tech.kicky.databinding.FragmentListBinding
import tech.kicky.storage.ScopedStorageFragmentDirections

/**
 * RecyclerView Item Decoration
 * author: yidong
 * 2021-03-31
 */
class DecorationFragment : Fragment() {
    private val pairs = listOf(
        "时间轴" to { },
        "粘性头部" to { },
        "多类型网格" to {}
    )

    private lateinit var mAdapter: EntryAdapter
    private val mBinding by lazy {
        FragmentListBinding.inflate(layoutInflater)
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
            mBinding.sampleList.adapter = mAdapter
            mBinding.sampleList.addDivider(it)
            mAdapter.setData(pairs)
        }
    }

    private fun directToMovie() {
        val direction = ScopedStorageFragmentDirections.scopedToMovies()
        findNavController().navigate(direction)
    }

    companion object {
        const val PICK_FILE = 1
    }
}