package tech.kicky

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import tech.kicky.databinding.ActivityMenuBinding

/**
 * Main Entry List
 * author: yidong
 * 2021/2/6
 */
class MenuActivity : AppCompatActivity() {
    private val mBinding: ActivityMenuBinding by lazy {
        ActivityMenuBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(mBinding.root)
    }
}