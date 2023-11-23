package plant.testtree.camerademo.util

import android.content.Context
import android.content.SharedPreferences

class PrefUtill(context: Context) {
    init {
        sharedPreferences = context.getSharedPreferences(PREFERENCE, 0)
    }

    companion object {
        var PREFERENCE = "Translator"
        lateinit var sharedPreferences: SharedPreferences
        private var instance: PrefUtill? = null
        fun getInstance(ctx: Context): PrefUtill? {
            if (instance == null) {
                instance = PrefUtill(ctx)
            }
            return instance
        }

        fun putString(key: String?, `val`: String?) {
            sharedPreferences.edit().putString(key, `val`).apply()
        }

        fun getString(key: String?, defultValue: String?): String? {
            return sharedPreferences.getString(key, defultValue)
        }

        fun putInt(key: String?, `val`: Int?) {
            sharedPreferences.edit().putInt(key, `val`!!).apply()
        }

        fun putBoolean(key: String?, `val`: Boolean?) {
            sharedPreferences.edit().putBoolean(key, `val`!!).apply()
        }

        fun getInt(key: String?, defultValue: Int?): Int {
            return sharedPreferences.getInt(key, defultValue!!)
        }
    }
}