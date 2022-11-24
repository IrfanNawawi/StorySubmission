package id.heycoding.storysubmission.utils

import android.content.Context
import android.content.SharedPreferences
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import id.heycoding.storysubmission.BuildConfig.PREF_NAME
import id.heycoding.storysubmission.data.remote.response.auth.AuthSession
import id.heycoding.storysubmission.utils.Const.Companion.NAME_KEY
import id.heycoding.storysubmission.utils.Const.Companion.STATE_KEY
import id.heycoding.storysubmission.utils.Const.Companion.TOKEN_KEY
import id.heycoding.storysubmission.utils.Const.Companion.USER_ID_KEY
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class Preferences @Inject constructor(@ApplicationContext context: Context) {

    val Context.datastore: DataStore<Preferences> by preferencesDataStore(name = PREF_NAME)
    private val dataStore = context.datastore

    suspend fun setUserLogin(auth: AuthSession) {
        dataStore.edit {
            it[NAME_KEY] = auth.name
            it[TOKEN_KEY] = auth.token
            it[USER_ID_KEY] = auth.userId
            it[STATE_KEY] = auth.isLogin
        }
    }

    suspend fun logout() {
        dataStore.edit {
            it[NAME_KEY] = ""
            it[TOKEN_KEY] = ""
            it[USER_ID_KEY] = ""
            it[STATE_KEY] = false
        }
    }

    fun getLoginData(): Flow<AuthSession> {
        return dataStore.data.map {
            AuthSession(
                it[NAME_KEY].toString(),
                it[TOKEN_KEY].toString(),
                it[USER_ID_KEY].toString(),
                it[STATE_KEY] ?: false
            )
        }
    }

    companion object {
        private val NAME_KEY = stringPreferencesKey("NAME")
        private val TOKEN_KEY = stringPreferencesKey("TOKEN")
        private val USER_ID_KEY = stringPreferencesKey("USER_ID")
        private val STATE_KEY = booleanPreferencesKey("STATE")
    }
}