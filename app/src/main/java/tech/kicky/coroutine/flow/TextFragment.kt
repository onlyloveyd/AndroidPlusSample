package tech.kicky.coroutine.flow

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.collect
import tech.kicky.coroutine.bus.LocalEventBus
import tech.kicky.databinding.FragmentTextBinding

/**
 * Fragment Show Text
 * author: yidong
 * 2021/1/16
 */
@InternalCoroutinesApi
class TextFragment : Fragment() {

    private val mBinding: FragmentTextBinding by lazy {
        FragmentTextBinding.inflate(layoutInflater, null, false)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        lifecycleScope.launchWhenStarted {
            LocalEventBus.events.collect {
                mBinding.tvTime.text = it.timestamp.toString()
            }
        }
        return mBinding.root
    }
}