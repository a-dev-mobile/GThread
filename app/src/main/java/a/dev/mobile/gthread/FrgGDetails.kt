package a.dev.mobile.gthread

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment

class FrgGDetails : Fragment() {

    //2
    companion object {

        private const val TAG = "== FrgGDetails"
        private const val G_MODEL = "model"
        private var isMM: Boolean = true
        private var isEx: Boolean = true
        fun newInstance(gModel: ModelG, isMM: Boolean): FrgGDetails {
            val args = Bundle()
            args.putSerializable(G_MODEL, gModel)
            val fragment = FrgGDetails()

            this.isMM = isMM
            isEx = gModel.class_ != ""

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
        val tvTypeThread: TextView = root.findViewById(R.id.tvTypeThread)
        val tvThreadPerInch: TextView = root.findViewById(R.id.tvThreadPerInch)
        val tvThreadPith: TextView = root.findViewById(R.id.tvThreadPith)
        val tvThreadClass: TextView = root.findViewById(R.id.tvThreadClass)
        val viewEx1: LinearLayout = root.findViewById(R.id.viewEx1)
        val tvTapDrill: TextView = root.findViewById(R.id.tvTapDrill)





        if (isMM) {
            tvNameThreadMain.text = "${model.desc1} x ${model.threadPitch}   ${model.class_}"
            tvNameThreadSub.text = "${model.desc2}\" - ${model.threadPer}"
            tvBaseDiamThread.text = model.desc1.removePrefix("G ") + " mm"
        } else {
            tvNameThreadMain.text = "${model.desc2}\" - ${model.threadPer}   ${model.class_}"
            tvNameThreadSub.text = "${model.desc1} x ${model.threadPitch}"
            tvBaseDiamThread.text = "${model.desc2.removePrefix("G ")}\""
        }


        if (isEx) tvTypeThread.text = "G - Трубная цилиндрическая наружняя"
        else tvTypeThread.text = "G - Трубная цилиндрическая внутренняя"

        tvThreadPerInch.text = model.threadPer
        tvThreadPith.text = model.threadPitch + " mm"

        if (isEx) tvThreadClass.text = model.class_
        else tvThreadClass.text = "no"

        if (isEx) viewEx1.visibility = View.GONE
        else {
            viewEx1.visibility = View.VISIBLE
            if (isMM) tvTapDrill.text = model.inTapDrill + " mm"
            else tvTapDrill.text = HelpMy.mmToInch(model.inTapDrill) + "\""
        }

        return root
    }
}
