package tech.kicky.storage

import android.Manifest
import android.os.Build
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
import tech.kicky.storage.viewmodel.MovieViewModel

/**
 * 视频列表
 *
 * @author yidong
 * @date 2/8/21
 */
class MoviesFragment : Fragment(), SwipeRefreshLayout.OnRefreshListener {
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
            mAdapter = MovieAdapter(it)
            mBinding.moviesList.adapter = mAdapter
        }
        mBinding.refresh.setOnRefreshListener(this)
        mBinding.fabAdd.setOnClickListener {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
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
            } else {
                viewModel.addMovie()
            }
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
        viewModel.getMovies()
    }

    override fun onRefresh() {
        viewModel.getMovies()
    }
}