package com.donneryst.popularmovies.constants;

import com.neovisionaries.i18n.LanguageCode;

/**
 * Created by jhpx on 2015/11/24.
 */
public class LanguageCodes {
    public static final CharSequence[] ALL_LANGUAGE_CODES;
    public static final CharSequence[] ALL_LANGUAGE_NAMES;

    static {
        ALL_LANGUAGE_CODES = new CharSequence[LanguageCode.values().length];
        ALL_LANGUAGE_NAMES = new CharSequence[LanguageCode.values().length];
        for (int i = 0; i < LanguageCode.values().length; i++) {
            ALL_LANGUAGE_CODES[i] = LanguageCode.values()[i].toString();
            ALL_LANGUAGE_NAMES[i] = LanguageCode.values()[i].getName();
        }
    }

}
