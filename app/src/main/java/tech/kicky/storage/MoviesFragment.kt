package tech.kicky.storage

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.permissionx.guolindev.PermissionX
import tech.kicky.databinding.FragmentMoviesBinding
import tech.kicky.storage.data.Movie
import tech.kicky.storage.viewmodel.MovieViewModel

/**
 * 视频列表
 *
 * @author yidong
 * @date 2/8/21
 */
class MoviesFragment : Fragment(), SwipeRefreshLayout.OnRefreshListener {

    companion object {
        private const val DELETE_PERMISSION_REQUEST = 0x1033
    }

    private lateinit var mAdapter: MovieAdapter

    private val mBinding by lazy {
        FragmentMoviesBinding.inflate(layoutInflater)
    }

    private val viewModel by viewModels<MovieViewModel>()

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
            mAdapter = MovieAdapter(it) { movie ->
                AlertDialog.Builder(context)
                    .setTitle("确认删除？")
                    .setMessage("是否删除视频${movie.title}")
                    .setPositiveButton("确定") { dialog, which ->
                        deleteMovie(movie)
                        dialog.dismiss()
                    }
                    .setNegativeButton("取消") { dialog, which ->
                        dialog.dismiss()
                    }.show()
            }
            mBinding.moviesList.adapter = mAdapter
        }
        mBinding.refresh.setOnRefreshListener(this)
        mBinding.fabAdd.setOnClickListener {
            addMovies()
        }
        viewModel.movieList.observe(viewLifecycleOwner) {
            mAdapter.setData(it)
            mAdapter.notifyDataSetChanged()
        }
        viewModel.toast.observe(viewLifecycleOwner) {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
        }
        viewModel.isRefresh.observe(viewLifecycleOwner) {
            mBinding.refresh.isRefreshing = it
        }
        viewModel.loading.observe(viewLifecycleOwner) {
            if (it) {
                mBinding.loading.visibility = View.VISIBLE
            } else {
                mBinding.loading.visibility = View.INVISIBLE
            }
        }

        viewModel.permissionNeededForDelete.observe(viewLifecycleOwner, { intentSender ->
            intentSender?.let {
                startIntentSenderForResult(
                    intentSender,
                    DELETE_PERMISSION_REQUEST,
                    null,
                    0,
                    0,
                    0,
                    null
                )
            }
        })
        getMovies()
    }

    override fun onRefresh() {
        getMovies()
    }

    private fun getMovies() {
        PermissionX.init(activity)
            .permissions(Manifest.permission.READ_EXTERNAL_STORAGE)
            .request { allGranted, _, deniedList ->
                if (allGranted) {
                    viewModel.getMovies()
                } else {
                    Toast.makeText(
                        context,
                        "These permissions are denied: $deniedList",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
    }

    private fun addMovies() {
        PermissionX.init(activity)
            .permissions(Manifest.permission.READ_EXTERNAL_STORAGE)
            .request { allGranted, _, deniedList ->
                if (allGranted) {
                    viewModel.addMovie()
                } else {
                    Toast.makeText(
                        context,
                        "These permissions are denied: $deniedList",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
    }

    private fun deleteMovie(movie: Movie) {
        viewModel.deleteMovie(movie)
//        PermissionX.init(activity)
//            .permissions(Manifest.permission.WRITE_EXTERNAL_STORAGE)
//            .request { allGranted, _, deniedList ->
//                if (allGranted) {
//                    viewModel.deleteMovie(movie)
//                } else {
//                    Toast.makeText(
//                        context,
//                        "These permissions are denied: $deniedList",
//                        Toast.LENGTH_LONG
//                    ).show()
//                }
//            }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == DELETE_PERMISSION_REQUEST) {
            viewModel.deletePendingMovie()
        }
    }
}