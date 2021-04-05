/**
 * Copyright 2017 Bo Song
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package tech.kicky.itemdecoration

import android.graphics.Rect
import android.util.SparseArray
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration
import androidx.recyclerview.widget.StaggeredGridLayoutManager

/**
 * Set dividers' properties(horizontal and vertical space...) of item with type.
 * 通过item type 设置边框属性
 * Created by bosong on 2017/3/10.
 */
class SCommonItemDecoration internal constructor(  // itemType -> prop
    val propMap: SparseArray<ItemDecorationProps>?
) :
    ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect, view: View,
        parent: RecyclerView, state: RecyclerView.State
    ) {
        val position = parent.getChildAdapterPosition(view)
        val adapter = parent.adapter ?: return
        val itemType = adapter.getItemViewType(position)
        val props: ItemDecorationProps?
        props = if (propMap != null) {
            propMap[itemType]
        } else {
            return
        }
        if (props == null) {
            return
        }
        var spanIndex = 0
        var spanSize = 1
        var spanCount = 1
        var orientation = RecyclerView.VERTICAL
        if (parent.layoutManager is GridLayoutManager) {
            val lp = view.layoutParams as GridLayoutManager.LayoutParams
            spanIndex = lp.spanIndex
            spanSize = lp.spanSize
            val layoutManager = parent.layoutManager as GridLayoutManager?
            spanCount =
                layoutManager!!.spanCount // Assume that there're spanCount items in this row/column.
            orientation = layoutManager.orientation
        } else if (parent.layoutManager is StaggeredGridLayoutManager) {
            val lp = view.layoutParams as StaggeredGridLayoutManager.LayoutParams
            spanIndex = lp.spanIndex
            val layoutManager = parent.layoutManager as StaggeredGridLayoutManager?
            spanCount =
                layoutManager!!.spanCount // Assume that there're spanCount items in this row/column.
            spanSize = if (lp.isFullSpan) spanCount else 1
            orientation = layoutManager.orientation
        }
        val isFirstRowOrColumn: Boolean
        val isLastRowOrColumn: Boolean
        val prePos = if (position > 0) position - 1 else -1
        val nextPos = if (position < adapter.itemCount - 1) position + 1 else -1
        // Last position on the last row 上一行的最后一个位置
        val preRowPos = if (position > spanIndex) position - (1 + spanIndex) else -1
        // First position on the next row 下一行的第一个位置
        val nextRowPos =
            if (position < adapter.itemCount - (spanCount - spanIndex)) position + (spanCount - spanIndex) - spanSize + 1 else -1
        isFirstRowOrColumn =
            position == 0 || prePos == -1 || itemType != adapter.getItemViewType(prePos) || preRowPos == -1 || itemType != adapter.getItemViewType(
                preRowPos
            )
        isLastRowOrColumn =
            position == adapter.itemCount - 1 || nextPos == -1 || itemType != adapter.getItemViewType(
                nextPos
            ) || nextRowPos == -1 || itemType != adapter.getItemViewType(nextRowPos)
        var left = 0
        var top = 0
        var right = 0
        var bottom = 0
        if (orientation == GridLayoutManager.VERTICAL) {
            if (props.hasLeftRightEdge) {
                left = props.leftRightSpace * (spanCount - spanIndex) / spanCount
                right = props.leftRightSpace * (spanIndex + (spanSize - 1) + 1) / spanCount
            } else {
                left = props.leftRightSpace * spanIndex / spanCount
                right =
                    props.leftRightSpace * (spanCount - (spanIndex + spanSize - 1) - 1) / spanCount
            }
            if (isFirstRowOrColumn) { // First row
                if (props.hasTopBottomEdge) {
                    top = props.topBottomSpace
                }
            }
            if (isLastRowOrColumn) { // Last row
                if (props.hasTopBottomEdge) {
                    bottom = props.topBottomSpace
                }
            } else {
                bottom = props.topBottomSpace
            }
        } else {
            if (props.hasTopBottomEdge) {
                top = props.topBottomSpace * (spanCount - spanIndex) / spanCount
                bottom = props.topBottomSpace * (spanIndex + (spanSize - 1) + 1) / spanCount
            } else {
                top = props.topBottomSpace * spanIndex / spanCount
                bottom =
                    props.topBottomSpace * (spanCount - (spanIndex + spanSize - 1) - 1) / spanCount
            }
            if (isFirstRowOrColumn) { // First column
                if (props.hasLeftRightEdge) {
                    left = props.leftRightSpace
                }
            }
            if (isLastRowOrColumn) { // Last column
                if (props.hasLeftRightEdge) {
                    right = props.leftRightSpace
                }
            } else {
                right = props.leftRightSpace
            }
        }
        outRect.set(left, top, right, bottom)
    }

    class ItemDecorationProps {
        /**
         * Space in left or right between items
         * 左右间距
         */
        var leftRightSpace = 0

        /**
         * Space in top or bottom between items
         * 上下间距
         */
        var topBottomSpace = 0

        /**
         * If this type of items has left and right space
         * 是否包含左右边距
         */
        var hasLeftRightEdge = false

        /**
         * If this type of items has top and bottom space
         * 是否包含上下边距
         */
        var hasTopBottomEdge = false

        internal constructor()
        constructor(
            topBottomSpace: Int,
            leftRightSpace: Int,
            hasTopBottomEdge: Boolean,
            hasLeftRightEdge: Boolean
        ) {
            this.leftRightSpace = leftRightSpace
            this.topBottomSpace = topBottomSpace
            this.hasTopBottomEdge = hasTopBottomEdge
            this.hasLeftRightEdge = hasLeftRightEdge
        }
    }

    class ItemDecorationBuilder {
        val propMap = SparseArray<ItemDecorationProps>() // itemType -> prop

        fun type(type: Int): ItemType {
            return ItemType(type, this)
        }

        fun build(): SCommonItemDecoration {
            return SCommonItemDecoration(propMap)
        }
    }

    class ItemType internal constructor(
        private val mType: Int,
        private val mBuilder: ItemDecorationBuilder
    ) {
        private val mProps = ItemDecorationProps()
        fun prop(
            leftRightSpace: Int,
            topBottomSpace: Int,
            hasLeftRightEdge: Boolean,
            hasTopBottomEdge: Boolean
        ): ItemType {
            mProps.leftRightSpace = leftRightSpace
            mProps.topBottomSpace = topBottomSpace
            mProps.hasLeftRightEdge = hasLeftRightEdge
            mProps.hasTopBottomEdge = hasTopBottomEdge
            return this
        }

        fun leftRightSpace(space: Int): ItemType {
            mProps.leftRightSpace = space
            return this
        }

        fun topBottomSpace(space: Int): ItemType {
            mProps.topBottomSpace = space
            return this
        }

        fun hasLeftRightEdge(hasEdge: Boolean): ItemType {
            mProps.hasLeftRightEdge = hasEdge
            return this
        }

        fun hasTopBottomEdge(hasEdge: Boolean): ItemType {
            mProps.hasTopBottomEdge = hasEdge
            return this
        }

        fun buildType(): ItemDecorationBuilder {
            mBuilder.propMap.put(mType, mProps)
            return mBuilder
        }
    }

    companion object {
        fun builder(): ItemDecorationBuilder {
            return ItemDecorationBuilder()
        }
    }
}