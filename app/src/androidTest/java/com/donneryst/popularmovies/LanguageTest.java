package com.donneryst.popularmovies;

import android.test.suitebuilder.annotation.SmallTest;

import com.neovisionaries.i18n.LanguageCode;

import junit.framework.TestCase;

/**
 * Created by jhpx on 2015/11/23.
 */
public class LanguageTest extends TestCase {

    @SmallTest
    public void testLanguage() {

        // List all the language codes.
        for (LanguageCode code : LanguageCode.values()) {
            System.out.format("[%s] %s\n", code, code.getName());
        }


    }
}