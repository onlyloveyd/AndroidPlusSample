package tech.kicky.storage

import android.content.Context
import android.view.ViewGroup
import com.bumptech.glide.Glide
import tech.kicky.common.BindingAdapter
import tech.kicky.common.BindingViewHolder
import tech.kicky.databinding.RvItemMovieBinding
import tech.kicky.storage.data.Movie

/**
 * 视频列表Adapter
 *
 * @author yidong
 * @date 2/8/21
 */
class MovieAdapter(context: Context) : BindingAdapter<Movie>(context) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BindingViewHolder {
        val binding = RvItemMovieBinding.inflate(layoutInflater, parent, false)
        return BindingViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BindingViewHolder, position: Int) {
        val binding = holder.binding as RvItemMovieBinding
        Glide.with(context).load(getItem(position).uri).into(binding.ivThumbnail)
    }
}