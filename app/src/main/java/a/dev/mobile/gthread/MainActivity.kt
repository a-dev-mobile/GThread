package a.dev.mobile.gthread

import a.dev.mobile.gthread.FrgG.OnGSelected
import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity(), OnGSelected {
    override fun onGSelected(modelG: ModelG) {
        Log.i(TAG, "==click ${modelG.desc2}")

        /*  val detailsFragment =
            DogDetailsFragment.newInstance(dogModel)
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.root_layout, detailsFragment, "dogDetails")
            .addToBackStack(null)
            .commit()
    }*/
    }

    companion object {
        private const val TAG = "== MainActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        DbSQLiteHelper(this).readableDatabase
        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .add(R.id.root_layout, FrgG.newInstance(), "gList")
                .commit()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_scrolling, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        super.onOptionsItemSelected(item)
        when (item.itemId) {

            R.id.action_rating -> actionRate()



                   }
        return true
    }

    private fun actionRate() {
        try {
            startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("market://details?id=" + getPackageName())
                )
            )
        } catch (e: ActivityNotFoundException) {
            startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("https://play.google.com/store/apps/details?id=" + getPackageName())
                )
            )
        }
    }
}
