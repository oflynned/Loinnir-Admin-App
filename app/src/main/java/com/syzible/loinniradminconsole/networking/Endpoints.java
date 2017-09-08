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
    private static final String API_URL = STEM_URL + "/api/v" + API_VERSION + "/admin";

    public static boolean isDebugMode() {
        return Build.FINGERPRINT.startsWith("generic")
                || Build.FINGERPRINT.startsWith("unknown")
                || Build.MODEL.contains("google_sdk")
                || Build.MODEL.contains("Emulator")
                || Build.MODEL.contains("Android SDK built for x86")
                || Build.MANUFACTURER.contains("Genymotion")
                || (Build.BRAND.startsWith("generic") && Build.DEVICE.startsWith("generic"))
                || "google_sdk".equals(Build.PRODUCT);
    }

    public static final String AUTHENTICATE = API_URL + "/authenticate";
    public static final String CLEAR_DUD_ACCOUNTS = API_URL + "/clear-dud-accounts";
    public static final String CLEAR_LOCALITY_CHATS = API_URL + "/clear-locality-chats";

    public static final String GET_LOCALITY_NAMES = API_URL + "/get-locality-names";
    public static final String GET_LOCALITY_MESSAGES_BY_LOCALITY = API_URL + "/get-locality-chat-by-name";
    public static final String GET_ALL_LOCALITY_MESSAGES = API_URL + "/get-all-locality-conversations";
    public static final String GET_LOCALITY_MESSAGES_LAST_24_HOURS = API_URL + "/locality-messages-last-24-hours";

    public static final String BROADCAST_PUSH_NOTIFICATION = API_URL + "/broadcast-push-notification";
    public static final String GET_ALL_PUSH_NOTIFICATIONS = API_URL + "/get-past-push-notification";
}
