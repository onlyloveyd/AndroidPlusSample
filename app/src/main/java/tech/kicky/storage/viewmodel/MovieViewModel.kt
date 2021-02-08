package tech.kicky.storage.viewmodel

import android.app.Application
import android.app.RecoverableSecurityException
import android.content.ContentUris
import android.content.ContentValues
import android.content.IntentSender
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.os.ParcelFileDescriptor
import android.provider.MediaStore
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.ResponseBody
import tech.kicky.common.Retrofitance
import tech.kicky.storage.data.Movie
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream


/**
 * Movie List View Model
 *
 * @author yidong
 * @date 2/8/21
 */
class MovieViewModel(application: Application) : AndroidViewModel(application) {

    private val _movieList = MutableLiveData<List<Movie>>()
    val movieList: LiveData<List<Movie>> = _movieList

    private val _toast = MutableLiveData<String>()
    val toast: LiveData<String> = _toast

    private val _loading = MutableLiveData(false)
    val loading: LiveData<Boolean> = _loading

    private val _isRefresh = MutableLiveData(false)
    val isRefresh: LiveData<Boolean> = _isRefresh

    private var pendingDeleteImage: Movie? = null
    private val _permissionNeededForDelete = MutableLiveData<IntentSender?>()
    val permissionNeededForDelete: LiveData<IntentSender?> = _permissionNeededForDelete

    fun getMovies() {
        val contentResolver = getApplication<Application>().contentResolver
        _isRefresh.value = true
        viewModelScope.launch(Dispatchers.IO) {
            val cursor = contentResolver.query(
                MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
                null,
                null,
                null,
                "${MediaStore.MediaColumns.DATE_ADDED} desc"
            )
            val movies = ArrayList<Movie>()
            if (cursor != null) {
                while (cursor.moveToNext()) {
                    val id =
                        cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.MediaColumns._ID))
                    val title =
                        cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.TITLE))
                    val uri =
                        ContentUris.withAppendedId(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, id)
                    movies.add(Movie(id, title, uri))
                }
                cursor.close()
            }
            _movieList.postValue(movies)
            _isRefresh.postValue(false)
        }
    }

    fun addMovie() {
        viewModelScope.launch {
            _loading.value = true
            withContext(Dispatchers.IO) {
                try {
                    val response =
                        Retrofitance.downloadApi.downloadVideo("https://raw.fastgit.org/zzuiekongning/sample-videos/master/store-aisle-detection.mp4")
                    if (downloadVideo(response, System.currentTimeMillis().toString())) {
                        _toast.postValue("视频保存成功")
                    } else {
                        _toast.postValue("视频保存失败")
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                    _toast.postValue("视频保存失败")
                } finally {
                    _loading.postValue(false)
                }
            }
        }
    }

    private fun getUriFromName(videoName: String): Uri? {
        val contentResolver = getApplication<Application>().contentResolver
        val uri: Uri?
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val values = ContentValues().apply {
                put(MediaStore.Video.Media.TITLE, "$videoName.mp4")
                put(MediaStore.Video.Media.MIME_TYPE, "video/mp4")
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    put(
                        MediaStore.MediaColumns.RELATIVE_PATH,
                        Environment.DIRECTORY_MOVIES + "/Sample"
                    )
//                    put(MediaStore.MediaColumns.IS_PENDING, 1)
                }
            }

            uri = contentResolver.insert(
                MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
                values
            )
        } else {
            val path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MOVIES)
            if (!path.exists()) {
                path.mkdirs()
            }
            val pathStr = path.absolutePath + "/Sample"
            val file = File(pathStr)
            if (!file.exists()) {
                file.mkdirs()
            }
            val videoPath = pathStr + File.separator + videoName + ".mp4"
            File(videoPath).apply {
                if (exists()) {
                    delete()
                }
            }
            val values = ContentValues()
            values.put(MediaStore.Video.Media.DISPLAY_NAME, videoName)
            values.put(MediaStore.Video.Media.MIME_TYPE, "video/mp4")
            values.put(MediaStore.Video.Media.DATA, videoPath)
            values.put(MediaStore.Video.Media.DATE_MODIFIED, System.currentTimeMillis() / 1000)
            uri = contentResolver.insert(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, values)
        }
        return uri
    }

    private fun downloadVideo(body: ResponseBody, title: String): Boolean {
        val contentResolver = getApplication<Application>().contentResolver
        val uri = getUriFromName(title)
        uri?.let { localUri ->
            val fileDescriptor: ParcelFileDescriptor? =
                contentResolver.openFileDescriptor(localUri, "w")
            val inStream: InputStream = body.byteStream()
            val outStream = FileOutputStream(fileDescriptor?.fileDescriptor)
            outStream.use { outPut ->
                var read: Int
                val buffer = ByteArray(2048)
                while (inStream.read(buffer).also { read = it } != -1) {
                    outPut.write(buffer, 0, read)
                }
            }
            return true
        }
        return false
    }

    fun deleteMovie(movie: Movie) {
        viewModelScope.launch {
            performDeleteMovie(movie)
        }
    }

    fun deletePendingMovie() {
        pendingDeleteImage?.let { image ->
            pendingDeleteImage = null
            deleteMovie(image)
        }
    }

    private suspend fun performDeleteMovie(movie: Movie) {
        withContext(Dispatchers.IO) {
            try {
                getApplication<Application>().contentResolver.delete(
                    movie.uri,
                    "${MediaStore.Video.Media._ID} = ?",
                    arrayOf(movie.id.toString())
                )
            } catch (securityException: SecurityException) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    val recoverableSecurityException =
                        securityException as? RecoverableSecurityException
                            ?: throw securityException
                    pendingDeleteImage = movie
                    _permissionNeededForDelete.postValue(
                        recoverableSecurityException.userAction.actionIntent.intentSender
                    )
                } else {
                    throw securityException
                }
            }
        }
    }
}