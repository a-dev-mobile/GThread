package a.dev.mobile.gthread

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment

class FrgGDetails : Fragment() {

    //2
    companion object {

        private const val TAG = "== FrgGDetails"
        private const val G_MODEL = "model"
        private var isMM: Boolean = true
        fun newInstance(gModel: ModelG, isMM: Boolean): FrgGDetails {
            val args = Bundle()
            args.putSerializable(G_MODEL, gModel)
            val fragment = FrgGDetails()
            this.isMM = isMM
            fragment.arguments = args
            return fragment
        }
    }

    //3
    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_g_details, container, false)

        val model = arguments!!.getSerializable(G_MODEL) as ModelG

        val tvNameThreadMain: TextView = root.findViewById(R.id.tvNameThreadMain)
        val tvNameThreadSub: TextView = root.findViewById(R.id.tvNameThreadSub)
        val tvBaseDiamThread: TextView = root.findViewById(R.id.tvBaseDiam)

        if (isMM) {
            tvNameThreadMain.text = "${model.desc1} x ${model.threadPitch}   ${model.class_}"
            tvNameThreadSub.text = "${model.desc2}\" - ${model.threadPer}"
            tvBaseDiamThread.text =model.desc1.removePrefix("G ")
        } else {
            tvNameThreadMain.text = "${model.desc2}\" - ${model.threadPer}   ${model.class_}"
            tvNameThreadSub.text = "${model.desc1} x ${model.threadPitch}"
            tvBaseDiamThread.text ="${model.desc2.removePrefix("G ")}\""

        }






        return root
    }
}
