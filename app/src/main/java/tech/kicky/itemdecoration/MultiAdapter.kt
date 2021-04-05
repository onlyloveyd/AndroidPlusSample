package tech.kicky.itemdecoration

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import tech.kicky.common.BindingAdapter
import tech.kicky.common.BindingViewHolder
import tech.kicky.common.ItemType
import tech.kicky.databinding.RvItemType1Binding
import tech.kicky.databinding.RvItemType2Binding
import tech.kicky.databinding.RvItemType3Binding
import tech.kicky.databinding.RvItemType6Binding

/**
 * 多类型适配器
 * author: yidong
 * 2021-03-30
 */
class MultiAdapter(context: Context) : BindingAdapter<ItemType>(context) {

    companion object {
        const val TYPE_3 = 0  // spanSize 3
        const val TYPE_2 = 1  // spanSize 2
        const val TYPE_6 = 3  // spanSize 6
        const val TYPE_1 = 4  // spanSize 1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BindingViewHolder {
        var binding: ViewBinding? = null
        when (viewType) {
            TYPE_1 -> binding = RvItemType1Binding.inflate(LayoutInflater.from(context))
            TYPE_2 -> binding = RvItemType2Binding.inflate(LayoutInflater.from(context))
            TYPE_3 -> binding = RvItemType3Binding.inflate(LayoutInflater.from(context))
            TYPE_6 -> binding = RvItemType6Binding.inflate(LayoutInflater.from(context))
        }
        return BindingViewHolder(binding!!)
    }

    override fun onBindViewHolder(holder: BindingViewHolder, position: Int) {

    }

    override fun getItemViewType(position: Int): Int {
        return getItem(position).getItemType()
    }
}