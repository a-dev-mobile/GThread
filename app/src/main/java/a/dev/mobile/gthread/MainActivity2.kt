package a.dev.mobile.gthread

import a.dev.mobile.gthread.R.id
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity2 : AppCompatActivity() {
    companion object {
        private const val TAG = "== MainActivity2"
    }

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
            }
        else
            when (sp.getString("pref_theme", "0")) {
                "0" -> setTheme(R.style.AppTheme_Light_Blue)
                "1" -> setTheme(R.style.AppTheme_Light_Cyan)
                "2" -> setTheme(R.style.AppTheme_Light_Gray)
                "3" -> setTheme(R.style.AppTheme_Light_Green)
                "4" -> setTheme(R.style.AppTheme_Light_Purple)
                "5" -> setTheme(R.style.AppTheme_Light_Red)
            }



        setContentView(R.layout.activity_main2)

        val navView: BottomNavigationView = findViewById(R.id.nav_view)


        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

        if (savedInstanceState == null) {
            loadFragment(true)
        }
    }

    private val mOnNavigationItemSelectedListener =
        BottomNavigationView.OnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.navigation_home -> {
                    Log.i(TAG, " navigation_1")

                    loadFragment(true)
                    return@OnNavigationItemSelectedListener true
                }
                R.id.navigation_dashboard -> {
                    Log.i(TAG, " navigation_2")
                    loadFragment(false)
                    return@OnNavigationItemSelectedListener true
                }
                R.id.navigation_notifications -> {
                    Log.i(TAG, " navigation_3")

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
