package a.dev.mobile.gthread

import a.dev.mobile.gthread.R.id
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity2 : AppCompatActivity() {
    companion object {
        private const val TAG = "== MainActivity2"
    }
    lateinit var mAdView: AdView
    private lateinit var modelG: ModelG

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        modelG = intent.getSerializableExtra(ConstText.GMODEL_INTENT) as ModelG

        val sp = PreferenceManager.getDefaultSharedPreferences(applicationContext)




        if (sp.getBoolean("pref_dark", false))
            when (sp.getString("pref_theme", "0")) {
                "0" -> setTheme(R.style.AppTheme_Dark_Blue)
                "1" -> setTheme(R.style.AppTheme_Dark_Cyan)
                "2" -> setTheme(R.style.AppTheme_Dark_Gray)
                "3" -> setTheme(R.style.AppTheme_Dark_Green)
                "4" -> setTheme(R.style.AppTheme_Dark_Purple)
                "5" -> setTheme(R.style.AppTheme_Dark_Red)
                "6" -> setTheme(R.style.AppTheme_Dark_Indigo)
            }
        else
            when (sp.getString("pref_theme", "0")) {
                "0" -> setTheme(R.style.AppTheme_Light_Blue)
                "1" -> setTheme(R.style.AppTheme_Light_Cyan)
                "2" -> setTheme(R.style.AppTheme_Light_Gray)
                "3" -> setTheme(R.style.AppTheme_Light_Green)
                "4" -> setTheme(R.style.AppTheme_Light_Purple)
                "5" -> setTheme(R.style.AppTheme_Light_Red)
                "6" -> setTheme(R.style.AppTheme_Light_Indigo)
            }


        setContentView(R.layout.activity_two)
        reklama()

        val navView: BottomNavigationView = findViewById(id.nav_view)


        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

        if (savedInstanceState == null) {
            loadFragment(true)
        }





    }

    private fun reklama() {

        MobileAds.initialize(this) {}
        mAdView = findViewById(R.id.ad_view2)
        val adRequest = AdRequest.Builder()
//            .addTestDevice("DCE6F5A8B4CFE247250D6311F72F819E")
//            .addTestDevice("1C44256E6D8F5C74BC88AC08D75443CA")
            .build()
        mAdView.loadAd(adRequest)

    }
    private val mOnNavigationItemSelectedListener =
        BottomNavigationView.OnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                id.navigation_home -> {
                    Log.i(TAG, " navigation_1")

                    loadFragment(true)
                    return@OnNavigationItemSelectedListener true
                }
                id.navigation_dashboard -> {
                    Log.i(TAG, " navigation_2")
                    loadFragment(false)
                    return@OnNavigationItemSelectedListener true
                }

            }
            false
        }

    private fun loadFragment(isMM: Boolean) {
        val fragment = FrgGDetails.newInstance(modelG, isMM)
        supportFragmentManager.beginTransaction()
            .replace(id.container, fragment)
            .commit()
    }



    //закрыть сразу активити при нажатии назад
    override fun onBackPressed() {
        super.onBackPressed()
        this.finish()
    }



}
