package tech.kicky

import android.content.Context
import android.view.ViewGroup
import androidx.navigation.NavDirections
import tech.kicky.common.BindingAdapter
import tech.kicky.common.BindingViewHolder
import tech.kicky.databinding.RvItemTextBinding

/**
 * 首页列表适配器
 *
 * @author yidong
 * @date 2/7/21
 */
class EntryAdapter(context: Context) :
    BindingAdapter<Pair<String, () -> Unit>>(context) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BindingViewHolder {
        val binding = RvItemTextBinding.inflate(layoutInflater, parent, false)
        return BindingViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BindingViewHolder, position: Int) {
        val binding = holder.binding as RvItemTextBinding
        val pair = getItem(position)
        binding.tvTitle.text = pair.first
        binding.root.setOnClickListener {
            pair.second()
        }
    }
}