package tech.kicky.storage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import tech.kicky.EntryAdapter
import tech.kicky.HomeFragmentDirections
import tech.kicky.databinding.FragmentScopedStorageBinding

/**
 * 分区存储示例
 * author: yidong
 * 2021/2/6
 */
class ScopedStorageFragment : Fragment() {
    private val pairs = listOf(
        "获取媒体库视频" to HomeFragmentDirections.homeToCoroutineSample(),
        "下载视频并保存到媒体库" to HomeFragmentDirections.homeToScopedStorage()
    )

    private lateinit var mAdapter: EntryAdapter
    private val mBinding by lazy {
        FragmentScopedStorageBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return mBinding.root
    }

}