package tech.kicky.camerax

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import tech.kicky.databinding.FragmentCameraXBinding

/**
 * CameraX Sample
 *
 * @author yidong
 * @date 2/19/21
 */
class CameraXFragment : Fragment() {

    private val mBinding by lazy {
        FragmentCameraXBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}