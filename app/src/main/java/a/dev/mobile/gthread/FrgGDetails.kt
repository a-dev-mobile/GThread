package a.dev.mobile.gthread

import a.dev.mobile.gthread.R.drawable
import android.annotation.SuppressLint
import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import kotlin.math.abs

class FrgGDetails : Fragment() {

    //2
    companion object {

        lateinit var sp: SharedPreferences
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

        sp = PreferenceManager.getDefaultSharedPreferences(context)

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
        val viewEx2: LinearLayout = root.findViewById(R.id.viewEx2)
        val viewEx3: LinearLayout = root.findViewById(R.id.viewEx3)
        val viewEx4: LinearLayout = root.findViewById(R.id.viewEx4)

        val tvTapDrill: TextView = root.findViewById(R.id.tvTapDrill)
        val tvInfoCenterTitle2: TextView = root.findViewById(R.id.tvInfoCenterTitle2)
        val tvMinorMajorDiam: TextView = root.findViewById(R.id.tvMinorMajorDiam)
        val tvMinorMajorDiamMax: TextView = root.findViewById(R.id.tvMinorMajorDiamMax)
        val tvMinorMajorDiamMean: TextView = root.findViewById(R.id.tvMinorMajorDiamMean)
        val tvMinorMajorDiamMin: TextView = root.findViewById(R.id.tvMinorMajorDiamMin)
        val tvMinorMajorDiamEs: TextView = root.findViewById(R.id.tvMinorMajorDiamEs)
        val tvMinorMajorDiamEi: TextView = root.findViewById(R.id.tvMinorMajorDiamEi)

        val tvInMajorDiamMin: TextView = root.findViewById(R.id.tvInMajorDiamMin)

        val tvPitchDiam: TextView = root.findViewById(R.id.tvPitchDiam)
        val tvPitchDiamMax: TextView = root.findViewById(R.id.tvPitchDiamMax)
        val tvPitchDiamMean: TextView = root.findViewById(R.id.tvPitchDiamMean)
        val tvPitchDiamMin: TextView = root.findViewById(R.id.tvPitchDiamMin)
        val tvPitchDiamEs: TextView = root.findViewById(R.id.tvPitchDiamEs)
        val tvPitchDiamEi: TextView = root.findViewById(R.id.tvPitchDiamEi)





        if (isMM) {
            tvNameThreadMain.text = "${model.desc1} x ${model.threadPitch}   ${model.class_}"
            tvNameThreadSub.text = "${model.desc2}\" - ${model.threadPer}"
            tvBaseDiamThread.text = model.desc1.removePrefix("G ")
        } else {
            tvNameThreadMain.text = "${model.desc2}\" - ${model.threadPer}   ${model.class_}"
            tvNameThreadSub.text = "${model.desc1} x ${model.threadPitch}"
            tvBaseDiamThread.text = model.desc2.removePrefix("G ")
        }


        if (isEx) tvTypeThread.text = "G - Трубная цилиндрическая наружняя"
        else tvTypeThread.text = "G - Трубная цилиндрическая внутренняя"

        tvThreadPerInch.text = HelpMy.formatDecimal(model.threadPer)



        if (isMM) tvThreadPith.text = model.threadPitch
        else tvThreadPith.text = HelpMy.mmToInch(model.threadPitch)





        if (isEx) tvThreadClass.text = model.class_


        if (isEx) {

            viewEx1.visibility = View.GONE
            viewEx2.visibility = View.VISIBLE
            viewEx3.visibility = View.GONE
            viewEx4.visibility = View.GONE
        } else {
            viewEx1.visibility = View.VISIBLE
            viewEx2.visibility = View.GONE
            viewEx3.visibility = View.VISIBLE
            viewEx4.visibility = View.VISIBLE
        }


        if (isMM) tvTapDrill.text = HelpMy.formatDecimal(model.inTapDrill)
        else tvTapDrill.text = HelpMy.mmToInch(model.inTapDrill)

        var diam: String
        var mean: String
        var min: String
        var max: String
        var ei = ""
        var es = ""

        if (isEx) {
            tvInfoCenterTitle2.text = resources.getString(R.string.major_diameter)

            diam = model.exMajorDiaMax
            ei = HelpMy.formatDecimal(
                (HelpMy.stringToDouble(model.exMajorDiaMin) - (HelpMy.stringToDouble(model.exMajorDiaMax))).toString()
            )

            mean = HelpMy.formatDecimal(
                (abs((HelpMy.stringToDouble(model.exMajorDiaMax) + (HelpMy.stringToDouble(model.exMajorDiaMin))) / 2).toString())
            )
            max = model.exMajorDiaMax
            min = model.exMajorDiaMin



            if (!isMM) {
                ei = HelpMy.mmToInch(ei)
                diam = HelpMy.mmToInch(diam)
                max = HelpMy.mmToInch(max)
                min = HelpMy.mmToInch(min)
                mean = HelpMy.mmToInch(mean)
            }


            tvMinorMajorDiam.text = HelpMy.formatDecimal(diam)
            tvMinorMajorDiamEi.text = HelpMy.formatDecimal(ei)
            tvMinorMajorDiamEs.text = ""


            tvMinorMajorDiamMin.text = HelpMy.formatDecimal(min)
            tvMinorMajorDiamMax.text = HelpMy.formatDecimal(max)
            tvMinorMajorDiamMean.text = HelpMy.formatDecimal(mean)
        } else {
            tvInfoCenterTitle2.text = resources.getString(R.string.minor_diameter)
            diam = model.inMinorDiaMin
            es =
                HelpMy.formatDecimal(
                    (HelpMy.stringToDouble(model.inMinorDiaMax) - (HelpMy.stringToDouble(model.inMinorDiaMin))).toString()
                )

            mean = HelpMy.formatDecimal(
                abs((HelpMy.stringToDouble(model.inMinorDiaMax) + (HelpMy.stringToDouble(model.inMinorDiaMin))) / 2).toString()
            )
            max = model.inMinorDiaMax
            min = model.inMinorDiaMin


            if (!isMM) {
                es = HelpMy.mmToInch(es)
                diam = HelpMy.mmToInch(diam)

                max = HelpMy.mmToInch(max)
                min = HelpMy.mmToInch(min)
                mean = HelpMy.mmToInch(mean)
            }


            tvMinorMajorDiam.text = HelpMy.formatDecimal(diam)
            tvMinorMajorDiamEs.text = "+$es"
            tvMinorMajorDiamEi.text = ""

            tvMinorMajorDiamMin.text = HelpMy.formatDecimal(min)
            tvMinorMajorDiamMax.text = HelpMy.formatDecimal(max)
            tvMinorMajorDiamMean.text = HelpMy.formatDecimal(mean)
        }






        if (isEx) {

            diam = HelpMy.formatDecimal(model.exPitchDiaMax)

            ei = HelpMy.formatDecimal(
                (HelpMy.stringToDouble(model.exPitchDiaMin) - (HelpMy.stringToDouble(model.exPitchDiaMax))).toString()
            )

            mean = HelpMy.formatDecimal(
                (abs((HelpMy.stringToDouble(model.exPitchDiaMax) + (HelpMy.stringToDouble(model.exPitchDiaMin))) / 2).toString())
            )

            max = HelpMy.formatDecimal(model.exPitchDiaMax)
            min = HelpMy.formatDecimal(model.exPitchDiaMin)
        } else {

            diam = HelpMy.formatDecimal(model.inPitchDiaMin)
            es =
                HelpMy.formatDecimal(
                    (HelpMy.stringToDouble(model.inPitchDiaMax) - (HelpMy.stringToDouble(model.inPitchDiaMin))).toString()
                )

            mean = HelpMy.formatDecimal(
                abs((HelpMy.stringToDouble(model.inPitchDiaMax) + (HelpMy.stringToDouble(model.inPitchDiaMin))) / 2).toString()
            )
            max = HelpMy.formatDecimal(model.inPitchDiaMax)
            min = HelpMy.formatDecimal(model.inPitchDiaMin)
        }





        if (!isMM) {
            es = HelpMy.mmToInch(es)
            ei = HelpMy.mmToInch(ei)
            diam = HelpMy.mmToInch(diam)



            max = HelpMy.mmToInch(max)
            min = HelpMy.mmToInch(min)
            mean = HelpMy.mmToInch(mean)
        }

        if (!isEx) es = "+$es"

        tvPitchDiam.text = diam
        tvPitchDiamMax.text = max
        tvPitchDiamMean.text = mean
        tvPitchDiamMin.text = min
        tvPitchDiamEs.text = es
        tvPitchDiamEi.text = ei




        if (isMM) tvInMajorDiamMin.text = HelpMy.formatDecimal(model.inMajorDiaMin)
        else tvInMajorDiamMin.text = HelpMy.mmToInch(model.inMajorDiaMin)





        setOtherSettingThread(root, model)

        setImageThread(root)


        return root
    }

