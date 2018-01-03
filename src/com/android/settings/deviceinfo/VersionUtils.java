
package com.android.settings.deviceinfo;

import android.os.SystemProperties;

public class VersionUtils {
    public static String getColtVersion(){
        String buildType = SystemProperties.get("ro.colt.build.version","");
	return buildType.equals("") ? "" : buildType;
    }
}
