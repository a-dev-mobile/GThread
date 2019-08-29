

package a.dev.mobile.gthread


import android.content.Context
import android.content.SharedPreferences
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import java.io.FileOutputStream

class DbSQLiteHelper(private val context: MainActivity) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {


        private const val TAG = "== DbSQLiteHelper"
        const val DATABASE_NAME = "ThreadDB.db"
        const val DATABASE_VERSION = 1
    }

//==============================

    private val preferences: SharedPreferences =
        context.getSharedPreferences("${context.packageName}.database_version", Context.MODE_PRIVATE)

    private fun isInstalledDatabaseOld(): Boolean {
        val oldVersionDataBase = preferences.getInt(DATABASE_NAME, 0)

        Log.i(TAG, "oldVersionDataBase - $oldVersionDataBase")
        Log.i(TAG, "newVersionDataBase - $DATABASE_VERSION")


        return oldVersionDataBase < DATABASE_VERSION
    }

    private fun writeDataBaseVersionInPreferences() {
        preferences.edit().apply {
            putInt(DATABASE_NAME, DATABASE_VERSION)
            apply()
        }
    }

//==============================

    override fun onCreate(p0: SQLiteDatabase?) {
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
    }

    //==============================

    override fun getReadableDatabase(): SQLiteDatabase {

        installOrUpdateIfNecessary()
        Log.i(TAG, "==get DB")
        return super.getReadableDatabase()
    }

    override fun getWritableDatabase(): SQLiteDatabase {
        throw RuntimeException("The $DATABASE_NAME database is not writable.")
    }
    //==============================

    @Synchronized
    private fun installOrUpdateIfNecessary() {
        if (isInstalledDatabaseOld()) {

            Log.i(TAG, "==Deleted old DB")
            context.deleteDatabase(DATABASE_NAME)

            Log.i(TAG, "==Copy DB")
            installDatabaseFromAssets()


            Log.i(TAG, "==Write new version DB")
            writeDataBaseVersionInPreferences()
        }
    }

    private fun installDatabaseFromAssets() {

        val checkDB = context.openOrCreateDatabase(DATABASE_NAME, Context.MODE_PRIVATE, null)
        checkDB?.close()

        val inputStream = context.assets.open(DATABASE_NAME)

        try {
            val file = context.getDatabasePath(DATABASE_NAME)
            val outputStream = FileOutputStream(file)

            val buffer = ByteArray(1024)
            while (inputStream.read(buffer) > 0) {
                outputStream.write(buffer)
                Log.d(TAG, "==writing>>")
            }

            outputStream.flush()
            outputStream.close()
            inputStream.close()
            Log.d(TAG, "==completed..")
        } catch (exception: Throwable) {
            throw RuntimeException("The $DATABASE_NAME database couldn't be installed.", exception)
        }
    }
}

