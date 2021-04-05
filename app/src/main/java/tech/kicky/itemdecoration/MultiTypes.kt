package tech.kicky.itemdecoration

import tech.kicky.common.ItemType

/**
 * 类型实体
 * author: yidong
 * 2021-03-30
 */
class MultiTypesSpanSize1 : ItemType {
    override fun getItemType() = MultiAdapter.TYPE_1
}

class MultiTypesSpanSize2 : ItemType {
    override fun getItemType() = MultiAdapter.TYPE_2
}

class MultiTypesSpanSize3 : ItemType {
    override fun getItemType() = MultiAdapter.TYPE_3
}

class MultiTypesSpanSize6 : ItemType {
    override fun getItemType() = MultiAdapter.TYPE_6
}