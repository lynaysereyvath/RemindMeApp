package com.lynaysereyvath.remindme.data.local

import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "app_datastore")

fun Context.readString(keyName: String): Flow<String> {
    val stringKey = stringPreferencesKey(keyName)
    val data = this.dataStore.data.map { preferences ->
        preferences[stringKey] ?: ""
    }
    return data
}

suspend fun Context.saveString(keyName: String, value: String) {
    val stringKey = stringPreferencesKey(keyName)
    this.dataStore.edit { preferences ->
        preferences[stringKey] = value
    }
}

suspend fun Context.addKey(id: Long) {
    val stringKey = stringPreferencesKey("ids")
    val type = object : TypeToken<ArrayList<Long>>() {}.type
    this.dataStore.edit { preferences ->
        val key = preferences[stringKey]
        Log.i("DataStore", "keys $key")
        val savedIds = if (key == null || key == "null") {
            arrayListOf()
        } else
            try {
                Gson().fromJson<ArrayList<Long>>(
                    key,
                    type
                )
            } catch (e: Exception) {
                Log.d("DataStore", e.message.toString())
                arrayListOf<Long>()
            }
        savedIds.add(id)
        preferences[stringKey] = Gson().toJson(savedIds)
    }
}

suspend fun Context.deleteKeys(ids: ArrayList<Long>) {
    val stringKey = stringPreferencesKey("ids")
    val type = object : TypeToken<ArrayList<Long>>() {}.type
    this.dataStore.edit { preferences ->
        val savedIds = try {
            Log.i("DataStore", "keys ${preferences[stringKey]}")
            Gson().fromJson<ArrayList<Long>>(preferences[stringKey], type)
        } catch (e: Exception) {
            Log.d("DataStore", e.message.toString())
            arrayListOf<Long>()
        }

        val newSavedIds = savedIds.filter { !ids.contains(it) }
        preferences[stringKey] = Gson().toJson(newSavedIds)
    }
}