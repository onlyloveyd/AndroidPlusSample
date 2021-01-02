package tech.kicky.coroutine

/**
 * WanAndroid Root
 * author: yidong
 * 2021/1/2
 */
data class WanAndroidRoot<T>(
    val data: List<T>,
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