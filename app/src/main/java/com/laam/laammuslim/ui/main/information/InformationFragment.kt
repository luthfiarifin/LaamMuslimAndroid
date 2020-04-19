package com.laam.laammuslim.ui.main.information

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.preference.EditTextPreference
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.laam.laammuslim.R
import com.laam.laammuslim.data.util.changeNavigation

class InformationFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preferences, rootKey)
        setHasOptionsMenu(true)

        setAyahFontSizeSummary()
    }

    private fun setAyahFontSizeSummary() {
        findPreference<EditTextPreference>("ayah_text_preference")?.apply {
            summary =
                "${sharedPreferences.getString("ayah_text_preference", "18")} Font Size"

            onPreferenceChangeListener =
                Preference.OnPreferenceChangeListener { _, o ->
                    summary = "${o.toString()} Font size"
                    true
                }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.about, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (item.itemId == R.id.action_about) {
            view?.changeNavigation(InformationFragmentDirections.actionNavigationInformationToAboutFragment())
            true
        } else {
            super.onOptionsItemSelected(item)
        }
    }
}
