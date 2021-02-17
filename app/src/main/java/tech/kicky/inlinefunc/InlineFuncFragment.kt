package tech.kicky.inlinefunc

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import tech.kicky.databinding.FragmentInlineFunctionBinding


class InlineFuncFragment : Fragment() {

    private val mBinding by lazy {
        FragmentInlineFunctionBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return mBinding.root
    }

    override fun onStart() {
        super.onStart()
        // 调用打印方法
        doPrint {
            printAndroid()
        }
    }

    // 定义一个打印方法，传入一个函数参数
    private fun doPrint(printOne: () -> Unit) {
        printOne()
    }

    // 打印一段文字
    private fun printAndroid() {
        println("Android")
    }

    fun printOpenCV() {
        println("OpenCV")
    }
}