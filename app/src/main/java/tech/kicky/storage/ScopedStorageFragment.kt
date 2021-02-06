package tech.kicky.storage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import tech.kicky.databinding.FragmentScopedStorageBinding

/**
 * 分区存储示例
 * author: yidong
 * 2021/2/6
 */
class ScopedStorageFragment : Fragment() {
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