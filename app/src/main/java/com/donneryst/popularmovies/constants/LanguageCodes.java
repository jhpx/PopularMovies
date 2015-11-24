package com.donneryst.popularmovies.constants;

import com.neovisionaries.i18n.LanguageCode;

/**
 * Created by jhpx on 2015/11/24.
 */
public class LanguageCodes {
    public static final CharSequence[] ALL_LANGUAGE_CODES;
    public static final CharSequence[] ALL_LANGUAGE_NAMES;

    static {
        // LanguageCode.values()[0] is undefined, skip this
        ALL_LANGUAGE_CODES = new CharSequence[LanguageCode.values().length - 1];
        ALL_LANGUAGE_NAMES = new CharSequence[LanguageCode.values().length - 1];
        for (int i = 1; i < LanguageCode.values().length; i++) {
            ALL_LANGUAGE_CODES[i - 1] = LanguageCode.values()[i].toString();
            ALL_LANGUAGE_NAMES[i - 1] = LanguageCode.values()[i].getName();
        }
    }

}
