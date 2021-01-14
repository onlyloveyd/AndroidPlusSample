package tech.kicky.coroutine.flow

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import tech.kicky.coroutine.databinding.ActivityFlowBinding

/**
Flow Practice
author: yidong
2021/1/10
 */
class FlowActivity : AppCompatActivity() {

    private val viewModel by viewModels<FlowViewModel>()

    private val mBinding: ActivityFlowBinding by lazy {
        ActivityFlowBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(mBinding.root)

        val adapter = UserAdapter(this, {}, {})
        mBinding.list.adapter = adapter
        viewModel.users.observe(this) {
            adapter.setData(it)
        }
        viewModel.toastMsg.observe(this, {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        })
    }

    fun addUser(view: View) {
        val uid = mBinding.etUserId.text.toString()
        val firstName = mBinding.etFirstName.text.toString()
        val lastName = mBinding.etLastName.text.toString()
        viewModel.addUser(uid, firstName, lastName)
    }
}