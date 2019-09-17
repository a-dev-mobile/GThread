

package a.dev.mobile.gthread


import a.dev.mobile.gthread.databinding.FragmentGDetailsBinding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

class FrgGDetails : Fragment() {

    //2
    companion object {

        private const val G_MODEL = "model"

        fun newInstance(gModel: ModelG): FrgGDetails {
            val args = Bundle()
            args.putSerializable(G_MODEL, gModel)
            val fragment = FrgGDetails()
            fragment.arguments = args
            return fragment
        }
    }

    //3
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        val fragmentGDetailsBinding =
            FragmentGDetailsBinding.inflate(inflater, container, false)

        val model = arguments!!.getSerializable(G_MODEL) as ModelG
        fragmentGDetailsBinding.gModel = model

        return fragmentGDetailsBinding.root
    }

}
