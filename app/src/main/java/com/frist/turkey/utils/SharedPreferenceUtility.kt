package com.frist.turkey.utils

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.util.Log

class SharedPreferenceUtility
private constructor(context: Context) {
     val PERSISTABLE_PREFERENCE_NAME = "com.frist.turkey"
    val TAG = SharedPreferenceUtility::class.java.simpleName

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences(PERSISTABLE_PREFERENCE_NAME, Context.MODE_PRIVATE)

    val editor: SharedPreferences.Editor = sharedPreferences.edit()

    companion object {

        @SuppressLint("StaticFieldLeak")
        private var instance: SharedPreferenceUtility? = null

        fun getInstance(ctx: Context): SharedPreferenceUtility {
            if (instance == null) {
                instance = SharedPreferenceUtility(ctx)
            }
            return instance!!
        }
    }


    var deviceToken: String
        get() = sharedPreferences["deviceToken"]!!
        set(value) = sharedPreferences.set("deviceToken", value)
    var currentUser: String
        get() = sharedPreferences["currentUser"]!!
        set(value) = sharedPreferences.set("currentUser", value)





    /**
     * puts a key value pair in shared prefs if doesn't exists, otherwise updates value on given [key]
     */
    operator fun SharedPreferences.set(key: String, value: Any?) {
        when (value) {
            is String? -> edit { it.putString(key, value) }
            is Int -> edit { it.putInt(key, value) }
            is Boolean -> edit { it.putBoolean(key, value) }
            is Float -> edit { it.putFloat(key, value) }
            is Long -> edit { it.putLong(key, value) }
            else -> Log.e(TAG, "Setting shared pref failed for key: $key and value: $value ")
        }
    }

    private inline fun SharedPreferences.edit(operation: (SharedPreferences.Editor) -> Unit) {
        val editor = this.edit()
        operation(editor)
        editor.apply()
    }

    /**
     * finds value on given key.
     * [T] is the type of value
     * @param defaultValue optional default value - will take null for strings, false for bool and -1 for numeric values if [defaultValue] is not specified
     */

    inline operator fun <reified T : Any> SharedPreferences.get(
        key: String,
        defaultValue: T? = null,
    ): T? {
        return when (T::class) {
            String::class -> getString(key, defaultValue as? String) as T?
            Int::class -> getInt(key, defaultValue as? Int ?: -1) as T?
            Boolean::class -> getBoolean(key, defaultValue as? Boolean ?: false) as T?
            Float::class -> getFloat(key, defaultValue as? Float ?: -1f) as T?
            Long::class -> getLong(key, defaultValue as? Long ?: -1) as T?
            else -> throw UnsupportedOperationException("Not yet implemented")
        }
    }

    fun deletePreferences() {
        editor.clear()
        editor.apply()
        editor.commit()
    }
}