    private fun setImageThread(root: View) {
        val ivDraw: ImageView = root.findViewById(R.id.iv_draw)
        val image: Int = if (!isEx) {
            if (sp.getBoolean("pref_dark", false)) drawable.ic_in_draw_light
            else drawable.ic_in_draw_dark
        } else {

            if (sp.getBoolean("pref_dark", false)) drawable.ic_ex_draw_light
            else drawable.ic_ex_draw_dark
        }

        ivDraw.setImageResource(image)
    }

    private fun setOtherSettingThread(root: View, model: ModelG) {
        val tvh: TextView = root.findViewById(R.id.tv_h)
        val tvH: TextView = root.findViewById(R.id.tv_H)
        val tvr: TextView = root.findViewById(R.id.tv_r)

        var hOrR = (0.960491 * (HelpMy.stringToDouble(model.threadPitch))).toString()
        if (isMM) tvH.text = HelpMy.formatDecimal(hOrR)
        else tvH.text = HelpMy.mmToInch(hOrR)


        hOrR = (0.640327 * (HelpMy.stringToDouble(model.threadPitch))).toString()
        if (isMM) tvh.text = HelpMy.formatDecimal(hOrR)
        else tvh.text = HelpMy.mmToInch(hOrR)


        hOrR = (0.137329 * (HelpMy.stringToDouble(model.threadPitch))).toString()
        if (isMM) tvr.text = HelpMy.formatDecimal(hOrR)
        else tvr.text = HelpMy.mmToInch(hOrR)
    }
}
