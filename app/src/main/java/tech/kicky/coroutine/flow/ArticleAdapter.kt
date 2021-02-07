package tech.kicky.coroutine.flow

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import tech.kicky.common.BindingViewHolder
import tech.kicky.coroutine.Article
import tech.kicky.databinding.ItemUserBinding

/**
 * Article Adapter
 * author: yidong
 * 2021/1/14
 */
class ArticleAdapter(
    private val context: Context,
    val click: () -> Unit,
    val longClick: () -> Unit
) :
    RecyclerView.Adapter<BindingViewHolder>() {

    private val dataList = ArrayList<Article>()

    fun setData(data: List<Article>) {
        this.dataList.clear()
        this.dataList.addAll(data)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BindingViewHolder {
        val binding = ItemUserBinding.inflate(LayoutInflater.from(context), parent, false)
        return BindingViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BindingViewHolder, position: Int) {
        val item = dataList[position]
        val binding = holder.binding as ItemUserBinding
        binding.text.text = "${item.title}"
    }

    override fun getItemCount() = dataList.size
}