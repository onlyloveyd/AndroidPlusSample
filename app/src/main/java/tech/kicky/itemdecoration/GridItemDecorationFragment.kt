package tech.kicky.itemdecoration

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import tech.kicky.common.ItemType
import tech.kicky.databinding.FragmentListBinding

/**
 * GridLayoutManager ItemDecoration
 * author: yidong
 * 2021-03-30
 */
class GridItemDecorationFragment : Fragment() {

    private val mBinding: FragmentListBinding by lazy {
        FragmentListBinding.inflate(layoutInflater)
    }
    private lateinit var mAdapter: MultiAdapter

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
            mAdapter = MultiAdapter(it)
            mBinding.sampleList.adapter = mAdapter
            val gridLayoutManager = GridLayoutManager(it, 6, GridLayoutManager.VERTICAL, false)
            val spanLookup = object : GridLayoutManager.SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int {
                    return when (mAdapter.getItemViewType(position)) {
                        MultiAdapter.TYPE_1 -> 1
                        MultiAdapter.TYPE_2 -> 2
                        MultiAdapter.TYPE_3 -> 3
                        else -> 6
                    }
                }
            }
            gridLayoutManager.spanSizeLookup = spanLookup
            mBinding.sampleList.layoutManager = gridLayoutManager
            mBinding.sampleList.addItemDecoration(
                SCommonItemDecoration.builder()
                    .type(MultiAdapter.TYPE_1).leftRightSpace(4).topBottomSpace(8)
                    .hasLeftRightEdge(false).hasTopBottomEdge(true).buildType()
                    .type(MultiAdapter.TYPE_2).leftRightSpace(4).topBottomSpace(8)
                    .hasLeftRightEdge(false).hasTopBottomEdge(true).buildType()
                    .type(MultiAdapter.TYPE_3).leftRightSpace(4).topBottomSpace(8)
                    .hasLeftRightEdge(false).hasTopBottomEdge(true).buildType()
                    .type(MultiAdapter.TYPE_6).leftRightSpace(4).topBottomSpace(8)
                    .hasLeftRightEdge(false).hasTopBottomEdge(true).buildType()
                    .build()
            )

            val list = ArrayList<ItemType>()
            for (i in 0..5) {
                val tmp = MultiTypesSpanSize1()
                list.add(tmp)
            }
            for (i in 0..5) {
                val tmp = MultiTypesSpanSize2()
                list.add(tmp)
            }
            for (i in 0..7) {
                val tmp = MultiTypesSpanSize3()
                list.add(tmp)
            }
            for (i in 0..1) {
                val tmp = MultiTypesSpanSize6()
                list.add(tmp)
            }

            mAdapter.setData(list)
        }
    }
}