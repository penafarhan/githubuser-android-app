package com.codeiva.githubuserapp.ui.settings

import android.content.SharedPreferences
import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.PreferenceManager
import androidx.preference.SwitchPreferenceCompat
import com.codeiva.githubuserapp.R
import com.codeiva.githubuserapp.service.AlarmReceiver
import kotlinx.coroutines.InternalCoroutinesApi

/**
 * @author farhan
 * created at at 10:23 on 22/09/2020.
 */
@InternalCoroutinesApi
class SettingFragment : PreferenceFragmentCompat(),
    SharedPreferences.OnSharedPreferenceChangeListener {
    private lateinit var reminderPreference: SwitchPreferenceCompat
    private lateinit var reminder: String
    private lateinit var alarmReceiver: AlarmReceiver

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.setting_preferences, rootKey)

        alarmReceiver = AlarmReceiver()

        init()
        setSummaries()
    }

    override fun onResume() {
        super.onResume()
        preferenceScreen.sharedPreferences.registerOnSharedPreferenceChangeListener(this)
    }

    override fun onPause() {
        super.onPause()
        preferenceScreen.sharedPreferences.unregisterOnSharedPreferenceChangeListener(this)
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences, key: String) {
        if (key == reminder) {
            reminderPreference.isChecked = sharedPreferences.getBoolean(reminder, false)
        }

        val state: Boolean =
            PreferenceManager.getDefaultSharedPreferences(context).getBoolean(reminder, false)

        setReminder(state)
    }

    private fun init() {
        reminder = resources.getString(R.string.reminder_key)
        reminderPreference =
            findPreference<SwitchPreferenceCompat>(reminder) as SwitchPreferenceCompat
    }

    private fun setReminder(state: Boolean) {
        if (state) {
            context?.let { alarmReceiver.setRepeatingAlarm(it) }
        } else {
            context?.let { alarmReceiver.cancelAlarm(it) }
        }
    }

    private fun setSummaries() {
        val sh = preferenceManager.sharedPreferences
        reminderPreference.isChecked = sh.getBoolean(reminder, false)
    }
}