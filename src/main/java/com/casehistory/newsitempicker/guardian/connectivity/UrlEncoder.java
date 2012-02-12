package com.casehistory.newsitempicker.guardian.connectivity;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

abstract class UrlEncoder {

    static String encode(String toEncode) {
        String encoded;

        try {
            encoded = URLEncoder.encode(toEncode, "UTF-8");
        }
        catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }

        return encoded;
    }

    static String decode(String toDecode) {
        String decoded;

        try {
            decoded = URLDecoder.decode(toDecode, "UTF-8");
        }
        catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }

        return decoded;
    }
}
