package tech.kicky.common

import android.content.Context
import android.view.LayoutInflater
import androidx.recyclerview.widget.RecyclerView
import tech.kicky.coroutine.flow.BindingViewHolder

/**
 * Base Binding ViewHolder Adapter
 *
 * @author yidong
 * @date 2/7/21
 */
abstract class BindingAdapter<T>(val context: Context) : RecyclerView.Adapter<BindingViewHolder>() {

    private val mData = ArrayList<T>()
    protected val layoutInflater: LayoutInflater by lazy {
        LayoutInflater.from(context)
    }

    override fun getItemCount(): Int = mData.size

    /**
     * 设置数据
     */
    fun setData(collections: List<T>) {
        mData.clear()
        mData.addAll(collections)
    }

    fun getItem(position: Int): T {
        return mData[position]
    }
}