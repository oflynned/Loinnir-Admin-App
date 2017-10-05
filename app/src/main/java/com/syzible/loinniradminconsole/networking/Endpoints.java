package com.syzible.loinniradminconsole.networking;

import android.os.Build;

/**
 * Created by ed on 07/09/2017.
 */

public class Endpoints {
    private static final int API_VERSION = 1;

    private static final String LOCAL_ENDPOINT = "http://10.0.2.2:3000";
    private static final String REMOTE_ENDPOINT = "https://loinnir.herokuapp.com";
    private static final String STEM_URL = isDebugMode() ? LOCAL_ENDPOINT : REMOTE_ENDPOINT;
    private static final String API_URL = STEM_URL + "/api/v" + API_VERSION;

    private static boolean isDebugMode() {
        return Build.FINGERPRINT.startsWith("generic")
                || Build.FINGERPRINT.startsWith("unknown")
                || Build.MODEL.contains("google_sdk")
                || Build.MODEL.contains("Emulator")
                || Build.MODEL.contains("Android SDK built for x86")
                || Build.MANUFACTURER.contains("Genymotion")
                || (Build.BRAND.startsWith("generic") && Build.DEVICE.startsWith("generic"))
                || "google_sdk".equals(Build.PRODUCT);
    }

    public static final String AUTHENTICATE = API_URL + "/admin/authenticate";

    public static final String GET_AREA_DATA = API_URL + "/admin/get-area-data";
    public static final String GET_USER_STATS = API_URL + "/admin/get-user-stats";
    public static final String GET_MESSAGE_STATS = API_URL + "/admin/get-message-stats";

    public static final String GET_ALL_USERS = API_URL + "/debug/get-all-users";
    public static final String GET_ALL_LOCALITY_MESSAGES = API_URL + "/debug/get-all-locality-conversations";
    public static final String GET_ALL_USER_MESSAGES = API_URL + "/debug/get-all-partner-conversations";

    public static final String GET_LOCALITY_MESSAGES_BY_LOCALITY = API_URL + "/admin/get-locality-chat-by-name";
    public static final String GET_LOCALITY_MESSAGES_LAST_24_HOURS = API_URL + "/admin/locality-messages-last-24-hours";

    public static final String BROADCAST_PUSH_NOTIFICATION = API_URL + "/admin/broadcast-push-notification";
    public static final String GET_ALL_PUSH_NOTIFICATIONS = API_URL + "/admin/get-past-push-notifications";
}
