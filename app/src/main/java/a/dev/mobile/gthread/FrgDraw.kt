package a.dev.mobile.gthread

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.os.Bundle
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment

class FrgDraw : Fragment() {


    companion object {

        private const val TAG = "== FrgDraw"

        private var isEx: Boolean = true
        private const val G_MODEL = "model"

        fun newInstance(gModel: ModelG): FrgDraw {
            val args = Bundle()
            args.putSerializable(G_MODEL, gModel)
            val fragment = FrgDraw()


            isEx = gModel.class_ != ""

            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val root = inflater.inflate(R.layout.draw, container, false)
        val ll: LinearLayout = root.findViewById(R.id.custom_view)

        ll.addView(context?.let { DrawView2D(it, null, 0) })

        val model = arguments!!.getSerializable(G_MODEL) as ModelG


        return root
    }

    class DrawView2D @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0
    ) : View(context, attrs, defStyleAttr) {


        @SuppressLint("DrawAllocation")
        override fun onDraw(c: Canvas) {

            var w = width
            var h = height

            val boltLight = resources.getDrawable(R.drawable.ic_custom)


            boltLight.setBounds(0, 0, w, h)

            boltLight.draw(c)
        }
    }
}