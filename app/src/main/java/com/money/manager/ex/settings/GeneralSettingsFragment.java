/*
 * Copyright (C) 2012-2016 The Android Money Manager Ex Project Team
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 3
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.money.manager.ex.settings;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.money.manager.ex.Constants;
import com.money.manager.ex.home.MainActivity;
import com.money.manager.ex.servicelayer.AccountService;
import com.money.manager.ex.currency.CurrencyService;
import com.money.manager.ex.domainmodel.Account;
import com.money.manager.ex.MoneyManagerApplication;
import com.money.manager.ex.R;
import com.money.manager.ex.currency.list.CurrencyListActivity;
import com.money.manager.ex.datalayer.AccountRepository;

import java.util.List;

/**
 * Fragment that contains the general settings.
 */
public class GeneralSettingsFragment
    extends PreferenceFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.settings_general);

        initializeControls();
    }

    // Private

    private void initializeControls() {
        AppSettings settings = new AppSettings(getActivity());

        // Application Locale

        final ListPreference lstLocaleApp = (ListPreference) findPreference(getString(R.string.pref_locale));
        if (lstLocaleApp != null) {
            String summary = settings.getGeneralSettings().getApplicationLanguage();
            setSummaryListPreference(lstLocaleApp, summary, R.array.application_locale_values, R.array.application_locale_entries);
            lstLocaleApp.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                @Override
                public boolean onPreferenceChange(Preference preference, Object newValue) {
                    String language = String.valueOf(newValue);
                    setSummaryListPreference(preference, language, R.array.application_locale_values, R.array.application_locale_entries);

                    // apply the chaneg to the current activity immediately
                    getActivity().recreate();

                    // other activities apply on create.

                    return true;
                }
            });
        }

        // default status

        final ListPreference lstDefaultStatus = (ListPreference) findPreference(getString(PreferenceConstants.PREF_DEFAULT_STATUS));
        if (lstDefaultStatus != null) {
            setSummaryListPreference(lstDefaultStatus, lstDefaultStatus.getValue(), R.array.status_values, R.array.status_items);
            lstDefaultStatus.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                @Override
                public boolean onPreferenceChange(Preference preference, Object newValue) {
                    setSummaryListPreference(lstDefaultStatus, newValue.toString(), R.array.status_values, R.array.status_items);
                    return true;
                }
            });
        }

        //default payee

        final ListPreference lstDefaultPayee = (ListPreference) findPreference(getString(PreferenceConstants.PREF_DEFAULT_PAYEE));
        if (lstDefaultPayee != null) {
            setSummaryListPreference(lstDefaultPayee, lstDefaultPayee.getValue(), R.array.new_transaction_dialog_values, R.array.new_transaction_dialog_items);
            lstDefaultPayee.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                @Override
                public boolean onPreferenceChange(Preference preference, Object newValue) {
                    setSummaryListPreference(lstDefaultPayee, newValue.toString(), R.array.new_transaction_dialog_values, R.array.new_transaction_dialog_items);
                    return true;
                }
            });
        }

    }

    public void setSummaryListPreference(Preference preference, String value, int idArrayValues, int idArrayItems) {
        final String[] values = getResources().getStringArray(idArrayValues);
        final String[] items = getResources().getStringArray(idArrayItems);
        for (int i = 0; i < values.length; i++) {
            if (value.equals(values[i])) {
                preference.setSummary(items[i]);
            }
        }
    }

}
