/*
 * Copyright (C) 2019 The Potato Open Sauce Project
 * 2019 Wave-OS
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.android.settings.colt.fragments;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.os.ServiceManager;
import android.os.SystemProperties;
import android.os.UserHandle;
import android.provider.Settings;
import android.support.v7.preference.ListPreference;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceScreen;
import android.util.Log;

import com.android.internal.logging.nano.MetricsProto.MetricsEvent;

import com.android.settings.R;
import com.android.settings.SettingsPreferenceFragment;
import com.android.settings.Utils;
import com.android.settings.wrapper.OverlayManagerWrapper;
import com.android.settings.wrapper.OverlayManagerWrapper.OverlayInfo;

import net.margaritov.preference.colorpicker.ColorPickerPreference;

public class ThemeFragment extends SettingsPreferenceFragment
        implements Preference.OnPreferenceChangeListener {

    private static final String ACCENT_COLOR = "accent_color";
    private static final String ACCENT_COLOR_PROP = "persist.sys.theme.accentcolor";
    private static final String KEY_SYSUI_THEME = "systemui_theme";

    private OverlayManagerWrapper mOverlayService;
    private ColorPickerPreference mThemeColor;
    private ListPreference mSystemUiThemePref;

     @Override
     public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.theme_pref);
        mOverlayService = ServiceManager.getService(Context.OVERLAY_SERVICE) != null ? new OverlayManagerWrapper()
                : null;
        setupAccentPref();
        setupStylePref();
    }

     @Override
     public boolean onPreferenceChange(Preference preference, Object newValue) {
        if (preference == mThemeColor) {
            int color = (Integer) newValue;
            String hexColor = String.format("%08X", (0xFFFFFFFF & color));
            SystemProperties.set(ACCENT_COLOR_PROP, hexColor);
            mOverlayService.reloadAndroidAssets(UserHandle.USER_CURRENT);
            mOverlayService.reloadAssets("com.android.settings", UserHandle.USER_CURRENT);
            mOverlayService.reloadAssets("com.android.systemui", UserHandle.USER_CURRENT);
        } else if (preference == mSystemUiThemePref) {
            int value = Integer.parseInt((String) newValue);
            Settings.Secure.putInt(getContext().getContentResolver(), Settings.Secure.THEME_MODE, value);
            mSystemUiThemePref.setSummary(mSystemUiThemePref.getEntries()[value]);
        }
        return true;
    }

     private void setupAccentPref() {
        mThemeColor = (ColorPickerPreference) findPreference(ACCENT_COLOR);
        String colorVal = SystemProperties.get(ACCENT_COLOR_PROP, "-1");
        int color = "-1".equals(colorVal)
                ? Color.WHITE
                : Color.parseColor("#" + colorVal);
        mThemeColor.setNewPreviewColor(color);
        mThemeColor.setOnPreferenceChangeListener(this);
    }

     private void setupStylePref() {
        mSystemUiThemePref = (ListPreference) findPreference(KEY_SYSUI_THEME);
        int value = Settings.Secure.getInt(getContext().getContentResolver(), Settings.Secure.THEME_MODE, 0);
        int index = mSystemUiThemePref.findIndexOfValue(Integer.toString(value));
        mSystemUiThemePref.setValue(Integer.toString(value));
        mSystemUiThemePref.setSummary(mSystemUiThemePref.getEntries()[index]);
        mSystemUiThemePref.setOnPreferenceChangeListener(this);
    }

     @Override
     public int getMetricsCategory() {
        return MetricsEvent.COLT_METRICSLOGGER;
    }

     @Override
     public void onResume() {
        super.onResume();
    }

     @Override
     public void onPause() {
        super.onPause();
    }
}
