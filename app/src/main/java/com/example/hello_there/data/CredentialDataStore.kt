package com.example.hello_there.data

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first

private const val DS_NAME = "credentials_pref"
val Context.credentialDataStore by preferencesDataStore(DS_NAME)

object CredentialPrefsKeys {
    // Stored as CSV of "email:pass" pairs separated by ;
    val TEACHER_LIST = stringPreferencesKey("teacher_list")
    val STUDENT_LIST = stringPreferencesKey("student_list")
}

class CredentialRepository(private val context: Context) {

    // Set to true to ALWAYS reset and recreate demo users on every app launch.
    private val forceDevReset = false

    private fun parseList(csv: String?): List<Pair<String, String>> =
        csv?.split(';')?.mapNotNull {
            val parts = it.split(':')
            if (parts.size == 2) parts[0] to parts[1] else null
        } ?: emptyList()

    private fun List<Pair<String, String>>.toCsv(): String =
        this.joinToString(";") { "${it.first}:${it.second}" }

    suspend fun ensureDefaultAccounts() {
        context.credentialDataStore.edit { prefs ->
            if (forceDevReset || !prefs.contains(CredentialPrefsKeys.TEACHER_LIST)) {
                prefs[CredentialPrefsKeys.TEACHER_LIST] = listOf(
                    "teacher1@example.com" to "pass123",
                    "teacher2@example.com" to "pass456"
                ).toCsv()
            }
            if (forceDevReset || !prefs.contains(CredentialPrefsKeys.STUDENT_LIST)) {
                prefs[CredentialPrefsKeys.STUDENT_LIST] = listOf(
                    "student1@example.com" to "stud111",
                    "student2@example.com" to "stud112",
                    "student3@example.com" to "stud113",
                    "student4@example.com" to "stud114",
                    "student5@example.com" to "stud115",
                    "student6@example.com" to "stud116",
                    "student7@example.com" to "stud117",
                    "student8@example.com" to "stud118",
                    "student9@example.com" to "stud119",
                    "student10@example.com" to "stud120"
                ).toCsv()
            }
        }
    }

    suspend fun validate(role: String, user: String, pass: String): Boolean {
        val prefs = context.credentialDataStore.data.first()
        val listCsv = when (role) {
            "teacher" -> prefs[CredentialPrefsKeys.TEACHER_LIST]
            "student" -> prefs[CredentialPrefsKeys.STUDENT_LIST]
            else -> null
        }
        return parseList(listCsv).any { it.first == user && it.second == pass }
    }

    suspend fun clearAll() {
        context.credentialDataStore.edit { it.clear() }
    }
}
