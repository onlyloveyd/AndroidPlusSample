package tech.kicky.coroutine.flow

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import tech.kicky.databinding.FragmentFlowBinding

/**
Flow Practice
author: yidong
2021/1/10
 */
@ExperimentalCoroutinesApi
@FlowPreview
class FlowFragment : Fragment() {

    private val viewModel by viewModels<FlowViewModel>()

    private val mBinding: FragmentFlowBinding by lazy {
        FragmentFlowBinding.inflate(layoutInflater)
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
            val adapter = UserAdapter(it, {}, {})
            mBinding.list.adapter = adapter
            viewModel.users.observe(viewLifecycleOwner) { users ->
                adapter.setData(users)
            }
            viewModel.toastMsg.observe(viewLifecycleOwner, { toast ->
                Toast.makeText(context, toast, Toast.LENGTH_SHORT).show()
            })

            mBinding.addUser.setOnClickListener {
                addUser()
            }
        }
    }

    fun addUser() {
        val uid = mBinding.etUserId.text.toString()
        val firstName = mBinding.etFirstName.text.toString()
        val lastName = mBinding.etLastName.text.toString()
        viewModel.addUser(uid, firstName, lastName)
    }
}