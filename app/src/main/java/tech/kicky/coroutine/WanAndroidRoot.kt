package tech.kicky.coroutine

/**
 * WanAndroid Root
 * author: yidong
 * 2021/1/2
 */
data class WanAndroidRoot<T>(
    val data: T,
    val errorCode: Int,
    val errorMsg: String
)

data class Banner(
    val desc: String,
    val id: Int,
    val imagePath: String,
    val isVisible: Int,
    val order: Int,
    val title: String,
    val type: Int,
    val url: String
)

data class HotKey(
    val id: Int,
    val link: String,
    val name: String,
    val order: Int,
    val visible: Int
)

data class PageRoot<T>(
    val curPage: Int,
    val datas: List<T>,
    val offset: Int,
    val over: Boolean,
    val pageCount: Int,
    val size: Int,
    val total: Int
)

data class Article(
    val title: String,
    val envelopePic: String,
    val desc: String
)