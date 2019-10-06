package a.dev.mobile.gthread

import a.dev.mobile.gthread.FrgG.OnGSelected
import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import kotlinx.android.synthetic.main.activity_one.toolbar

class MainActivity : AppCompatActivity(), OnGSelected {
    lateinit var mAdView: AdView

    companion object {
        private const val TAG = "== MainActivity"
    }

    override fun onGSelected(modelG: ModelG) {

        Log.i(TAG, "==click ${modelG.desc2}")

        val i = Intent(this, MainActivity2::class.java)
        i.putExtra(ConstText.GMODEL_INTENT, modelG)
        startActivity(i)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val sp = PreferenceManager.getDefaultSharedPreferences(applicationContext)




        if (sp.getBoolean("pref_dark", false)) {
            when (sp.getString("pref_theme", "0")) {
                "0" -> setTheme(R.style.AppTheme_Dark_Blue)
                "1" -> setTheme(R.style.AppTheme_Dark_Cyan)
                "2" -> setTheme(R.style.AppTheme_Dark_Gray)
                "3" -> setTheme(R.style.AppTheme_Dark_Green)
                "4" -> setTheme(R.style.AppTheme_Dark_Purple)
                "5" -> setTheme(R.style.AppTheme_Dark_Red)
                "6" -> setTheme(R.style.AppTheme_Dark_Indigo)
            }
        } else
        when (sp.getString("pref_theme", "0")) {
            "0" -> setTheme(R.style.AppTheme_Light_Blue)
            "1" -> setTheme(R.style.AppTheme_Light_Cyan)
            "2" -> setTheme(R.style.AppTheme_Light_Gray)
            "3" -> setTheme(R.style.AppTheme_Light_Green)
            "4" -> setTheme(R.style.AppTheme_Light_Purple)
            "5" -> setTheme(R.style.AppTheme_Light_Red)
            "6" -> setTheme(R.style.AppTheme_Light_Indigo)
        }

        setContentView(R.layout.activity_one)















        setSupportActionBar(toolbar)

        DbSQLiteHelper(this).readableDatabase






        if (sp.getInt("launch_count", 5) == 0) {
            RateDialog.show(this)
            val editor = sp.edit()
            editor.putInt("launch_count", -1)
            editor.apply()
        }




        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .add(R.id.root_layout, FrgG.newInstance(), "gList")
                .commit()
        }


        reklama()

    }

    private fun reklama() {

        MobileAds.initialize(this) {}
        mAdView = findViewById(R.id.ad_view1)
        val adRequest = AdRequest.Builder()
//            .addTestDevice("DCE6F5A8B4CFE247250D6311F72F819E")
//            .addTestDevice("1C44256E6D8F5C74BC88AC08D75443CA")
            .build()
        mAdView.loadAd(adRequest)

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        super.onOptionsItemSelected(item)
        when (item.itemId) {

            R.id.action_rating -> actionRate()

            R.id.action_settings -> {
                startActivity(
                    Intent(
                        this@MainActivity,
                        SettingsActivity::class.java
                    )
                )
            }

        }
        return true
    }

    private fun actionRate() {
        try {
            startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("market://details?id=$packageName")
                )
            )
        } catch (e: ActivityNotFoundException) {
            startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("https://play.google.com/store/apps/details?id=$packageName")
                )
            )
        }
    }
}
