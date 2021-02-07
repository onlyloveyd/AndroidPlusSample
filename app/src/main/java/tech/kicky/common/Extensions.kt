package tech.kicky.common

import android.content.Context
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView

/**
 * 自定义扩展方法
 *
 * @author yidong
 * @date 2/7/21
 */
fun RecyclerView.addDivider(context: Context) {
    this.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
}