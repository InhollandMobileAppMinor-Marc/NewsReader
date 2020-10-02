package nl.bouwman.marc.news.ui.utils

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys

val Context.defaultEncryptedPreferences: SharedPreferences
    get() = EncryptedSharedPreferences.create(
        this.packageName + "_preferences",
        MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC),
        this,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )

fun SharedPreferences.edit(block: SharedPreferences.Editor.() -> Unit) = edit().apply(block).apply()