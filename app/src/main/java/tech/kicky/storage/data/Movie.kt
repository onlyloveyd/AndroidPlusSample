package tech.kicky.storage.data

import android.net.Uri

/**
 * 视频实体
 *
 * @author yidong
 * @date 2/8/21
 */
data class Movie(val id: Long, val title: String, val uri: Uri)