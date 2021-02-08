package tech.kicky.storage

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.provider.MediaStore.EXTRA_OUTPUT
import android.provider.MediaStore.EXTRA_SIZE_LIMIT
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import tech.kicky.EntryAdapter
import tech.kicky.HomeFragmentDirections
import tech.kicky.common.addDivider
import tech.kicky.databinding.FragmentScopedStorageBinding

/**
 * 分区存储示例
 * author: yidong
 * 2021/2/6
 */
class ScopedStorageFragment : Fragment() {
    private val pairs = listOf(
        "获取媒体库视频" to { directToMovie() },
        "选择文件" to { pickFile() },
    )

    private lateinit var mAdapter: EntryAdapter
    private val mBinding by lazy {
        FragmentScopedStorageBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return mBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        context?.let {
            mAdapter = EntryAdapter(it)
            mBinding.sampleList.adapter = mAdapter
            mBinding.sampleList.addDivider(it)
            mAdapter.setData(pairs)
        }
    }

    private fun directToMovie() {
        val direction = ScopedStorageFragmentDirections.scopedToMovies()
        findNavController().navigate(direction)
    }

    private fun pickFile() {
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
        intent.addCategory(Intent.CATEGORY_OPENABLE)
        intent.type = "*/*"
        startActivityForResult(intent, PICK_FILE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            PICK_FILE -> {
                if (resultCode == Activity.RESULT_OK && data != null) {
                    val uri = data.data
                    if (uri != null) {
                        Toast.makeText(context, uri.toString(), Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    companion object {
        const val PICK_FILE = 1
    }
}