package com.syzible.loinniradminconsole.helpers;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

/**
 * Created by ed on 17/09/2017.
 */

public class EncodingUtils {
    public static String decodeText(String encodedText) {
        try {
            return URLDecoder.decode(encodedText, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return encodedText;
    }


}
