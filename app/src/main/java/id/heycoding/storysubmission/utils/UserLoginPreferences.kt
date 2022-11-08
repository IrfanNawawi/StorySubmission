package id.heycoding.storysubmission.utils

import android.content.Context
import android.content.SharedPreferences
import id.heycoding.storysubmission.BuildConfig.PREF_NAME
import id.heycoding.storysubmission.data.remote.response.auth.AuthSession
import id.heycoding.storysubmission.utils.Const.Companion.NAME_KEY
import id.heycoding.storysubmission.utils.Const.Companion.STATE_KEY
import id.heycoding.storysubmission.utils.Const.Companion.TOKEN_KEY
import id.heycoding.storysubmission.utils.Const.Companion.USER_ID_KEY

class UserLoginPreferences(context: Context) {
    private val pref: SharedPreferences =
        context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    private val editor = pref.edit()

    fun setUserLogin(auth: AuthSession) {
        editor.apply {
            putString(Const.NAME_KEY, auth.name)
            putString(Const.TOKEN_KEY, auth.token)
            putString(Const.USER_ID_KEY, auth.userId)
            putBoolean(Const.STATE_KEY, auth.isLogin)
            apply()
        }
    }

    fun getLoginData(): AuthSession {
        val loggedUser = AuthSession(
            pref.getString(NAME_KEY, "").toString(),
            pref.getString(TOKEN_KEY, "").toString(),
            pref.getString(USER_ID_KEY, "").toString(),
            pref.getBoolean(STATE_KEY, false)
        )
        return loggedUser
    }
}