package tech.kicky.storage

import android.content.Context
import android.view.ViewGroup
import com.bumptech.glide.Glide
import tech.kicky.R
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
class MovieAdapter(context: Context, val longClick: (Movie) -> Unit) :
    BindingAdapter<Movie>(context) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BindingViewHolder {
        val binding = RvItemMovieBinding.inflate(layoutInflater, parent, false)
        return BindingViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BindingViewHolder, position: Int) {
        val binding = holder.binding as RvItemMovieBinding
        Glide.with(context)
            .load(getItem(position).uri)
            .placeholder(R.drawable.ic_start)
            .error(R.drawable.ic_stop)
            .into(binding.ivThumbnail)
        holder.itemView.setOnLongClickListener {
            longClick(getItem(position))
            true
        }
    }
}