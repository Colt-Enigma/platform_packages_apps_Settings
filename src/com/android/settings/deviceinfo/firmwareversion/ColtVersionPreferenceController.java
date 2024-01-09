/*
 * Copyright (C) 2019 ColtOS Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.android.settings.deviceinfo.firmwareversion;

import android.content.Context;
import android.os.SystemProperties;
import android.text.TextUtils;

import androidx.annotation.VisibleForTesting;

import com.android.settings.R;
import com.android.settings.Utils;
import com.android.settings.core.BasePreferenceController;

public class ColtVersionPreferenceController extends BasePreferenceController {

    @VisibleForTesting
    static final String COLT_VERSION_PROPERTY = "ro.modversion";
    static final String COLT_RELEASETYPE_PROPERTY = "ro.colt.releasetype";
    static final String COLT_ZIPTYPE_PROPERTY = "ro.colt.ziptype";

    public ColtVersionPreferenceController(Context context, String preferenceKey) {
        super(context, preferenceKey);
    }

    @Override
    public int getAvailabilityStatus() {
        return !TextUtils.isEmpty(SystemProperties.get(COLT_VERSION_PROPERTY)) && !TextUtils.isEmpty(SystemProperties.get(COLT_RELEASETYPE_PROPERTY)) && !TextUtils.isEmpty(SystemProperties.get(COLT_ZIPTYPE_PROPERTY))
                ? AVAILABLE : UNSUPPORTED_ON_DEVICE;
    }

    @Override
    public CharSequence getSummary() {
        String coltVersion = SystemProperties.get(COLT_VERSION_PROPERTY);
        String coltReleaseType = SystemProperties.get(COLT_RELEASETYPE_PROPERTY);
        String coltZipType = SystemProperties.get(COLT_ZIPTYPE_PROPERTY);
        if (!coltVersion.isEmpty() && !coltReleaseType.isEmpty() && !coltZipType.isEmpty()) {
            return coltVersion + " | " + coltReleaseType + " | " + coltZipType;
        } else {
            return
                mContext.getString(R.string.device_info_default);
        }
    }
}
