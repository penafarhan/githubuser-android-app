package com.codeiva.githubuserapp.ui.settings

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.codeiva.githubuserapp.R
import kotlinx.android.synthetic.main.activity_settings.*
import kotlinx.coroutines.InternalCoroutinesApi

/**
 * @author farhan
 * created at at 8:56 on 22/09/2020.
 */
@InternalCoroutinesApi
class SettingsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        imageView_actionBack.setOnClickListener { onBackPressed() }

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.frameLayout_settings, SettingFragment())
            .commit()

        actionBar?.setDisplayHomeAsUpEnabled(true)
    }


}